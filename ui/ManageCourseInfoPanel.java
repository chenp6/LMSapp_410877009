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

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import LMSapp.*;
import character.Manager;
public class ManageCourseInfoPanel extends JPanel {


	public ManageCourseInfoPanel() {
		// 課程資訊管理
		setLayout(null);
		setBounds(0,30, 916, 800);
		JLabel titleInManageCourseInfo = new JLabel("課程資訊管理");
		titleInManageCourseInfo.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		titleInManageCourseInfo.setBounds(289, 0, 361, 106);
		add(titleInManageCourseInfo);
		JComboBox semesterComboInManageCourseInfo = new JComboBox(
				new Object[] { "請選擇", "107-2", "108-1", "108-2", "109-1" });
		semesterComboInManageCourseInfo.setBounds(425, 102, 173, 34);
		add(semesterComboInManageCourseInfo);
		JLabel semesterChoiceInManageCourseInfo = new JLabel("學期:");
		semesterChoiceInManageCourseInfo.setBounds(344, 100, 89, 38);
		add(semesterChoiceInManageCourseInfo);
		JLabel actionChoiceLabelInManageCourseInfo = new JLabel("進行動作:");
		actionChoiceLabelInManageCourseInfo.setBounds(321, 153, 89, 38);
		add(actionChoiceLabelInManageCourseInfo);
		JComboBox actionChoiceComboInManageCourseInfo = new JComboBox(new Object[] { "請選擇", "新增", "刪除", "修改" });
		actionChoiceComboInManageCourseInfo.setBounds(425, 151, 173, 34);
		add(actionChoiceComboInManageCourseInfo);
		JButton startChangeBtnInManageCourseInfo = new JButton("開始");
		startChangeBtnInManageCourseInfo.setBounds(425, 202, 111, 31);
		JPanel actionPanelInManageCourseInfo = new JPanel();
		actionPanelInManageCourseInfo.setBounds(15, 238, 896, 634);
		actionPanelInManageCourseInfo.setLayout(null);
		add(actionPanelInManageCourseInfo);
		add(startChangeBtnInManageCourseInfo);
		
		
		startChangeBtnInManageCourseInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionPanelInManageCourseInfo.removeAll();
				actionPanelInManageCourseInfo.repaint();
				actionPanelInManageCourseInfo.revalidate();
				String selectedSemester = (String) semesterComboInManageCourseInfo.getSelectedItem();
				String selectedAction = (String) actionChoiceComboInManageCourseInfo.getSelectedItem();
				if ("請選擇".equals(selectedSemester) || "請選擇".equals(selectedAction))
					return;
				switch (selectedAction) {
				case "新增":
					JLabel numLabelInManageCourseInfo = new JLabel("課程代碼:");
					JTextField courseNum = new JTextField();
					numLabelInManageCourseInfo.setBounds(310, 20, 102, 23);
					courseNum.setBounds(380, 20, 231, 29);
					JLabel nameLabelInManageCourseInfo = new JLabel("課程名稱:");
					JTextField courseName = new JTextField();
					nameLabelInManageCourseInfo.setBounds(310, 70, 102, 23);
					courseName.setBounds(380, 70, 231, 29);
					JLabel newCreditLabel = new JLabel("學分:");
					JTextField credit = new JTextField();
					newCreditLabel.setBounds(310, 120, 102, 23);
					credit.setBounds(380, 120, 231, 29);
					credit.setColumns(3);
					JLabel newProfessorLabelInManageCourseInfo = new JLabel("授課教授:");
					JTextField professor = new JTextField();
					newProfessorLabelInManageCourseInfo.setBounds(310, 170, 102, 23);
					professor.setBounds(380, 170, 231, 29);
					ButtonGroup typeGroup = new ButtonGroup();
					JLabel typeLabel = new JLabel("類型:");
					JRadioButton typeCom = new JRadioButton("必修");
					JRadioButton typeElect = new JRadioButton("選修");
					typeGroup.add(typeCom);
					typeGroup.add(typeElect);
					typeCom.setBounds(400, 220, 100, 23);
					typeElect.setBounds(500, 220, 100, 23);
					typeLabel.setBounds(310, 220, 111, 23);
					JButton sureAddInManageCourseInfo = new JButton("確認");
					sureAddInManageCourseInfo.setBounds(360, 270, 231, 29);
					actionPanelInManageCourseInfo.add(numLabelInManageCourseInfo);
					actionPanelInManageCourseInfo.add(courseNum);
					actionPanelInManageCourseInfo.add(nameLabelInManageCourseInfo);
					actionPanelInManageCourseInfo.add(courseName);
					actionPanelInManageCourseInfo.add(newCreditLabel);
					actionPanelInManageCourseInfo.add(credit);
					actionPanelInManageCourseInfo.add(newProfessorLabelInManageCourseInfo);
					actionPanelInManageCourseInfo.add(professor);
					actionPanelInManageCourseInfo.add(typeCom);
					actionPanelInManageCourseInfo.add(typeElect);
					actionPanelInManageCourseInfo.add(typeLabel);
					actionPanelInManageCourseInfo.add(sureAddInManageCourseInfo);
					actionPanelInManageCourseInfo.repaint();

					sureAddInManageCourseInfo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String selectedSemester = (String) semesterComboInManageCourseInfo.getSelectedItem();
							try {
								if(LMSapp.userAccount instanceof Manager)
									((Manager)LMSapp.userAccount).addNewCourse(selectedSemester
											,courseNum.getText(),courseName.getText(),credit.getText()
											,professor.getText(),typeElect.isSelected());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							courseNum.setText("");
							courseName.setText("");
							credit.setText("");
							professor.setText("");
							typeElect.setSelected(false);
							typeCom.setSelected(false);
						}
					});
					break;
				// 刪除課程
				case "刪除":
					JButton deleteSureBtn = new JButton("確定更改");
					deleteSureBtn.setBounds(400, 340, 111, 40);
					deleteSureBtn.setVisible(false);
					DefaultTableModel tableMInDeleteCourse;
					tableMInDeleteCourse = new DefaultTableModel(null, new String[] { "刪除", "課程代碼", "課程名稱", "學分","授課教授" });
					JTable listTableInDeleteCourse = new JTable(tableMInDeleteCourse) {
						public boolean isCellEditable(int row, int column) {
							if (column == 0)
								return true;
							else
								return false;
						}// 表格不允許被編輯
					};
					TableColumn column = listTableInDeleteCourse.getColumnModel().getColumn(0);
					for (int i = 0; i < tableMInDeleteCourse.getColumnCount(); i++) {
						TableColumn columnTemp = listTableInDeleteCourse.getColumnModel().getColumn(i);
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
						public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							JCheckBox checkBox = new JCheckBox();
							if ((Boolean) value == true)
								checkBox.setSelected(true);
							return checkBox;
						}
					});
					String fileInDeleteCourse = "data/課程資料.txt";
					FileReader fr;
					try {
						fr = new FileReader(fileInDeleteCourse);
					} catch (FileNotFoundException e1) {
						return;
					}
					BufferedReader br = new BufferedReader(fr);
					ArrayList<String[]> originalInDeleteCourse = new ArrayList<String[]>();
					try {
						while (br.ready()) {
							originalInDeleteCourse.add(br.readLine().split(" "));
						}
						fr.close();
					} catch (IOException e3) {
					}
					for (int i = 0; i < originalInDeleteCourse.size(); i++) {
						int len = 4;
						Object[] temp = new Object[len + 1];
						if(selectedSemester.equals(originalInDeleteCourse.get(i)[0])) {
							System.arraycopy(originalInDeleteCourse.get(i), 1, temp, 1, len);
							temp[0] = false;
							tableMInDeleteCourse.addRow(temp);
						}
					}
					JScrollPane coursePaneInDeleteCourse = new JScrollPane(listTableInDeleteCourse);
					coursePaneInDeleteCourse.setBounds(25, 20, 850, 300);
					coursePaneInDeleteCourse.setVisible(true);
					actionPanelInManageCourseInfo.add(coursePaneInDeleteCourse);
					actionPanelInManageCourseInfo.add(deleteSureBtn);
					deleteSureBtn.setVisible(true);
					deleteSureBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {

								if(LMSapp.userAccount instanceof Manager) {
									Object[][] tableData = getTableAsArray(listTableInDeleteCourse);
									((Manager)LMSapp.userAccount).deleteCourse(tableData,originalInDeleteCourse,selectedSemester);
								}
								for (int i = 0; i < listTableInDeleteCourse.getRowCount(); i++) {
									if ((Boolean) listTableInDeleteCourse.getValueAt(i, 0) == true) {
										tableMInDeleteCourse.removeRow(i);
										i--;
									}
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

					});
					break;				
				case "修改":
					JButton modifySureBtn = new JButton("確定更改");
					modifySureBtn.setBounds(400, 340, 111, 40);
					modifySureBtn.setVisible(false);
					DefaultTableModel tableMInModifyCourse;
					String file = "";
					JTable listTableInModifyCourse;
					file = "data/課程資料.txt";
					FileReader frInModifyCourse;
					try {
						frInModifyCourse = new FileReader(file);
					} catch (FileNotFoundException e1) {
						return;
					}
					BufferedReader brInModifyCourse = new BufferedReader(frInModifyCourse);
					StringBuilder beforeSemesterCourseInfo = new StringBuilder(); 
					ArrayList<String[]> thisSemesterCourseInfo = new ArrayList<String[]>();
					StringBuilder afterSemesterCourseInfo = new StringBuilder(); 
					tableMInModifyCourse = new DefaultTableModel(null, new String[] {"課程代碼", "課程名稱", "學分","授課教授","類型" });							
					try {
						while (brInModifyCourse.ready()) {
							String info = brInModifyCourse.readLine();
							String[] courseInfo = info.split(" ");
							if(selectedSemester.compareTo(courseInfo[0]) > 0)
								 beforeSemesterCourseInfo.append(info+"\n");
							else if(selectedSemester.compareTo(courseInfo[0]) == 0)
								thisSemesterCourseInfo.add(courseInfo);
							else
								afterSemesterCourseInfo.append(info+"\n");
						}
						frInModifyCourse.close();
						brInModifyCourse.close();
					} catch (IOException e3) {
					}
					if (tableMInModifyCourse != null) {
						for (int i = 0; i < thisSemesterCourseInfo.size(); i++) {	
							int len = 5;
							Object[] temp = new Object[len];	
							System.arraycopy(thisSemesterCourseInfo.get(i), 1, temp, 0, len);
							tableMInModifyCourse.addRow(temp);
						}
						listTableInModifyCourse = new JTable(tableMInModifyCourse);
						JScrollPane CoursePaneInModifyCourse = new JScrollPane(listTableInModifyCourse);
						CoursePaneInModifyCourse.setBounds(25, 20, 850, 300);
						CoursePaneInModifyCourse.setVisible(true);
						actionPanelInManageCourseInfo.add(CoursePaneInModifyCourse);
						actionPanelInManageCourseInfo.add(modifySureBtn);
						modifySureBtn.setVisible(true);
						tableMInModifyCourse.addTableModelListener(new TableModelListener() {
				            @Override
				            public void tableChanged(TableModelEvent e) {
				            	int row =e.getFirstRow();				            	
				            	int col = e.getColumn();
				            	String[] temp = thisSemesterCourseInfo.get(row);				            	
				            	temp[col+1] = (String)tableMInModifyCourse.getValueAt(row,col);
				            	thisSemesterCourseInfo.set(row, temp);
				            }			       
				        });
						modifySureBtn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									listTableInModifyCourse.repaint();
									if(LMSapp.userAccount instanceof Manager)
										((Manager)LMSapp.userAccount).modifyCourse(beforeSemesterCourseInfo.toString(),thisSemesterCourseInfo,afterSemesterCourseInfo.toString());
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}

						});
					}
					break;
				}
			}
		});
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
