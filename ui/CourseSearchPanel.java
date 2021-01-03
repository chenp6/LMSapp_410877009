package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
//課程查詢
public class CourseSearchPanel extends JPanel {
	public CourseSearchPanel() {
		Font textFont = new Font("textField", Font.PLAIN, 16);
		setBounds(0,30, 916, 800);
		setLayout(null);
		JLabel title = new JLabel("課程查詢");
		title.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		title.setBounds(338, 0, 240, 106);
		add(title);
		JComboBox semesterComboInClassInfo = new JComboBox(new Object[] { "請選擇", "107-2", "108-1", "108-2", "109-1" });
		JLabel semesterLabelInClassInfo = new JLabel("學期");
		String[] columns = { "課程代碼", "學期", "課程名稱", "學分", "授課教授", "必/選修", "選修學生清單" };
		DefaultTableModel tableM;
		tableM = new DefaultTableModel(null, columns);
		JTable courseTable = new JTable(tableM) {
			public boolean isCellEditable(int row, int column) {
				if (column == 6)
					return true;
				else
					return false;
			}// 表格不允許被編輯
		};
		courseTable.getColumnModel().getColumn(6).setCellRenderer(new CourseStudentBtnRender());
		courseTable.getColumnModel().getColumn(6).setCellEditor(new CourseStudentBtnEditor());
		JScrollPane coursePane = new JScrollPane(courseTable);
		TableColumn column;
		for (int i = 0; i < 6; i++) {
			column = courseTable.getColumnModel().getColumn(i);
			if (i == 2) {
				column.setPreferredWidth(300); // third column is bigger
			} else {
				column.setPreferredWidth(20);
			}
		}
		semesterComboInClassInfo.setBounds(400, 100, 100, 40);
		semesterLabelInClassInfo.setBounds(500, 100, 100, 30);
		coursePane.setBounds(8, 200, 900, 700);
		semesterLabelInClassInfo.setFont(textFont);
		add(semesterComboInClassInfo);
		add(semesterLabelInClassInfo);
		add(coursePane);
		
		semesterComboInClassInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String selectedSemester = (String) semesterComboInClassInfo.getSelectedItem();
				FileReader fr;
				try {
					fr = new FileReader("data/課程資料.txt");
				} catch (FileNotFoundException e1) {
					return;
				}
				BufferedReader br = new BufferedReader(fr);
				cleanTable(tableM);
				if (selectedSemester != "請選擇") {
					try {
						 CourseStudentBtn.courseStudentList = new ArrayList<String>();
						while (br.ready()) {
							String[] courseInfo;
								courseInfo = br.readLine().split(" ");
								if (courseInfo[0].equals(selectedSemester)) {							
									String studentList = makeStudentList(courseInfo[7], courseInfo[6]);			
									CourseStudentBtn btn =new CourseStudentBtn("查看");
									CourseStudentBtn.courseStudentList.add(studentList);
									Object[] temp = { courseInfo[0], courseInfo[1], courseInfo[2], courseInfo[3],
											courseInfo[4], courseInfo[5], btn };
									tableM.addRow(temp);										
								}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					add(coursePane);
				}
			}
		});
	}
	static void cleanTable(DefaultTableModel tableM) {
		while (tableM.getRowCount() > 0)
			tableM.removeRow(0);
	}

	static String makeStudentList(String accountList, String nameList) {
		String studentList = "";
		String[] accountArr = accountList.split(",");
		String[] nameArr = nameList.split(",");
		for (int i = 0; i < accountArr.length - 1; i++)
			studentList += accountArr[i] + nameArr[i] + "\n";
		studentList += accountArr[accountArr.length - 1] + nameArr[accountArr.length - 1];
		return studentList;
	}

}
