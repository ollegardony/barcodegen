package hu.barcode.dao;

import java.util.List;

import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 * 
 *          Interface Barcode database layer
 */
public interface BarcodeDAO {

	public List<BarcodePrice> getBarcodePriceList(int barcodeType, int codeNum);

	public BarcodeUser getBarcodeUser(String loginName);

	public BarcodeOrder getBarcodeOrder(String orderNumber);

	public BarcodeUser saveBarcodeUser(BarcodeUser user);

	public BarcodeOrder saveOrder(BarcodeOrder order);

}
