package com.intelizign.livedocsave.actions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.polarion.alm.tracker.ITrackerService;
import com.polarion.alm.tracker.model.IModule;
import com.polarion.alm.tracker.model.ITrackerProject;
import com.polarion.alm.tracker.model.IWorkItem;
import com.polarion.alm.ws.client.types.tracker.EnumOption;
import com.polarion.core.util.exceptions.UserFriendlyRuntimeException;
import com.polarion.core.util.logging.Logger;
import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.persistence.IDataService;

public class LiveDocSaveHandler implements InvocationHandler {
	private static final Logger log = Logger.getLogger(LiveDocSaveHandler.class);
	private IDataService ds;
	private ArrayList<Thread> currentActiveThreads = new ArrayList<Thread>();
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
		ITrackerService trackerService = PlatformContext.getPlatform().lookupService(ITrackerService.class);
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
		}catch(Exception e) {
			throw new ModuleProjectorTypeUndefined();
		}
		List<String> scriptFileNameList = Stream
				.of("",projectId + "-" + moduleTypeId + "-", moduleTypeId + "-", projectId + "-")
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
		if (method.getName().equals("save")  && (args.length == 1) && (args[0] instanceof IModule)) {
			
			log.info("-------------Modification Page ---------\n");
			ITrackerService trackerService = PlatformContext.getPlatform().lookupService(ITrackerService.class);
			ITrackerProject getTrackerProject = trackerService.getTrackerProject("PistonAssembly");
			IWorkItem workItem = getTrackerProject.getWorkItem("PA-512");
			EnumOption wiCustomField = (EnumOption)workItem.getCustomField("importance");
			log.info("Get Enumeration Id " + wiCustomField.getEnumId() + "Get Enumeration value" + wiCustomField.getName()+"\n");
			log.info("-------------Modification Page End ---------\n");
			
			return saveModule((IModule) args[0]);
		}
		return invokeDelegate(ds, method, args);
	}

	public void originalDataServiceMethod(IModule module) throws DataServiceSaveError {
		try {
		ds.save(module);
		}catch(Exception e) {
		throw new DataServiceSaveError(e);
		}
	}

	// This Method read the custom script and accured error its warning to the user
	private Object saveModule(IModule module) throws Throwable {
		try {
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
			String errormessage = "The LiveDocument is not saved because there was an " + 
			"error in the script: "
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
