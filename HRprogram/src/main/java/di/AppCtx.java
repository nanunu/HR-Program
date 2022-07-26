package di;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.LoginController;
import mailService.MailUtil;
import mailService.UserMailService;
import repository.Login_DAO;

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
	public Login_DAO login_DAO() { return new Login_DAO(dataSource()); }
	
	@Bean
	public MailUtil mailUtil() { return new MailUtil(); }
	
	@Bean
	public UserMailService userMailService() { return new UserMailService(); }
	
}
