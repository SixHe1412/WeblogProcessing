package functionSet;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.textfileoutput.TextFileOutputMeta;

public class TextOutput {

	PluginRegistry registry = null;
	
	public TextOutput(PluginRegistry registry) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
	}
	
	public StepMeta generateTextOutputStep(){
		
		TextFileOutputMeta textoutputMeta = new TextFileOutputMeta();
        String textoutputId = registry.getPluginId(StepPluginType.class, textoutputMeta);
        textoutputMeta.setDefault();
        textoutputMeta.setFilename("C:\\Users\\dell\\Desktop\\hhh");

        //���Output���赽ת����
        StepMeta textOutputMetaStep = new StepMeta(textoutputId,"Text output",textoutputMeta);
        
        return textOutputMetaStep;
	}
}
