package repository;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Staff_DAO {

	public Staff_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	private JdbcTemplate jt;
	
	
	
}
