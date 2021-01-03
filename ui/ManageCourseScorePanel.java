package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import LMSapp.*;
import character.Manager;

public class ManageCourseScorePanel extends JPanel {
	
	public ManageCourseScorePanel() {
		setLayout(null);
		setBounds(0,30, 916, 800);
		JLabel titleInManageCourseScore= new JLabel("學生成績管理");
		 titleInManageCourseScore.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		 titleInManageCourseScore.setBounds(300, 0, 491, 106);
		add(titleInManageCourseScore);		 
		JLabel semesterLabelInManageCourseScore = new JLabel("學期:");
		semesterLabelInManageCourseScore.setHorizontalAlignment(SwingConstants.CENTER);
		semesterLabelInManageCourseScore.setBounds(322,100, 71, 23);
		add(semesterLabelInManageCourseScore);
		JComboBox semesterComboInManageCourseScore = new JComboBox(
				new Object[] { "請選擇", "107-2", "108-1", "108-2", "109-1" });
		semesterComboInManageCourseScore.setBounds(425, 100, 173, 34);
		add(semesterComboInManageCourseScore);
		JLabel courseLabelInManageCourseScore = new JLabel("課程名稱:");
		courseLabelInManageCourseScore.setHorizontalAlignment(SwingConstants.CENTER);
		courseLabelInManageCourseScore .setBounds(255, 156, 155, 23);
		add(courseLabelInManageCourseScore);
		JComboBox courseComboInManageCourseScore =  new JComboBox(new Object[] {"請選擇"});
		courseComboInManageCourseScore.setBounds(425, 150, 250, 34);
		add(courseComboInManageCourseScore);
		semesterComboInManageCourseScore.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
			      if(e.getStateChange() == ItemEvent.SELECTED) {
			    	  String selectedSemester = (String)semesterComboInManageCourseScore.getSelectedItem();
			    	  Object[] courseArr = showSemesterCourse(selectedSemester);
			    	  courseComboInManageCourseScore.removeAllItems();
			    	  for(int i=0;i<courseArr.length;i++)
			    		  courseComboInManageCourseScore.addItem(courseArr[i]);			    	  
			       }				
			} 
		});
		JButton startBtnInManageCourseScore = new JButton("開始");
		startBtnInManageCourseScore.setBounds(400, 200, 100, 34);
		add(startBtnInManageCourseScore);
		DefaultTableModel tableMInCourseScore;
		tableMInCourseScore = new DefaultTableModel(null, new String[] { "學號", "學生姓名","成績"});

		
		
		startBtnInManageCourseScore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cleanTable(tableMInCourseScore);
					String selectedSemester = (String)semesterComboInManageCourseScore.getSelectedItem();
					String selectedCourse[] = ((String)courseComboInManageCourseScore.getSelectedItem()).split(" ");
					BufferedReader br = new BufferedReader(new FileReader("data/課程資料.txt"));
					while(br.ready()) {
						JTable tableInCourseScore =  new JTable(tableMInCourseScore) {
							public boolean isCellEditable(int row, int column) {
								if (column == 2)
									return true;
								else
									return false;
							}// 表格不允許被編輯
						};
						String[] courseInfoInCourseScore = br.readLine().split(" ");
						if(selectedSemester.equals(courseInfoInCourseScore[0])&&
								selectedCourse[0].equals(courseInfoInCourseScore[1])) {
								Object[] studentAccount = courseInfoInCourseScore[7].split(",");
								Object[] studentName = courseInfoInCourseScore[6].split(",");
								Object[] studentScore = courseInfoInCourseScore[8].split(",");
								for(int i=0;i<studentAccount.length;i++)
									tableMInCourseScore.addRow(new Object[] {studentAccount[i],studentName[i],studentScore[i]});
								JScrollPane accountPaneInCourseScore = new JScrollPane(tableInCourseScore);
								accountPaneInCourseScore.setBounds(25, 250, 850, 300);
								accountPaneInCourseScore .setVisible(false);
								getThisJPanel().add(accountPaneInCourseScore);
								accountPaneInCourseScore .setVisible(true);
								JButton saveChangeBtnInCourseScore = new JButton("儲存變更");
								saveChangeBtnInCourseScore.setVisible(false);
								saveChangeBtnInCourseScore.setBounds(400, 570, 100, 34);
								getThisJPanel().add(saveChangeBtnInCourseScore);
								saveChangeBtnInCourseScore.setVisible(true);
								saveChangeBtnInCourseScore.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent arg0) {
										try {
											if(LMSapp.userAccount instanceof Manager) {
												Object[][] tableData = getTableAsArray(tableInCourseScore);
												((Manager)LMSapp.userAccount).saveScore(tableData,selectedSemester,selectedCourse[0]);
											}
										} catch (IOException e) {}
									}
									
								});
								
								return;
							}
						
					}
				} catch (FileNotFoundException e1) {} catch (IOException e1) {}
				
			}			
		});
	}
	
	static Object[] showSemesterCourse(String selectedSemester) {
		ArrayList<Object> courseListInAddStudent = new ArrayList<Object>();
		courseListInAddStudent.add("請選擇");
		FileReader frInAddStudent = null;
		try {
			frInAddStudent= new FileReader("data/課程資料.txt");
		} catch (FileNotFoundException e1) {}
		BufferedReader brInAddStudent = new BufferedReader(frInAddStudent);			
		try {
			while (brInAddStudent.ready()) {
				String[] courseInfoList = brInAddStudent.readLine().split(" ");
				if(selectedSemester.equals(courseInfoList[0]))
					courseListInAddStudent.add(courseInfoList[1]+" "+courseInfoList[2]);
			}
			brInAddStudent.close();
		} catch (IOException e1) {}
		return courseListInAddStudent.toArray();
	}
	
	public JPanel getThisJPanel() {
		return this;
	}

	static void cleanTable(DefaultTableModel tableM) {
		while (tableM.getRowCount() > 0)
			tableM.removeRow(0);
	}
	
	public static Object[][] getTableAsArray(JTable table) {
		int row = table.getRowCount();
		int col = table.getColumnCount();
		Object[][] tableData = new Object[row][col];
		for(int i=0;i<row; i++)
			for(int j=0;j<col;j++)
				tableData[i][j] = table.getValueAt(i,j);
		return tableData;
	}
}
