package frameSet;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;

import functionSet.ChartShow;
import functionSet.IP;

public class FilePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public CardLayout card = new CardLayout();
	
	JPanel importPanel = null;
	JPanel setupPanel = null;
	JPanel userPanel = null;
	JPanel chartPanel = null;
    JPanel mapPanel = null;
    
	public JTextField txtSeparate = null;
	public JComboBox<String> comboBoxFormat = null;
	public JCheckBox ckbHeader = null;
	public JTextField txtDB = null;
	public JTextField txtColl = null;
	public JTable table = null;
	public DefaultTableModel model = null;
	public JCheckBox ckbUserOrnot = null;
	public JComboBox<String> comboBoxDB = null;
	public JComboBox<String> comboBoxColl = null;
	public JComboBox<String> comboBoxField = null;
	public JComboBox<String> comboBoxChart = null;
	String[] colNames = {"序号","文件/目录"};
	Object[][] cellData;
	
	MainFrame mainFrame;
	
	public FilePanel(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		this.setLayout(card);
		
		AddmapPanel();
		AddimportPanel();
		AddsetupPanel();
		AdduserPanel();
		AddchartPanel();
		AddmapPanel();
		//JScrollPane scrollPane1 = new JScrollPane(importPanel);
		this.add("import",importPanel);
		this.add("setup",setupPanel);
		this.add("user",userPanel);
		this.add("chart",chartPanel);
		this.add("map", mapPanel);
      
	}
	
	
	private void AddmapPanel() {
		
		mapPanel = new JPanel();
		GridBagLayout gbLayout = new GridBagLayout();
		mapPanel.setLayout(gbLayout);
		
		JButton btnbegin = new JButton("开始");
		mapPanel.add(btnbegin);
		
		btnbegin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			   DBCursor cur = mapReduce1();
			   while (cur.hasNext()) {
				    System.out.println(cur.next());
				
			   }
			}
				
		});
		
	}
	
	private void AddimportPanel(){
		
		importPanel = new JPanel();
		GridBagLayout gbLayout = new GridBagLayout();
		importPanel.setLayout(gbLayout);
		
		JLabel lbl1 = new JLabel("选中的文件");
		model = new DefaultTableModel(cellData,colNames);
		table = new JTable();
		table.setModel(model);
		JScrollPane scPane = new JScrollPane(table);
		scPane.setPreferredSize(new Dimension(20, 100));
		JButton btnBrower = new JButton("浏览");
		JButton btnPreData = new JButton("预览数据");
		JButton btnDelete = new JButton("删除");
		
		importPanel.add(lbl1);
		importPanel.add(scPane);
		importPanel.add(btnBrower);
		importPanel.add(btnPreData);
		importPanel.add(btnDelete);
		
		 GridBagConstraints gbc = new GridBagConstraints();
		 gbc.fill = GridBagConstraints.BOTH;
		 gbc.gridwidth = 0;
		 gbc.weightx = 0;
		 gbc.weighty = 0;
		 gbLayout.setConstraints(lbl1, gbc);
		 gbc.gridwidth = 0;
		 gbc.gridheight = 3;
		 gbc.weightx = 0.5;
		 gbc.weighty = 0.5;
		 gbLayout.setConstraints(scPane, gbc);
		 gbc.gridwidth = 1;
		 gbc.weightx = 0;
		 gbc.weighty = 0;
		 gbLayout.setConstraints(btnBrower, gbc);
		 gbc.gridwidth = 1;
		 gbc.weightx = 0;
		 gbc.weighty = 0;
		 gbLayout.setConstraints(btnPreData, gbc);
		 gbc.gridwidth = 0;
		 gbc.weightx = 0;
		 gbc.weighty = 0;
		 gbLayout.setConstraints(btnDelete, gbc);
		 
		 btnBrower.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser Jfc1 = new JFileChooser();
				Jfc1.setAcceptAllFileFilterUsed(false);
				FileFilter filter1 = new FileNameExtensionFilter("文本文档", "txt", "TXT");
				FileFilter filter2 = new FileNameExtensionFilter("日志文件", "log", "LOG");
				Jfc1.addChoosableFileFilter(filter1);
				Jfc1.addChoosableFileFilter(filter2);
				
				Jfc1.setMultiSelectionEnabled(true);
				Jfc1.setDialogType(JFileChooser.OPEN_DIALOG);
				Jfc1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int iRet = Jfc1.showOpenDialog(mainFrame);
				if(iRet == JFileChooser.APPROVE_OPTION){
					
					int fileCount = Jfc1.getSelectedFiles().length;
					Vector<Object> vec = null;
					for (int i = 0; i < fileCount; i++) {
						vec = new Vector<Object>();
						vec.add(table.getRowCount()+1);
						vec.add(Jfc1.getSelectedFiles()[i].toString());
						model.addRow(vec);
					}
				}
				else 
					return;
			}
		});
		 
		 btnPreData.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(table.getSelectedRow() != -1){
				   Object str = table.getValueAt(table.getSelectedRow(), 1);
				   File file = new File(str.toString());
				   BufferedInputStream fis;
				   BufferedReader reader = null;
				   try {
					   fis = new BufferedInputStream(new FileInputStream(file));
					   reader = new BufferedReader(new 
							   InputStreamReader(fis, "utf-8"),5*1024*1024);
				   } catch (FileNotFoundException | UnsupportedEncodingException e1) {
					   // TODO Auto-generated catch block
					   e1.printStackTrace();
				   }

				   //聚焦到原始数据显示tab页
				   mainFrame.tabControl.middleTab.setSelectedComponent(mainFrame.tabControl.tabRawData);
				   
				   String line = "";
				   int lineCount = 0;
				   mainFrame.tabControl.tabRawData.txtA.setText("");
				   try {
					   while((line=reader.readLine())!=null){
						   mainFrame.tabControl.tabRawData.txtA.append(line+"\n");
						   lineCount++;
						   if(lineCount>500)break;
					   }
				   } catch (IOException e1) {
					   // TODO Auto-generated catch block
					   e1.printStackTrace();
				   }
				}
				
			}
		});
		 
		 btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int rowCount = table.getSelectedRows().length;
				if(rowCount != 0){
					for (int i = 0; i < rowCount; i++) 
					    model.removeRow(table.getSelectedRows()[0]);
				}
			}
				
		});
		 
	}
	
	private void AddsetupPanel() {
		
		setupPanel = new JPanel();
		GridBagLayout gbLayout = new GridBagLayout();
		setupPanel.setLayout(gbLayout);
		
		JLabel lbltitle1 = new JLabel("参数设置");
		JLabel lbl2 = new JLabel("分隔符:");
		txtSeparate = new JTextField(";",5);
		JLabel lbl3 = new JLabel("格式:");
		comboBoxFormat = new JComboBox<String>();
		comboBoxFormat.addItem("DOS");
		comboBoxFormat.addItem("mixed");
		ckbHeader = new JCheckBox("设置头部");
		ckbHeader.setSelected(true);
		JLabel lblout = new JLabel("导出方式:");
		JComboBox<String> comboBoxDB = new JComboBox<String>();
		comboBoxDB.addItem("MongoDB");
		JLabel lbldb = new JLabel("Database:");
		txtDB = new JTextField();
		JLabel lblcoll = new JLabel("Collection:");
		txtColl = new JTextField();
		
		setupPanel.add(lbltitle1);
		setupPanel.add(lbl2);
		setupPanel.add(txtSeparate);
		setupPanel.add(lbl3);
		setupPanel.add(comboBoxFormat);
		setupPanel.add(ckbHeader);
		setupPanel.add(lblout);
		setupPanel.add(comboBoxDB);
		setupPanel.add(lbldb);
		setupPanel.add(txtDB);
		setupPanel.add(lblcoll);
		setupPanel.add(txtColl);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lbltitle1, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lbl2, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0;
		gbLayout.setConstraints(txtSeparate, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lbl3, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0;
		gbLayout.setConstraints(comboBoxFormat, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(ckbHeader, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lblout, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0;
		gbLayout.setConstraints(comboBoxDB, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lbldb, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0;
		gbLayout.setConstraints(txtDB, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lblcoll, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0;
		gbLayout.setConstraints(txtColl, gbc);
	}
	
	private void AdduserPanel() {
		
		userPanel = new JPanel();
		GridBagLayout gbLayout = new GridBagLayout();
		userPanel.setLayout(gbLayout);
		
		ckbUserOrnot = new JCheckBox("添加用户识别步骤");
		userPanel.add(ckbUserOrnot);
	
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0;
		gbLayout.setConstraints(ckbUserOrnot, gbc);
	}
	
	
	
	private void AddchartPanel(){
		
		chartPanel = new JPanel();
		GridBagLayout gbLayout = new GridBagLayout();
		chartPanel.setLayout(gbLayout);
		
		JLabel lbldb = new JLabel("Database:");
		JLabel lblcoll = new JLabel("Collection:");
		JLabel lblfield = new JLabel("Field:");
		JLabel lblchart = new JLabel("ChartType:");
		comboBoxDB = new JComboBox<String>();
		comboBoxColl = new JComboBox<String>();
		comboBoxField = new JComboBox<String>();
		comboBoxChart = new JComboBox<String>();
		comboBoxChart.addItem("饼状图");
		comboBoxChart.addItem("柱状图");
        JButton btnAdd1 = new JButton("添加");
        JButton btnAdd2 = new JButton("添加");
        JButton btnAdd3 = new JButton("添加");
        JButton btnShowChart = new JButton("显示Chart图");
        JPanel panel1 = new JPanel();
        
        chartPanel.add(lbldb);
        chartPanel.add(comboBoxDB);
        chartPanel.add(btnAdd1);
        chartPanel.add(lblcoll);
        chartPanel.add(comboBoxColl);
        chartPanel.add(btnAdd2);
        chartPanel.add(lblfield);
        chartPanel.add(comboBoxField);
        chartPanel.add(btnAdd3);
        chartPanel.add(lblchart);
        chartPanel.add(comboBoxChart);
        chartPanel.add(panel1);
        chartPanel.add(btnShowChart);
        
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lbldb, gbc);
		gbc.gridwidth = 4;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		gbLayout.setConstraints(comboBoxDB, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(btnAdd1, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lblcoll, gbc);
		gbc.gridwidth = 4;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		gbLayout.setConstraints(comboBoxColl, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(btnAdd2, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lblfield, gbc);
		gbc.gridwidth = 4;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		gbLayout.setConstraints(comboBoxField, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(btnAdd3, gbc);
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(lblchart, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0.2;
		gbc.weighty = 0;
		gbLayout.setConstraints(comboBoxChart, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(panel1, gbc);
		gbc.gridwidth = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbLayout.setConstraints(btnShowChart, gbc);
		
		
		btnAdd1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int dbNum = mainFrame.dbPanel.dbRoot.getChildCount();
				comboBoxDB.removeAllItems();
				for (int i = 0; i < dbNum; i++) 
					comboBoxDB.addItem(mainFrame.dbPanel.dbRoot.getChildAt(i).toString());
			}
		});
		
       btnAdd2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (comboBoxDB.getSelectedItem() == null) {
					return;
				}
			   int dbNum = mainFrame.dbPanel.dbRoot.getChildCount();
			   for (int i = 0; i < dbNum; i++) {
				   if(comboBoxDB.getSelectedItem().toString().equals(mainFrame.dbPanel.dbRoot.getChildAt(i).toString()))
				   {
					   int collNum = mainFrame.dbPanel.dbRoot.getChildAt(i).getChildCount();
					   comboBoxColl.removeAllItems();
					   for (int j = 0; j < collNum; j++) 
						    comboBoxColl.addItem(mainFrame.dbPanel.dbRoot.getChildAt(i).getChildAt(j).toString());
					   break;
				   }
			   }
			}
		});
       
       btnAdd3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (comboBoxColl.getSelectedItem() == null) {
					return;
				}
				DB db = mainFrame.dbPanel.mongo.getDB(comboBoxDB.getSelectedItem().toString());
				DBCollection coll = db.getCollection(comboBoxColl.getSelectedItem().toString());
				DBCursor cur = coll.find();
			
				    
				DBObject obj = cur.next();
				
				if(obj.keySet().size()>3)
				{
					comboBoxField.addItem("level");
					comboBoxField.addItem("type");
				}
				else {
					comboBoxField.addItem("prov");
				}
					
			   
			}
		});
       
       btnShowChart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String strField = comboBoxField.getSelectedItem().toString();
				String strChart = comboBoxChart.getSelectedItem().toString();
				new ChartShow(mainFrame,mapReduce1(),strField,strChart);
              
			}
		});
		
	}

	
	private DBCursor mapReduce1() {
		
		DB db = mainFrame.dbPanel.mongo.getDB(comboBoxDB.getSelectedItem().toString());
		DBCollection coll = db.getCollection(comboBoxColl.getSelectedItem().toString());
		
		//String map = "function(){emit(this."+comboBoxField.getSelectedItem()+",{count:1});}";
		String map = "function(){emit(this."+comboBoxField.getSelectedItem()+",1);}";
		
		/*String reduce = "function(key,values){";
		reduce = reduce + "var total=0;";
		reduce = reduce + "for(var i=0;i<values.length;i++){total+=values[i].count;}";
		reduce = reduce + "return total;}";*/
		
		String reduce = "function(key,values){";
		reduce = reduce + "var x=0;";
		reduce = reduce + "values.forEach(function (v){x+=v;});";
		reduce = reduce + "return x;}";
		
		String result = "resColl";
		
		MapReduceOutput mapReduceOutput= coll.mapReduce(map,reduce,result,null);
		DBCollection resultColl=mapReduceOutput.getOutputCollection();
		DBCursor cursor=resultColl.find();

       return cursor;
	}
	
	private DBCursor mapReduce2() {
		
		DB db = mainFrame.dbPanel.mongo.getDB(comboBoxDB.getSelectedItem().toString());
		DBCollection coll = db.getCollection(comboBoxColl.getSelectedItem().toString());
		
		String map = "function(){emit(this."+comboBoxField.getSelectedItem()+",1);}";
		
		
		String reduce = "function(key,values){";
		reduce = reduce + "var x=0;";
		reduce = reduce + "values.forEach(function (v){x+=v;});";
		reduce = reduce + "return x;}";
		
		String result = "resColl";
		
		DBObject query = new BasicDBObject("time",new BasicDBObject("$gte","00:00:00").
				            append("&lte", "06:00:00"));
		MapReduceOutput mapReduceOutput= coll.mapReduce(map,reduce,result,query);
		DBCollection resultColl=mapReduceOutput.getOutputCollection();
		DBCursor cursor=resultColl.find();

       return cursor;
	}
}
