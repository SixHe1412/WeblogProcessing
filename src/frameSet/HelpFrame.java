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
		setTitle("����");
		setSize(300, 200);
		setLocationRelativeTo(getContentPane());
		AddTab();
		setVisible(true);
	}
	
	private void AddTab() {
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel tabAbout = new JPanel();
		tabbedPane.addTab("����ϵͳ", new ImageIcon("images/star.png"),tabAbout,"����");
		
		JPanel tabUse = new JPanel();
		tabbedPane.addTab("ʹ��˵��",new ImageIcon("images/star.png"), tabUse,"˵��");
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		add(tabbedPane,BorderLayout.CENTER);
	}
}
