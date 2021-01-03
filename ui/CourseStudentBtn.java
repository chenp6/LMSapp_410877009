package ui;

import java.util.ArrayList;

import javax.swing.JButton;

public class CourseStudentBtn extends JButton {
	static ArrayList<String> courseStudentList = new ArrayList<String>();
	private String str;
	private int row;

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getStr() {
		return this.str;
	}
	
	public CourseStudentBtn() {
	}
	
	public CourseStudentBtn(String name) {
		super(name);
		this.str = name;
	}


}