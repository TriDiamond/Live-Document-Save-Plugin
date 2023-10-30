package com.intelizign.livedocsave.actions;

public class DataServiceSaveError extends Exception{
	
	private static final long serialVersionUID = 4938968958586726363L;

	public DataServiceSaveError(Throwable originalerror) {
		super(originalerror);
	}
}
