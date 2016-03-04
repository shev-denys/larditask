package spring.boot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
//import org.springframework.boot.actuate.autoconfigure.ManagementSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import spring.boot.web.configuration.DataSourceConfig;
import spring.boot.web.services.DbAddressBookService;
import spring.boot.web.services.FileAddressBookService;
import spring.boot.web.services.interfaces.UserAddressBook;

@SpringBootApplication
@PropertySource("file:${lardi.conf}")
public class Application extends SpringBootServletInitializer{

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
  
  @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
  

  @Autowired
  DataSourceConfig datasourceConfig;
  
  
//  @Bean
//  public InternalResourceViewResolver viewResolver() {
//      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//      viewResolver.setViewClass(JstlView.class);
//      viewResolver.setPrefix("WEB-INF/jsp/");
//      viewResolver.setSuffix(".jsp");
//      
//      System.out.println("Initializing view resolver");
//      return viewResolver;
//  }
  
  @Bean 
  public UserAddressBook useraddressbookService()
  { 
	  if(datasourceConfig.getDbType().equals("file"))
	  {
		  return new FileAddressBookService();
	  }
	  else if(datasourceConfig.getDbType().equals("mysql"))
		  return new DbAddressBookService();
	  throw new RuntimeException("Error with database type:" + datasourceConfig.getDbType());
  }
  
}
