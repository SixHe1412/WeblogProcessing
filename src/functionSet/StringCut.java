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
		//����ÿ�������id
        String stringcutId = registry.getPluginId(StepPluginType.class, stringcutMeta);
        //���ò���
        stringcutMeta.setFieldInStream(new String[]{"f4"});
        stringcutMeta.setFieldOutStream(new String[]{"time"});
        stringcutMeta.setCutFrom(new String[]{"1"});
        stringcutMeta.setCutTo(new String[]{"21"});
        
        //���StringCut���赽ת����
        StepMeta stringcutMetaStep = new StepMeta(stringcutId,"�����ַ���",stringcutMeta);
        
        return stringcutMetaStep;
	}
}
