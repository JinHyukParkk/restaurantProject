package POS;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class registerMenu {
	Controller con;
	protected JFrame frame = new JFrame("Menu Enroll");
	protected JPanel panel = new JPanel();
	protected JLabel label1 = new JLabel("   Menu");
	protected JLabel label2 = new JLabel("   Price");

	protected JTextField text1 = new JTextField();
	protected JTextField text2 = new JTextField();

	protected JButton buttonAdd = new JButton("Enroll");
	protected JButton buttonCancel = new JButton("Cancel");

	public registerMenu(Controller c) {
		this.con = c;
		
		panel.setLayout(null);
		label1.setBounds(20, 10, 100, 30);
		label2.setBounds(20, 50, 100, 30);
		text1.setBounds(100, 10, 120, 30);
		text2.setBounds(100, 50, 120, 30);
		buttonAdd.setBounds(250, 10, 100, 30);
		buttonCancel.setBounds(250, 50, 100, 30);

		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);

		buttonAdd.addActionListener(c);
		buttonCancel.addActionListener(c);
		panel.add(buttonAdd);
		panel.add(buttonCancel);

		frame.setBounds(500, 400, 400, 150);
		frame.add(panel);

	}
}
