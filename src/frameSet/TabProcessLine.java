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
	    txtA.setFont(new Font("微软雅黑",Font.PLAIN,14));
	    txtA.setBackground(Color.WHITE);
	    txtA.setEditable(false);
	    scrollPane = new JScrollPane(txtA);
	    
	    // 用自己的重载的OutputStream创建一个PrintStream
	    printStream = new PrintStream(new MyOutputStream());
	    // 指定标准输出到自己创建的PrintStream
	    System.setOut(printStream);
	    //System.setErr(printStream);
	    
		this.add(scrollPane,BorderLayout.CENTER);
	}
	
	public class MyOutputStream extends OutputStream{
	    public void write(int arg0) throws IOException {
	      // 写入指定的字节，忽略
	    }    
	    
	    public void write(byte data[]) throws IOException{
	      // 追加一行字符串
	      txtA.append(new String(data));
	    }
	    
	    public void write(byte data[], int off, int len) throws IOException {
	      // 追加一行字符串中指定的部分，这个最重要
	      txtA.append(new String(data, off, len));
	      // 移动TextArea的光标到最后，实现自动滚动
	      txtA.setCaretPosition(txtA.getText().length());
	    }
	}
}

   
