package POS;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Order {
	String[] buffer = new String[20];
	int[] bufferPrice = new int[20];
	String[][] order = new String[20][20];
	int[][] orderPrice = new int[20][20];
	int[] sumPrice = new int[20];

	protected void resetBuffer() {
		System.out.println("O.reserBuffer()");
		for (int i = 0; i < 20; i++) {
			buffer[i] = null;
			bufferPrice[i] = 0;
		}
	}

	protected void resetOrder(int numTable) {
		System.out.println("O.reserOrder()");
		for (int i = 0; i < 20; i++) {
			order[numTable][i] = null;
			orderPrice[numTable][i] = 0;
		}
		sumPrice[numTable] = 0;
	}

	protected void setSumPrice(int numTable) {
		for (int i = 0; i < 20; i++) {
			sumPrice[numTable] += orderPrice[numTable][i];
		}
	}

	protected void setBuffer(JButton buttonMenu) {
		for (int i = 0; i < 20; i++) {
			if (buffer[i] == null) {
				buffer[i] = buttonMenu.getText();
				break;
			}
		}
		if (buffer[19] != null) {
			JOptionPane.showMessageDialog(null, "20개이상 주문 할수 없습니다.");
		}
	}

	protected void addOrder(int numTable) {
		for (int i = 0; i < 20; i++) {
			order[numTable][i] = buffer[i];
		}
	}

	protected void loadorder(int numTable) {
		for (int i = 0; i < 20; i++) {
			buffer[i] = order[numTable][i];
		}
	}

	protected void cancel(int numTable, JTextArea orderText) {
		System.out.println("cancel()");
		resetBuffer();
		resetOrder(numTable);
		setTextNull(orderText);
	}

	protected void setBufferText(JTextArea orderText) {
		String str = " ===========주문 목록============\n";
		for (int i = 0; i < 20; i++) {
			if (buffer[i] != null) {
				str += " " + buffer[i] + "\t\t" + bufferPrice[i] + "\n";
			} else
				break;
		}
		orderText.setText(str);
		str = "";
	}

	protected void setTextNull(JTextArea orderText) {
		orderText.setText(" 주문이 없습니다");
	}

	protected void setText(int numTable, JTextArea orderText) {
		System.out.println("O.setText()");
		String rank = "";
		String str = " ===========주문 목록============\n";
		for (int i = 0; i < 20; i++) {
			if (order[numTable][i] != null) {
				str += " " + order[numTable][i] + "\t\t"+ orderPrice[numTable][i] + "\n";
			} else
				break;
		}
		str += " ______________________________\n 총 합계\t\t" + sumPrice[numTable];

		if (rank.equals("Gold")) {
			str += "\n 총 결제               " + (70 * sumPrice[numTable] / 100);
		} else if (rank.equals("Silver")) {
			str += "\n 총 결제               " + (80 * sumPrice[numTable] / 100);
		} else if (rank.equals("Bronze")) {
			str += "\n 총 결제               " + (90 * sumPrice[numTable] / 100);
		} else if (rank.equals("Normal")) {
			str += "\n 총 결제               " + (100 * sumPrice[numTable] / 100);
		}
		orderText.setText(str);
		str = "";
	}
}
