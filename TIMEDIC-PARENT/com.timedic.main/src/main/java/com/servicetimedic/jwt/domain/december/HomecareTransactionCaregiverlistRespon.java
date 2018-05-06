package com.servicetimedic.jwt.domain.december;

public class HomecareTransactionCaregiverlistRespon {
	
	private Long idCaregiver;
	
	private String orderNumber;
	
	private String orderName;
	
	private Long idServiceTrx;
	
	private String selectedService;


	public HomecareTransactionCaregiverlistRespon(Long idCaregiver,
			String orderNumber, String orderName, Long idServiceTrx,
			String selectedService) {
		super();
		this.idCaregiver = idCaregiver;
		this.orderNumber = orderNumber;
		this.orderName = orderName;
		this.idServiceTrx = idServiceTrx;
		this.selectedService = selectedService;
	}

	public Long getIdCaregiver() {
		return idCaregiver;
	}

	public void setIdCaregiver(Long idCaregiver) {
		this.idCaregiver = idCaregiver;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderName() {
		return orderName;
	}
	
	public String getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(String selectedService) {
		this.selectedService = selectedService;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Long getIdServiceTrx() {
		return idServiceTrx;
	}

	public void setIdServiceTrx(Long idServiceTrx) {
		this.idServiceTrx = idServiceTrx;
	}
	
}
