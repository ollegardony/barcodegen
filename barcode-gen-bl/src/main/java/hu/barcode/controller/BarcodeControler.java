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
import hu.barcode.services.BarcodeFault;
import hu.barcode.services.BarcodeFaultException;
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

	@RequestMapping(value = "/barcode/getPrice", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<BarcodePrice> getPrice(@RequestParam String userCode, @RequestParam int priceType) {

		return this.services.getBarcodePrice(userCode, priceType);
	}

	@ExceptionHandler(BarcodeFaultException.class)
	public BarcodeFault barcodeError(BarcodeFaultException exp) {

		return exp.getFault();
	}
}
