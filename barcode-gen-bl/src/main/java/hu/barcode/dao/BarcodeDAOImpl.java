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
	public List<BarcodePrice> getBarcodePriceList(int barcodeType, int codeNum) {

		Session session = hibernateSessionFactory.getCurrentSession();

		@SuppressWarnings("deprecation")
		Criteria root = session.createCriteria(BarcodePrice.class, "rc");

		if (barcodeType == 0 || barcodeType == 1) {
			root.add(Restrictions.eq("rc.barcodeType", BarcodeType.values()[barcodeType]));
		}

		if (barcodeType == 1 && codeNum > 0) {
			root.add(Restrictions.eq("rc.codeNumber", codeNum));
		}

		return (List<BarcodePrice>) root.list();

	}

	@Override
	public BarcodeUser getBarcodeUser(String loginName) {

		Session session = hibernateSessionFactory.getCurrentSession();

		@SuppressWarnings("deprecation")
		Criteria root = session.createCriteria(BarcodeUser.class, "rc");
		root.add(Restrictions.eq("rc.loginName", loginName));

		return (BarcodeUser) root.uniqueResult();

	}

	@Override
	public BarcodeOrder getBarcodeOrder(String orderNumber) {
		Session session = hibernateSessionFactory.getCurrentSession();

		@SuppressWarnings("deprecation")
		Criteria root = session.createCriteria(BarcodeOrder.class, "rc");
		root.add(Restrictions.eq("rc.orderNumber", orderNumber));
		return (BarcodeOrder) root.uniqueResult();
	}

	@Override
	public BarcodeUser saveBarcodeUser(BarcodeUser user) {
		Session session = hibernateSessionFactory.getCurrentSession();
		session.persist(user);
		session.flush();
		return user;
	}

	@Override
	public BarcodeOrder saveOrder(BarcodeOrder order) {
		Session session = hibernateSessionFactory.getCurrentSession();
		session.persist(order);
		if (order.getOrderNumber() == null || order.getOrderNumber().equals("")) {
			order.setOrderNumber(order.getId().toString());
			session.persist(order);
		}
		return order;

	}

}
