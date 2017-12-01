package POS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame{
	Controller C;
	protected JFrame frame = new JFrame("Resturant Order Manegement");
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();
	protected JPanel panel3 = new JPanel();
	protected JPanel panel4 = new JPanel();
	protected JPanel panel5 = new JPanel();
	protected JPanel panel6 = new JPanel();

	protected JMenuBar menuBar = new JMenuBar();
	protected JMenu menu = new JMenu("Menu");
	protected JMenuItem open = new JMenuItem("Open");
	protected JMenuItem log_In = new JMenuItem("Login");
	protected JLabel title = new JLabel("Restaurant Order Manegement");
	protected JTextField staffname = new JTextField("");
	protected JTextField staffrank = new JTextField("");
	protected JButton buttonTable[] = new JButton[20];

	protected JTextArea orderText = new JTextArea();
	protected JLabel label1 = new JLabel("Client Name");
	protected JLabel label2 = new JLabel("Table Num");
	protected JLabel label3 = new JLabel("Client Name");
	protected JTextField customerName = new JTextField(10);
	protected JComboBox<String> numTable = new JComboBox<String>();
	protected JButton order = new JButton("Order");
	protected JButton cancel = new JButton("Cancel");
	protected JButton calc = new JButton("Paid");
	protected JButton[] buttonMenu = new JButton[20];
	protected JTabbedPane tabbedPane = new JTabbedPane();
	protected JScrollPane orderTextPanel = new JScrollPane();

	protected JLabel label4 = new JLabel("Client");
	protected JTextField customerName2 = new JTextField(10);
	protected JButton addCustomer = new JButton("Enroll");
	protected JButton checkCustomer = new JButton("Search");
	protected JTextArea customerText = new JTextArea();

	protected JLabel label5 = new JLabel("Date");
	protected JComboBox<String> box = new JComboBox<String>();
	protected JTextArea salesText = new JTextArea();
	protected JScrollPane salesTextPanel = new JScrollPane();
	
	protected JPanel panel7 = new JPanel();
	protected JLabel label6 = new JLabel("Staff");
	protected JTextField employeeName = new JTextField();
	protected JButton addEmployee = new JButton("Enroll");
	protected JButton checkEmployee = new JButton("Search");
	protected JTextArea employeeText = new JTextArea();

	protected JPanel panel8 = new JPanel();
	protected JLabel label7 = new JLabel("Menu");
	protected JTextField menuName = new JTextField();
	protected JButton menuAdd = new JButton("Enroll");
	protected JButton checkMenu = new JButton("Search");
	protected JTextArea menuText = new JTextArea();

	private int X = 700;
	private int Y = 830;

	MainFrame(Controller C) {
		
		this.C = C;
		frame.setLayout(null);
		// menubar
		menuBar.add(menu);
		menu.add(open);
		menu.add(log_In);
		frame.setJMenuBar(menuBar);
		open.addActionListener(C);
		log_In.addActionListener(C);
		//

		// panel1
		panel1.setLayout(new BorderLayout());
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Serif", 2, 40));
		staffname.setHorizontalAlignment(SwingConstants.CENTER);
		staffrank.setHorizontalAlignment(SwingConstants.CENTER);
		staffname.setEditable(false);
		staffrank.setEditable(false);
		staffname.setBounds(620,68,70,30);
		staffrank.setBounds(620,38,70,30);
		panel1.add(staffname);
		panel1.add(staffrank);
		panel1.add("Center",title);
		
		
		// panel2
		panel2.setBorder(new TitledBorder("Table State"));
		panel2.setLayout(new GridLayout(4, 5));
		for (int i = 0; i < 20; i++) {
			buttonTable[i] = new JButton("" + (i + 1));
			panel2.add(buttonTable[i]);
			buttonTable[i].addActionListener(C);
			buttonTable[i].setBackground(Color.WHITE);
		}

		// panel3

		panel3.setBorder(new TitledBorder("OrderList"));
		panel3.setLayout(null);

		for (int i = 0; i < 20; i++) {
			numTable.addItem(i + 1 + "");

		}
		numTable.addActionListener(C);
		order.addActionListener(C);
		cancel.addActionListener(C);
		calc.addActionListener(C);

		// panel3 component locateion
		
		orderTextPanel.setViewportView(orderText);
		orderTextPanel.setBounds(10, 20, 220, 250);
		label1.setBounds(240, 10, 100, 30);
		customerName.setBounds(240, 40, 100, 30);
		label2.setBounds(240, 70, 100, 30);
		numTable.setBounds(240, 100, 100, 30);
		order.setBounds(240, 150, 100, 30);
		cancel.setBounds(240, 190, 100, 30);
		calc.setBounds(240, 230, 100, 30);

		// panel3 add component
		panel3.add(orderTextPanel);
		panel3.add(label1);
		panel3.add(customerName);
		panel3.add(label2);
		panel3.add(numTable);
		panel3.add(order);
		panel3.add(cancel);
		panel3.add(calc);
		
		// panel4
		panel4.setBorder(new TitledBorder("Menu"));
		panel4.setLayout(new GridLayout(10, 2));
		for (int i = 0; i < 20; i++) {
			buttonMenu[i] = new JButton();
			buttonMenu[i].setText(null);
			panel4.add(buttonMenu[i]);
			buttonMenu[i].addActionListener(C);
		}

		// panel5
		panel5.setLayout(null);
		addCustomer.addActionListener(C);
		checkCustomer.addActionListener(C);

		// panel 5 compent location
		label4.setBounds(10, 10, 100, 30);
		customerName2.setBounds(10, 40, 100, 30);
		addCustomer.setBounds(130, 40, 90, 30);
		checkCustomer.setBounds(230, 40, 90, 30);
		customerText.setBounds(10, 100, 310, 250);

		// panel 5 add compontet
		panel5.add(label4);
		panel5.add(customerName2);
		panel5.add(customerText);
		panel5.add(addCustomer);
		panel5.add(checkCustomer);

		// panel 6
		salesTextPanel.setViewportView(salesText);
		panel6.add(salesTextPanel);
		panel6.setLayout(null);
		
		box.addItem("");
		box.addActionListener(C);

		// panel 6 component location
		label5.setBounds(10, 10, 100, 30);
		box.setBounds(10, 40, 150, 30);
		salesTextPanel.setBounds(10, 100, 310, 210);

		// panel 6 add component
		panel6.add(label5);
		panel6.add(box);
		panel6.add(salesTextPanel);
		
		// panel 7
		panel7.setLayout(null);
		addEmployee.addActionListener(C);
		checkEmployee.addActionListener(C);

		// panel 8 component location
		label6.setBounds(10, 10, 100, 30);
		employeeName.setBounds(10, 40, 100, 30);
		addEmployee.setBounds(130, 40, 90, 30);
		checkEmployee.setBounds(230, 40, 90, 30);
		employeeText.setBounds(10, 100, 310, 250);

		// panel 8 add component
		panel7.add(label6);
		panel7.add(employeeName);
		panel7.add(addEmployee);
		panel7.add(checkEmployee);
		panel7.add(employeeText);

		// panel 8
		panel8.setLayout(null);
		menuAdd.addActionListener(C);
		checkMenu.addActionListener(C);

		// panel 8 component location
		label7.setBounds(10, 10, 100, 30);
		menuName.setBounds(10, 40, 120, 30);
		menuAdd.setBounds(140, 40, 80, 30);
		checkMenu.setBounds(230, 40, 80, 30);
		menuText.setBounds(10, 100, 310, 250);

		// panel 8 add component
		panel8.add(label7);
		panel8.add(menuName);
		panel8.add(menuAdd);
		panel8.add(checkMenu);
		panel8.add(menuText);

		// tabbedPane
		tabbedPane.setBorder(new TitledBorder("Enroll/Search"));
		tabbedPane.addTab("Client", panel5);
		tabbedPane.addTab("Sell", panel6);
		tabbedPane.addTab("Staff", panel7);
		tabbedPane.addTab("Menu", panel8);

		// panel Bounds
		panel1.setBounds(0, 0, X, Y / 8);
		panel2.setBounds(0, Y / 8, X / 2, 3 * Y / 8 - 30);
		panel3.setBounds(X / 2, Y / 8, X / 2, 3 * Y / 8 - 30);
		panel4.setBounds(0, Y / 2 - 30, X / 2, Y / 2 - 40);
		tabbedPane.setBounds(X / 2, Y / 2 - 30, X / 2, Y / 2 - 40);

		// frame
		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);
		frame.add(panel4);
		frame.add(tabbedPane);
		frame.setBounds(0, 0, X + 16, Y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
