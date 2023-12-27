package com.intelizign.livedocsave.actions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.polarion.alm.projects.model.IProject;
import com.polarion.alm.tracker.IModuleManager;
import com.polarion.alm.tracker.ITrackerService;
import com.polarion.alm.tracker.model.IModule;
import com.polarion.alm.tracker.model.ITrackerProject;
import com.polarion.alm.tracker.model.IWorkItem;
import com.polarion.core.util.exceptions.UserFriendlyRuntimeException;
import com.polarion.core.util.logging.Logger;
import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.persistence.IDataService;
import com.polarion.platform.persistence.model.IPObject;
import com.polarion.platform.persistence.model.IPObjectList;
import com.polarion.platform.persistence.spi.EnumOption;
import com.polarion.subterra.base.location.ILocation;
import com.polarion.subterra.base.location.Location;

public class LiveDocSaveHandler implements InvocationHandler {
	private static final Logger log = Logger.getLogger(LiveDocSaveHandler.class);
	private IDataService ds;
	private ArrayList<Thread> currentActiveThreads = new ArrayList<Thread>();
	private List<IWorkItem> workItemList = new ArrayList <IWorkItem> ();
	private Set<String> uniqueIds = new HashSet<>();
	private ITrackerService trackerService = PlatformContext.getPlatform().lookupService(ITrackerService.class);
	private static final String javascriptSuffix = ".js";
	private String preSave = "pre-save";
	private String postSave = "post-save";
	private String moduleTypeId;
	private String projectId;

	// This constructor store the Original data Service and get the Script Folder
	public LiveDocSaveHandler(IDataService ds) {
		this.ds = ds;
		log.info("Loaded, script directory is '" + getDocumentScriptFolder() + "\n");
	}

	// Presavescript File is Existing then Execute runhook script Method
	private void checkingPreSaveScript(List<String> scriptPathList, IModule module)
			throws IOException, ScriptException, HookScriptStopReturnValue, Throwable {
		List<File> preSaveScript = getExistingScriptFile(scriptPathList, preSave);
		List<File> preSaveScriptFile = preSaveScript.stream().filter(file -> file.getName().endsWith("pre-save.js"))
				.collect(Collectors.toList());
		if (preSaveScriptFile.isEmpty()) {
			log.info("Pre save Script File Not Found");
		} else {
			runHookScript(preSaveScriptFile, module);
		}
	}

	// Postsavescript File is Existing this below method Execute runhook script
	// Method
	private void checkingPostSaveScript(List<String> scriptPathList, IModule module)
			throws IOException, ScriptException, HookScriptStopReturnValue, Throwable {
		List<File> postSaveScript = getExistingScriptFile(scriptPathList, postSave);
		List<File> postSaveScriptFile = postSaveScript.stream().filter(file -> file.getName().endsWith("post-save.js"))
				.collect(Collectors.toList());
		try {
			if (postSaveScriptFile.isEmpty()) {
				log.info("Pre save Script File Not Found");
			} else {
				runHookScript(postSaveScriptFile, module);
			}
		} catch (HookScriptStopReturnValue error) {
			log.error("Post save cannot stop the saving because the LiveDocument" + error);
		}
	}

	// Evaluate the Existing script
	private void runHookScript(List<File> scriptFilePathList, IModule module)
			throws IOException, ScriptException, HookScriptStopReturnValue {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine scriptEngineObject = manager.getEngineByName("javascript");
		Bindings bindings = scriptEngineObject.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.put("trackerService", trackerService);
		bindings.put("polarionLog", log);
		bindings.put("module", module);
		for (File file : scriptFilePathList) {
			String scriptContent = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
			Object returnEvaluatingScriptObject = scriptEngineObject.eval(scriptContent);
			if (returnEvaluatingScriptObject != null && !returnEvaluatingScriptObject.toString().isEmpty()) {
				throw new HookScriptStopReturnValue(returnEvaluatingScriptObject.toString());
			}
		}
	}

	// Generate Script List to search for pre and post save Hooks
	public List<String> collectScriptFileName(IModule module) throws ModuleProjectorTypeUndefined {
		try {
			moduleTypeId = module.getType().getId();
			projectId = module.getProject().getId();
		} catch (Exception e) {
			throw new ModuleProjectorTypeUndefined();
		}
		List<String> scriptFileNameList = Stream
				.of("", projectId + "-" + moduleTypeId + "-", moduleTypeId + "-", projectId + "-")
				.collect(Collectors.toCollection(ArrayList::new));
		return scriptFileNameList;
	}

	// Get Existing script File in polarion documentsave Directory
	private List<File> getExistingScriptFile(List<String> scriptPaths, String suffix) {
		List<File> scriptFilePath = new ArrayList<File>();
		for (String path : scriptPaths) {
			String scriptFilePathJs = path + suffix + javascriptSuffix;
			File scriptFileJs = new File(getDocumentScriptFolder(), scriptFilePathJs);
			if (scriptFileJs.exists()) {
				scriptFilePath.add(scriptFileJs);
			}
		}
		return scriptFilePath;
	}

	// Get script Folder
	private static String getScriptFolder() {
		return System.getProperty("com.polarion.home") + "/../scripts/";
	}

	// Return documentsave Folder Directory
	private static String getDocumentScriptFolder() {
		return getScriptFolder() + "/livedocumentsave/";
	}

	/**
	 * This invoke method is called everytime a Method of DataService is called But
	 * we wrote the Logic its work only if save the Module
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
	
		if (method.getName().equals("delete") && (args.length == 1) && (args[0] instanceof IWorkItem)) {
		
			 deleteModule((IWorkItem) args[0]);
			
		}
		
		if (method.getName().equals("save") && (args.length == 1) && (args[0] instanceof IModule)) {	
			return saveModule((IModule) args[0]);
		}
		
		return invokeDelegate(ds, method, args);
	}

	public void originalDataServiceMethod(IModule module) throws DataServiceSaveError {
		try {
			ds.save(module);
		} catch (Exception e) {
			throw new DataServiceSaveError(e);
		}
	}
	
	/*
	 * Displaying Deleted WorkItem In Module
	 */
	private void deleteModule(IWorkItem workItem) throws Throwable {
		System.out.println("WorkItem Id is" + workItem.getId() + 
				"WorkItem Title is" + workItem.getTitle() + "\n");	
		
		getSpecificationFieldTestCaseWi(workItem);
	}
	
	/*
	 * Check CustomField Specification value in all workItem
	 */
	public void getSpecificationFieldTestCaseWi(IWorkItem workItem) {
		
		
		String heading = workItem.getId().toString();     // Get Deleted Heading
		String module = workItem.getModule().getId();     // Get Deleted header Module Id
		
		System.out.println("The heading and Module Name is" + heading + module);
		String wiQuery = "";
		if(module.equalsIgnoreCase("Specification_LOV")) { // If Document is Specification_LOV Below Query Going to Be Executed
			wiQuery = "select WORKITEM.C_URI from WORKITEM inner join PROJECT on PROJECT.C_URI = WORKITEM.FK_URI_PROJECT  inner join CF_WORKITEM CF1 on CF1.FK_WORKITEM = WORKITEM.C_PK where true and WORKITEM.C_TYPE = 'testcase' AND CF1.C_NAME = 'specification' AND CF1.C_STRING_VALUE='"+ heading+"'";
		}else if(module.equalsIgnoreCase("Generic_Test_Name")) {
			wiQuery = "select WORKITEM.C_URI from WORKITEM inner join PROJECT on PROJECT.C_URI = WORKITEM.FK_URI_PROJECT  inner join CF_WORKITEM CF1 on CF1.FK_WORKITEM = WORKITEM.C_PK where true and WORKITEM.C_TYPE = 'testcase' AND CF1.C_NAME = 'genericTestName' AND CF1.C_STRING_VALUE='"+ heading+"'";
		}else {
			log.info("Module Not Exist");
		}
		
		IPObjectList<IWorkItem> wiList =trackerService.getDataService().sqlSearch(wiQuery);
		int count = wiList.size();
		System.out.println("The WorkItem Count is" + count);
		for(IWorkItem wi : wiList) {
			System.out.println("WorkItem Id is" + wi.getId() +"\n");
		}

		displayCustomizedWarningMessage(wiList,workItem);
		
	}
	
	/*
	 * Display Customized Warning Message to the User
	 */
	public void displayCustomizedWarningMessage (List<IWorkItem> workItemList, IWorkItem workItem) {
		
		 
		String warningMessage = "Deleting " + workItem.getType().getId() + " in the current document "
				+ "is mapped to the following work item specification fields: ";
		 
		for (IWorkItem wi : workItemList) {
		   
		        uniqueIds.add(wi.getId());    
		}
		 
		// Append the unique work item IDs to the warning message
		if (!uniqueIds.isEmpty()) {
		    for (String id : uniqueIds) {
		    	warningMessage +=  id + ",";
		    }
		    // Remove the trailing comma and space if there are elements appended
		    warningMessage = warningMessage.substring(0, warningMessage.length() - 2);
		}
		 
		if (!warningMessage.equals("Deleting " + workItem.getType().getId() + " in the current document is mapped to the "
				+ "following work item specification fields:")) {
		    throw new UserFriendlyRuntimeException(warningMessage);
		}
	}
	


	// This Method read the custom script and occurred error its warning to the user
	private Object saveModule(IModule module) throws Throwable {
		try {
			//System.out.println("Module Id is" + module.getId());
			List<String> scriptPathsList = collectScriptFileName(module);
			log.info("SaveHandler instance " + hashCode() + " is called from thread with id "
					+ Thread.currentThread().hashCode() + " for Module with id " + module.getId());
			    synchronized (currentActiveThreads) {
				currentActiveThreads.contains(Thread.currentThread());
				currentActiveThreads.add(Thread.currentThread());
			}
			checkingPreSaveScript(scriptPathsList, module);
			originalDataServiceMethod(module);
			checkingPostSaveScript(scriptPathsList, module);

		} catch (ScriptException e) {
			log.error("Error in Hook-Script: " + e.toString());
			String errormessage = "The LiveDocument is not saved because there was an " + "error in the script: "
					+ e.getMessage();
			throw new UserFriendlyRuntimeException(errormessage);

		} catch (HookScriptStopReturnValue e) {
			log.info("Avoided saving of Live Document because of a return value of  the pre hook script: '"
					+ e.getMessage());
			throw new UserFriendlyRuntimeException(e.getMessage());

		} catch (DataServiceSaveError e) {
			log.error("The original method save() of the DataService has thrown an error (" + e.getCause().getMessage()
					+ "). We don't do anything here and let polarion handle the error.");
			throw e.getCause();
		} catch (Exception e) {
			log.error("There was an unhandled error within the plugin (see Details below). Call the normal save() "
					+ "method of the DataService to provide a normal behaviour.\n" + e.toString());
			e.printStackTrace();
			ds.save(module);

		} finally {
			// Remove the current thread From currentactive Thread List
			synchronized (currentActiveThreads) {
				currentActiveThreads.remove(Thread.currentThread());
			}
			log.info("End of LiveDocSaveHandler.");
		}
		return null;
	}

	// Return original Dataservice Excluding the Save operation
	private Object invokeDelegate(final IDataService rcs, Method method, Object[] args)
			throws IllegalAccessException, Throwable {
		try {
			return method.invoke(rcs, args);
		} catch (IllegalArgumentException e) {
			log.error("Internal error.", e);
			throw e;
		} catch (IllegalAccessException e) {
			log.error("Internal error.", e);
			throw e;
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}
}
