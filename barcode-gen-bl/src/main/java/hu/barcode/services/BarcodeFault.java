package hu.barcode.services;

public class BarcodeFault {
	
	private String errorCode;
	private String errorMessage;
	
	public BarcodeFault(String errCode, String errMsg){
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
