package hu.barcode.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class DAOBase {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DAOBase.class);
	
	@Autowired
	protected SessionFactory hibernateSessionFactory;
	
	public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}
	
}
