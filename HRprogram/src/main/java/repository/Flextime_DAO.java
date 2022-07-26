package repository;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import model.FlextimeDTO;

public class Flextime_DAO {
	
	private JdbcTemplate jt; 
	
	public Flextime_DAO(DataSource datasource) {
		this.jt = new JdbcTemplate(datasource);
	}
	
	/*사원이 신청한 탄력근무제를 DB에 넣는 함수*/
	public void insertFlexTimeDAO(FlextimeDTO command) {
		String sql = "insert into FlexTime(Ecode, FTstartday, Ftendday, MonStart, MonEnd, TueStart, TueEnd, WedStart, WedEnd, "
				+ "ThuStart, ThuEnd, FriStart, FriEnd, FTapproval) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jt.update(sql, command.getEcode(), command.getStartday(), command.getEndday(),
				command.getFreedaystart()[0], command.getFreedayend()[0],
				command.getFreedaystart()[1], command.getFreedayend()[1],
				command.getFreedaystart()[2], command.getFreedayend()[2],
				command.getFreedaystart()[3], command.getFreedayend()[3],
				command.getFreedaystart()[4], command.getFreedayend()[4],
				command.getFTapproval());
	}
	
	/*탄력근무제 신청*/
}
