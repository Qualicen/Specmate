package com.specmate.uitests.pagemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UITestUtil {
	/** 
     * If modal is visible we wait for a maximum of 10 seconds for the modal to become invisible again. 
     * If modal is not visible in the first place, we simply return 
     * 	*/
	public static void waitForModalToDisappear(WebDriver driver) {
		By modalLocator = By.id("loading-modal");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		try {
			new WebDriverWait(driver, 2).until(
			        ExpectedConditions.visibilityOfElementLocated(modalLocator));
		} catch (TimeoutException te) {
			// no modal displayed
			return;
		}
		new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	/**
	 * Checks if the element is present or absent
	 *
	 * @param selector
	 * @return true if the element specified by the selector can be found, false
	 *         otherwise
	 */
	public static boolean isElementPresent(By selector, WebDriver driver) {
		// Set the timeout to 1 second in order to avoid delay
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		boolean returnVal = true;
		try {
			driver.findElement(selector);
		} catch (NoSuchElementException e) {
			returnVal = false;
		} finally {
			// Change timeout back to the defined value
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return returnVal;
	}
	
	public static void scrollDownTo(By elementLocator, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Find element by locator
		WebElement Element = driver.findElement(elementLocator);

		// Scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
	}
	
	/** 
     * If the projects are not loaded beforehand, Specmate will display 'Bad Gateway' till they are loaded.
     * This method checks for 30 seconds (driver.findElement TimeOut (defined by implictlyWait) (5 seconds) * counter (6)) if the 
     * loading is finished and refreshes the page each 5 seconds
     * 	*/
    public static void waitForProjectsToLoad(WebDriver driver) {
    	boolean displayed = false;
    	int counter = 5;
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	do {
    		try {
    			counter--;
    			displayed = (counter<0) || driver.findElement(By.id("login-username-textfield")).isDisplayed();
    		} catch (NoSuchElementException e) {
    			driver.navigate().refresh();
    		}
    	} while(!displayed);
    	// Change timeout back to the defined value
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
    public static WebElement getElementWithIndex(int index, WebDriver driver, By selector) {
    	List<WebElement> nodeList = new ArrayList<WebElement>();
		
		nodeList = driver.findElements(selector);
		
		return nodeList.get(index);
    }
    
    public static void dragAndDrop(By element, int x, int y, WebDriver driver) {
    	// Create object of actions class
		Actions act = new Actions(driver);
 
		// find element which we need to drag
		WebElement drag = driver.findElement(element);
		
		// calling the method and x,y cordinates should be in the editor
		act.dragAndDropBy(drag, x, y).build().perform();
    }
}