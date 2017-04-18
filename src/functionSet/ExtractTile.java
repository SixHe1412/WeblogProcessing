package functionSet;

import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.scriptvalues_mod.ScriptValuesMetaMod;
import org.pentaho.di.trans.steps.scriptvalues_mod.ScriptValuesScript;

public class ExtractTile {

	PluginRegistry registry = null;
	private String[] fieldName = {"tileRow","tileCol","level"};
	private int[] fieldType ={ValueMetaInterface.TYPE_INTEGER,ValueMetaInterface.TYPE_INTEGER,ValueMetaInterface.TYPE_INTEGER};
	
	public ExtractTile(PluginRegistry registry) {
		// TODO Auto-generated constructor stub
		this.registry = registry;
	}
	
	public StepMeta generateExtractTileStep(){
		
		ScriptValuesMetaMod  scriptMeta = new ScriptValuesMetaMod();
		//设置每个步骤的id
        String scripttId = registry.getPluginId(StepPluginType.class, scriptMeta);
        //参数设置
        scriptMeta.allocate(3);
        scriptMeta.setFieldname(fieldName);
        scriptMeta.setType(fieldType);
        scriptMeta.setLength(new int[]{6,6,2});
        //scriptMeta.setPrecision(new int[]{6,6,2});
        scriptMeta.setReplace(new boolean[]{false,false,false});
        ScriptValuesScript[] jsScripts = {new ScriptValuesScript(0,"Script 1",getsScript())};
        scriptMeta.setJSScripts(jsScripts);
        
        //添加TextInput步骤到转换中
        StepMeta scriptMetaStep = new StepMeta(scripttId,"提取瓦片参数",scriptMeta);
        
        return scriptMetaStep;
	}
	
	private String getsScript() {
		
		String sScript = "var pieces = param.getString().split("+"\""+"\\\\&"+"\""+");\n"
	           + "if(type !="+"\""+"/DataServer"+"\""+"){\n"
			   + "for(var i in pieces){\n"
	           + "if((pieces[i].split("+"\""+"\\\\="+"\""+")[0]=="+"\""+"TileRow"+"\""+")||(pieces[i].split("+"\""+"\\\\="+"\""+")[0]=="+"\""+"TILEROW"+"\""+"))\n"
			   + "var tileRow = str2num(pieces[i].split("+"\""+"\\\\="+"\""+")[1]);\n"
	           + "else if((pieces[i].split("+"\""+"\\\\="+"\""+")[0]=="+"\""+"TileCol"+"\""+")||(pieces[i].split("+"\""+"\\\\="+"\""+")[0]=="+"\""+"TILECOL"+"\""+"))\n"
			   + "var tileCol = str2num(pieces[i].split("+"\""+"\\\\="+"\""+")[1]);\n"
	           + "else if((pieces[i].split("+"\""+"\\\\="+"\""+")[0]=="+"\""+"TileMatrix"+"\""+")||(pieces[i].split("+"\""+"\\\\="+"\""+")[0]=="+"\""+"TILEMATRIX"+"\""+"))\n"
			   + "var level = str2num(pieces[i].split("+"\""+"\\\\="+"\""+")[1]);\n"
	           + "}\n"
			   + "if(level>18)level = 18;\n"
	           + "if(level<2)level = 2;\n"
			   + "}\n"
	           + "else{\n"
			   + "var tileRow = str2num(pieces[1].split("+"\""+"\\\\="+"\""+")[1]);\n"
	           + "var tileCol = str2num(pieces[2].split("+"\""+"\\\\="+"\""+")[1]);\n"
			   + "var level = str2num(pieces[3].split("+"\""+"\\\\="+"\""+")[1]);\n"
	           + "}";
		 return sScript;
	}
}
