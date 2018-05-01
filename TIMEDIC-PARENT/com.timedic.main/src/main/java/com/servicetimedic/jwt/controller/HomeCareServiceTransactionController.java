package com.servicetimedic.jwt.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.HomecareAssessmentRecord;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;
import com.servicetimedic.jwt.domain.december.NumberOfRows;
import com.servicetimedic.jwt.domain.december.OrderRespons;
import com.servicetimedic.jwt.domain.december.SystemPaymentStatus;
import com.servicetimedic.jwt.fcm.push.notification.AndroidPushNotificationsServiceForCaregiver;
import com.servicetimedic.jwt.fcm.push.notification.AndroidPushNotificationsServiceForUser;
import com.servicetimedic.jwt.repository.CaregiversDbRepository;
import com.servicetimedic.jwt.repository.HomeCareAssessmentRecordDbRepository;
import com.servicetimedic.jwt.repository.HomeCareCaregiverTrxListRepository;
import com.servicetimedic.jwt.repository.HomeCarePatientDbRepository;
import com.servicetimedic.jwt.repository.HomeCareSeriveTransactionsDbRepository;
import com.servicetimedic.jwt.repository.UserDbRepository;

@Controller
@RestController
@RequestMapping(value = "/api")
public class HomeCareServiceTransactionController {
	
	private final SimpMessagingTemplate template;
	
	public static final Logger logger = LoggerFactory.getLogger(HomeCareServiceTransactionController.class);
	
	@Autowired
	AndroidPushNotificationsServiceForUser androidPushNotificationsServiceForUser;
	
	@Autowired
	AndroidPushNotificationsServiceForCaregiver androidPushNotificationsServiceForCaregiver;
	
	@Autowired
	private HomeCareSeriveTransactionsDbRepository homecareSeriveTransactionsDbRepository;
	
	@Autowired
	private HomeCarePatientDbRepository homeCarePatientDbRepository;
	
	@Autowired
	private HomeCareAssessmentRecordDbRepository homeCareAssessmentRecordDbRepository;
	
	@Autowired
	private UserDbRepository userDbRepository;
	
	@Autowired
	private CaregiversDbRepository caregiversDbRepository;
	
	@Autowired
	private HomeCareCaregiverTrxListRepository homeCareCaregiverTrxListRepository;
	
	private Pageable createPageRequest(int page, int size, String sort, String field) {
		Direction direction;
		if(sort.equals("ASC")){direction = Sort.Direction.ASC;}
		else{direction = Sort.Direction.DESC;}
		return new PageRequest(page, size, direction, field);
	}
	
	public String sendNotifToUser(String fcmTOken, String title ,String message) throws JSONException {
		
		String firebaseResponse = "";
		JSONObject body = new JSONObject();
		body.put("to", fcmTOken);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", message);
		notification.put("sound","default");
		
		body.put("notification", notification);
		
		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = androidPushNotificationsServiceForUser.send(request);
		CompletableFuture.allOf(pushNotification).join();
			
		try {
			firebaseResponse = pushNotification.get();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} 
		catch (ExecutionException e) {
			e.printStackTrace();
		}
		return firebaseResponse;
	}
	
	public String sendNotifToCaregiver(String fcmTOken, String title ,String message) throws JSONException {
		
		String firebaseResponse = "";
		JSONObject body = new JSONObject();
		body.put("to", fcmTOken);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", message);
		notification.put("sound","default");
		
		body.put("notification", notification);
		
		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = androidPushNotificationsServiceForCaregiver.send(request);
		CompletableFuture.allOf(pushNotification).join();
			
		try {
			firebaseResponse = pushNotification.get();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} 
		catch (ExecutionException e) {
			e.printStackTrace();
		}
		return firebaseResponse;
	}
	
	@Autowired
	HomeCareServiceTransactionController(SimpMessagingTemplate template){
        this.template = template;
    }
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CLINIC')")
	@RequestMapping(value = "/transactions/homecare", method = RequestMethod.GET)
	public List<HomecareServiceTransaction> getAllTransactionsHomecare() 
	{
		logger.info("Fetching All Transactions Homecare  Details");
		return homecareSeriveTransactionsDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER', 'CLINIC')")
	@RequestMapping(value = "/transactionWithPagination/homecare", method = RequestMethod.GET)
	public List<Object> getAllTransactionsHomecareWithPagination(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField) {
		
		List<HomecareServiceTransaction> data = homecareSeriveTransactionsDbRepository.findAllHomecareTrx(createPageRequest(page, size, sort, sortField));
		logger.info("Fetching All HomecareServiceTransaction Details with pagination order by " + sortField + " " + sort);
		
		NumberOfRows rows = new NumberOfRows();
		rows.setNumOfRows(homecareSeriveTransactionsDbRepository.findAll().size());
		List<Object> list = new ArrayList<Object>();
		list.add(data);
		list.add(rows);
		
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'CLINIC')")
	@RequestMapping(value = "/transactionWithPaginationByField", method = RequestMethod.GET)
	public List<Object> getAllTransactionWithPaginationByField(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("searchField") String searchField, @RequestParam("value") String value) throws ParseException {
		
		List<HomecareServiceTransaction> data = new ArrayList<>();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		
		if(searchField.equals("date")){
			String tanggal [] = value.split("-");
			int tg = Integer.parseInt(tanggal[2]); int bln = Integer.parseInt(tanggal[1]);int thn = Integer.parseInt(tanggal[0]);
			data = homecareSeriveTransactionsDbRepository.findHomecareTrxByDateInWithPagination(tg,bln,thn,createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findHomecareTrxByDateInWithPaginationGetCount(tg,bln,thn).size();
		}
		else if(searchField.equals("idUser")){
			long id = Long.parseLong(value);
			data = homecareSeriveTransactionsDbRepository.findHomecareTrxByIdUserWithPagination(id, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findHomecareTrxByIdUserWithPaginationGetCount(id).size();
		}
		else if(searchField.equals("dateOrderIn")){
			String tanggal [] = value.split("-");
			int tg = Integer.parseInt(tanggal[2]); int bln = Integer.parseInt(tanggal[1]);int thn = Integer.parseInt(tanggal[0]);
			data = homecareSeriveTransactionsDbRepository.findHomecareTrxByDateOrderInWithPagination(tg,bln,thn, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findHomecareTrxByDateOrderInWithPaginationGetCount(tg,bln,thn).size();
		}
		else if(searchField.equals("userCode")){
			data = homecareSeriveTransactionsDbRepository.findHomecareTrxByCodeUserWithPagination(value, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findHomecareTrxByCodeUserWithPaginationGetCount(value).size();
		}
		else if(searchField.equals("username")){
			data = homecareSeriveTransactionsDbRepository.findHomecareTrxByUsernameWithPagination(value, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findHomecareTrxByUsernameWithPaginationGetCount(value).size();
		}
		else if(searchField.equals("noOrder")){
			data = homecareSeriveTransactionsDbRepository.findByOrderNumber(value, createPageRequest(page, size, sort, sortField));
			rowCount = 1;//homecareSeriveTransactionsDbRepository.OrderNumberWithPaginationGetCount(value).size();
		}
		else if(searchField.equals("statusTrx")){
			data = homecareSeriveTransactionsDbRepository.findHomecareTrxBySistemTrxStatusWithPagination(Integer.parseInt(value), createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findHomecareTrxBySistemTrxStatusWithPaginationGetCount(Integer.parseInt(value)).size();
		}
		
		logger.info("Fetching HomecareServiceTransaction with "+ searchField +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN' , 'USER' , 'CLINIC' , 'CAREGIVER')")
	@RequestMapping(value = "/transactions/homecare/{id}", method = RequestMethod.GET)
	public ResponseEntity<HomecareServiceTransaction> getTransactionsHomecareById(@PathVariable Long id)
	{
		HomecareServiceTransaction homecareServiceTransaction = homecareSeriveTransactionsDbRepository.getOne(id);
		if (homecareServiceTransaction == null){
			logger.info("Transactions Homecare is null");
			return new ResponseEntity<HomecareServiceTransaction>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching Transactions Homecare by id "+id);
			return new ResponseEntity<HomecareServiceTransaction>(homecareServiceTransaction, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CLINIC')")
	@RequestMapping(value = "/transactions/homecare/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTransactionsHomecare(@PathVariable Long id) {
		HomecareServiceTransaction homecareServiceTransaction = homecareSeriveTransactionsDbRepository.getOne(id);
		
		if (homecareServiceTransaction != null) {
			//homecareSeriveTransactionsDbRepository.deleteById(id);
			homecareSeriveTransactionsDbRepository.delete(id);
			return new ResponseEntity<String>("Succesfully delete transactions homecare with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed transactions homecare", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'USER', 'CLINIC')")
	@RequestMapping(value = "/transactions/homecare/{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> updateTransactionsHomecare(@PathVariable(value = "id") Long id,@RequestBody HomecareServiceTransaction homecareServiceTrx) throws JSONException 
	{
		Date date = new Date();
		Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
		
		HomecareServiceTransaction respon = homecareSeriveTransactionsDbRepository.getOne(id);
		
		if(respon == null) {
			return new ResponseEntity<String>("Not Found transaction homecare", HttpStatus.NOT_FOUND);
	    }
		else{
			Long idUSer = respon.getHomecarePatientId().getIdAppUser().getId();
			String fcmToken = userDbRepository.findFcmTokenUserById(idUSer);
			
			if(homecareServiceTrx.getDate() != null){respon.setDate(homecareServiceTrx.getDate());}
			if(homecareServiceTrx.getDateOrderIn() != null){respon.setDateOrderIn(homecareServiceTrx.getDateOrderIn());}
			if(homecareServiceTrx.getExpiredTransactionTimeFixedPrice() !=null){respon.setExpiredTransactionTimeFixedPrice(homecareServiceTrx.getExpiredTransactionTimeFixedPrice());}
			if(homecareServiceTrx.getExpiredTransactionTimePrepaidPrice() !=null){respon.setExpiredTransactionTimePrepaidPrice(homecareServiceTrx.getExpiredTransactionTimePrepaidPrice());}
			if(homecareServiceTrx.getFixedPrice() != null){
				SystemPaymentStatus payment = new SystemPaymentStatus();
				payment.setId(2);
				respon.setFixedPrice(homecareServiceTrx.getFixedPrice());
				respon.setExpiredTransactionTimeFixedPrice(tomorrow);
				respon.setPaymentFixedPriceStatusId(payment);
			}
			if(homecareServiceTrx.getHomecareAssessmentRecordList() !=null){respon.setHomecareAssessmentRecordList(homecareServiceTrx.getHomecareAssessmentRecordList());}
			if(homecareServiceTrx.getHomecarePatientId() != null){respon.setHomecarePatientId(homecareServiceTrx.getHomecarePatientId());}
			if(homecareServiceTrx.getHomecareTransactionCaregiverlistList() != null){respon.setHomecareTransactionCaregiverlistList(homecareServiceTrx.getHomecareTransactionCaregiverlistList());}
			if(homecareServiceTrx.getLocationLatitude() != null){respon.setLocationLatitude(homecareServiceTrx.getLocationLatitude());}
			if(homecareServiceTrx.getLocationLongitude() != null){respon.setLocationLongitude(homecareServiceTrx.getLocationLongitude());}
			if(homecareServiceTrx.getPaymentMethodId() != null){respon.setPaymentMethodId(homecareServiceTrx.getPaymentMethodId());}
			if(homecareServiceTrx.getPredictionPrice() != null){respon.setPredictionPrice(homecareServiceTrx.getPredictionPrice());}
			if(homecareServiceTrx.getPrepaidPrice() != null){respon.setPrepaidPrice(homecareServiceTrx.getPrepaidPrice());}
			if(homecareServiceTrx.getReceiptPath() != null){respon.setReceiptPath(homecareServiceTrx.getReceiptPath());}
			if(homecareServiceTrx.getTransactionDescription() != null){respon.setTransactionDescription(homecareServiceTrx.getTransactionDescription());}
			if(homecareServiceTrx.getTransactionStatusId() != null){
				respon.setTransactionStatusId(homecareServiceTrx.getTransactionStatusId());
				if(homecareServiceTrx.getTransactionStatusId().getId() == 6){
					sendNotifToUser(fcmToken, "Status Transaksi", "Transaksi "+respon.getOrderNumber()+" anda telah selesai");
					if(homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(id) != null){
						for(Long idCaregiver : homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(id)){
							sendNotifToCaregiver(caregiversDbRepository.findFcmTokenCaregiverById(idCaregiver), "Status Transaksi", "Transaksi "+respon.getOrderNumber()+" anda telah selesai");
						}
					}
				}
				else if(homecareServiceTrx.getTransactionStatusId().getId() == 3 || homecareServiceTrx.getTransactionStatusId().getId() == 4){
					sendNotifToUser(fcmToken, "Status Transaksi", "Transaksi "+respon.getOrderNumber()+" anda telah dibatalkan");
					if(homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(id) != null){
						for(Long idCaregiver : homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(id)){
								sendNotifToCaregiver(caregiversDbRepository.findFcmTokenCaregiverById(idCaregiver), "Status Transaksi", "Transaksi "+respon.getOrderNumber()+" anda telah dibatalkan");
							}
					}
				}
				else if(homecareServiceTrx.getTransactionStatusId().getId() == 5){
					sendNotifToUser(fcmToken, "Status Transaksi", "Transaksi "+respon.getOrderNumber()+" anda telah expire");
					if(homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(id) != null){
						for(Long idCaregiver : homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(id)){
							sendNotifToCaregiver(caregiversDbRepository.findFcmTokenCaregiverById(idCaregiver), "Status Transaksi", "Transaksi "+respon.getOrderNumber()+" anda telah expire");
						}
					}
				}
			}
			if(homecareServiceTrx.getOrderNumber() != null){respon.setOrderNumber(homecareServiceTrx.getOrderNumber());}
			if(homecareServiceTrx.getPaymentPrepaidPriceStatusId() != null){
				respon.setPaymentPrepaidPriceStatusId(homecareServiceTrx.getPaymentPrepaidPriceStatusId());
				if(homecareServiceTrx.getPaymentPrepaidPriceStatusId().getId() == 1){
					sendNotifToUser(fcmToken, "Status Pembayaran Down Payment", "Pembayaran anda "+respon.getOrderNumber()+" telah terverifikasi/lunas");
				}
			}
			if(homecareServiceTrx.getPaymentFixedPriceStatusId() != null){
				respon.setPaymentFixedPriceStatusId(homecareServiceTrx.getPaymentFixedPriceStatusId());
				if(homecareServiceTrx.getPaymentFixedPriceStatusId().getId() == 1){
					sendNotifToUser(fcmToken, "Status Pembayaran Fixed Price", "Pembayaran anda "+respon.getOrderNumber()+" telah terverifikasi/lunas");
				}
			}
			if(homecareServiceTrx.getSumOfDays()!= null){respon.setSumOfDays(homecareServiceTrx.getSumOfDays());}
			if(homecareServiceTrx.getRefundPrice() != null){respon.setRefundPrice(homecareServiceTrx.getRefundPrice());}
			if(homecareServiceTrx.getDateTreatementStart() != null){respon.setDateTreatementStart(homecareServiceTrx.getDateTreatementStart());}
			if(homecareServiceTrx.getDateTreatementEnd() != null){respon.setDateTreatementEnd(homecareServiceTrx.getDateTreatementEnd());}
			if(homecareServiceTrx.getTransactionTypeId() !=null){respon.setTransactionTypeId(homecareServiceTrx.getTransactionTypeId());}
			
			homecareSeriveTransactionsDbRepository.save(respon);
			
			return new ResponseEntity<String>("Succesfully update transaction homecare with id "+id, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER', 'CLINIC')")
	@RequestMapping(value = "/sendToClient", method = RequestMethod.GET)
    public void test(@RequestParam("value") String value){
    	this.template.convertAndSend("/notification",  new SimpleDateFormat("HH:mm:ss").format(new Date())+" -- "+value);
    }
	
	public Date convertStringTimeToDate(String time) throws ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = time;
		Date date = formatter.parse(dateInString);		
		//java.sql.Date ddd = new java.sql.Date(date.getTime());
		//System.out.println(date);
		//System.out.println(ddd);
		return date;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER', 'CLINIC')")
	@RequestMapping(value = "/transactions/homecare/", method = RequestMethod.POST)
	public ResponseEntity<Object> createTransactionsHomecare(@RequestBody HomecareServiceTransaction homecareService) 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
		
		String generatedId = "TMDC-" ;//+ dateFormat2.format(date)+ homecareSeriveTransactionsDbRepository.getMaxId();
		
		SystemPaymentStatus payment = new SystemPaymentStatus();
		payment.setId(2);
		
		homecareService.setOrderNumber(generatedId);
		homecareService.setDateOrderIn(date);
		
		
		if(homecareService.getSelectedService().equals("Homecare 24 Jam") || homecareService.getSelectedService().equals("Homecare 12 Jam") || homecareService.getSelectedService().equals("Homecare 7 Jam")){
			homecareService.setExpiredTransactionTimePrepaidPrice(tomorrow);
			homecareService.setPaymentPrepaidPriceStatusId(payment);
		}
		else{
			homecareService.setExpiredTransactionTimeFixedPrice(tomorrow);
			homecareService.setPaymentFixedPriceStatusId(payment);
		}
		
		HomecareServiceTransaction process = homecareSeriveTransactionsDbRepository.save(homecareService);
		this.template.convertAndSend("/notification", process);
		
		HomecareServiceTransaction newID = new HomecareServiceTransaction();
		newID.setId(process.getId());
		List<HomecareAssessmentRecord> assessment = homecareService.getHomecareAssessmentRecordList();
		for(HomecareAssessmentRecord data : assessment)
		{
			data.setIdServiceTransaction(newID);
		}
		
		List<HomecareAssessmentRecord> data = homecareService.getHomecareAssessmentRecordList();
		
		for(HomecareAssessmentRecord x: data){
			homeCareAssessmentRecordDbRepository.save(x);
		}
		
		
		
		String message = "Thank You, Your homecare order has been recorded in timedic system with id " + process.getId();
		
		OrderRespons respons = new OrderRespons();
		
		respons.setMessage(message);
		respons.setDate(dateFormat.format(date));
		respons.setHttpStatus(HttpStatus.CREATED);
		
		
		return new ResponseEntity<Object>(respons , HttpStatus.CREATED);
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER', 'CLINIC')")
	@RequestMapping(value = "/orderactive/homecare/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HomecareServiceTransaction>> getOrderactiveHomecareById(@PathVariable Long id)
	{
		List<HomecareServiceTransaction> homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findOrderActiveHomecareByIdUser(id);
		if (homecareServiceTransaction == null){
			logger.info("orderactive Homecare is null");
			return new ResponseEntity<List<HomecareServiceTransaction>>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching orderactive Homecare by id user "+id);
			return new ResponseEntity<List<HomecareServiceTransaction>>(homecareServiceTransaction, HttpStatus.OK);
		}
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER', 'CLINIC')")
	@RequestMapping(value = "/history/homecare/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HomecareServiceTransaction>> getHistoryTrxHomecareById(@PathVariable Long id)
	{
		List<HomecareServiceTransaction> homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findHomecareTrxByIdUser(id);
		if (homecareServiceTransaction == null){
			logger.info("history Homecare is null");
			return new ResponseEntity<List<HomecareServiceTransaction>>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching history Homecare by id "+id);
			return new ResponseEntity<List<HomecareServiceTransaction>>(homecareServiceTransaction, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'CLINIC', 'USER' )")
	@RequestMapping(value = "/transactionOrderActiveWithPaginationByField", method = RequestMethod.GET)
	public List<Object> getAlltransactionOrderActiveWithPaginationByField(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("idUser") String idUser) throws ParseException {
	
		List<HomecareServiceTransaction> data = new ArrayList<>();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(idUser!=null){
			long id = Long.parseLong(idUser);
			data = homecareSeriveTransactionsDbRepository.findOrderActiveHomecareByIdUserWithPagination(id, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findOrderActiveHomecareByIdUserWithPaginationGetCount(id).size();
		}
	
		logger.info("Fetching HomecareServiceTransaction with id User "+ idUser +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'CLINIC', 'USER' )")
	@RequestMapping(value = "/transactionOrderHistoryWithPaginationByField", method = RequestMethod.GET)
	public List<Object> getAlltransactionOrderHistoryWithPaginationByField(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("idUser") String idUser) throws ParseException {
	
		List<HomecareServiceTransaction> data = new ArrayList<>();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(idUser!=null){
			long id = Long.parseLong(idUser);
			data = homecareSeriveTransactionsDbRepository.findOrderHistoryHomecareByIdUserWithPagination(id, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findOrderHistoryHomecareByIdUserWithPaginationGetCount(id).size();
		}
	
		logger.info("Fetching HomecareServiceTransaction with id User "+ idUser +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		return list;
	}
	
	//===================
	
	/*
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN' , 'CLINIC' , 'CAREGIVER' )")
	@RequestMapping(value = "/transactionOrderActiveWithPaginationByFieldByIdCaregiver", method = RequestMethod.GET)
	public List<Object> getAlltransactionOrderActiveWithPaginationByFieldByIdCaregiver(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("idCaregiver") String idCaregiver) throws ParseException {
	
		List<HomecareServiceTransaction> data = new ArrayList<>();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(idCaregiver!=null){
			long id = Long.parseLong(idCaregiver);
			data = homecareSeriveTransactionsDbRepository.findOrderActiveHomecareByIdCaregiverWithPagination(id, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findOrderActiveHomecareByIdCaregiverWithPaginationGetCount(id).size();
		}
	
		logger.info("Fetching HomecareServiceTransaction with id Caregiver "+ idCaregiver +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN' , 'CLINIC' , 'CAREGIVER' )")
	@RequestMapping(value = "/transactionOrderHistoryWithPaginationByFieldByIdCaregiver", method = RequestMethod.GET)
	public List<Object> getAlltransactionOrderHistoryWithPaginationByFieldByIdCaregiver(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("idCaregiver") String idCaregiver) throws ParseException {
	
		List<HomecareServiceTransaction> data = new ArrayList<>();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(idCaregiver!=null){
			long id = Long.parseLong(idCaregiver);
			data = homecareSeriveTransactionsDbRepository.findOrderHistoryHomecareByIdCaregiverWithPagination(id, createPageRequest(page, size, sort, sortField));
			rowCount = homecareSeriveTransactionsDbRepository.findOrderHistoryHomecareByIdCaregiverWithPaginationGetCount(id).size();
		}
	
		logger.info("Fetching HomecareServiceTransaction with id Caregiver "+ idCaregiver +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		return list;
	}
	*/
	

}
