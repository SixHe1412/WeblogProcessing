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
		middleTab.addTab("原始数据显示",new ImageIcon("images/text.png"),tabRawData,"源数据");
		
		tabProcessLine = new TabProcessLine();
		middleTab.addTab("处理行显示",new ImageIcon("images/console.png"),tabProcessLine,"处理行");
		
		tabDataShow = new TabDataShow();
		middleTab.addTab("数据显示",new ImageIcon("images/sheet.png"),tabDataShow,"数据表");
		
		tabChartShow = new TabChartShow();
		middleTab.addTab("图表显示",new ImageIcon("images/schart.png"),tabChartShow,"图表");
		
		tabMapShow = new TabMapShow();
		middleTab.addTab("地图显示", new ImageIcon("images/map.png"), tabMapShow, "地图");

		middleTab.doLayout();
		this.add(middleTab, BorderLayout.CENTER);
	}
}
