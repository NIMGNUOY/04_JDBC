package edu.kh.emp.run;

import edu.kh.emp.view.EmployeeView;

public class EmployeeRun {
	
	public static void main(String[] args) {
		
		try {
			
			EmployeeView view = new EmployeeView();
			
			view.startView();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
