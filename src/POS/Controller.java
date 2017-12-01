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
					JOptionPane.showMessageDialog(null, filePath + "\n파일 열었습니다");
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, "올바른 데이터 파일을 선택해주세요"); // qw
				}
			} else
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다");

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
					JOptionPane.showMessageDialog(null, "로그인 : Staff");
					MF.tabbedPane.remove(MF.panel6);
					MF.addCustomer.setVisible(false);
					MF.addEmployee.setVisible(false);
					MF.menuAdd.setVisible(false);
				} else if (employeeMode == 1) {
					JOptionPane.showMessageDialog(null, "로그인 : Supervisor");
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
				JOptionPane.showMessageDialog(null, "이름과 사원번호를 다시 확인해 주세요");
			}
		} else if (e.getSource() == LF.logoutButton) {
			System.out.println("LF.logoutButton");
			if (loginMode == 1) {
				loginMode = 0;
				MF.staffname.setText("");
				MF.staffrank.setText("");
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
				LF.frame.setVisible(false);
			} else
				JOptionPane.showMessageDialog(null, "로그인되어 있지않습니다.");
		}
		// MF
		if (loginMode == 1) {
			for (int i = 0; i < 20; i++) {
				if (e.getSource() == MF.buttonTable[i]) {	// 테이블 클릭 할 때  
					System.out.println("MF.buttonTable[" + i + "]");
					MF.numTable.setSelectedIndex(i);
				}
			}

			for (int i = 0; i < 20; i++) {
				if (e.getSource() == MF.buttonMenu[i]) {	// 메뉴 버튼 누를 때 
					try {
						O.setBuffer(MF.buttonMenu[i]);
						db.setBufferPrice(O.buffer, O.bufferPrice);
						O.setBufferText(MF.orderText);
					} catch (SQLException e1) {
						System.out.println(e1);
					}
				}
			}
			if (e.getSource() == MF.numTable) {	// 넘버테이블 바꾸면
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
					O.addOrder(MF.numTable.getSelectedIndex());// 버퍼에 있는거 올리고
					db.setPrice(O.order, O.orderPrice);// 오더의 프라이스 설정하고
					O.setSumPrice(MF.numTable.getSelectedIndex());
					if (O.buffer[0] == null) {
						JOptionPane.showMessageDialog(null, "메뉴를 골라주세요");
					} else {
						int select = MF.numTable.getSelectedIndex();
						O.setText(select, MF.orderText);
						MF.buttonTable[select].setBackground(Color.YELLOW);
						JOptionPane.showMessageDialog(null,(MF.numTable.getSelectedIndex() + 1)+ "번 테이블 주문 완료");
					}
				} catch (SQLException e1) {
					System.out.println("MF.order"+e1);
				}

			} else if (e.getSource() == MF.cancel) {
				System.out.println("MF.cancel");
				if (O.sumPrice[MF.numTable.getSelectedIndex()] == 0) {
					O.cancel(MF.numTable.getSelectedIndex(), MF.orderText);
					JOptionPane.showMessageDialog(null, "주문 내역이 없습니다.");
				} else {
					int select = MF.numTable.getSelectedIndex();
					O.cancel(select, MF.orderText);
					MF.buttonTable[select].setBackground(Color.WHITE);
					JOptionPane.showMessageDialog(null,MF.numTable.getSelectedIndex() + 1 + "번 테이블 주문 취소");
				}

			} else if (e.getSource() == MF.calc) {
				System.out.println("MF.calc");
				SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd");
			    date = fm1.format(new Date());
			    int select = MF.numTable.getSelectedIndex();
				try {
					if (MF.customerName.getText().isEmpty()) {// 고객정보없으면
						MF.customerName.setText("비회원");
						JOptionPane.showMessageDialog(null,"결제 고객이 입력되지 않아 비회원으로 결제");
					}
					if (MF.customerName.getText().equals("비회원")) {
						db.addBuy(MF.customerName.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addSell(LF.nameInput.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addRecord(O.order, O.orderPrice, date,select, "Normal");
						db.addDate(MF.box);
						
						if (O.sumPrice[select] == 0) {
							JOptionPane.showMessageDialog(null, "주문 내역이 없습니다.");// qw
						} else {
							JOptionPane.showMessageDialog(null,+O.sumPrice[select]+ "원 결제 완료");
							O.cancel(select,MF.orderText);//
							MF.buttonTable[select].setBackground(Color.WHITE);
						}
					} else {
						db.addBuy(MF.customerName.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addSell(LF.nameInput.getText(),O.sumPrice[select],db.getCustomerRank(MF.customerName.getText()));
						db.addRecord(O.order, O.orderPrice, date,select,db.getCustomerRank(MF.customerName.getText()));
						db.addDate(MF.box);
						if (O.sumPrice[select] == 0) {
							JOptionPane.showMessageDialog(null, "주문 내역이 없습니다.");// qw
						} else {
							if (db.getCustomerRank(MF.customerName.getText())
									.equals("Gold")) {
								JOptionPane.showMessageDialog(null,"할인 가격 : "+ (70 * O.sumPrice[MF.numTable.getSelectedIndex()] / 100)+ "원 결제");
							} else if (db.getCustomerRank(
									MF.customerName.getText()).equals("Silver")) {
								JOptionPane.showMessageDialog(null,"할인 가격 : "+ (80 * O.sumPrice[MF.numTable.getSelectedIndex()] / 100)+ "원 결제");
							} else if (db.getCustomerRank(
									MF.customerName.getText()).equals("Bronze")) {
								JOptionPane.showMessageDialog(null,"할인 가격 : "+ (90 * O.sumPrice[MF.numTable.getSelectedIndex()] / 100)+ "원 결제");
							} else if (db.getCustomerRank(
									MF.customerName.getText()).equals("Normal")) {
								JOptionPane.showMessageDialog(null,O.sumPrice[MF.numTable.getSelectedIndex()] + "원 결제");
							}
							db.setRank(MF.customerName.getText(),db.getBuy(MF.customerName.getText()));
							O.cancel(select,MF.orderText);//
							MF.buttonTable[select].setBackground(Color.WHITE);
						}
					}
					MF.customerName.setText("");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "결제오류.");
				}

			} else if (e.getSource() == MF.addCustomer) {
				System.out.println("MF.addCustomer");
				JF.frame.setVisible(true);
			} else if (e.getSource() == MF.checkCustomer) {
				System.out.println("MF.checkCustomer");
				try {
					if (MF.customerName2.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "고객명을 입력해주세요");// qw
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
						JOptionPane.showMessageDialog(null, "직원명을 입력해주세요");// qw
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
						JOptionPane.showMessageDialog(null, "메뉴명을 입력해주세요");// qw
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
					JOptionPane.showMessageDialog(null, "고객 등록 완료");
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
					JOptionPane.showMessageDialog(null, "올바른 생일과 숫자 입력해 주세요");
				} catch (SQLException e1) {
					e1.getStackTrace();
					JF.text1.setText("");
					JF.text2.setText("");
					JF.text3.setText("");
					JF.text1.requestFocus();
					JOptionPane.showMessageDialog(null, "다시 입력해 주세요");
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
					JOptionPane.showMessageDialog(null, "직원명을 입력해주세요");
				} else {
					try {
						db.addEmployee(AEF.text1.getText(), db.findEmployeeId(AEF.text1.getText()),
								(String) AEF.comoBox.getSelectedItem());
						JOptionPane.showMessageDialog(null, "직원 등록 완료");
						AEF.text1.setText("");
						AEF.text1.requestFocus();
						AEF.frame.setVisible(false);
					} catch (NumberFormatException e1) {
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "동일한 직원이 존재합니다");
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
						JOptionPane.showMessageDialog(null, "메뉴 등륵 완료.");
						System.out.println("메뉴수:" + db.countMenu());
						MeF.text1.setText("");
						MeF.text2.setText("");
						MeF.text1.requestFocus();
						MeF.frame.setVisible(false);
					} else
						JOptionPane.showMessageDialog(null, "메뉴 20개 초과");
				} catch (NumberFormatException e1) {
					MeF.text2.setText("");
					MeF.text2.requestFocus();
					JOptionPane.showMessageDialog(null, "올바른 가격을 입력해 주세요");
				} catch (SQLException e1) {
					MeF.text1.setText("");
					MeF.text2.setText("");
					MeF.text1.requestFocus();
					JOptionPane.showMessageDialog(null, "다시 입력해 주세요");
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
