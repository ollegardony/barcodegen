package hu.barcode.dao;

import java.util.List;

import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;

public interface BarcodeDAO {

	public List<BarcodePrice> getBarcodePriceList(int barcodeType);
	public BarcodeUser getBarcodeUser(String loginName);
	public BarcodeOrder getBarcodeOrder(String orederNumber);	
	public BarcodeUser saveBarcodeUser(BarcodeUser user);
	public BarcodeOrder saveOrder(BarcodeOrder order);
	
}
