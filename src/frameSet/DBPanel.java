package frameSet;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import renderSet.MyDefaultTreeCellRenderer;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import eventSet.MenuAndToolbarHandle;
import functionSet.GetMongodb;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DBPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public JTree dbTree = null;
	public DefaultTreeModel model = null;
	public JScrollPane scrollPane = null;
	public DefaultMutableTreeNode dbRoot = null;
	public DefaultMutableTreeNode dbNode = null;
	public DefaultMutableTreeNode colNode = null;
	public JButton btnShow = null;
	public JButton btnRefresh = null;
	
	public MongoClient mongo = null;
	MainFrame mainFrame = null;
	
	MenuAndToolbarHandle mtHandle = null;
	
	public DBPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout());
		try {
			mongo = new MongoClient("localhost",27017);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		mtHandle = new MenuAndToolbarHandle(mainFrame);
		
		JPanel panel1 = new JPanel(new FlowLayout());
		JLabel lbltitle = new JLabel("数据库显示区");
		lbltitle.setFont(new Font("微软雅黑",Font.PLAIN,14));
		panel1.add(lbltitle);
		add(panel1, BorderLayout.NORTH);
		
		AddTree();
		
		JPanel panel2 = new JPanel();
		btnShow = new JButton("显示");
		btnRefresh = new JButton("刷新");
		btnShow.addActionListener(mtHandle);
		btnRefresh.addActionListener(mtHandle);
		btnShow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DataTableShow();
				mainFrame.tabControl.middleTab.setSelectedComponent(mainFrame.tabControl.tabDataShow);
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainFrame.dbPanel.remove(scrollPane);
				mainFrame.dbPanel.updateUI();
				mainFrame.dbPanel.repaint();
				AddTree();
				mainFrame.dbPanel.update(mainFrame.dbPanel.getGraphics());
				mainFrame.dbPanel.repaint();
				
			}
		});
		panel2.add(btnShow);
		panel2.add(btnRefresh);
		add(panel2, BorderLayout.SOUTH);
	}
	
	private void AddTree() {
		
		try {
			
			List<String> strDB = mongo.getDatabaseNames();
			dbRoot = new DefaultMutableTreeNode("MongoDB");
			
			for (int i = 0; i < strDB.size(); i++) {
				dbNode = new DefaultMutableTreeNode(strDB.get(i));
				dbRoot.add(dbNode);
				Set<String> strCol = mongo.getDB(strDB.get(i)).getCollectionNames();
				Iterator it = strCol.iterator();
				while(it.hasNext()){
					colNode = new DefaultMutableTreeNode(it.next());
					dbNode.add(colNode);
				}
			}
			
			dbRoot.isRoot();
			
			model = new DefaultTreeModel(dbRoot);  
			
			dbTree = new JTree(model);
			dbTree.setCellRenderer(new MyDefaultTreeCellRenderer());
			
			scrollPane = new JScrollPane(dbTree);
			this.add(scrollPane, BorderLayout.CENTER);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(mainFrame,"未安装MongoDB或者MongoDB服务未启动","提示",2);
		    return;
		}
	}
	
	private void DataTableShow() {
		
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)dbTree.getLastSelectedPathComponent();
		if(selectNode == null)
			return;
	    else{
	    	if(selectNode.isLeaf())
	    	    DataToTable(selectNode);
	    }
	}
	
	private void DataToTable(DefaultMutableTreeNode selectNode) {
		
		
		DB db = mongo.getDB(selectNode.getParent().toString());
		DBCollection coll = db.getCollection(selectNode.toString());
		DBCursor cur = null;
		if(coll.getCount()>500)
			cur = coll.find().limit(500);
		else
		    cur = coll.find();
		DBObject obj = cur.next();
		
		Set<String> field = obj.keySet();
		Iterator it = field.iterator();
		String[] colNames = new String[field.size()];
		for (int i = 0; i < field.size(); i++) 
			colNames[i] = it.next().toString();
		
		Object[][] celldata = new Object[500][field.size()];
		mainFrame.tabControl.tabDataShow.model = new DefaultTableModel(celldata,colNames);
	    
		mainFrame.tabControl.tabDataShow.table.repaint();
		mainFrame.tabControl.tabDataShow.table.setModel(mainFrame.tabControl.tabDataShow.model);
	    
	    int rows = 0;
	    do{
		   for (int j = 0; j < field.size(); j++) 
			   mainFrame.tabControl.tabDataShow.table.setValueAt(obj.get(colNames[j]), rows, j);		   
		   rows++;
	
		   if(!cur.hasNext())
			   break;
		   obj = cur.next();
	    }while (true);
	}
	
	public DefaultTreeModel getModel() {
		
		return model;
	}
}
