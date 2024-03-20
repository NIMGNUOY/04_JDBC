package edu.kh.jdbc.member.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.Session;
import edu.kh.jdbc.member.model.dto.Member;
import edu.kh.jdbc.member.model.service.MemberService;

/**
 *	회원 전용 화면 
 */
public class MemberView {

	private Scanner sc = new Scanner(System.in);
	
	private MemberService service = new MemberService();
	
	
	/**
	 * 	회원 기능 메뉴 View
	 */
	public void memberMenu() {
		
		int input = 0;
		
		do {
			
			try {
				
				System.out.println("\n=====[회원 기능]=====\n");
				System.out.println("1. 내 정보 조회");
				System.out.println("2. 회원 목록 조회(아이디, 이름, 성별)");
				System.out.println("3. 내 정보 수정(이름, 성별)");
				System.out.println("4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
				System.out.println("5. 회원 탈퇴(보안코드, 비밀번호, UPDATE)");
				System.out.println("9. 메인 메뉴로 돌아가기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n 메뉴 선택 >> ");
				input = sc.nextInt();
				sc.nextLine();
				
				
				switch(input) {
				case 1 : selectMyInfo(); break;
				case 2 : selectMemberList(); break;
				case 3 : updateMember(); break;
				case 4 : updatePassword(); break;
				case 5 : if( unRegisterMenu() ) return;  break;
				case 9 : System.out.println("\n ===== 메인 메뉴로 돌아갑니다 ===== \n");break;
				case 0 : System.out.println("\n===== 프로그램 종료 =====\n"); 
						// JVM 강제 종료 구문
						// 매개변수는 기본 0, 다른 숫자는 오류를 의미
						System.exit(0);
				default : System.out.println("\n *** 메뉴 목록에 있는 번호만 입력해 주세요 *** \n");
				
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n *** 입력 형식이 올바르지 않습니다 *** \n");
				sc.nextLine();
				input = -1;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
		} while (input != 0);
		
	}
	
	
	
	public void selectMyInfo() {
		
		System.out.println("\n===== [내 정보 조회] =====\n");
		System.out.println("회원 번호 : " + Session.loginMember.getMemberNo());
		System.out.println("회원 아이디 : " + Session.loginMember.getMemberId());
		System.out.println("이름 : " + Session.loginMember.getMemberName());
		System.out.println("성별 : " + Session.loginMember.getMemberGender());
		System.out.println("가입일 : " + Session.loginMember.getEnrollDate());
		
	}
	
	
	/**
	 * 	회원 목록 조회
	 */
	public void selectMemberList() throws Exception {
		
		System.out.println("\n===== [회원 목록 조회] =====\n");
		
		try {
			// 회원 목록 조회 서비스 호출 후 결과 반환받기
			List<Member> list = service.selectMemberList(); 
			
			if(list.isEmpty()) {
				System.out.println("\n==== 조회 결과가 없습니다 =====\n");
				return;
			} 
			
			// 1. user04    유저사    남
			// 2. user03	유저삼	  여
			// ...
			int index = 1;
			for(Member member : list) {
				
				System.out.println(index + ". 아이디 : " + member.getMemberId() + " / 이름 : " + member.getMemberName()
				+ " / 성별 : " + member.getMemberGender());
				index ++;
				
			}
			
		} catch (Exception e) {
			System.out.println("\n***회원목록 조회 중 예외 발생 ***\n");
		}
		
		
	}
	
	/**
	 * 	내 정보 수정
	 */
	public void updateMember() {
		
		System.out.println("\n===== [내 정보 수정] =====\n");
		
		System.out.print("수정할 이름 : ");
		String memberName = sc.next();
		
		String memberGender = null;
		
		while (true) {
			
			System.out.print("수정할 성별(M/F) : ");
			memberGender = sc.next().toUpperCase();
			
			if(memberGender.equals("M") || memberGender.equals("F")) {
				break;
			} 
			
			System.out.println("\n *** M 또는 F를 입력해주세요 *** \n");
			
		}
		
		try {
			
			// 회원 정보 수정 서비스 호출 및 결과 반환받기
			int result = service.updateMember(memberName, memberGender, Session.loginMember.getMemberNo());
			// Session.loginMember.getMemberNo() : 현재 로그인한 회원의 번호
			
			if(result > 0) {
				System.out.println("\n===== 수정 되었습니다 =====\n");
				
				// Service 를 호출해서 DB만 수정
				//  -> DB와 JAVA 프로그램 데이터 동기화가 필요하다
				Session.loginMember.setMemberName(memberName);
				Session.loginMember.setMemberGender(memberGender);
				
			} else {
				System.out.println("\n*** 수정 실패 ***\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/**
	 * 	비밀번호 변경 메서드
	 */
	public void updatePassword() {
		
		System.out.println("\n =====[비밀번호 변경]=====\n");
		
		try {
			
			System.out.print("현재 비밀번호 입력 : ");
			String currPw = sc.next();
			
			if(currPw.equals(Session.loginMember.getMemberPw())) {
				
				String changedPw = null;
				
				while (true) {
				
					System.out.print("새 비밀번호 입력 : ");
					changedPw = sc.next();
					
					System.out.print("새 비밀번호 확인 : ");
					String changedPwCheck = sc.next();
					
					if(changedPw.equals(changedPwCheck)) {
						break;
					} else {
						System.out.println("\n *** 새 비밀번호가 일치하지 않습니다  *** \n");
					} 
					
					
				}
				
				String memberId = Session.loginMember.getMemberId();
				
				// 서비스 호출(현재 비밀번호, 새 비밀번호, 로그인한 회원 번호)
				// int result = service.updatePassword(currPw, changedPw, Session.loginMember.getMemberNo())
				int result = service.updatePassword(memberId,changedPw);
				
				if(result > 0) {
					System.out.println("\n===== 비밀번호 수정 완료 =====\n");
				} else {
					System.out.println("\n *** 비밀번호 수정 중 문제가 발생했습니다 다시 시도해주세요 *** \n");
				}
				
			} else {
				System.out.println("\n*** 비밀번호가 틀렸습니다 비밀번호를 확인해주세요 ***\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n*** 비밀번호 변경중 예외 발생 ***\n");
			e.printStackTrace();
		}
		
	}
	
	
	/** 회원탈퇴
	 * @return true / false
	 */
	public boolean unRegisterMenu() {
		
		System.out.println("\n===== [회원 탈퇴] ===== \n");
		
		System.out.print("현재 비밀번호 : ");
		String memberPw = sc.next();
		
		String code = service.createSecurityCode();
		System.out.printf("보안문자 입력 [%s] : ", code);	// 보안문자 입력 [240571] : 
		String input = sc.next();		// 보안문자 입력
			
		// 보안문자 일치 여부 확인
		if(!input.equals(code)) {		// 일치하지 않으면
			System.out.println("\n *** 보안문자가 일치하지 않습니다 *** \n");
			return false;
		} 
		
		while(true) {
			
			System.out.print("\n정말 탈퇴하시겠습니까(Y/N) : ");
			char check = sc.next().toUpperCase().charAt(0);
			
			if(check == 'N') {
				System.out.println("\n===== 탈퇴 취소 =====\n");
				return false;	// 해당 메서드 종료
			}
			
			if(check == 'Y') {
				break;	// 해당 반복문 종료
			}
			
			// 'Y' , 'N' 이 아닌 경우
			System.out.println("\n*** 잘못 입력하셨습니다 ***\n");
			
		}
		
		try {
			
			// 회원 탈퇴 서비스 호출
			int result = service.unRegisterMember(memberPw, Session.loginMember.getMemberNo());
			
			if(result > 0) {
				System.out.println("\n ===== 탈퇴 되었습니다 ===== \n");
				
				// 로그아웃 처리
				Session.loginMember = null;
				
				return true;
				
			} else {
				
				System.out.println("\n*** 현재 비밀번호가 일치하지 않습니다 ***\n");
				
			}
			
			
		} catch(Exception e) {
			System.out.println("\n*** 회원 탈퇴중 예외 발생 ***\n");
			e.printStackTrace();
		}
		
		return false;
		
	}
	
}






















