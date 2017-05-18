package hu.barcode.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;

@Service
public class BarcodeServiceImpl extends ServiceBase implements BarcodeService {

	@Override
	@Transactional
	public List<BarcodePrice> getBarcodePrice(String userCode, int barcodeType) throws BarcodeFaultException {
		logger.debug("Entering getBarcodePrice");
		
		BarcodeUser user = dao.getBarcodeUser(userCode);
				
		if (user == null || !user.getAdmunUser().equals(true)) {
			throwBarcodeFaultException("0001", "Hiba Ne admin user");
		}

		return dao.getBarcodePriceList(barcodeType);
	}
	
	@Override
	@Transactional
	public BarcodeUser saveBarcodeUser(String loginName, String emailAdress, String companyName, String companyAdress, String taxNumber) throws BarcodeFaultException {
		BarcodeUser user = new BarcodeUser();
		if (dao.getBarcodeUser(loginName) != null){
			throwBarcodeFaultException("0002", "Hiba van már ilyeb user");
		}
		user.setLoginName(loginName);;
		user.setEmailAdress(emailAdress);
		user.setCompanyName(companyName);
		user.setCompanyAdress(companyAdress);
		user.setTaxNumber(taxNumber);
				
		return dao.saveBarcodeUser(user);
	}

}
