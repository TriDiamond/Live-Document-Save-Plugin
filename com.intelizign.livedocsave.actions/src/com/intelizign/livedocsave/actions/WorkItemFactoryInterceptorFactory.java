package com.intelizign.livedocsave.actions;


import java.util.List;

import org.apache.hivemind.InterceptorStack;
import org.apache.hivemind.ServiceInterceptorFactory;
import org.apache.hivemind.internal.Module;

import com.polarion.platform.persistence.IDataService;
import com.polarion.platform.persistence.model.IPObject;
import com.polarion.platform.persistence.model.IPObjectFactory;
import com.polarion.platform.persistence.model.IPrototype;
import com.polarion.subterra.base.SubterraURI;
import com.polarion.subterra.base.data.object.IDataObject;


public class WorkItemFactoryInterceptorFactory implements ServiceInterceptorFactory {
	private IDataService dataService;

	public WorkItemFactoryInterceptorFactory(IDataService dataService) {
		this.dataService = dataService;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void createInterceptor(InterceptorStack stack, Module arg1, List arg2) {
		final IPObjectFactory delegate = (IPObjectFactory) stack.peek();
		stack.push(new IPObjectFactory() {

			@Override
			public Class getCommonSuperclass() {
				return delegate.getCommonSuperclass();
			}

			@Override
			public IPObject createObjectForURI(SubterraURI uri, IDataService dataService) {
				return delegate.createObjectForURI(uri, WorkItemFactoryInterceptorFactory.this.dataService);
			}

			@Override
			public IPObject createObjectForDAO(IDataObject dao, IDataService dataService) {
				return delegate.createObjectForDAO(dao, WorkItemFactoryInterceptorFactory.this.dataService);
			}

			@Override
			public IPObject createNewObject(IPrototype prototype, IDataService dataService) {
				return delegate.createNewObject(prototype, WorkItemFactoryInterceptorFactory.this.dataService);
			}
		});
	}

}