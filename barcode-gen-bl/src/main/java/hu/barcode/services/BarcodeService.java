package hu.barcode.services;

import java.util.List;

import hu.barcode.entities.BarcodePrice;

public interface BarcodeService {
	
	public List<BarcodePrice> getBarcodePrice(String userCode, int barcodeType) ;	
		
}
