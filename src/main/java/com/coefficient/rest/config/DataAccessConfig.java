package com.coefficient.rest.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@PropertySource(value = "file:///${app.conf.dir}/db.properties")
@Configuration
@EnableTransactionManagement
public class DataAccessConfig {

	@Value("${mySql.driverClassName}")
	private String MYSQL_DRIVER;

	@Value("${mysql.url}")
	private String MYSQL_URL;

	@Value("${mySql.username}")
	private String MYSQL_USERNAME;

	@Value("${mySql.password}")
	private String MYSQL_PASSWORD;

	@Bean
	public SessionFactory sessionFactory() {

		return localSessionFactoryBean().getObject();
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager htm = new HibernateTransactionManager();
		htm.setSessionFactory(sessionFactory);
		htm.setDataSource(dataSource());
		return htm;
	}

	@Bean
	@Autowired
	public LocalSessionFactoryBean localSessionFactoryBean() {

		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("element.bst.elementexploration");
		bean.setHibernateProperties(hibernateProperties());
		return bean;
	}

	private Properties hibernateProperties() {

		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5Dialect");
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, "update");
		return properties;
	}

	@Bean(destroyMethod = "close")
	public HikariDataSource dataSource() {

		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public HikariConfig hikariConfig() {

		HikariConfig config = new HikariConfig();
		config.setPoolName(PROPERTY_NAME_SPRING_HIKARI_CONNECTION_POOL);
		config.setConnectionTestQuery(PROPERTY_NAME_SELECT_1);
		config.setMaximumPoolSize(10);
		config.setIdleTimeout(30000);

		config.addDataSourceProperty("url", MYSQL_URL);
		config.addDataSourceProperty("user", MYSQL_USERNAME);
		config.addDataSourceProperty("password", MYSQL_PASSWORD);
		config.setDataSourceClassName(DATA_SOURCE_CLASS_NAME);

		return config;
	}

	private static final String DATA_SOURCE_CLASS_NAME = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";

	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

	private static final String PROPERTY_NAME_SPRING_HIKARI_CONNECTION_POOL = "springHikariConnectionPool";

	private static final String PROPERTY_NAME_SELECT_1 = "SELECT 1";
}
