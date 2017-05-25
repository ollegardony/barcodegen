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

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 * 
 * @ Service Layer
 * 
 */
@Service
public class BarcodeServiceImpl extends ServiceBase implements BarcodeService {

	@Autowired
	private ServicesUtil serviceUtils;

	// This method gives to user price list. (Only registration user!)
	// Database table: barcode_price (Datamatrix, Gs1 or both)
	@Override
	@Transactional
	public List<BarcodePrice> getBarcodePrice(String userCode, int barcodeType) throws BarcodeFaultException {
		addDebugLog("debug.getBarcodePrice.Enter", null);

		if (userCode == null || userCode.isEmpty()) {
			throwBarcodeFaultException("error.isEmpty.LoginName", null);
		}
		BarcodeUser user = dao.getBarcodeUser(userCode);

		if (user == null) {
			throwBarcodeFaultException("error.NotFound.User", new Object[] { userCode });
		}

		addDebugLog("debug.getBarcodePrice.User", new Object[] { user.getId().toString() });
		List<BarcodePrice> l = dao.getBarcodePriceList(barcodeType, 0);
		addDebugLog("debug.getBarcodePrice.ListCount", new Object[] { String.valueOf(l.size()) });

		addDebugLog("debug.getBarcodePrice.Return", null);
		return l;
	}

	// User registration save new user
	// Database table: barcode_user
	@Override
	@Transactional
	public BarcodeUser saveBarcodeUser(String loginName, String emailAdress, String companyName, String companyAdress, String taxNumber) throws BarcodeFaultException {
		addDebugLog("debug.saveBarcodeUser.Enter", null);

		if (loginName == null || loginName.isEmpty()) {
			throwBarcodeFaultException("error.isEmpty.LoginName", null);
		}

		if (dao.getBarcodeUser(loginName) != null) {
			throwBarcodeFaultException("error.Found.User", new Object[] { loginName });
		}

		BarcodeUser user = new BarcodeUser();
		user.setLoginName(loginName);

		if (emailAdress == null || emailAdress.isEmpty()) {
			throwBarcodeFaultException("error.isEmpty.EmailAdress", null);
		}

		if (!serviceUtils.emailValidation(emailAdress)) {
			throwBarcodeFaultException("error.EmailAdress", new Object[] { emailAdress });
		}
		user.setEmailAdress(emailAdress);

		if (companyName == null || companyName.isEmpty()) {
			throwBarcodeFaultException("error.isEmpty.CompanyName", null);
		}

		user.setCompanyName(companyName);
		user.setCompanyAdress(companyAdress);
		user.setTaxNumber(taxNumber);
		user.setAdminUser(false);

		addDebugLog("debug.saveBarcodeUser.BeginSave", new Object[] { loginName });
		user = dao.saveBarcodeUser(user);
		addDebugLog("debug.saveBarcodeUser.EndSave", new Object[] { user.getId().toString() });
		addDebugLog("debug.saveBarcodeUser.Return", null);
		return user;
	}

	// Save new barcode order. (Only registration user!), Calculate price and return price and order number
	// Database table: barcode_order
	@Override
	@Transactional
	public BarcodeOrderResponse saveBarcodeOrder(String loginName, int barcodeType, String datamatrixText, String gs1Code1, String gs1Code2, String gs1Code3, String gs1Code4, String gs1Code5) {
		addDebugLog("debug.saveBarcodeOrder.Enter", null);

		if (loginName == null || loginName.isEmpty()) {
			throwBarcodeFaultException("error.isEmpty.LoginName", null);
		}

		addDebugLog("debug.saveBarcodeOrder.getUserbegin", new Object[] { loginName });
		BarcodeUser user = dao.getBarcodeUser(loginName);

		if (user == null) {
			throwBarcodeFaultException("error.NotFound.User", new Object[] { loginName });
		}

		addDebugLog("debug.saveBarcodeOrder.getUserEnd", new Object[] { user.getId().toString() });
		BarcodeOrder order = new BarcodeOrder();
		order.setUserId(user);

		if (BarcodeType.values()[barcodeType].equals(BarcodeType.DATAMATRIX)) {
			if (datamatrixText == null || datamatrixText.equals("")) {
				throwBarcodeFaultException("error.isEmpty.DatamatrixText", null);
			}
			addDebugLog("debug.saveBarcodeOrder.setDatamatrixText", new Object[] { datamatrixText });
			order.setBarcodeType(BarcodeType.DATAMATRIX);
			order.setDatamatrixText(datamatrixText);
			addDebugLog("debug.saveBarcodeOrder.calcDatamatrix.pricebegin", new Object[] { String.valueOf(datamatrixText.length()) });
			order.setOrderPrice(serviceUtils.getDatamatrixPrice(dao.getBarcodePriceList(0, 0), datamatrixText.length()));
			addDebugLog("debug.saveBarcodeOrder.calcDatamatrix.priceEnd", new Object[] { order.getOrderPrice().toString() });
		} else {
			int gs1CodeNumber = 0;
			if (gs1Code1 == null || gs1Code1.equals("")) {
				throwBarcodeFaultException("error.isEmpty.Gs1Text", null);
			}
			gs1CodeNumber++;
			order.setGs1Code1(gs1Code1);
			addDebugLog("debug.saveBarcodeOrder.set.gs1", new Object[] { gs1Code1 });
			order.setBarcodeType(BarcodeType.GS1);
			if (gs1Code2 != null && !gs1Code1.equals("")) {
				gs1CodeNumber++;
				order.setGs1Code2(gs1Code2);
				addDebugLog("debug.saveBarcodeOrder.set.gs2", new Object[] { gs1Code2 });
				if (gs1Code3 != null && !gs1Code3.equals("")) {
					gs1CodeNumber++;
					order.setGs1Code3(gs1Code3);
					addDebugLog("debug.saveBarcodeOrder.set.gs3", new Object[] { gs1Code3 });
					if (gs1Code4 != null && !gs1Code4.equals("")) {
						gs1CodeNumber++;
						order.setGs1Code4(gs1Code4);
						addDebugLog("debug.saveBarcodeOrder.set.gs4", new Object[] { gs1Code4 });
					}
					if (gs1Code5 != null && !gs1Code5.equals("")) {
						gs1CodeNumber++;
						order.setGs1Code5(gs1Code5);
						addDebugLog("debug.saveBarcodeOrder.set.gs5", new Object[] { gs1Code5 });
					}
				}
			}
			addDebugLog("debug.saveBarcodeOrder.calcGs1.priceBegin", new Object[] { gs1CodeNumber });
			order.setOrderPrice(serviceUtils.getGs1Price(dao.getBarcodePriceList(1, gs1CodeNumber), gs1CodeNumber));
			addDebugLog("debug.saveBarcodeOrder.calcGs1.priceEnd", new Object[] { gs1CodeNumber, order.getOrderPrice().toString() });
		}

		addDebugLog("debug.saveBarcodeOrder.save.orderBegin", null);
		order.setOrderNumber("");
		order.setBarcodeState(BarcodeOrderState.SAVE);
		order = dao.saveOrder(order);
		addDebugLog("debug.saveBarcodeOrder.save.orderEnd", new Object[] { order.getId().toString(), order.getOrderNumber() });

		BarcodeOrderResponse resp = new BarcodeOrderResponse();
		resp.setOrderNumber(order.getOrderNumber());
		resp.setOrderPrice(order.getOrderPrice());
		resp.setUserCode(user.getLoginName());

		addDebugLog("debug.saveBarcodeOrder.Return", null);
		return resp;

	}

	// Get order, generate barcode, send email
	@Override
	@Transactional
	public BarcodeOrderState getBarcode(String orderNumber) throws BarcodeFaultException {
		addDebugLog("debug.getBarcode.Enter", null);

		if (orderNumber == null || orderNumber.isEmpty()) {
			throwBarcodeFaultException("error.isEmpty.OrderNumber", null);
		}

		addDebugLog("debug.getBarcode.GetOrderBegin", new Object[] { orderNumber });
		BarcodeOrder order = dao.getBarcodeOrder(orderNumber);

		if (order == null) {
			throwBarcodeFaultException("error.orderNumber", new Object[] { orderNumber });
		}
		addDebugLog("debug.getBarcode.GetOrderEnd", new Object[] { order.getId().toString() });

		if (order.getBarcodeState().equals(BarcodeOrderState.SENT)) {
			throwBarcodeFaultException("error.order.sent", new Object[] { orderNumber });
		}

		String filePath = "";
		if (order.getBarcodeType().equals(BarcodeType.DATAMATRIX)) {

			try {
				addDebugLog("debug.getBarcode.GenerateDatamatrixBegin", new Object[] { order.getId().toString(), order.getDatamatrixText() });
				filePath = serviceUtils.generateDatamatrix(order.getDatamatrixText(), order.getId());
				addDebugLog("debug.getBarcode.GenerateDatamatrixEnd", new Object[] { filePath });
			} catch (Exception ex) {
				throwBarcodeFaultException("error.genarateBarcode", new Object[] { orderNumber });
			}

		} else {
			try {
				StringBuilder gs1Code = new StringBuilder();
				gs1Code.append(serviceUtils.getGS1Code(order.getGs1Code1()));

				if (order.getGs1Code2() != null && !order.getGs1Code2().isEmpty()) {
					gs1Code.append(serviceUtils.FNC1);
					gs1Code.append(serviceUtils.getGS1Code(order.getGs1Code2()));
					if (order.getGs1Code3() != null && !order.getGs1Code3().isEmpty()) {
						gs1Code.append(serviceUtils.FNC1);
						gs1Code.append(serviceUtils.getGS1Code(order.getGs1Code3()));
						if (order.getGs1Code4() != null && !order.getGs1Code4().isEmpty()) {
							gs1Code.append(serviceUtils.FNC1);
							gs1Code.append(serviceUtils.getGS1Code(order.getGs1Code4()));
							if (order.getGs1Code5() != null && !order.getGs1Code5().isEmpty()) {
								gs1Code.append(serviceUtils.FNC1);
								gs1Code.append(serviceUtils.getGS1Code(order.getGs1Code5()));
							}

						}
					}
				}

				addDebugLog("debug.getBarcode.Generate.Gs1.Begin", new Object[] { order.getId().toString(), gs1Code.toString() });
				filePath = serviceUtils.generateGs1(gs1Code.toString(), order.getId());
				addDebugLog("debug.getBarcode.Generate.Gs1.End", new Object[] { filePath });
			} catch (Exception ex) {
				throwBarcodeFaultException("error.genarateBarcode", new Object[] { orderNumber });
			}
		}

		addDebugLog("debug.getBarcode.sendMail", null);
		if (!serviceUtils.sendEmailMessage(order, filePath)) {
			throwBarcodeFaultException("error.emailSend", new Object[] { orderNumber });
		}

		order.setBarcodeState(BarcodeOrderState.SENT);
		dao.saveOrder(order);
		BarcodeOrderState retState = order.getBarcodeState();
		addDebugLog("debug.getBarcode.sendMail", null);
		addDebugLog("debug.getBarcode.Return", null);

		return retState;
	}
}
