package renderSet;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override  
    public Component getTreeCellRendererComponent(JTree tree, Object value,  
            boolean sel, boolean expanded, boolean leaf, int row,  
            boolean hasFocus)  
    {  
		
		//ִ�и���ԭ�Ͳ���  
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
                row, hasFocus);  
        
        setText(value.toString());  
        
        if (sel)  
        {  
            setForeground(getTextSelectionColor());  
        }  
        else  
        {  
            setForeground(getTextNonSelectionColor());  
        }  
          
        //�õ�ÿ���ڵ��TreeNode  
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;  
          
        if(node.isLeaf())
        	this.setIcon(new ImageIcon("images/sheet.png"));
        else
        	this.setIcon(new ImageIcon("images/db.png"));
		return this;
    }
}
