/*
 * This Plugin is developed by Intelizign Lifecycle Services pvt Ltd
 */
package com.intelizign.livedocsave.actions;

import java.lang.reflect.Proxy;
import java.util.List;
import org.apache.hivemind.InterceptorStack;
import org.apache.hivemind.ServiceInterceptorFactory;
import org.apache.hivemind.internal.Module;

import com.polarion.platform.persistence.IDataService;

public class DataServiceInterceptorFactory implements ServiceInterceptorFactory {	   
	   @Override
	   public void createInterceptor(InterceptorStack stack, Module module, @SuppressWarnings("rawtypes") List parameters) {   
		    IDataService ds = (IDataService) stack.peek();
			Object ssProxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { IDataService.class }, new LiveDocSaveHandler(ds));
			stack.push(ssProxy); 
	   }
}
