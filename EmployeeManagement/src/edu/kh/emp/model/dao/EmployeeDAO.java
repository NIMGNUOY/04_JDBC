package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {

	// JDBC 객체 참조변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public EmployeeDAO() {
		
		try {
			
			prop = new Properties();
			
			prop.loadFromXML( new FileInputStream("query.xml") );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**	전체 사원 정보 조회 DAO
	 * @param conn
	 * @return List
	 */
	public List<Employee> selectAll(Connection conn) throws Exception {
		
		// 1. 결과 저장용 변수 선언
		List<Employee> list = new ArrayList<Employee>();
		
		try {
			
			String sql = prop.getProperty("selectAll");
			
			// Statement 객체 생성
			stmt = conn.createStatement();
			
			// SQL 을 수행 후 결과(ResultSet) 반환 받음
			rs = stmt.executeQuery(sql);
			
			// 조회 결과를 얻어와 한 행씩 접근하여 Employee 객체를 생성 후
			// 각각 컬럼값 담기
			// -> List 에 담기
			while( rs.next() ) {
				
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만 저장된 값들은 모두 숫자형태
				// -> DB에서 자동으로 형변환 진행해서 얻어옴
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String departmentTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone,
											departmentTitle, jobName, salary);
				// List 담기
				list.add(emp);
				
			}	// while 문 종료
			
		} finally {
			// 사용한 JDBC 객체 자원 반환
			close(rs);
			close(stmt);
		}
		
		return list;
	}

	
	/**	사원 정보 추가 DAO
	 * @param conn
	 * @param emp
	 * @return int result (1 or 0)
	 */
	public int insertEmployee(Connection conn, Employee emp) throws Exception {
		
		int result = 0;		// 결과 저장용 변수
		
		try {
			
			// SQL 작성
			String sql = prop.getProperty("insertEmployee");
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ?(Place Holder)에 알맞은 값 대입
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
			
		}
		
		return result;
	}

	/**	사번이 일치하는 사원 정보 조회 DAO
	 * @param conn
	 * @param empId
	 * @return Employee emp
	 * @throws Exception
	 */
	public Employee selectEmpId(Connection conn, int empId) throws Exception {
		
		Employee emp = null;
		
		try {
			
			String sql = prop.getProperty("selectOne");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			// stmt - 위치홀더 없을 때
			// pstmt - 위치홀더 있을 때
			
			// executeQuery - Select (ResultSet 반환)
			// executeUpdate - DML(Update / Delete / Insert)
			//   (int 반환 : 성공한 행의 개수)
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				String emp_name = rs.getString("EMP_NAME");
				String emp_no = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String dept_title = rs.getString("DEPT_TITLE");
				String job_name = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee(empId, emp_name, emp_no, email, phone,
						dept_title, job_name, salary);
				
				
			} 
					
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return emp;
		
	}
	

	/**	사번이 일치하는 사원 정보 수정 DAO
	 * @param conn
	 * @param emp
	 * @return int result
	 */
	public int updateEmployee(Connection conn, Employee emp) throws Exception {
		
		int result = 0;		// 결과 저장용 변수 선언
		
		try {
			
			String sql = prop.getProperty("updateEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			
			close(pstmt);
			
		}
		
		
		
		return result;
	}

	
	
	/**	사번과 일치하는 사원 정보 삭제 DAO
	 * @param conn
	 * @param empId
	 * @return int result
	 */
	public int deleteEmployee(Connection conn, int empId) throws Exception {

		int result = 0;		// 결과 저장용 변수 선언
		
		try {
			
			String sql = prop.getProperty("deleteEmployee");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			
			close(pstmt);
			
		}
		
		
		return result;
	}

	public List<Employee> selectDeptEmp(Connection conn, String deptTitle) throws Exception {
		
		List<Employee> list = new ArrayList<Employee>();
		
		
		try {
			
			String sql = prop.getProperty("selectDeptEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, deptTitle);
			
			rs = pstmt.executeQuery();
			
			while ( rs.next() ) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				list.add( new Employee(empId, empName, empNo, email, phone, deptTitle,
										jobName, salary) );
				
			}
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		return list;
	}

	public List<Employee> selectSalaryEmp(Connection conn, int inputSalary) throws Exception {
		
		List<Employee> list = new ArrayList<Employee>();
		
		try {
			
			String sql = prop.getProperty("selectSalaryEmp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, inputSalary);
			
			rs = pstmt.executeQuery();
			
			while( rs.next() ) {
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				list.add( new Employee(empId, empName, empNo, email, phone, deptTitle,
										jobName, salary) );
				
			}
			
			
		} finally {
			
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	public Map<String, Integer> selectDeptTotalSalary(Connection conn) throws Exception {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		try {
			
			String sql = prop.getProperty("selectDeptTotalSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while ( rs.next() ) {
				
				String deptTitle = rs.getString("DEPT_TITLE");
				int sumSalary = rs.getInt("SUM_SALARY");
				
				map.put(deptTitle, sumSalary);
				
			}
			
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return map;
	}

	public Employee selectEmpNo(Connection conn, String empNo) throws Exception {
		
		Employee emp = null;
		
		try {
			
			String sql = prop.getProperty("selectEmpNo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, empNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				emp = new Employee (empId, empName, empNo, email, phone, deptTitle,
						jobName, salary);
				
			}
			
			
			
		} finally {
			
			close(rs);
			close(pstmt);
			
		}
		
		
		return emp;
	}

	public Map<String, Double> selectJobAvgSalary(Connection conn) throws Exception {
		
		Map<String, Double> map = new HashMap<String, Double>();
		
		try {
			
			String sql = prop.getProperty("selectJobAvgSalary");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while ( rs.next() ) {
				
				String jobName = rs.getString("JOB_NAME");
				double avgSalary = rs.getDouble("AVG_SALARY");
				
				map.put(jobName, avgSalary);
				
			}
			
			
		} finally {
			
			close(rs);
			close(stmt);
			
		}
		
		
		return map;
	}
	
}
























