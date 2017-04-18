package functionSet;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.mongodboutput.MongoDbOutputMeta;

public class MongodbOutput {

	PluginRegistry registry = null;
	List<MongoDbOutputMeta.MongoField> mongoFields = new ArrayList<MongoDbOutputMeta.MongoField>();
	
	String strDB = null;
	String strColl = null;
	
	public MongodbOutput(PluginRegistry registry,String strDB,String strColl) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
		this.strDB = strDB;
		this.strColl = strColl;
	}
	
	public void addMongoField(String field,boolean[] useorNo) {
		
		String[] fields = field.split(" ");
		for (int i = 0; i < fields.length; i++) {
			MongoDbOutputMeta.MongoField m_field = new MongoDbOutputMeta.MongoField();
			m_field.m_incomingFieldName = fields[i];
			m_field.m_useIncomingFieldNameAsMongoFieldName = useorNo[i];
			mongoFields.add(m_field);
		}
	}
	
	public StepMeta generateMongoOutputStep(){
		
		MongoDbOutputMeta mongooutputMeta = new MongoDbOutputMeta();
		//设置每个步骤的id
		String mongooutputId = registry.getPluginId(StepPluginType.class, mongooutputMeta);
		mongooutputMeta.setDefault();
		mongooutputMeta.setCollection(strColl);
		mongooutputMeta.setDbName(strDB);
		boolean[] useorNo = {true,true,true,true,true,true,true,true};
		addMongoField("ip time brower tileRow tileCol level type byte",useorNo);
		
		mongooutputMeta.setMongoFields(mongoFields);
		
		//添加TextInput步骤到转换中
        StepMeta mongooutputMetaStep = new StepMeta(mongooutputId,"导入MongoDB",mongooutputMeta);
             
        return mongooutputMetaStep;
		
	}
}
