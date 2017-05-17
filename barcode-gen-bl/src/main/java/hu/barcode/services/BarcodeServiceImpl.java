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
				
		if (user == null || !userCode.equals("sa")) {
			throwBarcodeFaultException("0002", "Hiba");
		}

		return dao.getBarcodePriceList(barcodeType);
	}
	


}
