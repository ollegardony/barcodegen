package hu.barcode.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;

@Repository
public class BarcodeDAOImpl extends DAOBase implements BarcodeDAO {

	@SuppressWarnings("unchecked")
	@Override	
	public List<BarcodePrice> getBarcodePriceList(int priceType) {
		
		Session session = hibernateSessionFactory.getCurrentSession();

		@SuppressWarnings("deprecation")
		Criteria root = session.createCriteria(BarcodePrice.class, "rc");
		return (List<BarcodePrice>) root.list();

	}
	
	@Override
	public BarcodeUser getBarcodeUser(String loginName){
		
		BarcodeUser b = new BarcodeUser();
		return b;
		
	}
	
	@Override
	public BarcodeOrder getBarcodeOrder(String orederNumber){
		BarcodeOrder b = new BarcodeOrder();
		return b;
	}
	
	@Override
	public BarcodeUser saveBarcodeUser(BarcodeUser user){
		BarcodeUser b = new BarcodeUser();
		return b;
	}
	
	@Override
	public BarcodeOrder saveOrder(BarcodeOrder order){
		BarcodeOrder b = new BarcodeOrder();
		return b;
		
		
	}

}
