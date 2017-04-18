package functionSet;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.fieldsplitter.FieldSplitterMeta;

public class FieldSplit {

	PluginRegistry registry = null;
	
	String splitfield,delimiter;
	String[] fieldname;
	int[] fieldtype,fieldlength;
	
	public FieldSplit(PluginRegistry registry,String field,String delimiter,String[] fieldname,
			int[] fieldtype,int[] fieldlength) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
		this.delimiter = delimiter;
		this.splitfield = field;
		this.fieldname = fieldname;
		this.fieldtype = fieldtype;
		this.fieldlength = fieldlength;
	}
	
	public StepMeta generateFieldSplitStep() {
		
		FieldSplitterMeta fieldsplitMeta = new FieldSplitterMeta();
		//����ÿ�������id
		String fieldsplitId = registry.getPluginId(StepPluginType.class,fieldsplitMeta);
		//������������
		fieldsplitMeta.allocate(fieldname.length);
		fieldsplitMeta.setSplitField(splitfield);
		fieldsplitMeta.setDelimiter(delimiter);
		fieldsplitMeta.setFieldName(fieldname);
		fieldsplitMeta.setFieldType(fieldtype);
		fieldsplitMeta.setFieldLength(fieldlength);
		
		//��Ӳ�ֲ��赽ת����
        StepMeta fieldsplitMetaStep = new StepMeta(fieldsplitId,"���"+splitfield+"�ֶ�",fieldsplitMeta);
        //�����������spoon�����е���ʾλ��
        fieldsplitMetaStep.setDraw(true);
        fieldsplitMetaStep.setLocation(300, 100*fieldname.length);
		return fieldsplitMetaStep;
	}
}
