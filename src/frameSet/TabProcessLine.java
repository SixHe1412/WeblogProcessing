package frameSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TabProcessLine extends JPanel {

	private static final long serialVersionUID = 1L;

	public JTextArea txtA = null;
	JScrollPane scrollPane = null;
	
	private PrintStream printStream;
	
	public TabProcessLine() {
		
		this.setLayout(new BorderLayout());
	    txtA = new JTextArea();
	    txtA.setFont(new Font("΢���ź�",Font.PLAIN,14));
	    txtA.setBackground(Color.WHITE);
	    txtA.setEditable(false);
	    scrollPane = new JScrollPane(txtA);
	    
	    // ���Լ������ص�OutputStream����һ��PrintStream
	    printStream = new PrintStream(new MyOutputStream());
	    // ָ����׼������Լ�������PrintStream
	    System.setOut(printStream);
	    //System.setErr(printStream);
	    
		this.add(scrollPane,BorderLayout.CENTER);
	}
	
	public class MyOutputStream extends OutputStream{
	    public void write(int arg0) throws IOException {
	      // д��ָ�����ֽڣ�����
	    }    
	    
	    public void write(byte data[]) throws IOException{
	      // ׷��һ���ַ���
	      txtA.append(new String(data));
	    }
	    
	    public void write(byte data[], int off, int len) throws IOException {
	      // ׷��һ���ַ�����ָ���Ĳ��֣��������Ҫ
	      txtA.append(new String(data, off, len));
	      // �ƶ�TextArea�Ĺ�굽���ʵ���Զ�����
	      txtA.setCaretPosition(txtA.getText().length());
	    }
	}
}

   
