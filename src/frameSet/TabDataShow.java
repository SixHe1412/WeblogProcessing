package frameSet;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TabDataShow extends JPanel {

	private static final long serialVersionUID = 1L;

	public JTable table = null;
	public DefaultTableModel model = null;
    String[] colNames = {};
	Object[][] celldata;
	
	public TabDataShow() {
		// TODO Auto-generated constructor stub
		model = new DefaultTableModel(celldata,colNames);
		table = new JTable();
		table.setModel(model);
		table.setAutoscrolls(true);
		JScrollPane scrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		this.add(scrollPane,BorderLayout.CENTER);
	}
}
