package edu.kh.emp.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.emp.model.service.EmployeeService;
import edu.kh.emp.model.vo.Employee;

public class EmployeeView {
	
	private Scanner sc = new Scanner(System.in);
	private EmployeeService service = null;
	
	public EmployeeView() throws Exception {
		
		service = new EmployeeService();
		
	}

	public void startView() {
		
		int menuNum = 0;
		
		do {
			
			System.out.println("<< 직원 관리 프로그램 >>");
			System.out.println("1. 직원 추가");
			System.out.println("2. 직원 정보 수정");
			System.out.println("3. 직원 정보 삭제");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴 입력 : ");
			
			try {
				
				menuNum = sc.nextInt();
				
				switch(menuNum) {
				
				case 1 : addEmployee(); break;
				case 2 : /*updateEmployee();*/ break;
				case 3 : /*deleteEmployee();*/ break;
				case 0 : System.out.println("프로그램 종료"); break;
				
				}
				
			} catch (InputMismatchException e) {
				System.out.println("### 숫자만 입력해주세요 ###");
				menuNum = -1;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} while (menuNum != 0);
		
	}
	
	public void addEmployee() throws Exception {
		
		System.out.println("\n ===== [직원 추가] ===== \n");
		
		System.out.print("사원 번호 : ");
		int empId = sc.nextInt();
		
		System.out.print("이름 : ");
		String empName = sc.next();
		
		System.out.print("주민등록번호('-' 포함) : ");
		String empNo = sc.next();
		
		System.out.print("이메일 : ");
		String email = sc.next();
		
		System.out.print("전화번호 : ");
		String phone = sc.next();
		
		System.out.print("부서명 : ");
		String deptTitle = sc.next();
		
		System.out.print("직급명 : ");
		String jobName = sc.next();
		
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		
		Employee emp = new Employee (empId, empName, empNo, email, phone, deptTitle,
									jobName, salary);
		
		int result = service.addEmployee(emp);
		
		if( result > 0 ) System.out.println("직원 정보 추가 성공");
		else			 System.out.println("직원 추가 실패");
		
	}
	
}






















