/**
 */
package com.specmate.model.processes;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Step</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.processes.ProcessStep#getExpectedOutcome <em>Expected Outcome</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.processes.ProcessesPackage#getProcessStep()
 * @model
 * @generated
 */
public interface ProcessStep extends ProcessNode {
	/**
	 * Returns the value of the '<em><b>Expected Outcome</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expected Outcome</em>' attribute.
	 * @see #setExpectedOutcome(String)
	 * @see com.specmate.model.processes.ProcessesPackage#getProcessStep_ExpectedOutcome()
	 * @model annotation="http://specmate.com/form_meta shortDesc='Expected Outcome' longDesc='' required='false' type='text' position='101'"
	 * @generated
	 */
	String getExpectedOutcome();

	/**
	 * Sets the value of the '{@link com.specmate.model.processes.ProcessStep#getExpectedOutcome <em>Expected Outcome</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expected Outcome</em>' attribute.
	 * @see #getExpectedOutcome()
	 * @generated
	 */
	void setExpectedOutcome(String value);

} // ProcessStep
