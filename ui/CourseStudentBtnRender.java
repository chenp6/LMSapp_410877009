package ui;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CourseStudentBtnRender implements TableCellRenderer {
	private JButton button;

	public CourseStudentBtnRender() {
		button = new JButton("查看");
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return button;
	}

}
