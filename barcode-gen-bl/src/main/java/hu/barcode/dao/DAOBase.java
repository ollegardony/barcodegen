package hu.barcode.dao;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 * 
 *          Base class for database layer
 *          Handle database connect
 */
@Repository
public abstract class DAOBase {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DAOBase.class);

	@Autowired
	protected SessionFactory hibernateSessionFactory;

	public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		Session session = hibernateSessionFactory.getCurrentSession();
		return session.getEntityManagerFactory();
	}
}
