package functionSet;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;

import functionSet.FieldSelect;
import functionSet.FieldSplit;
import functionSet.MongodbOutput;
import functionSet.RecordFilter;
import functionSet.TextInput;
import frameSet.MainFrame;

public class ExecuteProcess {

    MainFrame mainFrame = null;
	
	Object[] filepath = null;
	String strSeparate = null;
	String strFormat = null;
	boolean blHeader = true;
	String strDB = null;
	String strCollection = null;
	
	static TextInput myTextInput = null;
	static StringCut myStringCut = null;
	static FieldSelect myFieldSelect = null;
	static FieldSplit myFieldSplit1 = null;
	static FieldSplit myFieldSplit2 = null;
	static RecordFilter myRecordFilter = null;
	static MongodbOutput myMongodbOutput = null;
	static TextOutput myTextOutput = null;
	static ExtractTile myExtractTile = null;
	static UserIdentify myUserIdentify = null;
	Trans trans = null;
	
	public ExecuteProcess(MainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
	}
	
	public void execute() {
	
		loadData();
		
		try{
			
		    KettleEnvironment.init();
		    TransMeta transMeta = new TransMeta();
		    //设置转换名称和说明
		    transMeta.setName("Log_Transformation");
		    
		    initStep();
		    StepMeta TextInput = myTextInput.generateTextInputStep();
		    transMeta.addStep(TextInput);
		    StepMeta StringCut = myStringCut.generateStringCutStep();
		    transMeta.addStep(StringCut);
		    StepMeta FieldSelect = myFieldSelect.generateFieldSelectStep();
		    transMeta.addStep(FieldSelect);
		    StepMeta FieldSplit1 = myFieldSplit1.generateFieldSplitStep();
		    transMeta.addStep(FieldSplit1);
		    StepMeta FieldSplit2 = myFieldSplit2.generateFieldSplitStep();
		    transMeta.addStep(FieldSplit2);
		    StepMeta RecordFilter = myRecordFilter.generateRecordFilterStep();
		    transMeta.addStep(RecordFilter);
		    StepMeta ExtractTile = myExtractTile.generateExtractTileStep();
		    transMeta.addStep(ExtractTile);
		    
		  
		    transMeta.addTransHop(new TransHopMeta(TextInput,StringCut));
		    transMeta.addTransHop(new TransHopMeta(StringCut,FieldSplit1));
		    transMeta.addTransHop(new TransHopMeta(FieldSplit1,FieldSplit2));
		    transMeta.addTransHop(new TransHopMeta(FieldSplit2,RecordFilter));
		    transMeta.addTransHop(new TransHopMeta(RecordFilter,ExtractTile));
		    transMeta.addTransHop(new TransHopMeta(ExtractTile,FieldSelect));
		    
		    if(!mainFrame.filePanel.ckbUserOrnot.isSelected()){
		    
		        StepMeta MongodbOutput = myMongodbOutput.generateMongoOutputStep();
		        transMeta.addStep(MongodbOutput);
		        transMeta.addTransHop(new TransHopMeta(FieldSelect,MongodbOutput));
		    }
		    else {
				StepMeta UserIdentify = myUserIdentify.generateUserIdentifyStep();
				transMeta.addStep(UserIdentify);
				transMeta.addTransHop(new TransHopMeta(FieldSelect, UserIdentify));
			}
		    
		    trans = new Trans(transMeta);
            trans.execute(null); 
            trans.waitUntilFinished();
            
	    } catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    return;
	    } 
	}
	
	private void loadData() {
		
		strSeparate = mainFrame.filePanel.txtSeparate.getText();
		strFormat = mainFrame.filePanel.comboBoxFormat.getSelectedItem().toString();
		if(!mainFrame.filePanel.ckbHeader.isSelected()) blHeader = false;
		int iCount = mainFrame.filePanel.table.getRowCount();
		filepath = new String[iCount];
		for (int i = 0; i < iCount; i++) 
			filepath[i] = mainFrame.filePanel.table.getValueAt(i, 1);
		strDB = mainFrame.filePanel.txtDB.getText();
		strCollection = mainFrame.filePanel.txtColl.getText();
	}
	
    private void initStep() {
		
    	//registry是给每个步骤生成一个标识Id用
        PluginRegistry registry = PluginRegistry.getInstance();
        
	    myTextInput = new TextInput(registry,filepath,strSeparate,strFormat,blHeader);
	    myStringCut = new StringCut(registry);
		myFieldSelect = new FieldSelect(registry);
		
		String[] fieldname = {"method","request","version"};
		int[] fieldtype = {ValueMetaInterface.TYPE_STRING,ValueMetaInterface.TYPE_STRING,
				ValueMetaInterface.TYPE_STRING};
		int[] fieldlength = {3,155,8};
		myFieldSplit1 = new FieldSplit(registry, "f6", " ", fieldname, fieldtype, fieldlength);
	
		String[] fieldname2 = {"type","param"};
		int[] fieldtype2 = {ValueMetaInterface.TYPE_STRING,ValueMetaInterface.TYPE_STRING};
		int[] fieldlength2 = {11,145};
		myFieldSplit2 = new FieldSplit(registry,"request","?",fieldname2,fieldtype2,fieldlength2);
	
	    myRecordFilter = new RecordFilter(registry);
	    myExtractTile = new ExtractTile(registry);
	    
	    myMongodbOutput = new MongodbOutput(registry,strDB,strCollection);
	    myUserIdentify = new UserIdentify(registry, strDB, strCollection);
	    
	}
    
    public void pause() {
		
    	trans.stopAll();
	}
}
