package hu.barcode.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;
import hu.barcode.enums.BarcodeType;

@Repository
public class BarcodeDAOImpl extends DAOBase implements BarcodeDAO {

	@SuppressWarnings("unchecked")
	@Override	
	public List<BarcodePrice> getBarcodePriceList(int barcodeType) {
		
		Session session = hibernateSessionFactory.getCurrentSession();

		@SuppressWarnings("deprecation")
		Criteria root = session.createCriteria(BarcodePrice.class, "rc");
		
		if (barcodeType == 0 || barcodeType == 1){
			root.add(Restrictions.eq("rc.barcodeType", BarcodeType.values()[barcodeType]));
		}
		
		return (List<BarcodePrice>) root.list();

	}
	
	@Override
	public BarcodeUser getBarcodeUser(String loginName){
		
		Session session = hibernateSessionFactory.getCurrentSession();

		@SuppressWarnings("deprecation")
		Criteria root = session.createCriteria(BarcodeUser.class, "rc");
		root.add(Restrictions.eq("rc.loginName", loginName));
		
		return (BarcodeUser) root.uniqueResult();
				
	}
	
	@Override
	public BarcodeOrder getBarcodeOrder(String orederNumber){
		BarcodeOrder b = new BarcodeOrder();
		return b;
	}
	
	@Override
	public BarcodeUser saveBarcodeUser(BarcodeUser user){
		Session session = hibernateSessionFactory.getCurrentSession();
		session.save(user);
		//session.persist(user);		
		return user;
	}
	
	@Override
	public BarcodeOrder saveOrder(BarcodeOrder order){
		BarcodeOrder b = new BarcodeOrder();
		return b;
		
		
	}

}
