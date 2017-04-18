package functionSet;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef.ClassType;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassMeta;

public class UserIdentify {

	PluginRegistry registry = null;
	String db= null;
	String coll = null;
	
	public UserIdentify(PluginRegistry registry,String db,String coll) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
		this.db = db;
		this.coll = coll;
	}
   
	public StepMeta generateUserIdentifyStep() {
		
		UserDefinedJavaClassMeta javaClassMeta = new UserDefinedJavaClassMeta();
		//设置每个步骤的id
        String javaClassId = registry.getPluginId(StepPluginType.class, javaClassMeta);
        //参数设置
        List<UserDefinedJavaClassDef> javaClassDef = new ArrayList<UserDefinedJavaClassDef>();
        javaClassDef.add(new UserDefinedJavaClassDef(ClassType.TRANSFORM_CLASS, "Processor", getSource()));	
        javaClassMeta.replaceDefinitions(javaClassDef);
        
        //添加TextInput步骤到转换中
        StepMeta javaClassMetaStep = new StepMeta(javaClassId,"用户识别",javaClassMeta);
        
        return javaClassMetaStep;
	}
	
	private String getSource() {
		
		String strSource = "import com.mongodb.DB;\n"
				         + "import com.mongodb.DBCollection;\n"
				         + "import com.mongodb.DBObject;\n"
				         + "import com.mongodb.MongoClient;\n"
				         + "import java.lang.Exception;\n"
				         + "import org.Input.mongodb.InputMongoDB;\n"
				         + "DBCollection coll;\n"
				         + "public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException\n"
				         + "{\n"
				         + "int i = 0;\n"
				         + "if (first){\n"
				         + "first = false;\n"
				         + "MongoClient mongo = null;\n"
				         + " try {\n"
				         + "mongo = new MongoClient("+"\""+"localhost"+"\""+",27017);\n"
				         + " } catch (Exception e) {\n"
				         + "e.printStackTrace();}\n"
				         + "DB db = mongo.getDB("+"\""+db+"\""+");\n"
				         + "coll = db.getCollection("+"\""+coll+"\""+");\n"
				         + "i = 1;}\n"
				         + "Object[] r = getRow();\n"
				         + "if (r == null) {setOutputDone();return false;}\n"
				         + "r = createOutputRow(r, data.outputRowMeta.size());\n"
				         + "String IP = get(Fields.In, "+"\""+"ip"+"\""+").getString(r);\n"
				         + "String SysBrower = get(Fields.In, "+"\""+"brower"+"\""+").getString(r);\n"
				         + "Long tileRow = get(Fields.In, "+"\""+"tileRow"+"\""+").getInteger(r);\n"
				         + "Long tileCol = get(Fields.In, "+"\""+"tileCol"+"\""+").getInteger(r);\n"
				         + "Long level = get(Fields.In, "+"\""+"level"+"\""+").getInteger(r);\n"
				         + "new InputMongoDB(IP,SysBrower,tileRow,tileCol,level,coll,i);\n"
				         + "putRow(data.outputRowMeta, r);\n"
				         + "return true;}";
		return strSource;
	}
}
