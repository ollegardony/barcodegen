package hu.barcode.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "barcode_user")
public class BarcodeUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "login_name", length = 10, nullable = false)
	String loginName;
	
	@Column(name = "company_name", length = 100, nullable = false)
	String companyName;
	
	@Column(name = "company_adress", length = 255, nullable = true)
	String companyAdress;
	
	@Column(name = "email_adress", length = 50, nullable = false)
	@Email
	String emailAdress;
	
	@Column(name = "tax_number", length = 20,nullable = true)
	String taxNumber;
	
	@Column(name = "admin_user", nullable = false, columnDefinition="int default 0")	
	Boolean adminUser;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAdress() {
		return companyAdress;
	}

	public void setCompanyAdress(String companyAdress) {
		this.companyAdress = companyAdress;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public Boolean getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(Boolean admunUser) {
		this.adminUser = admunUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	protected Class<? extends BaseEntity> getEqualsClass() {
		return BarcodeUser.class;
	}
}
