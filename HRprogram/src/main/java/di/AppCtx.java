package di;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import Service.*;
import controller.*;
import repository.*;
import mailService.MailUtil;
import mailService.UserMailService;


@Configuration
@EnableTransactionManagement
public class AppCtx {
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/HRProgram?characterEncoding=utf8&useSSL=false");
		ds.setUsername("root");
		ds.setPassword("1234");
		return ds;
	}
	
	//로그인에 트랜젝션적용
	@Bean
	public PlatformTransactionManager platform() { return new DataSourceTransactionManager(dataSource()); }
	
	
	// Controller
	@Bean
	public LoginController loginController() { return new LoginController(); }
	
	@Bean
	public FlextimeController flextimeController() { return new FlextimeController(); }
	
	@Bean
	public OverTimeController overTimeController() { return new OverTimeController(); }
	
	@Bean
	public HolidayController HolidayController() { return new HolidayController(); }

	
	// DAO	
	@Bean
	public Login_DAO login_DAO() { return new Login_DAO(dataSource()); }

	@Bean
	public Staff_DAO Staff_DAO() { return new Staff_DAO(dataSource()); }	
	
	@Bean
	public Flextime_DAO flextime_DAO() { return new Flextime_DAO(dataSource()); }
	
	@Bean
	public OverTime_DAO overTime_DAO() { return new OverTime_DAO(dataSource()); }
	
	@Bean
	public Holiday_DAO holiday_DAO() { return new Holiday_DAO(dataSource()); }

	// Service	
	@Bean
	public MailUtil mailUtil() { return new MailUtil(); }
	
	@Bean
	public UserMailService userMailService() { return new UserMailService(); }
	
	@Bean
	public OverTimeService overTimeService() {return new OverTimeService();}
	
	@Bean
	public HolidayService holidayService() { return new HolidayService(); }
	
	@Bean
	public FlexTimeService flexTimeService() { return new FlexTimeService();}
	
	@Bean
	public SearchService searchService() { return new SearchService(); }

	
	
	
	
}
