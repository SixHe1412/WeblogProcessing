package functionSet;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

public class TextInput {

	PluginRegistry registry = null;
	
	//选中的文件
	public String[] filepath = null;
	public String strSeparate = null;
	public String strFormat = null;
	public boolean blHeader = true;
	
	public TextInput(PluginRegistry registry,Object[] filepath,String strSeparate,String strFormat,boolean blHeader) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
		this.filepath = (String[])filepath;
		this.strSeparate = strSeparate;
		this.strFormat = strFormat;
		this.blHeader = blHeader;
	}
	
	public StepMeta generateTextInputStep(){
		
		TextFileInputMeta textinputMeta = new TextFileInputMeta();
		//设置每个步骤的id
        String textinputId = registry.getPluginId(StepPluginType.class, textinputMeta);
        //基本参数设置
        textinputMeta.setDefault();
        textinputMeta.allocate(filepath.length,13,0);
        textinputMeta.setFileName(filepath);
        textinputMeta.setSeparator(strSeparate);
        textinputMeta.setHeader(blHeader);
        textinputMeta.setFileFormat(strFormat);
        
        //获取字段
        TextFileInputField[] setField = {new TextFileInputField("f1",-1,15),
                                         new TextFileInputField("f2",-1,0),
                                         new TextFileInputField("f3",-1,0),
                                         new TextFileInputField("f4",-1,21),
                                         new TextFileInputField("f5",-1,6),
                                         new TextFileInputField("f6",-1,163),
                                         new TextFileInputField("f7",-1,15),
                                         new TextFileInputField("f8",-1,15),
                                         new TextFileInputField("f9",-1,299),
                                         new TextFileInputField("f10",-1,147),
                                         new TextFileInputField("f11",-1,0),
                                         new TextFileInputField("f12",-1,8),
                                         new TextFileInputField("f13",-1,0)}; 
        setField[0].setType(ValueMetaInterface.TYPE_STRING);
        setField[1].setType(ValueMetaInterface.TYPE_STRING);
        setField[2].setType(ValueMetaInterface.TYPE_STRING);
        setField[3].setType(ValueMetaInterface.TYPE_STRING);
        setField[4].setType(ValueMetaInterface.TYPE_STRING);
        setField[5].setType(ValueMetaInterface.TYPE_STRING);
        setField[6].setType(ValueMetaInterface.TYPE_INTEGER);
        setField[6].setFormat("#");
        setField[7].setType(ValueMetaInterface.TYPE_INTEGER);
        setField[7].setFormat("#");
        setField[8].setType(ValueMetaInterface.TYPE_STRING);
        setField[9].setType(ValueMetaInterface.TYPE_STRING);
        setField[10].setType(ValueMetaInterface.TYPE_STRING);
        setField[11].setType(ValueMetaInterface.TYPE_STRING);
        setField[12].setType(ValueMetaInterface.TYPE_STRING);
        textinputMeta.setInputFields(setField);
        
        //添加TextInput步骤到转换中
        StepMeta textInputMetaStep = new StepMeta(textinputId,"日志导入",textinputMeta);
        
        return textInputMetaStep;
	}
}
