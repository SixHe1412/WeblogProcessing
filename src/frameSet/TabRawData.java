package frameSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TabRawData extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JTextArea txtA = null;
	JScrollPane scrollPane = null;
	
	public TabRawData() {
		
		this.setLayout(new BorderLayout());
	    txtA = new JTextArea();
	    txtA.setFont(new Font("Î¢ÈíÑÅºÚ",Font.PLAIN,14));
	    txtA.setBackground(Color.WHITE);
	    txtA.setEditable(false);
	    scrollPane = new JScrollPane(txtA);
		this.add(scrollPane,BorderLayout.CENTER);
	}

}
