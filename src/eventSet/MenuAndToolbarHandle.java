package eventSet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import frameSet.DBPanel;
import frameSet.HelpFrame;
import frameSet.MainFrame;
import functionSet.ExecuteProcess;
import functionSet.GetMongodb;
import functionSet.IP;

public class MenuAndToolbarHandle implements ActionListener{

	MainFrame mainFrame = null;
	ExecuteProcess executeProcess = null;
	boolean blRun = false;
	
	public MenuAndToolbarHandle(MainFrame mainFrame){
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == mainFrame.exitItem | e.getSource() == mainFrame.btnExit){
			System.exit(0);
		}
		else if (e.getSource() == mainFrame.btnImport) {
			
			mainFrame.filePanel.card.show(mainFrame.filePanel, "import");
		}
		else if (e.getSource() == mainFrame.importItem) {
			
			mainFrame.filePanel.card.show(mainFrame.filePanel, "import");
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
					vec.add(mainFrame.filePanel.table.getRowCount()+1);
					vec.add(Jfc1.getSelectedFiles()[i].toString());
					mainFrame.filePanel.model.addRow(vec);
				}
			}
			else 
				return;
		}
        else if (e.getSource() == mainFrame.btnSetup) {
			
			mainFrame.filePanel.card.show(mainFrame.filePanel, "setup");
		}
        else if (e.getSource() == mainFrame.btnUserIdentify) {
        	
        	mainFrame.filePanel.card.show(mainFrame.filePanel, "user");
		}
        else if(e.getSource() == mainFrame.btnPause){
        	
        	if(blRun)
        	{
        	   if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(mainFrame, "确定要中止该执行程序吗？！","提示",JOptionPane.YES_OPTION))
        	   {
        		   executeProcess.pause();
        	       mainFrame.tabControl.tabProcessLine.txtA.append("\n转换已中止执行\n");
        	       blRun = false;
        	   }
        	}
        }
        else if(e.getSource() == mainFrame.btnChart){
        	
        	mainFrame.filePanel.card.show(mainFrame.filePanel, "chart");
        }
        else if(e.getSource() == mainFrame.btnMap){
        	
        	mainFrame.filePanel.card.show(mainFrame.filePanel, "map");
        }
        else if(e.getSource() == mainFrame.btnExecute | e.getSource() == mainFrame.runItem){
            	
        	if(mainFrame.filePanel.table.getRowCount() == 0)
        	{
        		//JOptionPane.showMessageDialog(mainFrame, "请先选择待处理的数据文件！", "提示", 2);
        		if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(mainFrame, "请先选择待处理的数据文件！","提示",JOptionPane.YES_OPTION))
        			mainFrame.filePanel.card.show(mainFrame.filePanel, "import");
        		return;
        	}
        	if (mainFrame.filePanel.txtDB.getText().equals("") | mainFrame.filePanel.txtColl.getText().equals("")) {
        		if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(mainFrame, "数据库或集合不能为空，请先选择！","提示",JOptionPane.YES_OPTION))
        			mainFrame.filePanel.card.show(mainFrame.filePanel, "setup");
        		return;
			}
        	blRun = true;
        	new Thread(new MyThread(1)).start();
        	new Thread(new MyThread(0)).start();
        	
        }
        else if(e.getSource() == mainFrame.mongodbItem){
        	
        	new GetMongodb(mainFrame);
        	mainFrame.dbPanel.update(mainFrame.dbPanel.getGraphics());
    		mainFrame.dbPanel.repaint();
        }
        else if(e.getSource() == mainFrame.aboutItem){
        	
        	new HelpFrame();
        	IP.load("ipbase/17monipdb.dat");
        	System.out.println(Arrays.toString(IP.find("202.114.118.134")));
        	
        }
		
	}
	
	public class MyThread extends Thread{
		
		int iTell;
		public MyThread(int itell) {
			// TODO Auto-generated constructor stub
			this.iTell = itell;
		}
		
		@Override
		public void run() {
			
			if(iTell==1){
				mainFrame.tabControl.tabProcessLine.txtA.setText("");
	        	mainFrame.tabControl.middleTab.setSelectedComponent(mainFrame.tabControl.tabProcessLine);
			}
			else {
			    executeProcess = new ExecuteProcess(mainFrame);
	        	executeProcess.execute();
	        	blRun = false;
			}
				
		}
	}
}
