package hu.barcode.services;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import hu.barcode.dao.BarcodeDAO;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *
 *          Base Services for Barcode generates Connect to database layer Handle
 *          exception, Handle error messages define default language for
 *          barcode.propeties
 */

@Service
@PropertySource("classpath:barcode.properties")
public abstract class ServiceBase {

	protected static final Logger logger = Logger.getLogger(ServiceBase.class);

	// Add database layer
	@Autowired
	protected BarcodeDAO dao;

	// handle message text User, error debug
	@Autowired
	private MessageSource messageSource;

	// bean for get properties value
	@Autowired
	private Environment env;

	public void setDao(BarcodeDAO dao) {
		this.dao = dao;
	}

	// Customize exception
	public void throwBarcodeFaultException(String errorCode, Object errorParameters[]) throws BarcodeFaultException {
		String errorMessage = messageSource.getMessage(errorCode, errorParameters, new Locale(env.getProperty("language")));
		BarcodeFault error = new BarcodeFault(errorCode, errorMessage);
		BarcodeFaultException ex = new BarcodeFaultException(error);
		logger.error("Error Code: " + errorCode + "  Error message: " + errorMessage);
		throw ex;
	}

	// Add text for debug log.
	public void addDebugLog(String debugCode, Object debugParameters[]) {
		logger.debug(messageSource.getMessage(debugCode, debugParameters, new Locale(env.getProperty("language"))));
	}
}
