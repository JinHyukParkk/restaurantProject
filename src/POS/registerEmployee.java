package POS;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class registerEmployee {
	Controller con;
	protected JFrame frame = new JFrame("Staff Enroll");
	protected JPanel panel = new JPanel();
	protected JLabel label1 = new JLabel("   Name");
	protected JLabel label2 = new JLabel("   Rank");

	protected JTextField text1 = new JTextField(5);
	protected JComboBox<String> comoBox = new JComboBox<String>();

	protected JButton buttonAdd = new JButton("Join");
	protected JButton buttonCancel = new JButton("Cancel");

	public registerEmployee(Controller c) {
		this.con = c;
		panel.setLayout(null);
		comoBox.addItem("Supervisor");
		comoBox.addItem("Staff");
		
		label1.setBounds(40, 30, 80, 30);
		label2.setBounds(40, 70, 80, 30);
		
		text1.setBounds(130, 30, 80, 30);
		comoBox.setBounds(130, 70, 80, 30);
		
		buttonAdd.setBounds(260, 30, 80, 30);
		buttonCancel.setBounds(260, 70, 80, 30);
		
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(comoBox);
		
		buttonAdd.addActionListener(c);
		buttonCancel.addActionListener(c);
		panel.add(buttonAdd);
		panel.add(buttonCancel);

		frame.setBounds(300,100,400, 180);
		frame.add(panel);

	}
}
