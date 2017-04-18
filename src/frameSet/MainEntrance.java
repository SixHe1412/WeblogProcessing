package frameSet;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainEntrance {

	static MainFrame myMainFrame = null;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
				try {
					/*
					 * 想要修改皮肤的话，只需要更改，下面这个函数的参数，具体改成什么样，可以打开，Referenced Libraries -> 点击substance.jar -> 找到org.jvnet.substance.skin这个包  -> 将下面的SubstanceDustCoffeeLookAndFeel 替换成 刚刚打开的包下的任意一个“Substance....LookAndFeel”即可
					 */
					/*UIManager
							.setLookAndFeel(new org.jvnet.substance.skin.SubstanceDustCoffeeLookAndFeel());*/
					
					UIManager
					.setLookAndFeel(new org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel());
					
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myMainFrame = new MainFrame();
				myMainFrame.setTitle("天地图日志数据接入系统");
			}
		});
		
	}
}
