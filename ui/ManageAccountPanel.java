package ui;

import LMSapp.*;
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
import javax.swing.JOptionPane;
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

import character.Manager;

public class ManageAccountPanel extends JPanel {

	public ManageAccountPanel() {
		// 用戶管理畫面
		setLayout(null);
		setBounds(0, 30, 916, 800);
		JLabel titleInManageAccount = new JLabel("用戶管理");
		titleInManageAccount.setFont(new Font("微軟正黑體", Font.PLAIN, 60));
		titleInManageAccount.setBounds(350, 15, 240, 106);
		add(titleInManageAccount);
		JComboBox characterComboInManageAccount = new JComboBox(new Object[] { "請選擇", "學生", "教授", "管理員" });
		characterComboInManageAccount.setBounds(425, 102, 173, 34);
		add(characterComboInManageAccount);
		JLabel chracterChoiceInManageAccount = new JLabel("用戶類型:");
		chracterChoiceInManageAccount.setBounds(321, 102, 89, 38);
		add(chracterChoiceInManageAccount);
		JLabel actionChoiceLabelInManageAccount = new JLabel("進行動作:");
		actionChoiceLabelInManageAccount.setBounds(321, 151, 89, 38);
		add(actionChoiceLabelInManageAccount);
		JComboBox actionChoiceComboInManageAccount = new JComboBox(new Object[] { "請選擇", "新增", "刪除", "修改" });
		actionChoiceComboInManageAccount.setBounds(425, 151, 173, 34);
		add(actionChoiceComboInManageAccount);
		JButton startChangeBtnInAccountManage = new JButton("開始");
		startChangeBtnInAccountManage.setBounds(425, 202, 111, 31);
		JPanel actionPanel = new JPanel();
		actionPanel.setBounds(15, 238, 896, 634);
		actionPanel.setLayout(null);
		add(actionPanel);
		add(startChangeBtnInAccountManage);

		startChangeBtnInAccountManage.addActionListener(new ActionListener() {
			@SuppressWarnings("serial")
			public void actionPerformed(ActionEvent arg0) {
				actionPanel.removeAll();
				actionPanel.repaint();
				actionPanel.revalidate();
				String selectedCharacter = (String) characterComboInManageAccount.getSelectedItem();
				String selectedAction = (String) actionChoiceComboInManageAccount.getSelectedItem();
				if ("請選擇".equals(selectedCharacter) || "請選擇".equals(selectedAction)) {
					return;
				}
				switch (selectedAction) {
				case "新增":
					JLabel nameLabel = new JLabel("帳戶姓名:");
					JTextField nameTextField = new JTextField();
					JLabel newAccountLabel = new JLabel("新增帳號:");
					JTextField newAccountTextField = new JTextField();
					JLabel hint = new JLabel("注意:學生帳號為學號，教授、管理員為員編");
					JLabel newPasswordLabel = new JLabel("新增密碼:");
					JTextField newPasswordTextFile = new JTextField();
					JLabel yearLabel = new JLabel("入學年度:");
					JTextField yearTextField = new JTextField();
					hint.setBounds(340, 0, 300, 23);
					nameLabel.setBounds(340, 26, 102, 23);
					nameTextField.setBounds(410, 26, 231, 29);
					newAccountLabel.setBounds(340, 81, 102, 23);
					newAccountTextField.setBounds(410, 81, 231, 29);
					newAccountTextField.setColumns(9);
					newPasswordLabel.setBounds(340, 135, 102, 23);
					newPasswordTextFile.setColumns(16);
					newPasswordTextFile.setBounds(410, 135, 231, 29);
					yearLabel.setBounds(340, 178, 111, 23);
					yearTextField.setColumns(16);
					yearTextField.setBounds(410, 178, 231, 29);
					JButton sureAddInManageAccount = new JButton("確認");
					sureAddInManageAccount.setBounds(410, 221, 231, 29);
					actionPanel.add(hint);
					actionPanel.add(nameLabel);
					actionPanel.add(nameTextField);
					actionPanel.add(newAccountLabel);
					actionPanel.add(newAccountTextField);
					actionPanel.add(newPasswordLabel);
					actionPanel.add(newPasswordTextFile);
					actionPanel.add(yearLabel);
					actionPanel.add(yearTextField);
					actionPanel.add(sureAddInManageAccount);
					actionPanel.repaint();
					if ("學生".equals(selectedCharacter)) {
						yearLabel.setVisible(true);
						yearTextField.setVisible(true);
					} else {
						yearLabel.setVisible(false);
						yearTextField.setVisible(false);
					}
					sureAddInManageAccount.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String selectedCharacter = (String) characterComboInManageAccount.getSelectedItem();						
							String accountText = newAccountTextField.getText();
							String passwordText = newPasswordTextFile.getText();
							String nameText = nameTextField.getText();
							String year = "";
							if ("學生".equals(selectedCharacter))
									year = yearTextField.getText();
							try {						
								if (LMSapp.userAccount instanceof Manager) 
									((Manager) LMSapp.userAccount).addNewAccount(selectedCharacter,nameText, accountText,passwordText,year);
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							newAccountTextField.setText("");
							newPasswordTextFile.setText("");
							nameTextField.setText("");
							yearTextField.setText("");
						}
					});
					break;
				case "刪除":
					JButton deleteSureBtn = new JButton("確定更改");
					deleteSureBtn.setBounds(400, 340, 111, 40);
					deleteSureBtn.setVisible(false);
					DefaultTableModel tableMInDeleteAccount;
					if ("學生".equals(selectedCharacter))
						tableMInDeleteAccount = new DefaultTableModel(null,
								new String[] { "刪除", "帳號", "密碼", "帳戶姓名", "入學年份" });
					else
						tableMInDeleteAccount = new DefaultTableModel(null, new String[] { "刪除", "帳號", "密碼", "帳戶姓名", });
					JTable listTableInDeleteAccount = new JTable(tableMInDeleteAccount) {
						public boolean isCellEditable(int row, int column) {
							if (column == 0)
								return true;
							else
								return false;
						}// 表格不允許被編輯
					};
					TableColumn column = listTableInDeleteAccount.getColumnModel().getColumn(0);
					for (int i = 0; i < tableMInDeleteAccount.getColumnCount(); i++) {
						TableColumn columnTemp = listTableInDeleteAccount.getColumnModel().getColumn(i);
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
					String fileInDeleteAccount = "data/" + selectedCharacter + "帳戶資料.txt";
					FileReader fr;
					try {
						fr = new FileReader(fileInDeleteAccount);
					} catch (FileNotFoundException e1) {
						return;
					}
					BufferedReader br = new BufferedReader(fr);
					ArrayList<String[]> storeAccountInfoInDeleteAccount = new ArrayList<String[]>();
					try {
						while (br.ready()) {
							String[] accountInfoInDeleteAccount = br.readLine().split(" ");
							storeAccountInfoInDeleteAccount.add(accountInfoInDeleteAccount);
						}
						fr.close();
						br.close();
					} catch (IOException e3) {
					}
					for (int i = 0; i < storeAccountInfoInDeleteAccount.size(); i++) {
						int len = storeAccountInfoInDeleteAccount.get(i).length;
						Object[] temp = new Object[len + 1];
						System.arraycopy(storeAccountInfoInDeleteAccount.get(i), 0, temp, 1, len);
						temp[0] = false;
						tableMInDeleteAccount.addRow(temp);
					}
					JScrollPane accountPaneInDeleteAccount = new JScrollPane(listTableInDeleteAccount);
					accountPaneInDeleteAccount.setBounds(25, 20, 850, 300);
					accountPaneInDeleteAccount.setVisible(true);
					actionPanel.add(accountPaneInDeleteAccount);
					actionPanel.add(deleteSureBtn);
					deleteSureBtn.setVisible(true);
					deleteSureBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								int row = listTableInDeleteAccount.getRowCount();
								int col = listTableInDeleteAccount.getColumnCount();
								Object[][] tableData = new Object[row][col];
								for(int i=0;i<row;i++)
									for(int j=0;j<col;j++)
										tableData[i][j] = listTableInDeleteAccount.getValueAt(i, j);
								if (LMSapp.userAccount instanceof Manager)
									((Manager) LMSapp.userAccount).deleteAccount(tableData,
											selectedCharacter);
								for (int i = 0; i < listTableInDeleteAccount.getRowCount(); i++) {
									if ((Boolean) listTableInDeleteAccount.getValueAt(i, 0) == true) {
										tableMInDeleteAccount.removeRow(i);
										i--;
									}
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

					});
					break;
				// L----修改帳戶
				case "修改":

					JLabel enterLabel = new JLabel("請輸入欲修改帳號:");
					enterLabel.setBounds(300, 36, 118, 23);
					actionPanel.add(enterLabel);
					JTextField accountTextField = new JTextField();
					accountTextField.setBounds(410, 33, 231, 29);
					accountTextField.setColumns(9);
					actionPanel.add(accountTextField);
					JButton startManageBtn = new JButton("開始");
					startManageBtn.setBounds(434, 75, 170, 40);
					actionPanel.add(startManageBtn);

					JLabel modifynameLabel = new JLabel("帳戶姓名:");
					JTextField modifynameTextField = new JTextField();
					JLabel modifyPasswordLabel = new JLabel("新增密碼:");
					JTextField modifyPasswordTextField = new JTextField();
					JLabel modifyYearLabel = new JLabel("入學年度:");
					JTextField modifyYearTextField = new JTextField();
					JButton sureManageBtn = new JButton("確認");

					startManageBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							modifynameLabel.setBounds(340, 125, 102, 23);
							modifynameTextField.setBounds(410, 125, 231, 29);
							modifyPasswordLabel.setBounds(340, 179, 102, 23);
							modifyPasswordTextField.setColumns(16);
							modifyPasswordTextField.setBounds(410, 179, 231, 29);
							modifyYearLabel.setBounds(340, 222, 111, 23);
							modifyYearTextField.setColumns(16);
							modifyYearTextField.setBounds(410, 222, 231, 29);
							sureManageBtn.setBounds(410, 265, 231, 29);

							actionPanel.add(modifynameLabel);
							actionPanel.add(modifynameTextField);
							actionPanel.add(modifyPasswordLabel);
							actionPanel.add(modifyPasswordTextField);
							actionPanel.add(modifyYearLabel);
							actionPanel.add(modifyYearTextField);
							actionPanel.add(sureManageBtn);
							actionPanel.repaint();

							if ("學生".equals(selectedCharacter)) {
								modifyYearLabel.setVisible(true);
								modifyYearTextField.setVisible(true);
							} else {
								modifyYearLabel.setVisible(false);
								modifyYearTextField.setVisible(false);
							}
							
							sureManageBtn.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent arg0) {
									if (LMSapp.userAccount instanceof Manager) {
										try {
											String account = accountTextField.getText();
											String name = modifynameTextField.getText();
											String password = modifyPasswordTextField.getText();
											String year = modifyYearTextField.getText();
											if(name==""||password==""||year=="")
												return;
											accountTextField.setText("");
											modifynameTextField.setText("");
											modifyPasswordTextField.setText("");
											modifyYearTextField.setText("");
											actionPanel.remove(modifynameLabel);
											actionPanel.remove(modifynameTextField);
											actionPanel.remove(modifyPasswordLabel);
											actionPanel.remove(modifyPasswordTextField);
											actionPanel.remove(modifyYearLabel);
											actionPanel.remove(modifyYearTextField);
											actionPanel.remove(sureManageBtn);
											actionPanel.repaint();
											repaint();
											LMSapp.frame.repaint();
											switch (selectedCharacter) {
											case "學生":
												((Manager) LMSapp.userAccount).modifyStudentAccount(account, password,
														name, year);
												break;
											case "教授":
												((Manager)LMSapp.userAccount).modifyProfessorAccount(account,password,name);
												break;
											case "管理員":
												 ((Manager)LMSapp.userAccount).modifyManagerAccount(account,password,name);
												break;
											}

										} catch (IOException e) {
											e.printStackTrace();
										}

									}
								}
							});

						}
					});

					break;

				}

			}
		});

	}

}