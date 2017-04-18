package frameSet;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabControl extends JPanel {
	private static final long serialVersionUID = 1L;

	public JTabbedPane middleTab = null;
	public TabProcessLine tabProcessLine = null;
	public TabDataShow tabDataShow = null;
	public TabChartShow tabChartShow = null;
	public TabRawData tabRawData = null;
	public TabMapShow tabMapShow = null;
	
	public TabControl() {
		
		this.setLayout(new BorderLayout());
		if(middleTab != null)
		{
			middleTab.removeAll();
			middleTab= null;
		}
		middleTab = new JTabbedPane();
		
		tabRawData = new TabRawData();
		middleTab.addTab("ԭʼ������ʾ",new ImageIcon("images/text.png"),tabRawData,"Դ����");
		
		tabProcessLine = new TabProcessLine();
		middleTab.addTab("��������ʾ",new ImageIcon("images/console.png"),tabProcessLine,"������");
		
		tabDataShow = new TabDataShow();
		middleTab.addTab("������ʾ",new ImageIcon("images/sheet.png"),tabDataShow,"���ݱ�");
		
		tabChartShow = new TabChartShow();
		middleTab.addTab("ͼ����ʾ",new ImageIcon("images/schart.png"),tabChartShow,"ͼ��");
		
		tabMapShow = new TabMapShow();
		middleTab.addTab("��ͼ��ʾ", new ImageIcon("images/map.png"), tabMapShow, "��ͼ");

		middleTab.doLayout();
		this.add(middleTab, BorderLayout.CENTER);
	}
}
