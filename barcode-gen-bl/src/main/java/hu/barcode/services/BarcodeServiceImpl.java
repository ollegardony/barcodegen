package hu.barcode.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;
import hu.barcode.enums.BarcodeOrderState;
import hu.barcode.enums.BarcodeType;

@Service
public class BarcodeServiceImpl extends ServiceBase implements BarcodeService {
	
	@Autowired
	private ServicesUtil serviceUtils;
	
	@Override
	@Transactional
	public List<BarcodePrice> getBarcodePrice(String userCode, int barcodeType) throws BarcodeFaultException {
		logger.debug("Entering getBarcodePrice");

		BarcodeUser user = dao.getBarcodeUser(userCode);

		if (user == null || !user.getAdminUser().equals(true)) {
			throwBarcodeFaultException("0001", "Hiba Ne admin user");
		}

		return dao.getBarcodePriceList(barcodeType);
	}

	@Override
	@Transactional
	public BarcodeUser saveBarcodeUser(String loginName, String emailAdress, String companyName, String companyAdress,
			String taxNumber) throws BarcodeFaultException {
		BarcodeUser user = new BarcodeUser();
		if (dao.getBarcodeUser(loginName) != null) {
			throwBarcodeFaultException("0002", "Hiba van már ilyeb user");
		}
		user.setLoginName(loginName);
		;
		user.setEmailAdress(emailAdress);
		user.setCompanyName(companyName);
		user.setCompanyAdress(companyAdress);
		user.setTaxNumber(taxNumber);
		user.setAdminUser(false);

		return dao.saveBarcodeUser(user);
	}

	@Override
	@Transactional
	public BarcodeOrderResponse saveBarcodeOrder(String loginName, int barcodeType, String datamatrixText,
			String gs1Code1, String gs1Code2, String gs1Code3, String gs1Code4, String gs1Code5) {

		BarcodeUser user = dao.getBarcodeUser(loginName);

		if (user == null) {
			throwBarcodeFaultException("0002", "Nincs ilyen felhasználó! Kérem regisztráljon!");
		}

		BarcodeOrder order = new BarcodeOrder();
		order.setUserId(user);

		if (BarcodeType.values()[barcodeType].equals(BarcodeType.DATAMATRIX)) {
			if (datamatrixText == null || datamatrixText.equals("")) {
				throwBarcodeFaultException("0002", "Nincs kitöltve a datamátrix szövege!");
			}
			order.setBarcodeType(BarcodeType.DATAMATRIX);
			order.setDatamatrixText(datamatrixText);
			order.setOrderPrice(serviceUtils.getDatamatrixPrice(datamatrixText.length()));
		} else {
			int gs1CodeNumber = 0;
			if (gs1Code1 == null || gs1Code1.equals("")) {
				throwBarcodeFaultException("0002", "Hiányos GS1 kód kitöltése");
			}
			gs1CodeNumber++;
			order.setGs1Code1(gs1Code1);
			if (gs1Code2 != null && !gs1Code1.equals("")) {
				gs1CodeNumber++;
				order.setGs1Code2(gs1Code2);
				if (gs1Code3 != null && !gs1Code3.equals("")) {
					gs1CodeNumber++;
					order.setGs1Code3(gs1Code3);
					if (gs1Code4 != null && !gs1Code4.equals("")) {
						gs1CodeNumber++;
						order.setGs1Code4(gs1Code4);
					}
					if (gs1Code5 != null && !gs1Code5.equals("")) {
						gs1CodeNumber++;
						order.setGs1Code5(gs1Code5);
					}
				}
			}
			order.setOrderPrice(serviceUtils.getGs1Price(gs1CodeNumber));
		}
		
		order.setOrderNumber(serviceUtils.getOrderNumber());
		order.setBarcodeState(BarcodeOrderState.SAVE);
		
		dao.saveOrder(order);

		BarcodeOrderResponse resp = new BarcodeOrderResponse();

		return resp;

	}
	
	public BarcodeOrderState getBarcode(String orderNumber){
		
		return BarcodeOrderState.SENT;
	}
}
