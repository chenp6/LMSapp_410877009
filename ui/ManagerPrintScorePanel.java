package ui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import LMSapp.LMSapp;
import character.Manager;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class ManagerPrintScorePanel extends JPanel {


	public ManagerPrintScorePanel() {
		setLayout(null);
		setBounds(0,30, 916, 800);
		JLabel titleInPrintScore = new JLabel("列印學生學期成績");
		titleInPrintScore.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		titleInPrintScore.setBounds(218, 0, 480, 106);
		add(titleInPrintScore);
		JComboBox semesterComboInPrintScore = new JComboBox(
				new Object[] { "請選擇", "107-2", "108-1", "108-2", "109-1" });
		semesterComboInPrintScore.setBounds(398, 187, 173, 34);
		add(semesterComboInPrintScore);
		JLabel semesterChoiceInPrintScore = new JLabel("學期:");
		semesterChoiceInPrintScore.setBounds(336, 185, 58, 38);
		add(semesterChoiceInPrintScore);
		JButton startChangeBtnInPrintScore = new JButton("開始");

		startChangeBtnInPrintScore.setBounds(408, 247, 111, 31);
		add(startChangeBtnInPrintScore);
		

		
		JTextField accountTextField = new JTextField();
		accountTextField.setBounds(398, 124, 227, 40);
		add(accountTextField);
		accountTextField.setColumns(9);
		
		JLabel accountInPrintScore = new JLabel("輸入學生學號:");
		accountInPrintScore.setBounds(317, 126, 89, 38);
		add(accountInPrintScore);
		
		JPanel actionPanelInPrintScore = new JPanel();
		actionPanelInPrintScore.setBounds(15, 269, 896, 603);
		actionPanelInPrintScore.setLayout(null);
		add(actionPanelInPrintScore);

		startChangeBtnInPrintScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPanelInPrintScore.removeAll();
				actionPanelInPrintScore.repaint();
				actionPanelInPrintScore.revalidate();
				String account = accountTextField.getText();
				String selectedSemester = (String) semesterComboInPrintScore.getSelectedItem();
				JButton printBtnInPrintScore = new JButton("列印成績單");
				printBtnInPrintScore.setBounds(618,0, 154, 30);
				actionPanelInPrintScore.add(printBtnInPrintScore);
				printBtnInPrintScore.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(LMSapp.userAccount instanceof Manager)
							try {
								((Manager)LMSapp.userAccount).printScore(selectedSemester,account);
							} catch (IOException e) {}
					}
				});
				DefaultTableModel tableMInPrintScore;
				tableMInPrintScore = new DefaultTableModel(null, new String[] { "課程代碼", "課程名稱", "學分", "必/選修", "成績" });
				JTable courseTableInPrintScore = new JTable(tableMInPrintScore);
				JScrollPane coursePaneInPrintScore = new JScrollPane(courseTableInPrintScore);
				TableColumn columnInPrintScore = null;
				for (int i = 0; i < 4; i++) {
					columnInPrintScore = courseTableInPrintScore.getColumnModel().getColumn(i);
					if (i == 1) {
						columnInPrintScore.setPreferredWidth(300); // third column is bigger
					} else {
						columnInPrintScore.setPreferredWidth(20);
					}
				}
				FileReader fr;
				try {
					fr = new FileReader("data/課程資料.txt");
					BufferedReader br = new BufferedReader(fr);
					cleanTable(tableMInPrintScore);
					try {
						while (br.ready()) {
							String[] courseInfo = br.readLine().split(" ");
							if (courseInfo[0].equals(selectedSemester)) {
								List studentList = (List) Arrays.asList(courseInfo[7].split(","));
								int studentIndex = studentList.indexOf(account);
								String[] studentScore = courseInfo[8].split(",");
								if (studentIndex != -1) {
									//課程代碼", "課程名稱", "學分", "必/選修", "成績"
									Object[] temp = { courseInfo[1], courseInfo[2],
											courseInfo[3], courseInfo[5],studentScore[studentIndex] };
									tableMInPrintScore.addRow(temp);
								}
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					coursePaneInPrintScore.setBounds(8, 30, 850, 600);
					actionPanelInPrintScore.add(coursePaneInPrintScore);
					actionPanelInPrintScore.repaint();
					fr.close();
				} catch (FileNotFoundException e1) {
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
				
			}
		});
		
	}
	static void cleanTable(DefaultTableModel tableM) {
		while (tableM.getRowCount() > 0)
			tableM.removeRow(0);
	}
}
