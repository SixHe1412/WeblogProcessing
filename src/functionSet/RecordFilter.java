package functionSet;

import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.row.ValueMetaAndData;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.filterrows.FilterRowsMeta;
import org.pentaho.di.core.Condition;

public class RecordFilter {

	PluginRegistry registry = null;
	
	public RecordFilter(PluginRegistry registry) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
	}
	
	public StepMeta generateRecordFilterStep(){
		
		FilterRowsMeta filterRowsMeta = new FilterRowsMeta();
		//设置每个步骤的id
		String filterRowsId = registry.getPluginId(StepPluginType.class,filterRowsMeta);
		//基本参数设置z
		Condition condition = null;
		try {
			condition = new Condition(false,"method",0,null,new ValueMetaAndData("method",(String)"GET"));
			condition.addCondition(new Condition(2,"param",8,null,null));
			condition.addCondition(new Condition(2,"f7",0,null,new ValueMetaAndData("f7",(long) 200)));
		} catch (KettleValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		filterRowsMeta.setCondition(condition);
		
		 //添加TextInput步骤到转换中
        StepMeta filterRowsMetaStep = new StepMeta(filterRowsId,"过滤记录",filterRowsMeta);
         
        //设置步骤显示位置
        filterRowsMetaStep.setDraw(true);
        filterRowsMetaStep.setLocation(400, 100);
        
        return filterRowsMetaStep;
	}
}
