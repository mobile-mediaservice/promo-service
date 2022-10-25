package com.medias.spring.promo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.Column;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medias.spring.promo.entities.PromoActive;
import com.medias.spring.promo.entities.PromoArchive;
import com.medias.spring.promo.entities.PromoUsed;
import com.medias.spring.promo.entities.Promocode;
import com.medias.spring.promo.payloads.ActivatePromoPayload;
import com.medias.spring.promo.payloads.UsePromoPayload;
import com.medias.spring.promo.repositories.PromoActiveRepository;
import com.medias.spring.promo.repositories.PromoArchiveRepository;
import com.medias.spring.promo.repositories.PromoUsedRepository;
import com.medias.spring.promo.repositories.PromocodeRepository;


@RestController
public class ServiceController {
	
	@Autowired
    PromocodeRepository promocodeRepository;
	@Autowired
    PromoActiveRepository activePromoRepository;
	@Autowired
    PromoUsedRepository usedPromoRepository;
	@Autowired
    PromoArchiveRepository archivePromoRepository;
	
	private static final Timer timer = new Timer();
	
	ServiceController(){  
		/*
		  * Додати автоматичне архівування таблиці active_promo в archive_promo. 
		  * Переносим один до одного, по завершеному періоду. Раз на тиждень, в неділю.
		  * 
		  * Регламентне завдання виконується кожної неділі о 12:00
		*/
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		timer.schedule(new TimerTask(){
		     public void run(){
		    	 List<PromoActive> expired = activePromoRepository.findByDtToLessThan(new Date());
		    	 List<PromoArchive> archive = new ArrayList();
		    	 if(!expired.isEmpty()) {
		    		for(int i =0; i< expired.size(); i++) {
		    			archive.add(expired.get(i).toArchive());
		    		}
		    		archivePromoRepository.saveAll(archive);
		    		activePromoRepository.deleteAll(expired);
		    	 }
		         System.out.println("Протерміновані промо коди архівовано успішно!");
		     }
		},calendar.getTime(), 7*24*60*60*1000);
	}

	

	@GetMapping("/List") // в залежності від переданих параметрів (фільтрів) вертає перелік доступних клієнту промокодів.
	public List<PromoActive> List(
		@RequestParam(value = "gup_id", required = true) Long gupId, 
		@RequestParam(value = "wh_id", required = false) String whId, // id складу
		@RequestParam(value = "s_country", required = false) String sCountry // код країни відправки в форматі iso_2
	) {
		/*
		 * Метод вичитує дані з таблиці active_promo по фільтрам встановленим в запиті, та повертає список промокодів для даного користувача у форматі JSON. 
		 * Вичитуються тільки промокоди в діапазоні дат.
		 */
		Date now = new Date();
		return activePromoRepository.findByGupIdAndDtFromLessThanAndDtToGreaterThan(gupId,now,now);
	}
	
	@PostMapping("/AcivatePromo") // метод для активації промокодів клієнтом
	public PromoActive AcivatePromo(@RequestBody ActivatePromoPayload payload) throws RequestException {
		
		/*
		 * 
		 * Метод перевіряє наявність promo в таблиці promocodes по значенню поля promo_id(id). 
		 * Якщо нема - видає помилку, що промокод не знайдений.
		 * Перевіряє поле is_activated якщо значення true система інформує клієнта,що даний промокод уже було активовано. 
		 * Перевіряє період дії промокоду, якщо в період дії не попадає - видає помилку, 
		 * Перевіряє, щоб знайдений промокод мав або такий самий gup_id як з API або пустий - тоді ок, інакше видає помилку, що промокод не знайдений. 
		 * Якщо все пройшло успішно,і промкод знайдений по умовах - додає запис в таблицю active_promo 
		 * promo_id = promo_id (отриманий з API), 
		 * gup_id = gup_id (отриманий з API), 
		 * user - з API, 
		 * Кількості заповнюєм так: з qty заповнюєм qty_total, в qty_used пишем 0.
		 * решта даних копіює з таблиці promocodes;
		 * В таблиці promocodes, якщо gup_id проставлений, відмічає успішну активацію (змінює ознкаку is_activated=1).
		 */
		
		if(payload.promo_id == null) throw new RequestException("Параметер \"promo_id\" обовʼязковий");
		if(payload.gup_id == null) throw new RequestException("Параметер \"gup_id\" обовʼязковий");
		
		Date now = new Date();
		Promocode promocode = promocodeRepository.findById(payload.promo_id).orElseThrow(()->  new RequestException("Промокод не знайдено"));
		if(promocode.isActivated()) throw new RequestException("Даний промокод уже було активовано");
		if(now.before(promocode.getDtFrom())) throw new RequestException("Період дії промокоду ще не настав");
		if(now.after(promocode.getDtTo())) throw new RequestException("Період дії промокоду вже завершився");
		if(promocode.getGupId() != null &&  promocode.getGupId() != payload.gup_id) throw new RequestException("Промокод не знайдено");
		
		PromoActive activePromo = new PromoActive();
		activePromo.setPromoId(payload.promo_id);
		activePromo.setGupId(payload.gup_id);	
		activePromo.setDtFrom(promocode.getDtFrom());		
		activePromo.setDtTo(promocode.getDtTo());	
		activePromo.setQtyTotal(promocode.getQty());	
		activePromo.setUser(payload.user);	
		activePromoRepository.save(activePromo);
		
		if(promocode.getGupId() != null) {
			promocode.setActivated(true);
			promocodeRepository.save(promocode);
		}

		return activePromo;
	}
	
	@PostMapping("/UsePromo") // метод який відмічає використані клієнтом промокоди.
	public PromoUsed UsePromo(@RequestBody UsePromoPayload payload) throws Exception {
		/*
		 * Шукає промокод по клієнту в таблиці used_promo, перевіряє період. 
		 * Якщо не знаходить - вертає помилку. 
		 * Перевіряє, щоб qty_total було більше за qty_used
		 * Якщо qty_total = qty_used - вертаєм помилку з текстом “промокод використаний”. 
		 * Якщо знаходить, записує використання промокоду в таблицю used_promo. 
		 * Якщо знайдений промокод має qty_total відмінну від 9999, 
		 * то дописуєм qty_used+1. В поле date автоматично записуєм поточну дату.
		 * 
		 */
		if(payload.promo_id == null) throw new Exception("Параметер \"promo_id\" обовʼязковий");
		if(payload.gup_id == null) throw new Exception("Параметер \"gup_id\" обовʼязковий");
		if(payload.barcode == null) throw new Exception("Параметер \"barcode\" обовʼязковий");
		
		List<PromoActive> result = activePromoRepository.findByPromoIdAndGupId(payload.promo_id, payload.gup_id);
		if(result.isEmpty()) new Exception("Промокод не знайдено");
		PromoActive promocode = result.get(0);
		if(promocode.getQtyUsed() >= promocode.getQtyTotal()) new Exception("Промокод використаний");
		if(promocode.getQtyTotal() != 9999) {
			promocode.setQtyUsed(promocode.getQtyUsed() + 1);
			activePromoRepository.save(promocode);
		}
		PromoUsed used = new PromoUsed(payload.promo_id, payload.gup_id, new Date(), payload.barcode);
		usedPromoRepository.save(used);
		return used;
	}
	
	
}
