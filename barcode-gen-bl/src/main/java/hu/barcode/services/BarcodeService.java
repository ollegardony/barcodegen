package hu.barcode.services;

import java.util.List;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;

public interface BarcodeService {
	
	public List<BarcodePrice> getBarcodePrice(String userCode, int barcodeType) ;	
	public BarcodeUser saveBarcodeUser(String loginName, String emailAdress, String companyName, String companyAdress,  String taxNumber);
		
}
