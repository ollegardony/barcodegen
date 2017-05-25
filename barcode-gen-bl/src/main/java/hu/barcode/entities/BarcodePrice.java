package hu.barcode.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

import hu.barcode.enums.BarcodeType;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *
 *          Store price list
 *          Auto insert when deployed api (insert script.)
 */
@Entity
@Table(name = "barcode_price")
public class BarcodePrice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "barcode_type", nullable = false)
	@Range(min = 0, max = 1)
	private BarcodeType barcodeType;

	@Column(name = "char_from", nullable = true)
	private Integer charFrom;

	@Column(name = "char_to", nullable = true)
	private Integer charTo;

	@Column(name = "code_number", nullable = true)
	private Integer codeNumber;

	@Column(name = "price", nullable = true)
	private Double price;

	public BarcodeType getBarcodeType() {
		return barcodeType;
	}

	public void setBarcodeType(BarcodeType barcodeType) {
		this.barcodeType = barcodeType;
	}

	public Integer getCharFrom() {
		return charFrom;
	}

	public void setCharFrom(Integer charFrom) {
		this.charFrom = charFrom;
	}

	public Integer getCharTo() {
		return charTo;
	}

	public void setCharTo(Integer charTo) {
		this.charTo = charTo;
	}

	public Integer getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(Integer codeNumber) {
		this.codeNumber = codeNumber;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;

	}

	@Override
	protected Class<? extends BaseEntity> getEqualsClass() {
		return BarcodePrice.class;
	}

}
