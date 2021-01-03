package LMSapp;
import ui.*;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import character.*;

public class LMSapp {
	public static Account userAccount ;
	//測試時，可以直接用以下身分，不用從登入開始(上面那行要註解掉)
	//學生
	//public static Account userAccount = new Student("410877009","123","陳品蓉","108",'s');
	//教授
	//public static Account userAccount = new Professor("77001","123","葉道明",'p');
	//管理員
	//public static Account userAccount = new Manager("7777","1234","黃管理員",'m');
	
	
	//若要從不同頁面測試(不用登入)，到UIframe裡面改JPanel loginPanel = new 你要的Panel();
	
	
	public static UIframe frame =new UIframe();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 顯示JFrame
					frame.setVisible(true);
					// JFrame關閉時的操作
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
				} catch (Exception e) {}
			}
		});
	}
	


}
