package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import model.DepartmentDTO;
import model.EmployeeDTO;

public class Staff_DAO {

	public Staff_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	private JdbcTemplate jt;
	
	/*부서DTO를 어레이리스트로 돌려주는 함수*/
	public List<DepartmentDTO> getDepartmentList() {
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

		return jt.query(sql, mapper);
	}
	
	/*Employee 테이블에서 사원정보를 들고오는 함수
	  if(type)==1 : 탄력근무
	  if(type)==2 : 인사기록카드
	 */
	public EmployeeDTO getEmployeeInfo(String Ecode, int type) {
		
		EmployeeDTO eDTO = null;
		String sql = "select * from Employee where Ecode=?";
		
		RowMapper<EmployeeDTO> mapper1 = new RowMapper<EmployeeDTO>() {

			@Override
			public EmployeeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setEcode(rs.getString("Ecode"));
				dto.setEname(rs.getString("Ename"));
				dto.setDcode(rs.getString("Dcode"));
				dto.setPosition(rs.getString("position"));
				return dto;
			}
		};
		
		RowMapper<EmployeeDTO> mapper2 = new RowMapper<EmployeeDTO>() {

			@Override
			public EmployeeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setEcode(rs.getString("Ecode"));
				dto.setEname(rs.getString("Ename"));
				dto.setDcode(rs.getString("Dcode"));
				dto.setPosition(rs.getString("position"));
				dto.setEmail(rs.getString("email"));
				dto.setJoinday(rs.getString("joinday"));
				dto.setPhone(rs.getString("phone"));
				dto.setBirth(rs.getString("birth"));
				dto.setZipcode(rs.getString("zipcode"));
				dto.setAddress(rs.getString("address"));
				dto.setSex(rs.getString("sex"));
				dto.setEducation(rs.getString("education"));
				dto.setMilitary(rs.getBoolean("military"));
				dto.setMaritalStatus(rs.getString("maritalStatus"));
				dto.setLicence(rs.getString("licence"));
				dto.setBank(rs.getString("bank"));
				dto.setBankbook(rs.getString("bankbook"));
				dto.setDepositor(rs.getString("depositor"));
				dto.setCar(rs.getString("car"));
				return dto;
			}
		};
		
		if(type==1) {
			eDTO = jt.queryForObject(sql, mapper1, Ecode);
		}else if(type==2) {
			eDTO = jt.queryForObject(sql, mapper2, Ecode);
		}
		
		return eDTO;
	}
	
}
