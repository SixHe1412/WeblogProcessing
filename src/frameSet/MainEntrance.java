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
					 * ��Ҫ�޸�Ƥ���Ļ���ֻ��Ҫ���ģ�������������Ĳ���������ĳ�ʲô�������Դ򿪣�Referenced Libraries -> ���substance.jar -> �ҵ�org.jvnet.substance.skin�����  -> �������SubstanceDustCoffeeLookAndFeel �滻�� �ոմ򿪵İ��µ�����һ����Substance....LookAndFeel������
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
				myMainFrame.setTitle("���ͼ��־���ݽ���ϵͳ");
			}
		});
		
	}
}
