package POS;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginFrame {
	protected JFrame frame = new JFrame("Login");
	protected JPanel panel = new JPanel();
	Controller con;
	protected JLabel idLabel = new JLabel("Name");
	protected JLabel pwdLabel = new JLabel("ID");
	protected JTextField nameInput = new JTextField("¹Ú¼öÁø");
	protected JTextField idInput = new JTextField("9002");
	protected JButton loginButton = new JButton("Login");
	protected JButton logoutButton = new JButton("Logout");
	LoginFrame(Controller c) {
		this.con = c;

		panel.setLayout(null);
		frame.setBounds(600, 300, 320, 130);
		idLabel.setBounds(20, 10, 60, 30);
		pwdLabel.setBounds(20, 50, 60, 30);
		nameInput.setBounds(100, 10, 80, 30);
		idInput.setBounds(100, 50, 80, 30);

		loginButton.setBounds(190, 10, 100, 30);
		logoutButton.setBounds(190, 50, 100, 30);
		
		loginButton.addActionListener(c);
		logoutButton.addActionListener(c);

		panel.add(logoutButton);
		panel.add(idLabel);
		panel.add(pwdLabel);
		panel.add(idInput);
		panel.add(nameInput);
		panel.add(loginButton);

		frame.add(panel);

	}

}
