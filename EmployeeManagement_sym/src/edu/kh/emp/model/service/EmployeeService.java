package edu.kh.emp.model.service;

import java.sql.Connection;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

public class EmployeeService {
	
	private EmployeeDAO dao = null;
	
	public EmployeeService() throws Exception {
		dao = new EmployeeDAO();
	}

	public int addEmployee(Employee emp) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.addEmployee(conn, emp);
		
		if(result > 0) conn.commit();
		else		   conn.rollback();
		
		return result;
		
	}

	public int displayAllEmployee() throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.displayAllEmployee(conn);
		
		return result;
	}

}
