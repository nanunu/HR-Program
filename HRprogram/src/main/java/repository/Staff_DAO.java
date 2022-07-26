package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.DepartmentDTO;

public class Staff_DAO {

	public Staff_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	private JdbcTemplate jt;
	
	/*부서DTO를 어레이리스트로 돌려주는 함수*/
	public ArrayList<DepartmentDTO> getDepartmentList() {
		String sql = "select * from Department";
		
		RowMapper<DepartmentDTO> mapper = new RowMapper<DepartmentDTO>() {

			@Override
			public DepartmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DepartmentDTO dto = new DepartmentDTO();
				dto.setDcode(rs.getString("Dcode"));
				dto.setDname(rs.getString("Dname"));
				return dto;
			}
		};
		
		return (ArrayList<DepartmentDTO>) jt.query(sql, mapper);
	}
	
	
}
