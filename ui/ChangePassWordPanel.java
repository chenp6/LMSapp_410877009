package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import LMSapp.*;
public class ChangePassWordPanel extends JPanel {

	public ChangePassWordPanel() {
		Font textFont = new Font("textField", Font.PLAIN, 16);
		setBounds(0,30, 916, 800);
		setLayout(null);
		JLabel title = new JLabel("修改密碼");
		title.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		title.setBounds(349, 56, 240, 106);
		add(title);
		JLabel oldPasswordLabel = new JLabel("舊密碼:");
		JPasswordField oldPassword = new JPasswordField(16);
		JLabel newPasswordLabel = new JLabel("新密碼:");
		JPasswordField newPassword = new JPasswordField(16);
		JLabel checkedPasswordLabel = new JLabel("請再輸入一次新密碼:");
		JPasswordField checkedPassword = new JPasswordField(16);
		JButton inSureBtn = new JButton("確認變更");
		oldPasswordLabel.setFont(textFont);
		oldPassword.setFont(textFont);
		newPasswordLabel.setFont(textFont);
		newPassword.setFont(textFont);
		checkedPasswordLabel.setFont(textFont);
		checkedPassword.setFont(textFont);
		oldPasswordLabel.setBounds(265, 193, 75, 34);
		oldPassword.setBounds(349, 193, 294, 40);
		newPasswordLabel.setBounds(265, 264, 75, 34);
		newPassword.setBounds(349, 264, 294, 40);
		checkedPasswordLabel.setBounds(143, 343, 213, 34);
		checkedPassword.setBounds(361, 340, 294, 40);
		inSureBtn.setBounds(349, 432, 131, 66);
		add(oldPasswordLabel);
		add(oldPassword);
		add(newPasswordLabel);
		add(newPassword);
		add(checkedPasswordLabel);
		add(checkedPassword);
		add(inSureBtn);
		
		inSureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newPw = new String(newPassword.getPassword());
				String checkedPw = new String(checkedPassword.getPassword());
				boolean correctPw = false;
				if (newPw.equals(checkedPw)) {
					try {
						correctPw = LMSapp.userAccount.changePassword(new String(oldPassword.getPassword()),
								new String(checkedPassword.getPassword()));
						LMSapp.userAccount.password = newPw;
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					if (correctPw)
						JOptionPane.showMessageDialog(new JFrame(), "密碼變更成功", "變更密碼", JOptionPane.INFORMATION_MESSAGE);
					else {
						JOptionPane.showMessageDialog(new JFrame(), "密碼不符，請重新輸入", "變更密碼", JOptionPane.ERROR_MESSAGE);
						return;
					}
					oldPassword.setText("");
					newPassword.setText("");
					checkedPassword.setText("");
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "密碼不符，請重新輸入", "變更密碼", JOptionPane.ERROR_MESSAGE);
					oldPassword.setText("");
					newPassword.setText("");
					checkedPassword.setText("");
				}
			}
		});
	}
	
}
