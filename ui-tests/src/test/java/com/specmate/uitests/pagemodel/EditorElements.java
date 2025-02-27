package com.specmate.uitests.pagemodel;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Class Editor Elements
 */
public class EditorElements {
	protected WebDriver driver;
	protected Actions builder;

	/** Property Editor Elements and their locators */
	protected By propertiesName = By.id("properties-name-textfield");
	protected By propertiesDescription = By.id("properties-description-textfield");
	protected By propertiesCondition = By.id("properties-condition-textfield");

	/** Editor elements */
	protected By toolbarMove = By.id("toolbar-tools.select-button");
	protected By toolbarDelete = By.id("toolbar-tools.delete-button");
	protected By toolbarClear = By.id("toolbar-clear-button");
	protected By editor = By.id("mxGraphContainer");

	/** Pop-Up Elements and their locators */
	protected By accept = By.id("popup-accept-button");
	protected By cancel = By.id("popup-dismiss-button");

	/** Links & Actions */
	By generateTestSpec = By.id("generatetestspec-button");
	By relatedRequirement = By.id("traces-addrequirement-textfield");

	By suggestionItem =  By.cssSelector("[id*='ngb-typeahead']");

	public EditorElements(WebDriver driver, Actions builder) {
		this.driver = driver;
		this.builder = builder;
	}

	public void delete(WebElement element) {
		driver.findElement(toolbarDelete).click();
		element.click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void clear() {
		driver.findElement(toolbarClear).click();
		driver.findElement(accept).click();
	}

	public void clickOnRelatedRequirement(String requirement) {
		WebElement itemSearchField = driver.findElement(By.id("item-search-bar-input"));
		itemSearchField.clear();
		itemSearchField.sendKeys(requirement);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionItem));
		driver.findElement(suggestionItem).click();
	}

	public void setModelName(String name) {
		driver.findElement(toolbarMove).click();
		driver.findElement(editor).click();
		WebElement modelName = driver.findElement(propertiesName);
		modelName.clear();
		modelName.sendKeys(name);
	}

	public void setModelDescription(String description) {
		driver.findElement(editor).click();
		WebElement modelDescription = driver.findElement(propertiesDescription);
		modelDescription.clear();
		modelDescription.sendKeys(description);
	}

	public void setDescription(String description) {
		WebElement modelDescription = driver.findElement(propertiesDescription);
		modelDescription.clear();
		modelDescription.sendKeys(description);
	}

	/**
	 * establishes a connection from node1 to node2 and returns the newly created
	 * connection
	 */
	public int connect(WebElement nodeElement1, WebElement nodeElement2) {

		int numberOfConnections = driver
				.findElements(
						By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > path:nth-child(2)"))
				.size();


		builder.moveToElement(nodeElement1, 0, 15).click().build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 30);

		// Get the connection pop up element, which needs to be dragged to the
		// connecting node
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("g > g:nth-child(3) > g[style='cursor: pointer; visibility: visible;']")));
		WebElement connectionPopUp = driver
				.findElement(By.cssSelector("g > g:nth-child(3) > g[style='cursor: pointer; visibility: visible;']"));

		Actions action = new Actions(driver);

		action.dragAndDrop(connectionPopUp, nodeElement2).build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > path:nth-child(2)")));

		return numberOfConnections;
	}
	
	public void connectById(String nodeId1, String nodeId2) {
		WebElement nodeElement1 = driver.findElement(By.id(nodeId1));

		builder.moveToElement(nodeElement1, 0, 15).click().build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 30);

		// Get the connection pop up element, which needs to be dragged to the
		// connecting node
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("g > g:nth-child(3) > g[style='cursor: pointer; visibility: visible;']")));
		WebElement connectionPopUp = driver
				.findElement(By.cssSelector("g > g:nth-child(3) > g[style='cursor: pointer; visibility: visible;']"));

		Actions action = new Actions(driver);

		WebElement nodeElement2 = driver.findElement(By.id(nodeId2));
		action.dragAndDrop(connectionPopUp, nodeElement2).build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > path:nth-child(2)")));
	}

	/**
	 *
	 * @param assertedNodeNumber
	 * @param assertedConnectionNumber
	 * @return true if the model contains the number of nodes and connections
	 *         specified by the parameters
	 */
	public boolean correctModelCreated(int assertedNodeNumber, int assertedConnectionNumber) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > path:nth-child(2)")));

		int numberOfStartEndNodes = driver
				.findElements(
						By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > ellipse:nth-child(1)"))
				.size();

		int numberOfActivities = driver
				.findElements(By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > rect")).size();
		
		int numberOfDecisions = driver
				.findElements(By.cssSelector(
						"g > g:nth-child(2) > g[style*='visibility: visible;'] > path[stroke-width='2']:nth-child(1)"))
				.size();
		int numberOfConnections = driver
				.findElements(
						By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > path:nth-child(2)"))
				.size();

		int numberOfNodes = numberOfStartEndNodes + numberOfActivities + numberOfDecisions;

		return (numberOfNodes == assertedNodeNumber && numberOfConnections == assertedConnectionNumber);
	}

	public boolean errorMessageDisplayed() {
		return UITestUtil.isElementPresent(By.cssSelector(".text-danger"), driver);
	}

	public boolean noWarningsMessageDisplayed() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("g > g:nth-child(2) > g[style*='visibility: visible;'] > path:nth-child(2)")));
		return UITestUtil.isElementPresent(By.cssSelector(".text-success"), driver);
	}

	/**
	 * Generates a test specification within the Editor
	 */
	public void generateTestSpecification() {
		// Wait as the save operation needs time to finish
		UITestUtil.absoluteWait(1500);
		UITestUtil.scrollDownTo(generateTestSpec, driver);
		driver.findElement(generateTestSpec).click();
		UITestUtil.waitForModalToDisappear(driver);
	}

	public void addRelatedRequirement(String name) {
		UITestUtil.scrollDownTo(relatedRequirement, driver);
		WebElement relatedRequirementField = driver.findElement(relatedRequirement);
		relatedRequirementField.clear();
		relatedRequirementField.sendKeys(name);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionItem));
		driver.findElement(suggestionItem).click();
	}

	public void removeRelatedRequirement() {
		List<WebElement> relatedRequirementsList = new ArrayList<WebElement>();
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id^=delete-related-requirement]")));
		relatedRequirementsList = driver.findElements(By.cssSelector("[id^=delete-related-requirement]"));

		// Delete the first related requirement
		relatedRequirementsList.get(0).click();
	}

	/**
	 * Checks if the test specification contains the number of expected rows
	 *
	 * @param expectedRows
	 * @return true if expected rows equals actual rows of test specification
	 */
	public boolean correctTestSpecificationGenerated(int expectedRows) {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".test-case-row")));
		int numberOfTestCases = driver.findElements(By.cssSelector(".test-case-row")).size();

		return numberOfTestCases == expectedRows;
	}
}
