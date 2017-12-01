package POS;

import java.io.BufferedReader;
import java.io.FileReader;

public class LoadData {
	int customerCol = 4;
	int employeeCol = 2;
	int menuCol = 2;
	int customer_num = 0;
	int employee_num = 0;
	int menu_num = 0;
	
	Object[][] customer;
	Object[][] employee;
	Object[][] menu;

	public void makeArray(String fileName) throws Exception {
		System.out.println("LD.makeArray()");
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		String word[];
		customer_num = Integer.parseInt(br.readLine());
		customer = new Object[customerCol][customer_num];
		
		System.out.println("customer_num : " + customer_num);
		for (int i = 0; i < customer_num; i++) {
			line = br.readLine();
			System.out.println("line : " + line);
			word = line.split("\t");
			for (int j = 0; j < customerCol; j++) {
				customer[j][i] = word[j];
			}
		}
		employee_num = Integer.parseInt(br.readLine());
		employee = new Object[employeeCol][employee_num];
		System.out.println("employee_num : " + employee_num);
		for (int i = 0; i < employee_num; i++) {
			line = br.readLine();
			System.out.println("line : " + line);
			word = line.split("\t");
			for (int j = 0; j < employeeCol; j++) {
				employee[j][i] = word[j];
			}
		}
		menu_num = Integer.parseInt(br.readLine());
		menu = new Object[menuCol][menu_num];
		System.out.println("menu_num : " + menu_num);
		for (int i = 0; i < menu_num; i++) {
			line = br.readLine();
			System.out.println("line : " + line);
			word = line.split("\t");
			for (int j = 0; j < menuCol; j++) {
				menu[j][i] = word[j];
			}
		}
		br.close();
	}

}
