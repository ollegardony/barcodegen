package hu.barcode.services;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 * 
 *          Give back customize error to user
 */
public class BarcodeFault {

	private String errorCode;
	private String errorMessage;

	public BarcodeFault(String errCode, String errMsg) {
		this.errorCode = errCode;
		this.errorMessage = errMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
