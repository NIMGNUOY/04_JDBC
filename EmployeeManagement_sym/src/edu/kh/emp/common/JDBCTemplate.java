package edu.kh.emp.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	public static Connection conn = null;
	
	/**	DB 연결 정보를 담고있는 Connection 객체 생성 및 반환 메서드
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			
			if( conn == null || conn.isClosed() ) {
				
				Properties prop = new Properties();
				
				prop.loadFromXML( new FileInputStream("driver.xml") );
				
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String pw = prop.getProperty("password");
				
				Class.forName(driver);
				
				conn = DriverManager.getConnection(url, user, pw);
				
				conn.setAutoCommit(false);
				
			} 
			
		} catch (Exception e) {
			System.out.println("[Connection 생성 중 예외발생]");
			e.printStackTrace();
		} 
		
		return conn;
		
	}
	
	
	/**	Connection 객체 자원 반환 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		
		try {
			if( conn != null && !conn.isClosed() ) conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	/** Statement(부모), PreparedStatement(자식) 객체 자원 반환 메서드
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		
		try {
			if( stmt != null && !stmt.isClosed() ) stmt.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	
	/**	ResultSet 객체 자원 반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		
		try {
			if( rs != null && !rs.isClosed() ) rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**	트랜잭션 commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		
		try {
			if( conn != null && !conn.isClosed() ) conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**	트랜잭션 rollback 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		
		try {
			if( conn != null && !conn.isClosed() ) conn.rollback();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}


































