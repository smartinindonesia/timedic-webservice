package com.servicetimedic.jwt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.text.ParseException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;
import com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlist;
import com.servicetimedic.jwt.domain.december.NumberOfRows;
import com.servicetimedic.jwt.fcm.push.notification.AndroidPushNotificationsServiceForCaregiver;
import com.servicetimedic.jwt.repository.CaregiversDbRepository;
import com.servicetimedic.jwt.repository.HomeCareCaregiverTrxListRepository;
import com.servicetimedic.jwt.repository.HomeCareSeriveTransactionsDbRepository;


@RestController
@RequestMapping(value = "/api")
public class HomeCareCaregiverTransactionList {
	
	public static final Logger logger = LoggerFactory.getLogger(HomeCareCaregiverTransactionList.class);

	
	@Autowired
	private HomeCareCaregiverTrxListRepository homeCareCaregiverTrxListRepository;
	
	@Autowired
	AndroidPushNotificationsServiceForCaregiver androidPushNotificationsServiceForCaregiver;
	
	@Autowired
	private CaregiversDbRepository caregiversDbRepository;
	
	@Autowired
	private HomeCareSeriveTransactionsDbRepository homecareSeriveTransactionsDbRepository;
	
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
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverTrxList", method = RequestMethod.POST)
	public ResponseEntity<Object> creatCaregiverTrxList(@RequestBody HomecareTransactionCaregiverlist homecareCaregiverTransactionList) throws JSONException 
	{
		HomecareServiceTransaction idTrx = new HomecareServiceTransaction();
		HomecareTransactionCaregiverlist tempData = new HomecareTransactionCaregiverlist();
		tempData = homecareCaregiverTransactionList;
		idTrx.setId(homecareCaregiverTransactionList.getIdTransaction());
		tempData.setIdServiceTransaction(idTrx);
		//System.out.println(homecareCaregiverTransactionList.getIdServiceTransaction());
		
	
		HomecareTransactionCaregiverlist data = homeCareCaregiverTrxListRepository.save(tempData);
		logger.info("Succesfully Create one Homecare Caregiver trx List");
		
		String noOrder = homecareSeriveTransactionsDbRepository.findOrderNoByIdTrx(homecareCaregiverTransactionList.getIdTransaction());
		
		if(homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(homecareCaregiverTransactionList.getIdTransaction()) != null){
			for(Long idCaregiver : homeCareCaregiverTrxListRepository.findIdCaregiverByIdTrx(homecareCaregiverTransactionList.getIdTransaction())){
				sendNotifToCaregiver(caregiversDbRepository.findFcmTokenCaregiverById(idCaregiver), "Order Masuk" ,"Ada orderan masuk dengan No Order "+noOrder);
			}
		}
		
		return new ResponseEntity<Object>(data, HttpStatus.CREATED);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/caregiverTrxList/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> createCaregiverTrxListById(@PathVariable Long id) 
	{
		HomecareTransactionCaregiverlist trx = homeCareCaregiverTrxListRepository.getOne(id);
		if (trx == null){
			logger.info("Homecare Caregiver trx List is null");
			return new ResponseEntity<Object>("Homecare Caregiver trx List is null",HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching Caregiver trx List by id "+id);
			return new ResponseEntity<Object>(trx, HttpStatus.OK);
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverTrxList/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCaregiverTrxListById(@PathVariable Long id) 
	{
		HomecareTransactionCaregiverlist trx = homeCareCaregiverTrxListRepository.getOne(id);
		if (trx == null){
			logger.info("Homecare Caregiver trx List is null");
			return new ResponseEntity<Object>("Homecare Caregiver trx List is null",HttpStatus.NO_CONTENT);
		}
		else{
			homeCareCaregiverTrxListRepository.delete(id);
			logger.info("Succesfully delete Caregiver trx List by id "+id);
			return new ResponseEntity<Object>("Succesfully delete Caregiver trx List by id "+id, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverTrxList/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCaregiverTrxListById(@PathVariable Long id, @RequestBody HomecareTransactionCaregiverlist homecareTransactionCaregiverlist) 
	{
		HomecareTransactionCaregiverlist trx = homeCareCaregiverTrxListRepository.getOne(id);
		HomecareServiceTransaction idTrx = new HomecareServiceTransaction();
		
		if(homecareTransactionCaregiverlist.getCaregiverName() != null){trx.setCaregiverName(homecareTransactionCaregiverlist.getCaregiverName());}
		if(homecareTransactionCaregiverlist.getRegisterNurseNumber() != null){trx.setRegisterNurseNumber(homecareTransactionCaregiverlist.getRegisterNurseNumber());}
		if(homecareTransactionCaregiverlist.getIdCaregiver() != null){trx.setIdCaregiver(homecareTransactionCaregiverlist.getIdCaregiver());}
		if(homecareTransactionCaregiverlist.getIdServiceTransaction() != null){trx.setIdServiceTransaction(homecareTransactionCaregiverlist.getIdServiceTransaction());}
		if(homecareTransactionCaregiverlist.getIdHomecareClinic() != null){trx.setIdHomecareClinic(homecareTransactionCaregiverlist.getIdHomecareClinic());}
		if(homecareTransactionCaregiverlist.getRateStatus() != null){
			System.out.println(homecareTransactionCaregiverlist.getRateStatus());
			trx.setRateStatus(homecareTransactionCaregiverlist.getRateStatus());}
		if(homecareTransactionCaregiverlist.getIdTransaction() != null ){
			trx.setIdTransaction(homecareTransactionCaregiverlist.getIdTransaction());
			idTrx.setId(homecareTransactionCaregiverlist.getIdTransaction());
			trx.setIdServiceTransaction(idTrx);
		}
		if(homecareTransactionCaregiverlist.getDay() != null){trx.setDay(homecareTransactionCaregiverlist.getDay());}
		if(homecareTransactionCaregiverlist.getTime() != null){trx.setTime(homecareTransactionCaregiverlist.getTime());}
		if(homecareTransactionCaregiverlist.getDate() != null){trx.setDate(homecareTransactionCaregiverlist.getDate());}
		
		if(trx!=null){
			homeCareCaregiverTrxListRepository.save(trx);
			return new ResponseEntity<Object>("Succesfully update Caregiver trx List by id "+id, HttpStatus.OK);
		}else{
			return new ResponseEntity<Object>("Failed update Caregiver trx List", HttpStatus.NOT_FOUND);
		}
	}
	
	private Pageable createPageRequest(int page, int size, String sort, String field) {
		Direction direction;
		if(sort.equals("ASC")){direction = Sort.Direction.ASC;}
		else{direction = Sort.Direction.DESC;}
		return new PageRequest(page, size, direction, field);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN' , 'CLINIC' , 'CAREGIVER' )")
	@RequestMapping(value = "/transactionOrderActiveWithPaginationByFieldByIdCaregiver", method = RequestMethod.GET)
	public List<Object> getAlltransactionOrderActiveWithPaginationByFieldByIdCaregiver(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("idCaregiver") String idCaregiver) throws ParseException {
	
		List<HomecareTransactionCaregiverlist> data = new ArrayList<>();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(idCaregiver!=null){
			long id = Long.parseLong(idCaregiver);
			data = homeCareCaregiverTrxListRepository.findOrderActiveHomecareByIdCaregiverWithPagination(id, createPageRequest(page, size, sort, sortField));
			rowCount = homeCareCaregiverTrxListRepository.findOrderActiveHomecareByIdCaregiverWithPaginationGetCount(id).size();
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
	
		List<HomecareTransactionCaregiverlist> data = new ArrayList<>();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(idCaregiver!=null){
			long id = Long.parseLong(idCaregiver);
			data = homeCareCaregiverTrxListRepository.findOrderHistoryHomecareByIdCaregiverWithPagination(id, createPageRequest(page, size, sort, sortField));
			rowCount = homeCareCaregiverTrxListRepository.findOrderHistoryHomecareByIdCaregiverWithPaginationGetCount(id).size();
		}
	
		logger.info("Fetching HomecareServiceTransaction with id Caregiver "+ idCaregiver +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		return list;
	}
	
}
