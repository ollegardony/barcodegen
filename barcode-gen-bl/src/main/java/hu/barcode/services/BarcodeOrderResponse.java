package hu.barcode.services;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *
 *          Response data structure for ordering
 *          Services give back order data (json format)
 */
public class BarcodeOrderResponse {

	String userCode;
	String orderNumber;
	Double orderPrice;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
}
