package hu.barcode.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.barcode.dao.BarcodeDAO;

@Service
public abstract class ServiceBase {

	protected static final Logger logger = Logger.getLogger(ServiceBase.class);

	@Autowired
	protected BarcodeDAO dao;

	public void setDao(BarcodeDAO dao) {
		this.dao = dao;
	}

	public void throwBarcodeFaultException(String errorCode, String errorMessage) throws BarcodeFaultException {
		
		BarcodeFault error = new BarcodeFault(errorCode, errorMessage);
		BarcodeFaultException ex = new BarcodeFaultException(error);
		logger.error("Error Code: " + errorCode + "  Error message: " + errorMessage);
		throw ex;
	}
}
