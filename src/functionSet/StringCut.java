package functionSet;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.stringcut.StringCutMeta;

public class StringCut {

	PluginRegistry registry = null;
	
	public StringCut(PluginRegistry registry) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
	}
	
	public StepMeta generateStringCutStep(){
		
		StringCutMeta stringcutMeta = new StringCutMeta();
		//设置每个步骤的id
        String stringcutId = registry.getPluginId(StepPluginType.class, stringcutMeta);
        //设置参数
        stringcutMeta.setFieldInStream(new String[]{"f4"});
        stringcutMeta.setFieldOutStream(new String[]{"time"});
        stringcutMeta.setCutFrom(new String[]{"1"});
        stringcutMeta.setCutTo(new String[]{"21"});
        
        //添加StringCut步骤到转换中
        StepMeta stringcutMetaStep = new StepMeta(stringcutId,"剪切字符串",stringcutMeta);
        
        return stringcutMetaStep;
	}
}
