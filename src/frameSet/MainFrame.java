package frameSet;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import eventSet.MenuAndToolbarHandle;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JMenuBar menuBar = null;
    JMenu fileMenu = null;
    JMenu helpMenu = null;
    JMenu dbMenu = null;
    JMenu dbSetupItem = null;
    public JMenuItem importItem = null;
    public JMenuItem exitItem = null;
    public JMenuItem aboutItem = null;
    public JMenuItem runItem = null;
    public JMenuItem mongodbItem = null;
	
    public JButton btnImport = null;
    public JButton btnSetup = null;
    public JButton btnUserIdentify = null;
    public JButton btnPause = null;
    public JButton btnChart = null;
    public JButton btnExecute = null;
    public JButton btnMap = null;
    public JButton btnExit = null;
    
    JSplitPane mainSplitPane = null;
    JSplitPane rightSplitPane = null;
    public TabControl tabControl = null;
    public FilePanel filePanel = null;
    public DBPanel dbPanel = null;
    JScrollPane scrollPane1 = null;
    JScrollPane scrollPane2 = null;
    
    MenuAndToolbarHandle mtHandle = null;
    
	public MainFrame() {
		
		AddMenuBar();
		AddToolBar();
		SetSplitPanel();
		mtHandle = new MenuAndToolbarHandle(this);
		AddListener();
		
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(getContentPane());
		this.setVisible(true);
		
	}
	
	private void AddMenuBar() {
		
		fileMenu = new JMenu("文件");
		importItem = new JMenuItem("导入数据文件");
		fileMenu.add(importItem);
		runItem = new JMenuItem("执行");
		fileMenu.add(runItem);
		fileMenu.addSeparator();
		exitItem = new JMenuItem("退出");
		fileMenu.add(exitItem);
		
		dbMenu = new JMenu("数据库");
		dbSetupItem = new JMenu("设置数据库");
		mongodbItem = new JMenuItem("mongodb数据库");
		dbSetupItem.add(mongodbItem);
		dbMenu.add(dbSetupItem);
		
		
		helpMenu = new JMenu("帮助");
		aboutItem = new JMenuItem("关于");
		helpMenu.add(aboutItem);
		
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(dbMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);
		
	}
	
	private void AddToolBar() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		btnImport = new JButton(new ImageIcon("images/import.png"));
		btnImport.setToolTipText("导入数据");
		btnImport.setContentAreaFilled(false);  
		btnImport.setMargin(new Insets(0, 0, 0, 0));  
		btnImport.setFocusPainted(false);  
		
		btnSetup = new JButton(new ImageIcon("images/set2.png"));
		btnSetup.setToolTipText("步骤设置");
		btnSetup.setContentAreaFilled(false);  
		btnSetup.setMargin(new Insets(0, 0, 0, 0));  
		btnSetup.setFocusPainted(false);
		
		btnUserIdentify = new JButton(new ImageIcon("images/user1.png"));
		btnUserIdentify.setToolTipText("用户识别");
		btnUserIdentify.setContentAreaFilled(false);  
		btnUserIdentify.setMargin(new Insets(0, 0, 0, 0));  
		btnUserIdentify.setFocusPainted(false); 
		
		btnPause = new JButton(new ImageIcon("images/pause1.png"));
		btnPause.setToolTipText("中止执行");
		btnPause.setContentAreaFilled(false);  
		btnPause.setMargin(new Insets(0, 0, 0, 0));  
		btnPause.setFocusPainted(false);
		
		btnExecute = new JButton(new ImageIcon("images/run1.png"));
		btnExecute.setToolTipText("执行");
		btnExecute.setContentAreaFilled(false);  
		btnExecute.setMargin(new Insets(0, 0, 0, 0));  
		btnExecute.setFocusPainted(false);
		
		btnChart = new JButton(new ImageIcon("images/chart2.png"));
		btnChart.setToolTipText("图表显示");
		btnChart.setContentAreaFilled(false);  
		btnChart.setMargin(new Insets(0, 0, 0, 0));  
		btnChart.setFocusPainted(false);
		
		btnMap = new JButton(new ImageIcon("images/map2.png"));
		btnMap.setToolTipText("地图显示");
		btnMap.setContentAreaFilled(false);  
		btnMap.setMargin(new Insets(0, 0, 0, 0));  
		btnMap.setFocusPainted(false);
		
		btnExit = new JButton(new ImageIcon("images/exit.png"));
		btnExit.setToolTipText("退出程序");
		btnExit.setContentAreaFilled(false);  
		btnExit.setMargin(new Insets(0, 0, 0, 0));  
		btnExit.setFocusPainted(false);  
		
		panel.add(btnImport);
		panel.add(btnSetup);
		panel.add(btnUserIdentify);
		panel.add(btnExecute);
		panel.add(btnPause);
		panel.add(btnChart);
		panel.add(btnMap);
		panel.add(btnExit);
		this.add(panel,BorderLayout.BEFORE_FIRST_LINE);
	}
	
	private void SetSplitPanel() {
		
		mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainSplitPane.setOneTouchExpandable(true);
		mainSplitPane.setDividerLocation(700);
		
		rightSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		filePanel = new FilePanel(this);
		dbPanel = new DBPanel(this);
		scrollPane1 = new JScrollPane(filePanel);
		scrollPane2 = new JScrollPane(dbPanel);
        scrollPane1.setAutoscrolls(true);
        scrollPane2.setAutoscrolls(true);
        rightSplitPane.setOneTouchExpandable(true);
        rightSplitPane.setDividerLocation(250);
        rightSplitPane.setTopComponent(filePanel);
        rightSplitPane.setBottomComponent(dbPanel);
		
		tabControl = new TabControl();
		mainSplitPane.setLeftComponent(tabControl);
		mainSplitPane.setRightComponent(rightSplitPane);
		
		this.getContentPane().add(mainSplitPane);
		
	}
	
	private void AddListener() {
		
		importItem.addActionListener(mtHandle);
		btnImport.addActionListener(mtHandle);
		btnSetup.addActionListener(mtHandle);
		btnUserIdentify.addActionListener(mtHandle);
		btnPause.addActionListener(mtHandle);
		btnChart.addActionListener(mtHandle);
		btnMap.addActionListener(mtHandle);
		runItem.addActionListener(mtHandle);
        btnExecute.addActionListener(mtHandle);
		exitItem.addActionListener(mtHandle);
        btnExit.addActionListener(mtHandle);	
        
        mongodbItem.addActionListener(mtHandle);
        aboutItem.addActionListener(mtHandle);
	}

}
