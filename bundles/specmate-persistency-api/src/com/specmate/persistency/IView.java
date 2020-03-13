package com.specmate.persistency;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public interface IView {
	/** Retrieves the EMF resource. */
	public Resource getResource();

	/** Retrieves an object by its id */
	public EObject getObjectById(Object originId);

	/** Retreives objects by a ocl query */
	List<Object> query(String queryString, Object context);
	
	List<Object> querySQL(String queryString, Object context, long lastActive);
	
	List<Object> querySQLWithName(String queryString, Object context, String userName, long lastActive);

	public void close();
}
