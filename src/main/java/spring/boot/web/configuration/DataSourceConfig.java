package spring.boot.web.configuration;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.actuate.autoconfigure.ManagementSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataSourceConfig {

	@Bean
	@Primary
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		if (isfileType()) {
			dataSource.setUrl("jdbc:h2:mem:testdb");
			dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUsername("user");
			dataSource.setPassword("");
		} else {
			dataSource.setUrl(dbUrl);
			dataSource.setDriverClassName(datasourceDriver);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
		}

	
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);
		if (isfileType())
			jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
		else
			jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		return jpaVendorAdapter;
	}

	@Value("${datasource_driver}")
	private String datasourceDriver;

	@Value("${path_to_db_file}")
	private String fileDbPath;

	@Value("${db_type}")
	private String dbType;

	@Value("${datasource_url}")
	private String dbUrl;

	@Value("${datasource_username}")
	private String username;

	@Value("${datasource_password}")
	private String password;

	public boolean isfileType() {
		return (getDbType().equals("file"));
	}

	public String getFileDbPath() {
		return fileDbPath;
	}

	public String getDbType() {
		return dbType;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setFileDbPath(String fileDbPath) {
		this.fileDbPath = fileDbPath;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
