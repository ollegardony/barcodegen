package hu.barcode.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;
import hu.barcode.enums.BarcodeOrderState;
import hu.barcode.services.BarcodeFault;
import hu.barcode.services.BarcodeFaultException;
import hu.barcode.services.BarcodeOrderResponse;
import hu.barcode.services.BarcodeService;

@RestController
public class BarcodeControler {

	@Autowired
	private BarcodeService services;

	public void setServices(BarcodeService services) {
		this.services = services;
	}

	@RequestMapping("/barcode")
	public String barcodeMain() {
		return "This is a barcode generator services. ";
	}

	//@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/barcode/getPrice", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<BarcodePrice> getPrice(@RequestParam String userCode, @RequestParam int barcodeType) {

		return this.services.getBarcodePrice(userCode, barcodeType);
	}
	
	@RequestMapping(value = "/barcode/saveUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public BarcodeUser saveBarcodeUser(@RequestParam String loginName, @RequestParam String emailAdress, @RequestParam String companyName, @RequestParam String companyAdress,  @RequestParam String taxNumber) {

		return this.services.saveBarcodeUser(loginName, emailAdress, companyName, companyAdress, taxNumber );
	}

	@RequestMapping(value = "/barcode/saveOrder", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public BarcodeOrderResponse saveBarcodeOrder(@RequestParam String loginName, @RequestParam int barcodeType, @RequestParam String datamatrixText, @RequestParam String gs1Code1, @RequestParam String gs1Code2,  
				@RequestParam String gs1Code3, @RequestParam String gs1Code4, @RequestParam String gs1Code5) {

		return this.services.saveBarcodeOrder(loginName, barcodeType, datamatrixText, gs1Code1, gs1Code2, gs1Code3, gs1Code4, gs1Code5);
	}

	
	@RequestMapping(value = "/barcode/getBarcode", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public BarcodeOrderState getBarcode(@RequestParam String orderNumber) {

		return this.services.getBarcode(orderNumber);
	}
	
	@ExceptionHandler(BarcodeFaultException.class)
	public BarcodeFault barcodeError(BarcodeFaultException exp) {

		return exp.getFault();
	}
	
	@ExceptionHandler(Exception.class)
	public BarcodeFault handleAllException(Exception ex) {
		BarcodeFault fault = new BarcodeFault("1000", "Egyéb Hiba Forduljon a szolgáltatóhoz");
		return fault;
	}
}
