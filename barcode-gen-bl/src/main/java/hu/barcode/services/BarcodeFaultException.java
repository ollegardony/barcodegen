package hu.barcode.services;

public class BarcodeFaultException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	protected final BarcodeFault fault;

	public BarcodeFaultException(BarcodeFault fault) {		
		this.fault = fault;
	}

	public BarcodeFault getFault() {
		return fault;
	}
}
