package POS;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class registerClient {
	Controller con;
	protected JFrame frame = new JFrame("Client Enroll");
	protected JPanel panel = new JPanel();
	protected JLabel label1 = new JLabel("   Name");
	protected JLabel label2 = new JLabel("   Birth(4)");
	protected JLabel label3 = new JLabel("   Tel");
	protected JTextField text1 = new JTextField(10);
	protected JTextField text2 = new JTextField(10);
	protected JTextField text3 = new JTextField(10);
	protected JButton buttonJoin = new JButton("Join");
	protected JButton buttonCancel = new JButton("Cancel");

	public registerClient(Controller c) {
		this.con = c;
		
		panel.setLayout(null);

		label1.setBounds(20, 10, 100, 30);
		label2.setBounds(20, 50, 100, 30);
		label3.setBounds(20, 90, 100, 30);
		
		text1.setBounds(150, 10, 80, 30);
		text2.setBounds(150, 50, 80, 30);
		text3.setBounds(150, 90, 80, 30);
		
		buttonJoin.setBounds(250, 20, 100, 30);
		buttonCancel.setBounds(250, 80, 100, 30);
		
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(label3);
		panel.add(text3);

		buttonJoin.addActionListener(c);
		buttonCancel.addActionListener(c);
		panel.add(buttonJoin);
		panel.add(buttonCancel);

		frame.setBounds(500, 400, 400, 180);
		frame.add(panel);

	}
}
