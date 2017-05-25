package hu.barcode.services;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *
 *          Customized exception
 *          Throw from services and handle in controller
 */
public class BarcodeFaultException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	protected final BarcodeFault fault;

	public BarcodeFaultException(BarcodeFault fault) {
		this.fault = fault;
	}

	public BarcodeFault getFault() {
		return fault;
	}
}
