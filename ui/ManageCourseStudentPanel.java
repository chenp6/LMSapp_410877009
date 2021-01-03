package ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import LMSapp.*;
import character.Manager;
public class ManageCourseStudentPanel extends JPanel {

	public ManageCourseStudentPanel() {
		setLayout(null);
		setBounds(0,30, 916, 800);
		JLabel titleInManageCourseStudent = new JLabel("學生選修課程管理");
		titleInManageCourseStudent.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		titleInManageCourseStudent.setBounds(213, -15, 491, 106);
		add(titleInManageCourseStudent);
		JLabel accountLabelInManageCourseStudent = new JLabel("請輸入學生學號:");
		accountLabelInManageCourseStudent.setHorizontalAlignment(SwingConstants.CENTER);
		accountLabelInManageCourseStudent.setBounds(262, 100, 148, 38);
		add(accountLabelInManageCourseStudent);
		JTextField accountTextInManageCourseStudent = new JTextField();
		accountTextInManageCourseStudent.setBounds(425, 102, 150, 32);
		add(accountTextInManageCourseStudent);
		JComboBox semesterComboInManageCourseStudent = new JComboBox(
				new Object[] { "請選擇", "107-2", "108-1", "108-2", "109-1" });
		semesterComboInManageCourseStudent.setBounds(425, 145, 173, 34);
		add(semesterComboInManageCourseStudent);
		JLabel actionChoiceLabelInManageCourseStudent = new JLabel("進行動作:");
		actionChoiceLabelInManageCourseStudent.setBounds(322, 191, 89, 38);
		JLabel semesterLabelInManageCourseStudent = new JLabel("學期:");
		semesterLabelInManageCourseStudent.setHorizontalAlignment(SwingConstants.CENTER);
		semesterLabelInManageCourseStudent.setBounds(322, 153, 71, 23);
		add(semesterLabelInManageCourseStudent);
		add(actionChoiceLabelInManageCourseStudent);
		JComboBox actionChoiceComboInManageCourseStudent = new JComboBox(new Object[] { "請選擇", "新增", "刪除" });
		actionChoiceComboInManageCourseStudent.setBounds(425, 189, 173, 34);
		add(actionChoiceComboInManageCourseStudent);
		JButton startChangeBtnInManageCourseStudent = new JButton("開始");
		startChangeBtnInManageCourseStudent.setBounds(383, 244, 111, 31);
		JPanel actionPanelInManageCourseStudent = new JPanel();
		actionPanelInManageCourseStudent.setBounds(15, 290, 888, 538);
		actionPanelInManageCourseStudent.setLayout(null);
		add(actionPanelInManageCourseStudent);
		add(startChangeBtnInManageCourseStudent);

		startChangeBtnInManageCourseStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionPanelInManageCourseStudent.removeAll();
				actionPanelInManageCourseStudent.repaint();
				actionPanelInManageCourseStudent.revalidate();
				String accountToCourse = accountTextInManageCourseStudent.getText();
				String selectedSemester = (String) semesterComboInManageCourseStudent.getSelectedItem();
				String selectedAction = (String) actionChoiceComboInManageCourseStudent.getSelectedItem();
				switch (selectedAction) {
				// 新增課程選修學生
				case "新增":
					Object[] courseArr = showSemesterCourse(selectedSemester);
					JLabel newStudentCourseLabel = new JLabel("新增課程:");
					newStudentCourseLabel.setBounds(340, 26, 102, 23);
					JComboBox courseChoiceComboInAddStudent = new JComboBox(courseArr);
					courseChoiceComboInAddStudent.setBounds(425, 26, 250, 29);
					JButton sureAddInAddStudent = new JButton("確認");
					sureAddInAddStudent.setBounds(410, 100, 231, 32);
					newStudentCourseLabel.setVisible(false);
					courseChoiceComboInAddStudent.setVisible(false);
					sureAddInAddStudent.setVisible(false);
					actionPanelInManageCourseStudent.add(newStudentCourseLabel);
					actionPanelInManageCourseStudent.add(courseChoiceComboInAddStudent);
					actionPanelInManageCourseStudent.add(sureAddInAddStudent);
					newStudentCourseLabel.setVisible(true);
					courseChoiceComboInAddStudent.setVisible(true);
					sureAddInAddStudent.setVisible(true);
					sureAddInAddStudent.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String[] selectedCourseInfo = ((String) courseChoiceComboInAddStudent.getSelectedItem())
									.split(" ");
							String selectedCourseNum = selectedCourseInfo[0];
							try {
								if(LMSapp.userAccount instanceof Manager) {
									((Manager)LMSapp.userAccount).addNewCourseStudent(accountToCourse, selectedSemester, selectedCourseNum);
								}
							} catch (IOException e1) {
							}
						}
					});
					break;
				case "刪除":
					FileReader frInDeleteStudent = null;
					try {
						frInDeleteStudent = new FileReader("data/課程資料.txt");
					} catch (FileNotFoundException e1) {
					}
					BufferedReader brInDeleteStudent = new BufferedReader(frInDeleteStudent);
					try {
						DefaultTableModel tableMInDeleteStudent;
						tableMInDeleteStudent = new DefaultTableModel(null,
								new String[] { "刪除", "課程代碼", "課程名稱", "學分" });
						JTable listTableInDeleteStudent = new JTable(tableMInDeleteStudent) {
							public boolean isCellEditable(int row, int column) {
								if (column == 0)
									return true;
								else
									return false;
							}// 表格不允許被編輯
						};
						TableColumn column = listTableInDeleteStudent.getColumnModel().getColumn(0);
						for (int i = 0; i < tableMInDeleteStudent.getColumnCount(); i++) {
							TableColumn columnTemp = listTableInDeleteStudent.getColumnModel().getColumn(i);
							if (i == 0) {
								columnTemp.setPreferredWidth(30);
							} else {
								columnTemp.setPreferredWidth(250);
							}
						}
						JCheckBox wantDelete = new JCheckBox();
						column.setCellEditor(new DefaultCellEditor(wantDelete));
						column.setCellRenderer(new TableCellRenderer() {
							@Override
							public Component getTableCellRendererComponent(JTable table, Object value,
									boolean isSelected, boolean hasFocus, int row, int column) {
								JCheckBox checkBox = new JCheckBox();
								if ((Boolean) value == true)
									checkBox.setSelected(true);
								return checkBox;
							}
						});
						while (brInDeleteStudent.ready()) {
							int len = 3;
							Object[] temp = new Object[len + 1];
							String[] courseInfoListInDeleteStudent = brInDeleteStudent.readLine().split(" ");
							String[] accountInCourse = courseInfoListInDeleteStudent[7].split(",");
							List<String> checkInCourse = (List<String>) Arrays.asList(accountInCourse);
							if (selectedSemester.equals(courseInfoListInDeleteStudent[0])
									&& checkInCourse.indexOf(accountToCourse) != -1) {
								System.arraycopy(courseInfoListInDeleteStudent, 1, temp, 1, len);
								temp[0] = false;
								tableMInDeleteStudent.addRow(temp);
							}

						}
						brInDeleteStudent.close();
						JButton deleteSureBtn = new JButton("確定更改");
						deleteSureBtn.setBounds(400, 300, 111, 40);
						deleteSureBtn.setVisible(false);
						JScrollPane coursePaneInDeleteStudent = new JScrollPane(listTableInDeleteStudent);
						coursePaneInDeleteStudent.setBounds(25, 0, 850, 300);
						coursePaneInDeleteStudent.setVisible(false);
						actionPanelInManageCourseStudent.add(coursePaneInDeleteStudent);
						actionPanelInManageCourseStudent.add(deleteSureBtn);
						coursePaneInDeleteStudent.setVisible(true);
						deleteSureBtn.setVisible(true);
						deleteSureBtn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								listTableInDeleteStudent.repaint();
								try {
									if(LMSapp.userAccount instanceof Manager) {
										Object[][] tableData = getTableAsArray(listTableInDeleteStudent); 
										((Manager)LMSapp.userAccount).deleteCourseStudent(tableData, accountToCourse, selectedSemester);
									
									}
									for (int i = 0; i < listTableInDeleteStudent.getRowCount(); i++) {
										if ((Boolean) listTableInDeleteStudent.getValueAt(i, 0) == true) {
											tableMInDeleteStudent.removeRow(i);
											i--;
										}
									}

								} catch (IOException e1) {
								}
							}
						});
					} catch (IOException e1) {
					}
					break;
				}
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
