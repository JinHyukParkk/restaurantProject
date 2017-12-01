package POS;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Controller implements ActionListener {
	DB_Connect db;
	public File file = null;
	JFileChooser chooser = new JFileChooser();
	MainFrame MF = new MainFrame(this);
	LoginFrame LF = new LoginFrame(this);
	registerClient JF = new registerClient(this);
	registerEmployee AEF = new registerEmployee(this);
	registerMenu MeF = new registerMenu(this);
	Order O = new Order();
	LoadData LD = new LoadData();
	String date;
	String filePath;
	int loginMode = 0;
	int employeeMode = 0;

	public Controller(String id, String pass){	
		db = new DB_Connect(id,pass);
		MF.frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == MF.open) {
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
	        	file = chooser.getSelectedFile();
			}
			if (file != null) {
				filePath = file.getAbsolutePath();
				db.dropTable();
				db.createTable();
				try {
					for (int i = 0; i < 20; i++) {
						MF.buttonMenu[i].setText(null);
					}
					LD.makeArray(filePath);
					db.load(this);
					JOptionPane.showMessageDialog(null, filePath + "\n���� �������ϴ�");
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, "�ùٸ� ������ ������ �������ּ���"); // qw
				}
			} else
				JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�");

		} else if (e.getSource() == MF.log_In) {
			LF.frame.setVisible(true);
		} else if (e.getSource() == LF.loginButton) {
			System.out.println("LF.loginButton");
			try {
				String name = LF.nameInput.getText();
				String id = LF.idInput.getText();
				db.logIn(name, id, this);
				loginMode = 1;
				employeeMode = db.setEmployeeMode(name,id);
				if (employeeMode == 0) {
					JOptionPane.showMessageDialog(null, "�α��� : Staff");
					MF.tabbedPane.remove(MF.panel6);
					MF.addCustomer.setVisible(false);
					MF.addEmployee.setVisible(false);
					MF.menuAdd.setVisible(false);
				} else if (employeeMode == 1) {
					JOptionPane.showMessageDialog(null, "�α��� : Supervisor");
					MF.addCustomer.setVisible(true);
					MF.addEmployee.setVisible(true);
					MF.menuAdd.setVisible(true);
					MF.tabbedPane.remove(MF.panel7);
					MF.tabbedPane.remove(MF.panel8);
					MF.tabbedPane.addTab("Sell", MF.panel6);
					MF.tabbedPane.addTab("Staff", MF.panel7);
					MF.tabbedPane.addTab("Menu", MF.panel8);
					db.addDate(MF.box);
				}
				LF.frame.setVisible(false);
			} catch (SQLException e1) {
				LF.nameInput.setText("");
				LF.idInput.setText("");
				LF.nameInput.requestFocus();
				JOptionPane.showMessageDialog(null, "�̸��� �����ȣ�� �ٽ� Ȯ���� �ּ���");
			}
		} else if (e.getSource() == LF.logoutButton) {
			System.out.println("LF.logoutButton");
			if (loginMode == 1) {
				loginMode = 0;
				MF.staffname.setText("");
				MF.staffrank.setText("");
				JOptionPane.showMessageDialog(null, "�α׾ƿ� �Ǿ����ϴ�.");
				LF.frame.setVisible(false);
			} else
				JOptionPane.showMessageDialog(null, "�α��εǾ� �����ʽ��ϴ�.");
		}
		// MF
		if (loginMode == 1) {
			for (int i = 0; i < 20; i++) {
				if (e.getSource() == MF.buttonTable[i]) {	// ���̺� Ŭ�� �� ��  
					System.out.println("MF.buttonTable[" + i + "]");
					MF.numTable.setSelectedIndex(i);
				}
			}

			for (int i = 0; i < 20; i++) {
				if (e.getSource() == MF.buttonMenu[i]) {	// �޴� ��ư ���� �� 
					try {
						O.setBuffer(MF.buttonMenu[i]);
						db.setBufferPrice(O.buffer, O.bufferPrice);
						O.setBufferText(MF.orderText);
					} catch (SQLException e1) {
						System.out.println(e1);
					}
				}
			}
			if (e.getSource() == MF.numTable) {	// �ѹ����̺� �ٲٸ�
				System.out.println("MF.numTable");
				O.loadorder(MF.numTable.getSelectedIndex());
				if (O.sumPrice[MF.numTable.getSelectedIndex()] == 0) {
					O.setTextNull(MF.orderText);
				} else
					O.setText(MF.numTable.getSelectedIndex(), MF.orderText);
			}

			else if (e.getSource() == MF.order) {
				System.out.println("MF.order");
				try {
					O.sumPrice[MF.numTable.getSelectedIndex()] = 0;
					O.addOrder(MF.numTable.getSelectedIndex());// ���ۿ� �ִ°� �ø���
					db.setPrice(O.order, O.orderPrice);// ������ �����̽� �����ϰ�
					O.setSumPrice(MF.numTable.getSelectedIndex());
					if (O.buffer[0] == null) {
						JOptionPane.showMessageDialog(null, "�޴��� ����ּ���");
					} else {
						int select = MF.numTable.getSelectedIndex();
						O.setText(select, MF.orderText);
						MF.buttonTable[select].setBackground(Color.YELLOW);
						JOptionPane.showMessageDialog(null,(MF.numTable.getSelectedIndex() + 1)+ "�� ���̺� �ֹ� �Ϸ�");
					}
				} catch (SQLException e1) {
					System.out.println("MF.order"+e1);
				}

			} else if (e.getSource() == MF.cancel) {
				System.out.println("MF.cancel");
				if (O.sumPrice[MF.numTable.getSelectedIndex()] == 0) {
					O.cancel(MF.numTable.getSelectedIndex(), MF.orderText);
					JOptionPane.showMessageDialog(null, "�ֹ� ������ �����ϴ�.");
				} else {
					int select = MF.numTable.getSelectedIndex();
					O.cancel(select, MF.orderText);
					MF.buttonTable[select].setBackground(Color.WHITE);
					JOptionPane.showMessageDialog(null,MF.numTable.getSelectedIndex() + 1 + "�� ���̺� �ֹ� ���");
				}

			} else if (e.getSource() == MF.calc) {
				System.out.println("MF.calc");
				SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd");
			    date = fm1.format(new Date());
			    int select = MF.numTable.getSelectedIndex();
				try {
					if (MF.customerName.getText().isEmpty()) {// ������������
						MF.customerName.setText("��ȸ��");
						JOptionPane.showMessageDialog(null,"���� ���� �Էµ��� �ʾ� ��ȸ������ ����");
					}
					if (MF.customerName.getText().equals("��ȸ��")) {
						db.addBuy(MF.customerName.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addSell(LF.nameInput.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addRecord(O.order, O.orderPrice, date,select, "Normal");
						db.addDate(MF.box);
						
						if (O.sumPrice[select] == 0) {
							JOptionPane.showMessageDialog(null, "�ֹ� ������ �����ϴ�.");// qw
						} else {
							JOptionPane.showMessageDialog(null,+O.sumPrice[select]+ "�� ���� �Ϸ�");
							O.cancel(select,MF.orderText);//
							MF.buttonTable[select].setBackground(Color.WHITE);
						}
					} else {
						db.addBuy(MF.customerName.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addSell(LF.nameInput.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addRecord(O.order, O.orderPrice, date,select,db.getCustomerRank(MF.customerName.getText()));
						db.addDate(MF.box);
						if (O.sumPrice[select] == 0) {
							JOptionPane.showMessageDialog(null, "�ֹ� ������ �����ϴ�.");// qw
						} else {
							if (db.getCustomerRank(MF.customerName.getText())
									.equals("Gold")) {
								JOptionPane.showMessageDialog(null,"���� ���� : "+ (70 * O.sumPrice[MF.numTable.getSelectedIndex()] / 100)+ "�� ����");
							} else if (db.getCustomerRank(
									MF.customerName.getText()).equals("Silver")) {
								JOptionPane.showMessageDialog(null,"���� ���� : "+ (80 * O.sumPrice[MF.numTable.getSelectedIndex()] / 100)+ "�� ����");
							} else if (db.getCustomerRank(
									MF.customerName.getText()).equals("Bronze")) {
								JOptionPane.showMessageDialog(null,"���� ���� : "+ (90 * O.sumPrice[MF.numTable.getSelectedIndex()] / 100)+ "�� ����");
							} else if (db.getCustomerRank(
									MF.customerName.getText()).equals("Normal")) {
								JOptionPane.showMessageDialog(null,O.sumPrice[MF.numTable.getSelectedIndex()] + "�� ����");
							}
							db.setRank(MF.customerName.getText(),db.getBuy(MF.customerName.getText()));
							O.cancel(select,MF.orderText);//
							MF.buttonTable[select].setBackground(Color.WHITE);
						}
					}
					MF.customerName.setText("");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "��������.");
				}

			} else if (e.getSource() == MF.addCustomer) {
				System.out.println("MF.addCustomer");
				JF.frame.setVisible(true);
			} else if (e.getSource() == MF.checkCustomer) {
				System.out.println("MF.checkCustomer");
				try {
					if (MF.customerName2.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "������ �Է����ּ���");// qw
					} else {
						db.checkCustomer(MF.customerName2.getText(),MF.customerText);
						MF.customerName2.setText("");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} else if (e.getSource() == MF.box) {
				try {
					db.checkSales((String) MF.box.getSelectedItem(),MF.salesText);
				} catch (SQLException e1) {
					System.out.println("MF.box error");
				}
			} else if (e.getSource() == MF.addEmployee) {
				System.out.println("MF.addEmployee");
				AEF.frame.setVisible(true);
			} else if (e.getSource() == MF.checkEmployee) {
				try {
					if (MF.employeeName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "�������� �Է����ּ���");// qw
					} else {
						db.check_employee(MF.employeeName.getText(),MF.employeeText);
						MF.employeeName.setText("");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.out.println("MF.checkEmployee");
				
			} else if (e.getSource() == MF.menuAdd) {
				System.out.println("MF.menuAdd");
				MeF.frame.setVisible(true);
			} else if (e.getSource() == MF.checkMenu) {
				try {
					if (MF.menuName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "�޴����� �Է����ּ���");// qw
					} else {
						db.checkMenu(MF.menuName.getText(), MF.menuText);
						MF.menuName.setText("");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.out.println("MF.checkMenu");
			}
			
			// JF
			else if (e.getSource() == JF.buttonJoin) {
				System.out.println("JF.buttonJoin");
				try {
					System.out.println("count : " + db.countCustomer());
					String add_name = JF.text1.getText();
					String add_birth = JF.text2.getText();
					String add_tel = JF.text3.getText();
					db.addCustomer(add_name, db.findCustomerId(add_name),
							Integer.valueOf(add_birth),
							Integer.valueOf(add_tel));
					JOptionPane.showMessageDialog(null, "�� ��� �Ϸ�");
					JF.text1.setText("");
					JF.text2.setText("");
					JF.text3.setText("");
					JF.text1.requestFocus();
					JF.frame.setVisible(false);
				} catch (NumberFormatException e1) {
					e1.getStackTrace();
					JF.text2.setText("");
					JF.text3.setText("");
					JF.text2.requestFocus();
					JOptionPane.showMessageDialog(null, "�ùٸ� ���ϰ� ���� �Է��� �ּ���");
				} catch (SQLException e1) {
					e1.getStackTrace();
					JF.text1.setText("");
					JF.text2.setText("");
					JF.text3.setText("");
					JF.text1.requestFocus();
					JOptionPane.showMessageDialog(null, "�ٽ� �Է��� �ּ���");
				}
			} else if (e.getSource() == JF.buttonCancel) {
				System.out.println("JF.buttonCancel");
				JF.text1.setText("");
				JF.text2.setText("");
				JF.text3.setText("");
				JF.text1.requestFocus();
				JF.frame.setVisible(false);
			}

			// AEF
			else if (e.getSource() == AEF.buttonAdd) {
				System.out.println("dfd" + AEF.text1.getText());
				System.out.println("AEF.buttonAdd");
				if (AEF.text1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�������� �Է����ּ���");
				} else {
					try {
						db.addEmployee(AEF.text1.getText(), db.findEmployeeId(AEF.text1.getText()),
								(String) AEF.comoBox.getSelectedItem());
						JOptionPane.showMessageDialog(null, "���� ��� �Ϸ�");
						AEF.text1.setText("");
						AEF.text1.requestFocus();
						AEF.frame.setVisible(false);
					} catch (NumberFormatException e1) {
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "������ ������ �����մϴ�");
						AEF.text1.setText("");
						AEF.text1.requestFocus();
					}
				}
			} else if (e.getSource() == AEF.buttonCancel) {
				System.out.println("AEF.buttonCancel");
				AEF.text1.setText("");
				AEF.text1.requestFocus();
				AEF.frame.setVisible(false);
			}

			// Mef
			else if (e.getSource() == MeF.buttonAdd) {
				try {
					if (db.countMenu() < 20) {
						db.add_menu(MeF.text1.getText(),Integer.valueOf(MeF.text2.getText()));
						MF.buttonMenu[db.countMenu() - 1].setText(MeF.text1.getText());
						JOptionPane.showMessageDialog(null, "�޴� � �Ϸ�.");
						System.out.println("�޴���:" + db.countMenu());
						MeF.text1.setText("");
						MeF.text2.setText("");
						MeF.text1.requestFocus();
						MeF.frame.setVisible(false);
					} else
						JOptionPane.showMessageDialog(null, "�޴� 20�� �ʰ�");
				} catch (NumberFormatException e1) {
					MeF.text2.setText("");
					MeF.text2.requestFocus();
					JOptionPane.showMessageDialog(null, "�ùٸ� ������ �Է��� �ּ���");
				} catch (SQLException e1) {
					MeF.text1.setText("");
					MeF.text2.setText("");
					MeF.text1.requestFocus();
					JOptionPane.showMessageDialog(null, "�ٽ� �Է��� �ּ���");
				}
				System.out.println("MeF.buttonAdd");
			} else if (e.getSource() == MeF.buttonCancel) {
				System.out.println("MeF.buttonCancel");
				MeF.text1.setText("");
				MeF.text2.setText("");
				MeF.frame.setVisible(false);
			}
		}
	}
}
