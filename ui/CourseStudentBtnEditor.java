package ui;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CourseStudentBtnEditor extends DefaultCellEditor {

	private CourseStudentBtn button;
	private int rowNum;
	private ListEvent event;
	private String lbl = "查看";
	private Boolean clicked;

	public CourseStudentBtnEditor() {
		super(new JTextField());
		button = new CourseStudentBtn("查看");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 調用自訂處理方法
				fireEditingStopped();
			}
		});

	}



	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

			CourseStudentBtn btn = (CourseStudentBtn) value;
			rowNum = row;
			clicked = true;
			return button;

	}
	
	
	 @Override
	  public Object getCellEditorValue() {
	     if(clicked)
	      {
	        JOptionPane.showMessageDialog(button, CourseStudentBtn.courseStudentList.get(rowNum), "修課名單", JOptionPane.PLAIN_MESSAGE);		
	      }
	    clicked=false;
	    return button;
	  }

	   @Override
	   public boolean stopCellEditing() {
		 clicked=false;
	     return super.stopCellEditing();
	   }
	   
	   @Override
	   protected void fireEditingStopped() {
	     super.fireEditingStopped();
	   }
}

abstract class ListEvent {
	public abstract void invoke(ActionEvent e);
}
