package hu.barcode.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;
import hu.barcode.enums.BarcodeType;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *
 * @Data access layer
 */
@Repository
@PropertySource("classpath:barcode.properties")
public class BarcodeDAOImpl extends DAOBase implements BarcodeDAO {

	@Autowired
	private Environment env;

	// Select barcode_price with where clauses
	@Override
	public List<BarcodePrice> getBarcodePriceList(int barcodeType, int codeNum) {

		EntityManager em = getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<BarcodePrice> criteriaQuery = builder.createQuery(BarcodePrice.class);
		Root<BarcodePrice> root = criteriaQuery.from(BarcodePrice.class);

		criteriaQuery.select(root);

		if (barcodeType == 0 || barcodeType == 1) {
			criteriaQuery.where(builder.equal(root.get("barcodeType"), BarcodeType.values()[barcodeType]));
		}

		if (barcodeType == 1 && codeNum > 0) {
			criteriaQuery.where(builder.equal(root.get("codeNumber"), codeNum));
		}

		List<BarcodePrice> l = em.createQuery(criteriaQuery).getResultList();

		return l;

	}

	// Select from barcode_use where login_name = " loginName"
	@Override
	public BarcodeUser getBarcodeUser(String loginName) {

		EntityManager em = getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<BarcodeUser> criteriaQuery = builder.createQuery(BarcodeUser.class);
		Root<BarcodeUser> root = criteriaQuery.from(BarcodeUser.class);

		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get("loginName"), loginName));
		List<BarcodeUser> userList = em.createQuery(criteriaQuery).getResultList();

		if (userList.isEmpty()) {
			return null;
		}

		return userList.get(0);
	}

	// Select barcode_order where order_number = "orderNumber"
	@Override
	public BarcodeOrder getBarcodeOrder(String orderNumber) {

		EntityManager em = getEntityManagerFactory().createEntityManager();
		CriteriaBuilder builder = getEntityManagerFactory().getCriteriaBuilder();
		CriteriaQuery<BarcodeOrder> criteriaQuery = builder.createQuery(BarcodeOrder.class);
		Root<BarcodeOrder> root = criteriaQuery.from(BarcodeOrder.class);

		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get("orderNumber"), orderNumber));

		List<BarcodeOrder> orderList = em.createQuery(criteriaQuery).getResultList();

		if (orderList.isEmpty()) {
			return null;
		}

		return orderList.get(0);
	}

	// Save user Entity
	@Override
	public BarcodeUser saveBarcodeUser(BarcodeUser user) {
		Session session = hibernateSessionFactory.getCurrentSession();
		session.save(user);
		return user;
	}

	// Save order Entity
	@Override
	public BarcodeOrder saveOrder(BarcodeOrder order) {
		Session session = hibernateSessionFactory.getCurrentSession();
		session.save(order);

		if (order.getOrderNumber() == null || order.getOrderNumber().equals("")) {
			String orderNumber = env.getProperty("order.number.prefix");
			order.setOrderNumber(orderNumber + order.getId().toString());
			session.persist(order);
		}
		return order;

	}

}
