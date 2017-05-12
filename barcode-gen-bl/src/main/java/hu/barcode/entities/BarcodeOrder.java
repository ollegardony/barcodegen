package hu.barcode.entities;

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

	@ManyToOne
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
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

	@Override
	protected Class<? extends BaseEntity> getEqualsClass() {
		return BarcodeUser.class;
	}
}
