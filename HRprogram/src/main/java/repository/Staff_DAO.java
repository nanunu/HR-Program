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
import model.PositionDTO;

public class Staff_DAO {

	public Staff_DAO(DataSource ds) { this.jt = new JdbcTemplate(ds); }
	
	private JdbcTemplate jt;
	
	RowMapper<EmployeeDTO> mapper = new RowMapper<EmployeeDTO>() {

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
	
	/*부서DTO를 리스트로 돌려주는 함수*/
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
	
	/*부서 리스트를 Map으로 변환하여 돌려주는 함수*/
	public Map<String, String> getDepartmentMap(List<DepartmentDTO> dList){
		Map<String, String> dmap = new HashMap<String, String>();
		for(int i=0; i<dList.size(); i++) {
			dmap.put(dList.get(i).getDcode(), dList.get(i).getDname());
		}
		return dmap;
	}
	
	/*직급DTO를 리스트로 돌려주는 함수*/
	public List<PositionDTO> getPositionList(){
		String sql = "select * from PositionT";
		
		RowMapper<PositionDTO> mapper = new RowMapper<PositionDTO>() {

			@Override
			public PositionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PositionDTO dto = new PositionDTO();
				dto.setPosition(rs.getString("position"));
				dto.setPname(rs.getString("Pname"));
				return dto;
			}
		};
		return jt.query(sql, mapper);
	}
	
	/*직급 리스트를 Map으로 변환하여 돌려주는 함수*/
	public Map<String, String> getPositionMap(List<PositionDTO> pList){
		Map<String, String> pmap = new HashMap<String, String>();
		for(int i=0; i<pList.size(); i++) {
			pmap.put(pList.get(i).getPosition(), pList.get(i).getPname());
		}
		return pmap;
	}
	
	/*Employee 테이블에서 사원정보를 들고오는 함수 - 인사기록카드*/
	public EmployeeDTO getEmployeeInfo(String Ecode) {
		
		EmployeeDTO eDTO = null;
		String sql = "select * from Employee where Ecode=?";
		
		eDTO = jt.queryForObject(sql, mapper, Ecode);
		
		return eDTO;
	}
	
	/*모든 사원들의 정보를 들고오는 함수*/
	public ArrayList<EmployeeDTO> getEmployList(){
		
		String sql = "select * from Employee";
	
		return (ArrayList<EmployeeDTO>) jt.query(sql, mapper);
	}
}
