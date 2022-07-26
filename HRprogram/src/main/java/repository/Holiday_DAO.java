package repository;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Holiday_DAO {

	JdbcTemplate jt;
	
	public Holiday_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
}
