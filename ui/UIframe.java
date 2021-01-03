package ui;




import javax.swing.JFrame;
import javax.swing.JPanel;



public class UIframe extends JFrame {
	public  JPanel contextPanel = new JPanel();
	public UIframe() {
			
			setTitle("高燕大學生成績管理系統");
			setSize(916, 884);
			getContentPane().setLayout(null);
			
			contextPanel = new LoginPanel();//測試panel時替換這行的LoginPanel()
			contextPanel.setBounds(0, 50, 916, 614);
			getContentPane().add(contextPanel);
			
			// 顯示JFrame
			setVisible(true);
			// JFrame關閉時的操作
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void changePage(JPanel newPanel){
		remove(contextPanel);
		contextPanel = newPanel;
		add(contextPanel);
		repaint();
	}

	
	
}
