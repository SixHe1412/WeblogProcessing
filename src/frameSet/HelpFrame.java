package frameSet;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class HelpFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public HelpFrame() {
		// TODO Auto-generated constructor stub
		
		setLayout(new BorderLayout());
		setTitle("关于");
		setSize(300, 200);
		setLocationRelativeTo(getContentPane());
		AddTab();
		setVisible(true);
	}
	
	private void AddTab() {
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel tabAbout = new JPanel();
		tabbedPane.addTab("关于系统", new ImageIcon("images/star.png"),tabAbout,"关于");
		
		JPanel tabUse = new JPanel();
		tabbedPane.addTab("使用说明",new ImageIcon("images/star.png"), tabUse,"说明");
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		add(tabbedPane,BorderLayout.CENTER);
	}
}
