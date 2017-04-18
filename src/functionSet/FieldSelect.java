package functionSet;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.selectvalues.SelectValuesMeta;

public class FieldSelect {

	PluginRegistry registry = null;
	private String[] selectName = {"f1","time","f10","tileRow","tileCol","level","type","f8","version",
			                       "f7","param","f9","f2","f3","f4","f5","f11","f12","f13","method"};
	private String[] selectRename = {"ip",null,"brower",null,null,null,null,"byte",null,
			                         null,null,null,null,null,null,null,null,null,null,null};
	private String[] deleteName = {"f2","f3","f4","f5","f7","f9","f11","f12","f13","method","param","version"};
	private int[] selectLength = {15,20,147,6,6,2,11,6,0,0,0,0,0,0,0,0,0,0,0,0};
	
	public FieldSelect(PluginRegistry registry) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
	}
	
	public StepMeta generateFieldSelectStep(){
		
		SelectValuesMeta selectValuesMeta = new SelectValuesMeta();
		//设置每个步骤的id
        String selectvaluetId = registry.getPluginId(StepPluginType.class, selectValuesMeta);
		//参数设置
        selectValuesMeta.allocate(selectRename.length, deleteName.length, 0);
        selectValuesMeta.setSelectName(selectName);
        selectValuesMeta.setSelectRename(selectRename);
		selectValuesMeta.setDeleteName(deleteName);
		selectValuesMeta.setSelectLength(selectLength);
		
		 //添加TextInput步骤到转换中
        StepMeta selectValuesMetaStep = new StepMeta(selectvaluetId,"过滤字段",selectValuesMeta);
         
		return selectValuesMetaStep;
	}
}
