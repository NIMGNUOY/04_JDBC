package edu.kh.emp.model.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

public class EmployeeService {
	
	private EmployeeDAO dao = new EmployeeDAO();

	
	/**	전체 사원 정보 조회 서비스
	 * @return List
	 */
	public List<Employee> selectAll() throws Exception {
		
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. dao 호출
		List<Employee> list = dao.selectAll(conn);
		
		close(conn);
		
		return list;
	}


	/**	사원 정보 추가 서비스
	 * @param emp
	 * @return int result (1 or 0)
	 */
	public int insertEmployee(Employee emp) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.insertEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}


	/**	사번이 일치하는 사원 정보 조회 서비스
	 * @param empId
	 * @return Employee emp
	 * @throws Exception
	 */
	public Employee selectEmpId(int empId) throws Exception {
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpId(conn, empId);
		
		close(conn);
		
		return emp;
	}


	/**	사번이 일치하는 사원 정보 수정 서비스
	 * @param emp
	 * @return int result
	 */
	public int updateEmployee(Employee emp) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.updateEmployee(conn, emp);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}


	/**	사번이 일치하는 사원 정보 삭제 서비스
	 * @param empId
	 * @return int result
	 */
	public int deleteEmployee(int empId) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.deleteEmployee(conn, empId);
		
		if(result > 0 ) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}


	public List<Employee> selectDeptEmp(String deptTitle) throws Exception {
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectDeptEmp(conn, deptTitle);
			
		close(conn);
		
		return list;
	}


	public List<Employee> selectSalaryEmp(int inputSalary) throws Exception {
		
		Connection conn = getConnection();
		
		List<Employee> list = dao.selectSalaryEmp(conn, inputSalary);
		
		close(conn);
		
		return list;
	}


	public Map<String, Integer> selectDeptTotalSalary() throws Exception {
		
		Connection conn = getConnection();
		
		Map<String, Integer> map = dao.selectDeptTotalSalary(conn);
		
		close(conn);
		
		return map;
	}


	public Employee selectEmpNo(String empNo) throws Exception {
		
		Connection conn = getConnection();
		
		Employee emp = dao.selectEmpNo(conn, empNo);
		
		close(conn);
		
		return emp;
	}


	public Map<String, Double> selectJobAvgSalary() throws Exception {
		
		Connection conn = getConnection();
		
		Map<String, Double> map = dao.selectJobAvgSalary(conn);
		
		close(conn);
		
		return map;
	}


	
	
	

}
