package edu.kh.emp.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.emp.common.JDBCTemplate.*;
import edu.kh.emp.model.vo.Employee;

public class EmployeeDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	
	public EmployeeDAO() throws Exception {
		
		try {
			
			prop = new Properties();
			
			prop.loadFromXML( new FileInputStream("query.xml") );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public int addEmployee(Connection conn, Employee emp) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("insert");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDepartmentTitle());
			pstmt.setInt(7, emp.getSalary());
			
			result = pstmt.executeUpdate();
			
		} finally {
			
			close(pstmt);
			
		}
		
		return 0;
	}

	public int displayAllEmployee(Connection conn) throws Exception {
		
		String sql = prop.getProperty("displayAll");
		
		pstmt = conn.prepareStatement(sql);
				
		
		
		return 0;
	}

	

}
