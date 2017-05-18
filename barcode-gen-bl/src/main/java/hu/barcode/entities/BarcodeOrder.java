package hu.barcode.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;


import org.hibernate.validator.constraints.Range;

import hu.barcode.enums.BarcodeOrderState;
import hu.barcode.enums.BarcodeType;

@Entity
@Table(name = "barcode_order")
public class BarcodeOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	BarcodeUser userId;

	@Column(name = "order_number", length = 10, nullable = false)
	String orderNumber;

	@Column(name = "order_price", nullable = false)
	Double orderPrice;

	@Column(name = "barcode_type", nullable = false)
	@Range(min = 0, max = 1)
	BarcodeType barcodeType;

	@Column(name = "barcode_state", nullable = false)
	@Range(min = 0, max = 2)
	BarcodeOrderState barcodeState;

	@Column(name = "datamatrix_text", length = 255, nullable = true)
	String datamatrixText;

	@Column(name = "gs1_code1", length = 50, nullable = true)
	String gs1Code1;

	@Column(name = "gs1_code2", length = 50, nullable = true)
	String gs1Code2;

	@Column(name = "gs1_code3", length = 50, nullable = true)
	String gs1Code3;

	@Column(name = "gs1_code4", length = 50, nullable = true)
	String gs1Code4;

	@Column(name = "gs1_code5", length = 50, nullable = true)
	String gs1Code5;

	public BarcodeUser getUserId() {
		return userId;
	}

	public void setUserId(BarcodeUser userId) {
		this.userId = userId;
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

	public BarcodeType getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(BarcodeType barcodeType) {
		this.barcodeType = barcodeType;
	}

	public BarcodeOrderState getBarcodeState() {
		return barcodeState;
	}

	public void setBarcodeState(BarcodeOrderState barcodeState) {
		this.barcodeState = barcodeState;
	}

	public String getDatamatrixText() {
		return datamatrixText;
	}

	public void setDatamatrixText(String datamatrixText) {
		this.datamatrixText = datamatrixText;
	}

	public String getGs1Code1() {
		return gs1Code1;
	}

	public void setGs1Code1(String gs1Code1) {
		this.gs1Code1 = gs1Code1;
	}

	public String getGs1Code2() {
		return gs1Code2;
	}

	public void setGs1Code2(String gs1Code2) {
		this.gs1Code2 = gs1Code2;
	}

	public String getGs1Code3() {
		return gs1Code3;
	}

	public void setGs1Code3(String gs1Code3) {
		this.gs1Code3 = gs1Code3;
	}

	public String getGs1Code4() {
		return gs1Code4;
	}

	public void setGs1Code4(String gs1Code4) {
		this.gs1Code4 = gs1Code4;
	}

	public String getGs1Code5() {
		return gs1Code5;
	}

	public void setGs1Code5(String gs1Code5) {
		this.gs1Code5 = gs1Code5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	protected Class<? extends BaseEntity> getEqualsClass() {
		return BarcodeUser.class;
	}
}
