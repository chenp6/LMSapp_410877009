package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



import LMSapp.LMSapp;
import character.Student;

public class StudentMyClassPanel extends JPanel {

	/**
	 我的課程(學生)
	 */
	public StudentMyClassPanel() {
		setLayout(null);
		setBounds(0,30, 916, 800);
		JLabel title = new JLabel("我的課程");
		title.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		title.setBounds(334, 10, 240, 106);
		add(title);
		JComboBox semesterComboInMyClass = new JComboBox(new Object[] { "請選擇", "107-2", "108-1", "108-2", "109-1" });
		JLabel semesterLabelInMyClass = new JLabel("學期");
		DefaultTableModel tableMInStudentScore;
		tableMInStudentScore = new DefaultTableModel(null, new String[] { "學期", "課程代碼", "課程名稱", "學分", "必/選修", "成績" });
		JTable courseTableInMyClass = new JTable(tableMInStudentScore);
		JScrollPane coursePaneInMyClass = new JScrollPane(courseTableInMyClass);
		TableColumn columnInMyClass = null;
		for (int i = 0; i < 5; i++) {
			columnInMyClass = courseTableInMyClass.getColumnModel().getColumn(i);
			if (i == 2) {
				columnInMyClass.setPreferredWidth(300); // third column is bigger
			} else {
				columnInMyClass.setPreferredWidth(20);
			}
		}
		JButton printTranscriptBtn = new JButton("列印總成績單");
		printTranscriptBtn.setBounds(319, 98, 271, 31);
		add(printTranscriptBtn);
		semesterComboInMyClass.setBounds(368, 139, 120, 40);
		semesterLabelInMyClass.setBounds(498, 144, 100, 30);
		coursePaneInMyClass.setBounds(0, 200, 900, 700);
		add(semesterComboInMyClass);
		add(semesterLabelInMyClass);
		add(coursePaneInMyClass);
		
		semesterComboInMyClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedSemester = (String) semesterComboInMyClass.getSelectedItem();
				FileReader fr;
				try {
					fr = new FileReader("data/課程資料.txt");
					BufferedReader br = new BufferedReader(fr);
					cleanTable(tableMInStudentScore);
					try {
						while (br.ready()) {
							String[] courseInfo = br.readLine().split(" ");
							if (courseInfo[0].equals(selectedSemester)) {
								List studentList = (List) Arrays.asList(courseInfo[7].split(","));
								int studentIndex = studentList.indexOf(LMSapp.userAccount.account);
								String[] studentScore = courseInfo[8].split(",");
								if (studentIndex != -1) {
									Object[] temp = { courseInfo[0], courseInfo[1], courseInfo[2], courseInfo[3],
											courseInfo[5], studentScore[studentIndex] };
									tableMInStudentScore.addRow(temp);
								}
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					add(coursePaneInMyClass);
					fr.close();
				} catch (FileNotFoundException e1) {
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		printTranscriptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(LMSapp.userAccount instanceof Student)
					((Student)LMSapp.userAccount).printScore();
			}
		});
	}
	static void cleanTable(DefaultTableModel tableM) {
		while (tableM.getRowCount() > 0)
			tableM.removeRow(0);
	}
}
