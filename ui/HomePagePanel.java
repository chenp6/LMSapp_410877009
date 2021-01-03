package ui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

import LMSapp.LMSapp;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class HomePagePanel extends JPanel {

	public HomePagePanel() {
		setBounds(0,30, 916, 800);
		setLayout(null);
		String characterStr="";
		switch (LMSapp.userAccount.character) {
		case 'm':
			characterStr = "管理員";
			LMSapp.frame.add(new ManagerMenu());
			break;
		case 'p':
			characterStr = "教授";
			LMSapp.frame.add(new ProfessorMenu());
			break;
		case 's':
			characterStr = "同學";
			LMSapp.frame.add(new StudentMenu());
			break;
		}
		String welcomeStr = "歡迎回來!!!  "+LMSapp.userAccount.name+" "+characterStr+"，\n請按上方選單按鈕進行操作";
		
		JTextPane welcomeText = new JTextPane();
		welcomeText.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		welcomeText.setText(welcomeStr);
		welcomeText.setBounds(96, 200, 795, 489);
		welcomeText.setEditable(false);
		welcomeText.setBackground(getBackground());
		add(welcomeText);
	}
}
