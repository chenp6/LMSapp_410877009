package character;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import LMSapp.LMSapp;

public class Student extends Account {

	String entryYear;
	public Student(String account, String password, String name,String entryYear,char character) {
		super(account, password, name,character);
		this.entryYear = entryYear;
	}
	@Override
	public boolean changePassword(String oldPassword, String newPassword) throws IOException {
		boolean correctPw = false;//輸入的舊密碼是否正確，是否為本人
		FileReader fr = null;
		fr = new FileReader("data/學生帳戶資料.txt");
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
		FileOutputStream writerStream = new FileOutputStream("data/學生帳戶資料.txt");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
		writer.write(writeText);
		writer.close();
		return correctPw;
	}
	
	
	public boolean printScore() {
		String path = "";
		JFileChooser fileChoice = new JFileChooser();
		fileChoice.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int approve = fileChoice.showSaveDialog(null);
		if (approve == JFileChooser.APPROVE_OPTION)
			path = fileChoice.getSelectedFile().getPath()+LMSapp.userAccount.account+LMSapp.userAccount.name+"的成績單.pdf";
		File checkFile = new File(path);
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
			PdfWriter.getInstance(document,
					new FileOutputStream(path));
			document.open();
			document.add(new Paragraph( LMSapp.userAccount.account +  LMSapp.userAccount.name + "的總成績單\n\n", chinessFont));
			PdfPTable scoreTable = new PdfPTable(6);
			scoreTable.setWidths(new float[] { 10f, 15f, 50f, 10f, 13f, 10f });
			scoreTable.addCell(new PdfPCell(new Paragraph("學期", chinessFont)));
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
					List studentList = (List) Arrays.asList(courseInfo[7].split(","));
					int studentIndex = studentList.indexOf( LMSapp.userAccount.account);
					String[] studentScore = courseInfo[8].split(",");
					if (studentIndex != -1) {
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[0])));
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[1])));
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[2], chinessFont)));
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[3])));
						scoreTable.addCell(new PdfPCell(new Paragraph(courseInfo[5], chinessFont)));
						scoreTable.addCell(new PdfPCell(new Paragraph(studentScore[studentIndex])));
					}
				}
				fr.close();
				document.add(scoreTable);
				document.close();
				JOptionPane.showMessageDialog(new JTextField(), "下載完成", "下載總成績單", JOptionPane.PLAIN_MESSAGE);
				return true;
		} catch (FileNotFoundException | DocumentException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	

	
}
