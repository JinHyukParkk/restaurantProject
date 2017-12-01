package POS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class DB_Connect {
	protected static Connection dbTest;
	protected int customerId = 0;
	protected int employeeId = 0;
	protected String ID;
	protected String PW;

	protected DB_Connect(String id, String pass) {
		try {
			this.ID = id;
			this.PW = pass;
			Class.forName("oracle.jdbc.OracleDriver");
			dbTest = java.sql.DriverManager.getConnection("jdbc:oracle:thin:"
					+ "@localhost:1521:XE", ID, PW);
			dbTest.commit();
			JOptionPane.showMessageDialog(null, "Database Connect Success.");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Database Connect Fail.");
			System.out.println("SQLException:" + e);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

	// Table
	protected void dropTable() {
		try{
			dropTable1();
		}catch (SQLException e1) {
			System.out.println("sell drop");
			System.out.println(e1);
		}
		try{
			dropTable2();
		}catch (SQLException e1) {
			System.out.println("customer drop");
			System.out.println(e1);
		}
		try{
			dropTable3();
		}catch (SQLException e1) {
			System.out.println("empoly drop");
			System.out.println(e1);
		}
		try{
			dropTable4();
		}catch (SQLException e1) {
			System.out.println("menu drop");
			System.out.println(e1);
		}
	}
	protected void dropTable1() throws SQLException {
		System.out.println("db.dropTable1()");
		String sqlStr;
		sqlStr = "DROP table sell";
		System.out.println(sqlStr);
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
	}

	protected void dropTable2() throws SQLException {
		System.out.println("db.dropTable()2");
		String sqlStr;
		sqlStr = "DROP table customer";
		System.out.println(sqlStr);
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr);
		stmt2.executeQuery();
		stmt2.close();
	}

	protected void dropTable3() throws SQLException {
		System.out.println("db.dropTable3()");
		String sqlStr;
		sqlStr = "DROP table employee";
		System.out.println(sqlStr);
		PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr);
		stmt3.executeQuery();
		stmt3.close();
	}

	protected void dropTable4() throws SQLException {
		System.out.println("db.dropTable()4");
		String sqlStr;
		sqlStr = "DROP table menu";
		System.out.println(sqlStr);
		PreparedStatement stmt4 = dbTest.prepareStatement(sqlStr);
		stmt4.executeQuery();
		stmt4.close();

	}

	protected void createTable(){
		try {
			createTable2();
		} catch (SQLException e1) {
			System.out.println("customer create");
		}
		try {
			createTable3();
		} catch (SQLException e1) {
			System.out.println("employ create");
		}
		try {
			createTable4();
		} catch (SQLException e1) {
			System.out.println("menu create");
		}
		try {
			createTable1();
		} catch (SQLException e1) {
			System.out.println("sell create");
		}				
	}
	
	protected void createTable1() throws SQLException {
		System.out.println("db.createTable1()");
		String sqlStr;
		sqlStr = "Create table sell\n 	(day	varchar2(10) not null,\n 	menuName	varchar(25) not null,\n 	price	numeric(12,2) check (price > 0) not null,\n	foreign key (menuName) references menu)";
		System.out.println(sqlStr);
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();

	}

	protected void createTable2() throws SQLException {
		System.out.println("db.createTable2()");
		String sqlStr;
		sqlStr = "Create table customer\n 	(customerName	varchar(25) not null,\n 	ID	char(4)  not null,\n 	birth	number(4) not null,\n 	phone	number(4) not null,\n 	rank	varchar(20) not null,\n 	buy	number (12) check (buy >= 0) not null,\n 	primary key (ID))";
		System.out.println(sqlStr);
		PreparedStatement stmt2 = dbTest.prepareStatement(sqlStr);
		stmt2.executeQuery();
		stmt2.close();
	}

	protected void createTable3() throws SQLException {
		System.out.println("db.createTable3()");
		String sqlStr;
		sqlStr = "Create table employee\n	(employeeName	varchar(20) not null,\n 	ID	char(4) not null,\n 	rank	varchar(20) not null,\n 	sell	number(12) check (sell >= 0) not null,\n 	primary key (ID))";
		System.out.println(sqlStr);
		PreparedStatement stmt3 = dbTest.prepareStatement(sqlStr);
		stmt3.executeQuery();
		stmt3.close();
	}

	protected void createTable4() throws SQLException {
		System.out.println("db.createTable4()");
		String sqlStr;
		sqlStr = "create table menu\n	(menuName	varchar(25) not null,\n 	price	numeric(12,2) check (price > 0) not null,\n 	primary key (menuName))";
		System.out.println(sqlStr);
		PreparedStatement stmt4 = dbTest.prepareStatement(sqlStr);
		stmt4.executeQuery();
		stmt4.close();

	}

	// Login
	protected void logIn(String employeeName, String ID, Controller c)
			throws SQLException {
		System.out.println("db.logIn()");
		String sqlStr = "SELECT rank FROM employee"
				+ " WHERE employeeName='" + employeeName + "' and ID='" + ID
				+ "'";
		System.out.println(sqlStr);
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		String rank = "";
		while(rs.next()){
			rank = rs.getString("rank");
		}
		if(rank.equals("")){
			rs.next();
		}else{
			c.MF.staffname.setText(employeeName);
			c.MF.staffrank.setText(rank);
		}
		rs.close();
		stmt.close();
	}

	protected int setEmployeeMode(String employeeName, String ID)
			throws SQLException {
		int a = 0;
		System.out.println("db.setEmployeeMode()");
		String sqlStr = "SELECT rank FROM employee" + " WHERE employeeName='"
				+ employeeName + "' and ID='" + ID + "'";
		System.out.println(sqlStr);
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		rs.next();
		if (rs.getString("rank").equals("Supervisor")) {
			a = 1;
		}
		rs.close();
		stmt.close();
		return a;
	}

	// Open
	protected void load(Controller c) throws SQLException {
		System.out.println("db.loadCustmer()");
		String sqlStr = "";
		for (int i = 0; i < c.LD.customer_num; i++) {// c=6
			sqlStr = "INSERT into customer values (";
			for (int j = 0; j < 4; j++) {
				sqlStr += "'" + c.LD.customer[j][i] + "'";
				if (j == 0) {
					sqlStr += "," + countCustomer();
				}
				if (j == 3) {
					break;
				} else {
					sqlStr += ",";
				}
			}
			if (c.LD.customer[3][i].equals("Gold")) {
				sqlStr += ",1000000)";
			} else if (c.LD.customer[3][i].equals("Silver")) {
				sqlStr += ",500000)";
			} else if (c.LD.customer[3][i].equals("Bronze")) {
				sqlStr += ",300000)";
			} else if (c.LD.customer[3][i].equals("Normal")) {
				sqlStr += ",0)";
			}

			System.out.println(sqlStr);
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
		}
		
		System.out.println("db.loadEmployee()");
		sqlStr = "";
		for (int i = 0; i < c.LD.employee_num; i++) {
			sqlStr = "INSERT into employee values (";
			for (int j = 0; j < 2; j++) {
				sqlStr += "'" + c.LD.employee[j][i] + "'";
				if (j == 0) {
					sqlStr += "," + countEmployee();
				}
				if (j == 1) {
					break;
				} else
					sqlStr += ",";
			}
			sqlStr += ",0";
			sqlStr += ")";
			System.out.println(sqlStr);
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
		}
		
		System.out.println("db.loadMenu()");
		sqlStr = "";
		for (int i = 0; i < c.LD.menu_num; i++) {
			sqlStr = "INSERT into menu values (";
			for (int j = 0; j < 2; j++) {
				sqlStr += "'" + c.LD.menu[j][i] + "'";
				if (j == 1) {
					break;
				} else
					sqlStr += ",";
			}
			sqlStr += ")";
			System.out.println(sqlStr);
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
		}

		System.out.println("db.refreshMenu()");
		String a = "";
		sqlStr = "SELECT menuName FROM Menu";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		for (int i = 0; i < c.LD.menu_num; i++) {
			rs.next();
			a = rs.getString("MENUNAME");
			c.MF.buttonMenu[i].setText(a);
		}
		rs.close();
		stmt.close();
	}

	protected void setBufferPrice(String[] buffer, int[] bufferprice) throws SQLException {
		System.out.println("db.setBufferPrice()");

		String sqlStr = "";

		for (int i = 0; i < 20; i++) {
			sqlStr = "SELECT price FROM menu" + " WHERE menuName='" + buffer[i]
					+ "'";
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bufferprice[i] = Integer.parseInt(rs.getString("price"));

			}
			rs.close();
			stmt.close();
		}
	}

	protected void setPrice(String[][] order, int[][] price) throws SQLException {
		System.out.println("db.setPrice");
		String sqlStr = "";
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				sqlStr = "SELECT price FROM menu" + " WHERE menuName='"
						+ order[i][j] + "'";
				PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
				ResultSet rs = stmt.executeQuery();
				// System.out.println(sqlStr);
				while (rs.next()) {
					price[i][j] = Integer.parseInt(rs.getString("price"));
				}
				rs.close();
				stmt.close();
			}
		}

	}

	//
	protected void addBuy(String customerName, int price, String rank)
			throws SQLException {
		String sqlStr = "";
		System.out.println("db.addBuy()");
		if (rank.equals("Gold")) {
			sqlStr = "UPDATE customer SET buy=buy+" + (70 * price / 100)
					+ " WHERE customerName = '" + customerName + "'";
			System.out.println(sqlStr);
		} else if (rank.equals("Silver")) {
			sqlStr = "UPDATE customer SET buy=buy+" + (80 * price / 100)
					+ " WHERE customerName = '" + customerName + "'";
			System.out.println(sqlStr);
		}

		else if (rank.equals("Bronze")) {
			sqlStr = "UPDATE customer SET buy=buy+" + (90 * price / 100)
					+ " WHERE customerName = '" + customerName + "'";
			System.out.println(sqlStr);
		} else if (rank.equals("Normal")) {
			sqlStr = "UPDATE customer SET buy=buy+" + price
					+ " WHERE customerName = '" + customerName + "'";
			System.out.println(sqlStr);
		}
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		rs.close();
		stmt.close();
	}

	protected void addSell(String employeeName, int price, String rank)
			throws SQLException {
		System.out.println("db.addSell()");
		String sqlStr = "";
		if (rank.equals("Gold")) {
			sqlStr = "UPDATE employee SET sell=sell+" + (70 * price / 100)
					+ " WHERE employeeName = '" + employeeName + "'";
			System.out.println(sqlStr);
		} else if (rank.equals("Silver")) {
			sqlStr = "UPDATE employee SET sell=sell+" + (80 * price / 100)
					+ " WHERE employeeName = '" + employeeName + "'";
			System.out.println(sqlStr);
		} else if (rank.equals("Bronze")) {
			sqlStr = "UPDATE employee SET sell=sell+" + (90 * price / 100)
					+ " WHERE employeeName = '" + employeeName + "'";
			System.out.println(sqlStr);
		} else if (rank.equals("Normal")) {
			sqlStr = "UPDATE employee SET sell=sell+" + price
					+ " WHERE employeeName = '" + employeeName + "'";
			System.out.println(sqlStr);
		}
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		rs.close();
		stmt.close();
	}

	protected int getBuy(String customerName) throws SQLException {
		System.out.println("db.getBuy()");
		int a = 0;
		String sqlStr = "SELECT buy FROM customer" + " WHERE customerName='"
				+ customerName + "'";
		System.out.println(sqlStr);
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			a = Integer.parseInt(rs.getString("buy"));
		}
		rs.close();
		stmt.close();
		return a;
	}

	protected String getCustomerRank(String customerName) throws SQLException {
		String a = "";
		System.out.println("db.checkCustomerRank");
		String sqlStr = "SELECT rank FROM customer" + " WHERE customerName='"
				+ customerName + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		rs.next();
		a = rs.getString("rank");

		rs.close();
		stmt.close();
		return a;
	}

	protected void setRank(String customerName, int buy) throws SQLException {
		System.out.println("db.setRank()");
		String sqlStr = "";
		if (buy >= 1000000) {
			sqlStr = "UPDATE customer SET rank='Gold' WHERE customerName = '"
					+ customerName + "'";
			System.out.println(sqlStr);
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.close();
			stmt.close();
		} else if (buy >= 500000) {
			sqlStr = "UPDATE customer SET rank='Silver' WHERE customerName = '"
					+ customerName + "'";
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.close();
			stmt.close();
		} else if (buy >= 300000) {
			sqlStr = "UPDATE customer SET rank='Bronze' WHERE customerName = '"
					+ customerName + "'";
			PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			rs.close();
			stmt.close();
		}

	}

	protected void addRecord(String[][] order, int[][] orderPrice, String date,
			int numTable, String rank) throws SQLException {
		System.out.println("db.addRecord()");
		String sqlStr = "";
		for (int i = 0; i < 20; i++) {
			if (order[numTable][i] == null) {
				break;
			} else {
				if (rank.equals("Gold")) {
					sqlStr = "INSERT into sell values ('" + date + "', '"
							+ order[numTable][i] + "', "
							+ (70 * orderPrice[numTable][i] / 100) + ")";
				} else if (rank.equals("Silver")) {
					sqlStr = "INSERT into sell values ('" + date + "', '"
							+ order[numTable][i] + "', "
							+ (80 * orderPrice[numTable][i] / 100) + ")";
				} else if (rank.equals("Bronze")) {
					sqlStr = "INSERT into sell values ('" + date + "', '"
							+ order[numTable][i] + "', "
							+ (90 * orderPrice[numTable][i] / 100) + ")";
				} else if (rank.equals("Normal")) {
					sqlStr = "INSERT into sell values ('" + date + "', '"
							+ order[numTable][i] + "', "
							+ orderPrice[numTable][i] + ")";
				}
				System.out.print(order[numTable][i] + " : ");
				System.out.println(orderPrice[numTable][i]);
				System.out.println(sqlStr);
				PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
				stmt.executeQuery();
				stmt.close();
			}
		}

	}

	protected void addDate(JComboBox<String> box) throws SQLException {
		System.out.println("addDate()");
		box.removeAllItems();
		String sqlStr = "SELECT distinct day as F FROM sell order by day desc";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		System.out.println(sqlStr);
		while (rs.next()) {
			box.addItem(rs.getString("F"));
		}
		rs.close();
		stmt.close();
	}

	// Customer Panel
	protected int findCustomerId(String name) throws SQLException {
		System.out.println("db.findCustomerId()");
		String sqlStr = "SELECT customerName FROM customer WHERE customerName='" + name + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		String a = null;
		while (rs.next()) {
			a = (rs.getString("customerName)"));
		}
		if(a == null){
			return countCustomer();
		}else
			return countCustomer()-1;
		
	}
	
	protected int countCustomer() throws SQLException {
		System.out.println("db.countCustomer()");
		String sqlStr = "SELECT count(customerName) FROM customer";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			customerId = Integer.parseInt(rs.getString("count(customerName)"));
			customerId += 1000;
		}
		rs.close();
		stmt.close();
		return customerId;
	}

	protected void addCustomer(String customerName, int id, int birth, int phone) throws SQLException {
		System.out.println("db.addCustomer()");
		String sqlStr = "INSERT into customer values ('" + customerName + "', "
				+ id + ", " + birth + ", " + phone + ", 'Normal',0)";
		System.out.println(sqlStr);
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
	}

	protected void checkCustomer(String customerName, JTextArea textArea)
			throws SQLException {
		System.out.println("db.checkCustomer()");
		String sqlStr = "SELECT customerName, id, birth, phone, rank, buy FROM customer"
				+ " WHERE customerName='" + customerName + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();

		textArea.setText(" 검색결과없음");
		while (rs.next()) {
			textArea.setText(" 고 객 명 : " + rs.getString("customerName") + "\n"
					+ " 고객 ID : " + rs.getString("id") + "\n 생      일 : "
					+ rs.getString("birth") + "\n 전화번호 : "
					+ rs.getString("phone") + "\n 고객등급 : "
					+ rs.getString("rank") + "\n 총 구매금액 : "
					+ rs.getString("buy") + " 원");
		}
		rs.close();
		stmt.close();
	}

	// SalesPanel

	protected String getSell(String day) throws SQLException {
		System.out.println("db.getSell()");
		String a = "";
		String sqlStr = "SELECT sum(price) FROM sell where day <= '" + day+"'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			a = rs.getString("sum(price)");
		}
		rs.close();
		stmt.close();
		return a;
	}

	protected String getDailySell(String d) throws SQLException {
		System.out.println("db.getDailySell()");
		String a = "";
		String sqlStr = "SELECT sum(price) FROM sell WHERE day='" + d + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			a = rs.getString("sum(price)");
		}
		rs.close();
		stmt.close();
		return a;
	}

	protected String getMaxMenu() throws SQLException {
		System.out.println("db.getMaxMenu()");
		String a = "";
		String sqlStr = "select menuName from SELL group by menuName HAVING COUNT(MENUNAME) >= ALL (select count(menuName) from sell group by menuName)";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			a += rs.getString("menuName") + "\n";
		}
		rs.close();
		stmt.close();
		return a;
	}

	protected String getMinMenu() throws SQLException {
		System.out.println("db.getMinMenu()");
		String a = "";
		String sqlStr = "select menuName from SELL group by menuName HAVING COUNT(MENUNAME) <= ALL (select count(menuName) from sell group by menuName)";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			a += rs.getString("menuName") + "\n ";
		}
		rs.close();
		stmt.close();
		return a;
	}

	protected void checkSales(String d, JTextArea textArea) throws SQLException {
		System.out.println("db.checkSales()");
		textArea.setText(" 일 매출 : " + getDailySell(d)
				+ "\n_________________________\n 가장 많이 팔린 메뉴\n " + getMaxMenu()
				+ "\n 가장 적게 팔린 메뉴\n " + getMinMenu()
				+ "__________________________\n 누적매출 :  " + getSell(d));
	}

	// employee Panel
	
	protected int findEmployeeId(String name) throws SQLException {
		System.out.println("db.findCustomerId()");
		String sqlStr = "SELECT employeeName FROM employee WHERE employeeName='" + name + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		String a = null;
		while (rs.next()) {
			a = (rs.getString("employeeName)"));
		}
		if(a == null){
			return countEmployee();
		}else
			return countEmployee()-1;
		
	}
	
	protected int countEmployee() throws SQLException {
		System.out.println("db.countEmployee");
		String sqlStr = "SELECT count(employeeName) FROM employee";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			employeeId = Integer.parseInt(rs.getString("count(employeeName)"));
			employeeId += 9000;
		}
		rs.close();
		stmt.close();
		return employeeId;
	}

	protected void addEmployee(String employeeName, int id, String rank)
			throws SQLException {
		System.out.println("addEmployee()");
		String sqlStr = "INSERT into employee values ('" + employeeName + "', "
				+ id + " ,'" + rank + "',0)";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		System.out.println(sqlStr);
		stmt.executeQuery();
		stmt.close();
	}

	protected void check_employee(String employeeName, JTextArea textArea)
			throws SQLException {
		System.out.println("db.checkEmployee");
		String sqlStr = "SELECT employeeName, rank, sell FROM employee"
				+ " WHERE employeeName='" + employeeName + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();

		textArea.setText(" 검색결과없음");
		while (rs.next()) {
			textArea.setText(" 직원명 : " + rs.getString("employeeName")
					+ "\n 직   급 : " + rs.getString("rank") + "\n " + "총실적 : "
					+ rs.getString("sell"));

		}
		rs.close();
		stmt.close();
	}

	// menu Panel

	protected int countMenu() throws SQLException {
		System.out.println("db.getNumMenu()");
		int countMenu = 0;
		String sqlStr = "SELECT count(menuName) FROM menu";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);//
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			countMenu = Integer.parseInt(rs.getString("count(menuName)"));
		}
		rs.close();
		stmt.close();
		return countMenu;
	}

	protected void add_menu(String menuName, int price) throws SQLException {
		System.out.println("db.addMenu()");
		String sqlStr = "INSERT into menu values ('" + menuName + "', " + price + ")";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
	}

	protected void checkMenu(String menuName, JTextArea textArea)
			throws SQLException {
		System.out.println("db.checkMenu()");
		String sqlStr = "SELECT menuName, price FROM menu"
				+ " WHERE menuName='" + menuName + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		textArea.setText(" 검색결과없음");
		while (rs.next()) {
			textArea.setText(" 메뉴명 : " + rs.getString("menuName") + "\n"
					+ " 가   격 : " + rs.getString("price"));
		}
		rs.close();
		stmt.close();
	}

}