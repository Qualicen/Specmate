package com.specmate.uitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.interactions.Actions;

import com.specmate.uitests.pagemodel.CEGEditorElements;
import com.specmate.uitests.pagemodel.CommonControlElements;
import com.specmate.uitests.pagemodel.LoginElements;
import com.specmate.uitests.pagemodel.RequirementOverviewElements;


public class ModelEditorTest extends TestBase {
	public ModelEditorTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	/**
	 * Runs a test verifying the creation of a CEG.
	 * 
	 * @throws InvalidElementStateException
	 */
	@Test
	public void verifyModelEditorTest() throws InvalidElementStateException {
		Actions builder = new Actions(driver);

		RequirementOverviewElements requirementOverview = new RequirementOverviewElements(driver);
		CEGEditorElements cegEditor = new CEGEditorElements(driver, builder);
		CommonControlElements commonControl = new CommonControlElements(driver);
		LoginElements login = new LoginElements(driver);

		driver.get("http://localhost:8080/");

		if (!login.isLoggedIn()) {
			performLogin(login);
			assertTrue(login.isLoggedIn());
		}
		
		// Navigation to requirement
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");

		// Creating and opening new model
		String modelName = "Model By Automated UI Test " + new Timestamp(System.currentTimeMillis());
		requirementOverview.createCEGModelFromRequirement(modelName);

		// Adding nodes to the CEG
		String nodeAlter = cegEditor.createNode("Alter", "> 17", 50, 100);// results in x=7, y=60
		String nodeFS = cegEditor.createNode("Führerschein", "vorhanden", 50, 300);// results in x=7, y=27
		String nodeAutofahren = cegEditor.createNode("Autofahren", "erlaubt", 300, 200);

		// Check if error message is shown (Assert true)
		assertTrue(cegEditor.errorMessageDisplayed());

		// Connecting created nodes
		cegEditor.connectNode(nodeAlter, nodeAutofahren);
		cegEditor.connectNode(nodeFS, nodeAutofahren);

		// Check if error message is hidden (Assert false)
		assertTrue(cegEditor.noWarningsMessageDisplayed());

		// Negate Connection
		cegEditor.toggleNegateButtonOnLastConnection();

		// Check if tilde is shown (Assert True)
		assertTrue(cegEditor.negationDisplayed());

		// Remove negation
		cegEditor.toggleNegateButtonOnLastConnection();

		// Check if tile is hidden (Assert false)
		assertFalse(cegEditor.negationDisplayed());

		// Change connection type in sidebar
		cegEditor.changeTypeToOR(nodeAutofahren);
		cegEditor.changeTypeToAND(nodeAutofahren);

		// Change connection type in node
		cegEditor.changeTypeToORInNode(nodeAutofahren);
		cegEditor.changeTypeToANDInNode(nodeAutofahren);

		// Check number of nodes
		assertTrue(cegEditor.correctModelCreated(3, 2));

		// Save CEG
		commonControl.save();

		// Create test specification
		cegEditor.generateTestSpecification();

		assertTrue(cegEditor.correctTestSpecificationGenerated(3));

		// Click on created CEG in the requirement overview
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.clickOnCreatedModel(modelName);

		// Save CEG
		commonControl.save();

		// Duplicate CEG
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.duplicateCEGModel(modelName);
		// Click on it, to check if the duplication created a new model
		requirementOverview.clickOnDuplicateModel(modelName);

		// Delete duplicate
		cegEditor.clickOnRelatedRequirement("Erlaubnis Autofahren");
		requirementOverview.deleteDuplicateModel(modelName);
		requirementOverview.refreshRequirementOverviewPage();
		// The model should be deleted, thus, use assertFalse
		assertFalse(requirementOverview.checkForDeletedDuplicateModel(modelName));

		// Delete created model
		requirementOverview.deleteModel(modelName);

		requirementOverview.refreshRequirementOverviewPage();
		// The model should be deleted, thus, use assertFalse
		assertFalse(requirementOverview.checkForDeletedModel(modelName));
	}
}
