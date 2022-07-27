package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import model.EmployeeHolidayDTO;

@Repository
public class Holiday_DAO {

	JdbcTemplate jt;
	
	public Holiday_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	// DTO로 가져오기
	public EmployeeHolidayDTO Select_Holiday(String Ecode) {
		String sql = "select * from EH where Ecode=? and residualholiday is not 0";
		RowMapper<EmployeeHolidayDTO> mapper = new RowMapper<EmployeeHolidayDTO>() {

			@Override
			public EmployeeHolidayDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmployeeHolidayDTO dto = new EmployeeHolidayDTO();
				dto.setNumOfmyholiday(rs.getDouble("numOfmyholiday"));
				dto.setResidualholiday(rs.getDouble("residualholiday"));
				dto.setUsenumOfholiday(rs.getDouble("usenumOfholiday"));
				return dto;
			}
			
		};
		
		List<EmployeeHolidayDTO> list = jt.query(sql,mapper,Ecode);
		
		if(list!=null) { return list.get(0); }
		else { return null; }
		
	}

	public 
	
	
	
}//class end
