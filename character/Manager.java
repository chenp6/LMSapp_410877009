package character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import LMSapp.LMSapp;

public class Manager extends Account {

	public Manager(String account, String password, String name, char character) {
		super(account, password, name, character);
	}

	@Override
	public boolean changePassword(String oldPassword, String newPassword) throws IOException {
		boolean correctPw = false;// 輸入的舊密碼是否正確，是否為本人
		if(newPassword.contains(" "))
			return correctPw;
		FileReader fr = null;
		fr = new FileReader("data/管理員帳戶資料.txt");
		BufferedReader br = new BufferedReader(fr);
		String writeText = "";
		while (br.ready())
			try {
				{
					String temp = br.readLine();
					String[] info = temp.split(" ");
					if (info[0].equals(account) && info[1].equals(oldPassword)) {
						correctPw = true;
						temp = "";
						info[1] = newPassword;
						for (int i = 0; i < info.length - 1; i++)
							temp += info[i] + " ";
						temp += info[info.length - 1];
					}
					writeText += temp + "\n";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		br.close();
		FileOutputStream writerStream = new FileOutputStream("data/管理員帳戶資料.txt");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
		writer.write(writeText);
		writer.close();
		return correctPw;
	}

	// 新增帳戶
	public boolean addNewAccount(String selectedCharacter, String name, String account, String password, String year)
			throws IOException {
		int accountLen = account.length();
		if (("學生".equals(selectedCharacter) && accountLen != 9) || ("教授".equals(selectedCharacter) && accountLen != 5)
				|| ("管理員".equals(selectedCharacter) && accountLen != 4)) {
			JOptionPane.showMessageDialog(new JTextField(), "格式不符，請重新填寫", "新增帳戶失敗", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(name.equals("")||password.equals("")||name.contains(" ")||password.contains(" ")) {
			JOptionPane.showMessageDialog(new JTextField(), "資料不可為空，請重新填寫", "新增帳戶失敗", JOptionPane.ERROR_MESSAGE);
			return false;
		}
			
		StringBuilder writeText = new StringBuilder();
		writeText.append(account + " ");
		writeText.append(password + " ");
		writeText.append(name + " ");
		if ("學生".equals(selectedCharacter)) {
			if(year.equals("")||year.equals(" "))
				return false;
			writeText.append(year + " ");
		}
		writeText.append("\n");
		String file = "data/" + selectedCharacter + "帳戶資料.txt";
		String updateString = addAccountToAccountList(account ,selectedCharacter, writeText.toString(), file);
		if("duplicate data".equals(updateString))
			return false;
		FileWriter writer = new FileWriter(file);
		writer.write(updateString);
		writer.close();
		JOptionPane.showMessageDialog(new JTextField(), "帳戶成功", "新增帳戶", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String addAccountToAccountList(String account ,String selectedCharacter, String writeText, String file) throws IOException {
		FileReader fr = null;
		fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuilder storeBefore = new StringBuilder();
		StringBuilder storeAfter = new StringBuilder();
		while (br.ready()) {
			String str = br.readLine();
			String[] info = str.split(" "); 
			if(account.equals(info[0])) {
				br.close();
				return "duplicate data";
			}				
			if (writeText.compareTo(str) > 0)
				storeBefore.append(str + '\n');
			else if (writeText.compareTo(str) < 0)
				storeAfter.append(str + '\n');
			
		}
		br.close();
		return storeBefore.toString() + writeText + storeAfter.toString();
	}

	// 刪除帳戶

	public boolean deleteAccount(Object[][] tableData, String selectedCharacter) throws IOException {
		String file = "data/" + selectedCharacter + "帳戶資料.txt";
		StringBuilder writeText = new StringBuilder();
		ArrayList<Integer> removeRow = new ArrayList<Integer>();
		for (int i = 0; i < tableData.length; i++) {
			if ((Boolean) tableData[i][0] == true) {
				removeRow.add(i);
			}
		}
		String[] tableArr = deleteAccountInAccountList(tableData, removeRow);
		for (String str : tableArr)
			writeText.append(str);
		FileWriter writer = new FileWriter(file);
		writer.write(writeText.toString());
		writer.close();
		JOptionPane.showMessageDialog(new JTextField(), "帳戶刪除成功", "刪除帳戶", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String[] deleteAccountInAccountList(Object[][] tableData, ArrayList<Integer> ignoreRow) {
		String[] tempArr = new String[tableData.length - ignoreRow.size()];
		int index = 0;
		for (int row = 0; row < tableData.length; row++) {
			if (ignoreRow.indexOf(row) != -1)
				continue;
			StringBuilder temp = new StringBuilder();
			for (int col = 1; col < tableData[row].length - 1; col++)
				temp.append(tableData[row][col] + " ");
			temp.append(tableData[row][tableData[row].length - 1] + "\n");
			tempArr[index] = temp.toString();
			index++;
		}
		Arrays.sort(tempArr);
		return tempArr;
	}

	// 修改學生帳戶
	public boolean modifyStudentAccount(String account, String password, String name, String year) throws IOException {
		String tempName = name.replace(" ","");
		String tempPassword = password.replace(" ","");
		String tempYear = year.replace(" ", "");
		if(tempName.equals("")||tempPassword.equals("")||tempYear.equals("")) {
				JOptionPane.showMessageDialog(new JTextField(), "資料不可為空，請重新填寫", "更改帳戶", JOptionPane.ERROR_MESSAGE);
				return false;
		}
		String updateAccountText = getModifiedStudentData(account, password, name, year);
		FileWriter writerAccount = new FileWriter("data/學生帳戶資料.txt");
		writerAccount.write(updateAccountText);
		writerAccount.close();
		JOptionPane.showMessageDialog(new JTextField(), "更改帳戶成功", "更改帳戶", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String getModifiedStudentData(String account, String password, String name, String year)
			throws IOException {
		FileReader fr = new FileReader("data/學生帳戶資料.txt");
		BufferedReader br = new BufferedReader(fr);
		StringBuilder strBuild = new StringBuilder();
		while (br.ready()) {
			String accountList = br.readLine();
			String[] accountInfo = accountList.split(" ");
			if (account.equals(accountInfo[0])) {
				if (!(name.equals(accountInfo[2])))
					updateCourseStudentName(account, name);
				accountInfo[1] = password;
				accountInfo[2] = name;
				accountInfo[3] = year;
				accountList = String.join(" ", accountInfo);
			}
			strBuild.append(accountList + "\n");
		}
		br.close();
		return strBuild.toString();
	}

	private void updateCourseStudentName(String account, String name) throws IOException {
		StringBuilder updateStr = new StringBuilder();
		FileReader fr = new FileReader("data/課程資料.txt");
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String courseStr = br.readLine();
			String[] courseInfo = courseStr.split(" ");

			List<String> accountList = (List<String>) Arrays.asList(courseInfo[7].split(","));
			int studentIndex = accountList.indexOf(account);
			if (studentIndex != -1) {
				String[] nameList = courseInfo[6].split(",");
				nameList[studentIndex] = name;
				String updateNameList = String.join(",", nameList);
				courseInfo[6] = updateNameList;
				courseStr = String.join(" ", courseInfo);
			}
			updateStr.append(courseStr + "\n");
		}
		br.close();
		fr.close();
		FileWriter writer = new FileWriter("data/課程資料.txt");
		writer.write(updateStr.toString());
		writer.close();
	}

	// 修改教授帳戶
	public boolean modifyProfessorAccount(String account, String password, String name) throws IOException {
		String tempName = name.replace(" ","");
		String tempPassword = password.replace(" ","");
		if(tempName.equals("")||tempPassword.equals("")) {
				JOptionPane.showMessageDialog(new JTextField(), "資料不可為空，請重新填寫", "更改帳戶", JOptionPane.ERROR_MESSAGE);
				return false;
		}
		String updateAccountText = getModifiedProfessorData(account, password, name);
		FileWriter writerAccount = new FileWriter("data/教授帳戶資料.txt");
		writerAccount.write(updateAccountText);
		writerAccount.close();
		JOptionPane.showMessageDialog(new JTextField(), "更改帳戶成功", "更改帳戶", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String getModifiedProfessorData(String account, String password, String name) throws IOException {
		FileReader fr = new FileReader("data/教授帳戶資料.txt");
		BufferedReader br = new BufferedReader(fr);
		StringBuilder strBuild = new StringBuilder();
		while (br.ready()) {
			String accountList = br.readLine();
			String[] accountInfo = accountList.split(" ");
			if (account.equals(accountInfo[0])) {
				if (!(name.equals(accountInfo[2])))
					updateCourseProfessorName(accountInfo[2], name);
				accountInfo[1] = password;
				accountInfo[2] = name;
				accountList = String.join(" ", accountInfo);
			}
			strBuild.append(accountList + "\n");
		}
		br.close();
		return strBuild.toString();
	}

	private void updateCourseProfessorName(String oldName, String newName) throws IOException {
		StringBuilder updateStr = new StringBuilder();
		FileReader fr = new FileReader("data/課程資料.txt");
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String courseStr = br.readLine();
			String[] courseInfo = courseStr.split(" ");
			if (oldName.equals(courseInfo[4])) {
				courseInfo[4] = newName;
				courseStr = String.join(" ", courseInfo);
			}
			updateStr.append(courseStr + "\n");
		}
		br.close();
		fr.close();
		FileWriter writer = new FileWriter("data/課程資料.txt");
		writer.write(updateStr.toString());
		writer.close();
	}

	// 修改管理員帳戶
	public boolean modifyManagerAccount(String account, String password, String name) throws IOException {
		String tempName = name.replace(" ","");
		String tempPassword = password.replace(" ","");
		if(tempName.equals("")||tempPassword.equals("")) {
				JOptionPane.showMessageDialog(new JTextField(), "資料不可為空，請重新填寫", "更改帳戶", JOptionPane.ERROR_MESSAGE);
				return false;
		}
		String updateAccountText = getModifiedManagerData(account, password, name);
		FileWriter writerAccount = new FileWriter("data/管理員帳戶資料.txt");
		writerAccount.write(updateAccountText);
		writerAccount.close();
		JOptionPane.showMessageDialog(new JTextField(), "更改帳戶成功", "更改帳戶", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String getModifiedManagerData(String account, String password, String name) throws IOException {
		FileReader fr = new FileReader("data/管理員帳戶資料.txt");
		BufferedReader br = new BufferedReader(fr);
		StringBuilder strBuild = new StringBuilder();
		while (br.ready()) {
			String accountList = br.readLine();
			String[] accountInfo = accountList.split(" ");
			if (account.equals(accountInfo[0])) {
				accountInfo[1] = password;
				accountInfo[2] = name;
				accountList = String.join(" ", accountInfo);
			}
			strBuild.append(accountList + "\n");
		}
		br.close();
		return strBuild.toString();
	}
	// ===================課程管理===================

	// 新增課程
	public boolean addNewCourse(String semester,String courseNum,String courseName,String credit,String professor,boolean type) throws IOException {
		 String tempNum = courseNum.replace(" ", "");
		 String tempName = courseName.replace(" ", "");
		 String tempProfessor = professor.replace(" ", "");
		 String tempCredit = credit.replace(" ", "");
		if(tempNum.equals("")||tempName.equals("")||tempCredit.equals("")||
				tempProfessor.equals("")) {
				JOptionPane.showMessageDialog(new JTextField(), "資料不可為空，請重新填寫", "新增課程", JOptionPane.PLAIN_MESSAGE);			
				return false;
		}
		
		StringBuilder newInfo = new StringBuilder();
		newInfo.append(semester + " ");
		newInfo.append(courseNum+ " ");
		newInfo.append(courseName + " ");
		newInfo.append(credit + " ");
		newInfo.append(professor + " ");
		if (type)
			newInfo.append("選修 ");
		else
			newInfo.append("必修 ");
		newInfo.append("未設定 未設定 未設定\n");
		
		String writeText = newInfo.toString();
		String updateText = addCourseToCourseList(writeText,courseNum,semester);
		if("duplicate data".equals(updateText)) {
			JOptionPane.showMessageDialog(new JTextField(), "<html><body>該學期已有課程使用此課程代碼，請重新填寫</body></html>", "新增課程失敗", JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		FileWriter writer = new FileWriter("data/課程資料.txt");
		writer.write(updateText);
		writer.close();
		JOptionPane.showMessageDialog(new JTextField(), "新增課程成功", "新增課程", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String addCourseToCourseList(String writeText,String courseNum,String semester) throws IOException {
		String file = "data/課程資料.txt";
		FileReader fr = null;
		fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuilder storeBefore = new StringBuilder();
		StringBuilder storeAfter = new StringBuilder();
		while (br.ready()) {
			String str = br.readLine();
			String courseInfo[]  = str.split(" ");
			if(courseInfo[1].equals(courseNum)&&courseInfo[0].equals(semester)) {
				br.close();
				return "duplicate data";
			}
			if (writeText.compareTo(str) > 0)
				storeBefore.append(str + '\n');
			else if (writeText.compareTo(str) < 0)
				storeAfter.append(str + '\n');
		}
		br.close();
		return storeBefore.toString() + writeText + storeAfter.toString();
	}

	// 刪除課程
	public boolean deleteCourse(Object[][] table, ArrayList<String[]> originalContext, String selectedSemester)
			throws IOException {
		String updateText = deleteCourseInCourseList(table, originalContext, selectedSemester);
		FileWriter writer = new FileWriter("data/課程資料.txt");
		writer.write(updateText.toString());
		writer.close();
		JOptionPane.showMessageDialog(new JTextField(), "刪除課程成功", "刪除課程", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String deleteCourseInCourseList(Object[][] table, ArrayList<String[]> orignalContext,
			String selectedSemester) {
		String file = "data/課程資料.txt";
		StringBuilder writeText = new StringBuilder();
		ArrayList<Integer> removeRow = new ArrayList<Integer>();
		for (int i = 0; i < table.length; i++) {
			if ((Boolean) table[i][0] == true) {
				removeRow.add(i);
			}
		}
		String[] tempArr = new String[orignalContext.size() - removeRow.size()];
		int index = 0;
		int removeIndex = 0;
		for (int row = 0; row < orignalContext.size(); row++) {
			if (selectedSemester.equals(orignalContext.get(row)[0])) {
				if (removeRow.indexOf(removeIndex) != -1) {
					removeIndex++;
					continue;
				}
				removeIndex++;
			}
			StringBuilder temp = new StringBuilder();
			for (int col = 0; col < orignalContext.get(0).length - 1; col++)
				temp.append(orignalContext.get(row)[col] + " ");
			temp.append(orignalContext.get(row)[orignalContext.get(0).length - 1] + "\n");
			tempArr[index] = temp.toString();
			index++;
		}
		Arrays.sort(tempArr);
		for (String str : tempArr)
			writeText.append(str);
		return writeText.toString();
	}

	public boolean modifyCourse(String beforeContext, ArrayList<String[]> thisSemesterContext, String afterContext)
			throws IOException {
		FileWriter writer = new FileWriter("data/課程資料.txt");
		StringBuilder writeText = new StringBuilder();
		writeText.append(beforeContext);
		for (int i = 0; i < thisSemesterContext.size(); i++) {
			int len = thisSemesterContext.get(i).length;
			for (int j = 0; j < len - 1; j++)
				writeText.append(thisSemesterContext.get(i)[j] + " ");
			writeText.append(thisSemesterContext.get(i)[len - 1] + "\n");
		}
		writeText.append(afterContext);
		writer.write(writeText.toString());
		writer.close();
		JOptionPane.showMessageDialog(new JTextField(), "更改課程成功", "更改課程", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	public boolean addNewCourseStudent(String account, String selectedSemester, String selectedCourseNum) throws IOException {
		String updateText = addStudentToCourse(account, selectedSemester, selectedCourseNum);
		if (updateText == "empty name") {
			JOptionPane.showMessageDialog(new JTextField(), "查無此學生，請重新填寫", "選修課程", JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		else if (updateText=="in the class") {
			JOptionPane.showMessageDialog(new JTextField(), "此學生已選修課", "重複選修", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		else {
			FileOutputStream writerStream = new FileOutputStream("data/課程資料.txt");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
			writer.write(updateText.toString());
			writer.close();
			JOptionPane.showMessageDialog(new JTextField(), "選修課程成功", "選修課程", JOptionPane.PLAIN_MESSAGE);
			return true;

		}

	}

	private String addStudentToCourse(String account, String selectedSemester, String selectedCourseNum)
			throws IOException {
		String name = findStudent(account);
		if (name == "")
			return "empty name";
		FileReader fr = null;
		fr = new FileReader("data/課程資料.txt");
		BufferedReader br = new BufferedReader(fr);
		StringBuilder writeText = new StringBuilder();
		boolean added = false;
		while (br.ready()) {
			String course = br.readLine();
			String[] info = course.split(" ");
			if (selectedCourseNum.equals(info[1]) && selectedSemester.equals(info[0])) {
				String studentList = info[6];
				String studentAccount = info[7];
				String[] checkArr = studentAccount.split(",");
				List<String> checkList = (List<String>) Arrays.asList(checkArr);
				if (checkList.indexOf(account) != -1)
					return "in the class";
				String studentScore = info[8];

				StringBuilder nameText = new StringBuilder();
				StringBuilder accountText = new StringBuilder();
				StringBuilder scoreText = new StringBuilder();
				if ("未設定".equals(studentList)) {
					nameText.append(name);
					accountText.append(account);
					scoreText.append("-");
				} else {
					String[] nameArr = studentList.split(",");
					String[] accountArr = studentAccount.split(",");
					String[] scoreArr = studentScore.split(",");
					if (account.compareTo(accountArr[0]) < 0) {
						nameText.append(name + "," + nameArr[0]);
						accountText.append(account + "," + accountArr[0]);
						scoreText.append("-," + scoreArr[0]);
						added = true;
					} else {
						nameText.append(nameArr[0]);
						accountText.append(accountArr[0]);
						scoreText.append(scoreArr[0]);
					}
					for (int i = 1; i < nameArr.length; i++) {
						if (added == false && account.compareTo(accountArr[i]) < 0) {
							nameText.append("," + name);
							accountText.append("," + account);
							scoreText.append(",-");
							added = true;
						}
						nameText.append("," + nameArr[i]);
						accountText.append("," + accountArr[i]);
						scoreText.append("," + scoreArr[i]);
					}
					if(added==false) {
						nameText.append("," + name);
						accountText.append("," + account);
						scoreText.append(",-");
					}
				}
				writeText.append(info[0] + " " + info[1] + " " + info[2] + " " + info[3] + " " + info[4] + " " + info[5]
						+ " " + nameText.toString() + " " + accountText.toString() + " " + scoreText.toString() + "\n");
			} else
				writeText.append(course + "\n");
		}
		br.close();
		return writeText.toString();
	}

	public boolean deleteCourseStudent(Object[][] table, String account, String selectedSemester) throws IOException {
		String updateText = deleteStudentInCourse(table, selectedSemester, account);
		FileOutputStream writerStream = new FileOutputStream("data/課程資料.txt");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
		writer.write(updateText);
		writer.close();
		JOptionPane.showMessageDialog(new JTextField(), "刪除選修課程成功", "刪除課程", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String deleteStudentInCourse(Object[][] table, String selectedSemester, String deletedAccount)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/課程資料.txt"));
		StringBuilder writeText = new StringBuilder();
		int checkIndex = 0;
		try {
			while (br.ready()) {
				String[] courseInfoList = br.readLine().split(" ");
				String[] accountInCourse = courseInfoList[7].split(",");
				String[] nameInCourse = courseInfoList[6].split(",");
				String[] scoreInCourse = courseInfoList[8].split(",");
				List<String> checkInCourse = (List<String>) Arrays.asList(accountInCourse);
				int stuIndex = checkInCourse.indexOf(deletedAccount);
				writeText.append(courseInfoList[0]);
				for (int i = 1; i < 6; i++)
					writeText.append(" " + courseInfoList[i]);
				if (selectedSemester.equals(courseInfoList[0]) && stuIndex != -1) {
					if ((Boolean) table[checkIndex][0] == true) {
						if (stuIndex != 0) {
							writeText.append(" " + nameInCourse[0]);
							for (int i = 1; i < nameInCourse.length; i++) {
								if (stuIndex == i)
									continue;
								writeText.append("," + nameInCourse[i]);
							}
							writeText.append(" " + accountInCourse[0]);
							for (int i = 1; i < accountInCourse.length; i++) {
								if (stuIndex == i)
									continue;
								writeText.append("," + accountInCourse[i]);
							}
							writeText.append(" " + scoreInCourse[0]);
							for (int i = 1; i < scoreInCourse.length; i++) {
								if (stuIndex == i)
									continue;
								writeText.append("," + scoreInCourse[i]);
							}
						} else {
							if (accountInCourse.length == 1) {
								writeText.append(" 未設定 未設定 未設定");
							} else {
								writeText.append(" " + nameInCourse[1]);
								for (int i = 2; i < nameInCourse.length; i++) {
									if (stuIndex == i)
										continue;
									writeText.append("," + nameInCourse[i]);
								}
								writeText.append(" " + accountInCourse[1]);
								for (int i = 2; i < accountInCourse.length; i++) {
									if (stuIndex == i)
										continue;
									writeText.append("," + accountInCourse[i]);
								}
								writeText.append(" " + scoreInCourse[1]);
								for (int i = 2; i < scoreInCourse.length; i++) {
									if (stuIndex == i)
										continue;
									writeText.append("," + scoreInCourse[i]);
								}
							}
						}
					}
					checkIndex++;
				} else {
					for (int i = 6; i < 9; i++)
						writeText.append(" " + courseInfoList[i]);
				}
				writeText.append("\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writeText.toString();
	}

	public boolean saveScore(Object[][] table, String selectedSemester, String selectedCourseNum) throws IOException {
		String updateText = updateStudentScoreInCourse(table, selectedSemester, selectedCourseNum);
		if("invalid score".equals(updateText))
			return false;
		FileOutputStream writerStream = new FileOutputStream("data/課程資料.txt");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
		writer.write(updateText);
		writer.close();
		JOptionPane.showMessageDialog(new JTextField(), "儲存成績成功", "儲存成績", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	private String updateStudentScoreInCourse(Object[][] table, String selectedSemester, String selectedCourseNum)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/課程資料.txt"));
		StringBuilder updateText = new StringBuilder();
		while (br.ready()) {
			String courseInfoLine = br.readLine();
			String[] courseInfo = courseInfoLine.split(" ");
			for (int index = 0; index < courseInfo.length - 1; index++)
				updateText.append(courseInfo[index] + " ");
			if (selectedSemester.equals(courseInfo[0]) && selectedCourseNum.equals(courseInfo[1])) {
				String scoreTemp = ((String) table[0][2]).replace(" ", "");
				if("".equals(scoreTemp)) {
					JOptionPane.showMessageDialog(new JTextField(), "輸入成績錯誤", "輸入錯誤", JOptionPane.INFORMATION_MESSAGE);
					return "invalid score";
				}	
				updateText.append((String) table[0][2]);
				for (int i = 1; i < table.length; i++) {
					scoreTemp = ((String) table[i][2]).replace(" ", "");
					if("".equals(scoreTemp)) {
						JOptionPane.showMessageDialog(new JTextField(), "輸入成績錯誤", "輸入錯誤", JOptionPane.INFORMATION_MESSAGE);
						return "invalid score";
					}	
					updateText.append("," + (String) table[i][2]);
				}
			} else
				updateText.append(courseInfo[courseInfo.length - 1]);
			updateText.append("\n");
		}
		return updateText.toString();
	}

	public boolean printScore(String selectedSemester, String account) throws IOException {
		String file = "";
		JFileChooser fileChoice = new JFileChooser();
		fileChoice.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int approve = fileChoice.showSaveDialog(null);
		String name = findStudent(account);
		if (approve == JFileChooser.APPROVE_OPTION)
			file = fileChoice.getSelectedFile().getPath() + account + name + "_" + selectedSemester + "學期" + "的成績單.pdf";
		File checkFile = new File(file);

		if (checkFile.exists()) {
			int result = JOptionPane.showConfirmDialog(new JTextField(), "檔案已存在，是否覆蓋?", "確認訊息",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == JOptionPane.NO_OPTION) {
				return false;
			}
		}
		Document document = new Document();
		try {
			BaseFont baseFont = BaseFont.createFont("font/chinese.simyou.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			com.itextpdf.text.Font chinessFont = new com.itextpdf.text.Font(baseFont);
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			document.add(new Paragraph(account + name + " " + selectedSemester + "的成績單\n\n", chinessFont));
			PdfPTable scoreTable = new PdfPTable(5);
			scoreTable.setWidths(new float[] { 15f, 50f, 10f, 13f, 10f });
			scoreTable.addCell(new PdfPCell(new Paragraph("課程代碼", chinessFont)));
			scoreTable.addCell(new PdfPCell(new Paragraph("課程名稱", chinessFont)));
			scoreTable.addCell(new PdfPCell(new Paragraph("學分", chinessFont)));
			scoreTable.addCell(new PdfPCell(new Paragraph("必/選修", chinessFont)));
			scoreTable.addCell(new PdfPCell(new Paragraph("成績", chinessFont)));
			FileReader fr;
			try {
				fr = new FileReader("data/課程資料.txt");
			} catch (FileNotFoundException e1) {
				return false;
			}
			BufferedReader br = new BufferedReader(fr);
			ArrayList<ArrayList<String>> studentCourseInfo = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < 4; i++)
				studentCourseInfo.add(new ArrayList());
			while (br.ready()) {
				String[] courseInfo = br.readLine().split(" ");
				if (selectedSemester.equals(courseInfo[0])) {
					List studentList = (List) Arrays.asList(courseInfo[7].split(","));
					int studentIndex = studentList.indexOf(account);
					String[] studentScore = courseInfo[8].split(",");
					if (studentIndex != -1) {
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[1])));
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[2], chinessFont)));
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[3])));
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[5], chinessFont)));
						scoreTable.addCell(new PdfPCell(new Paragraph(studentScore[studentIndex])));
					}
				}
			}
			document.add(scoreTable);
			fr.close();
			document.close();
			JOptionPane.showMessageDialog(new JTextField(), "下載完成", "成績單下載", JOptionPane.PLAIN_MESSAGE);
			return true;
		} catch (FileNotFoundException | DocumentException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	private String findStudent(String account) throws IOException {
		FileReader fr = null;
		fr = new FileReader("data/學生帳戶資料.txt");
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String[] studentInfo = br.readLine().split(" ");
			if (account.equals(studentInfo[0]))
				return studentInfo[2];
		}
		return "";
	}

}
