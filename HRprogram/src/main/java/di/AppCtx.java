package di;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.*;
import repository.*;
import mailService.MailUtil;
import mailService.UserMailService;


@Configuration
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
	
	@Bean
	public LoginController loginController() { return new LoginController(); }
	
	@Bean
	public StaffController StaffController() { return new StaffController(); }
	
	@Bean
	public FlextimeController flextimeController() { return new FlextimeController(); }
	
	@Bean
	public Login_DAO login_DAO() { return new Login_DAO(dataSource()); }

	@Bean
	public MailUtil mailUtil() { return new MailUtil(); }
	
	@Bean
	public UserMailService userMailService() { return new UserMailService(); }
		
	@Bean
	public Staff_DAO Staff_DAO() { return new Staff_DAO(dataSource()); }	
	
	@Bean
	public Flextime_DAO flextime_DAO() { return new Flextime_DAO(dataSource()); }
	
}
