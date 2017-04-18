package functionSet;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import renderSet.MyDefaultTreeCellRenderer;

import com.mongodb.MongoClient;

import frameSet.MainFrame;

public class GetMongodb {

	MainFrame mainFrame = null;
	
	public GetMongodb(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		 
		this.mainFrame = mainFrame;
		AddDBTree();
	}
	
	public void AddDBTree() {
		
		try {
			mainFrame.dbPanel.mongo = new MongoClient("localhost",27017);
			
			List<String> strDB = mainFrame.dbPanel.mongo.getDatabaseNames();
			mainFrame.dbPanel.dbRoot = new DefaultMutableTreeNode("MongoDB");
			
			for (int i = 0; i < strDB.size(); i++) {
				mainFrame.dbPanel.dbNode = new DefaultMutableTreeNode(strDB.get(i));
				mainFrame.dbPanel.dbRoot.add(mainFrame.dbPanel.dbNode);
				Set<String> strCol = mainFrame.dbPanel.mongo.getDB(strDB.get(i)).getCollectionNames();
				Iterator it = strCol.iterator();
				while(it.hasNext()){
					mainFrame.dbPanel.colNode = new DefaultMutableTreeNode(it.next());
					mainFrame.dbPanel.dbNode.add(mainFrame.dbPanel.colNode);
				}
			}
			
			mainFrame.dbPanel.dbRoot.isRoot();
			
			mainFrame.dbPanel.model = new DefaultTreeModel(mainFrame.dbPanel.dbRoot);      
			mainFrame.dbPanel.dbTree = new JTree(mainFrame.dbPanel.model);
			mainFrame.dbPanel.dbTree.setCellRenderer(new MyDefaultTreeCellRenderer());
			mainFrame.dbPanel.scrollPane = new JScrollPane(mainFrame.dbPanel.dbTree);
			
			mainFrame.dbPanel.add(mainFrame.dbPanel.scrollPane, BorderLayout.CENTER);
			
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(mainFrame,"未安装MongoDB或者MongoDB服务未启动","提示",2);
		    return;
		}
		
		
		
		
	}
}
