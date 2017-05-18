package hu.barcode.services;

import java.util.List;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;
import hu.barcode.enums.BarcodeOrderState;

public interface BarcodeService {
	
	public List<BarcodePrice> getBarcodePrice(String userCode, int barcodeType) ;	
	public BarcodeUser saveBarcodeUser(String loginName, String emailAdress, String companyName, String companyAdress,  String taxNumber);
	public BarcodeOrderResponse saveBarcodeOrder(String loginName, int barcodeType, String datamatrixText, String gs1Code1, String gs1Code2, String gs1Code3, String gs1Code4, String gs1Code5);
	public BarcodeOrderState getBarcode(String orderNumber);
}
