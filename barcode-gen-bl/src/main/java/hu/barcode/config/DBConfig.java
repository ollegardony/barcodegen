package hu.barcode.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 15/05/2017
 *
 *          Configure Database Connection Set properties:
 *          persistence-mssql.properties (Database acsess url, After
 *          deploy(update, create or drop)
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:persistence-mssql.properties")
public class DBConfig {

	@Autowired
	private Environment env;

	AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan(new String[] { "hu.barcode.entities" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		
		return dataSource;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

		// properties.put("javax.persistence.schema-generation.database.action",
		// "create");
		// properties.put("javax.persistence.schema-generation.create-source",
		// "script");
		// properties.put("javax.persistence.schema-generation.create-script-source",
		// "scripts/createdb.sql");
		// properties.put("hibernate.hbm2ddl.import_files_sql_extractor",
		// "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");

		return properties;
	}

}
