package hu.barcode.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.barcode.entities.BarcodePrice;

@Service
public class BarcodeServiceImpl extends ServiceBase implements BarcodeService {

	@Override
	@Transactional
	public List<BarcodePrice> getBarcodePrice(String userCode, int priceType) throws BarcodeFaultException {
		logger.debug("Entering getBarcodePrice");

		if (userCode == null || !userCode.equals("sa")) {
			throwBarcodeFaultException("0002", "Hiba");
		}

		return dao.getBarcodePriceList(priceType);
	}

}
