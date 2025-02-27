package com.specmate.connectors.api;

import com.specmate.export.api.IExporter;

public interface IProject {

	/**
	 * @return the id of the project
	 */
	String getID();

	/**
	 * @return the defined connector for the project, or <code>null</code>.
	 */
	IConnector getConnector();

	/**
	 * @return the defined sink to which test information is exported, or
	 *         <code>null</code>.
	 */
	IExporter getExporter();

}
