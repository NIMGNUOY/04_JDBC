package edu.kh.jdbc.run;

import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run4 {

	public static void main(String[] args) {
		
		// 삭제할 번호를 입력받아
		// 번호와 일치하는 행 삭제
		
		// 삭제 성공 시 -> "삭제되었습니다" 출력
		// 삭제 실패 시 -> "일치하는 번호가 없습니다" 출력
		// 예외 발생 시 -> "삭제 중 예외 발생" 출력
		
		TestService service = new TestService();
		
		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.print("삭제할 번호 입력 : ");
			int testNo = sc.nextInt();
			
			int result = service.delete(testNo);
			
			if(result > 0) {
				System.out.println("삭제되었습니다");
			} else {
				System.out.println("일치하는 번호가 없습니다");
			}
			
			
		} catch (Exception e) {
			System.out.println("삭제 중 예외 발생");
			e.printStackTrace();
		}
		
	}
	
}
