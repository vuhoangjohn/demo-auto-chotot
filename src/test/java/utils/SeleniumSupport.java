package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import configs.Configs;
import datas.PathProvider;

public class SeleniumSupport {
	private static WebDriver driver = null;
	private String ELEMENT_FILE_PATH = PathProvider.getElements("DEFAULT.properties");
	private ReadProperties ELEMENT_FILE = new ReadProperties(ELEMENT_FILE_PATH);
	private Actions actions;
	private JavascriptExecutor jsEx;
	
	
	public SeleniumSupport(){
		super();
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setup() {
		setup(Configs.BROWSER);
	}
	
	public void setup(String browser) {
		browser = browser.toUpperCase();
		switch (browser) {
		case "CHROME":
			System.setProperty("webdriver.chrome.driver", PathProvider.getChromeDriver());
			ChromeOptions options = new ChromeOptions();
		    options.setExperimentalOption("useAutomationExtension", false);
			driver = new ChromeDriver(options);
			break;
		case "FF":
			break;
		case "OPERA":
			break;
		case "IE":
			break;
		case "EDGE":
			break;
		case "CC":
			break;
		case "UC":
			break;
		default:
			break;
		}
		if(Configs.SCREEN_SIZE.isEmpty()) 
			setScreenSize("MAX");
		else
			setScreenSize(Configs.SCREEN_SIZE);
		
		actions = new Actions(driver);
		jsEx = (JavascriptExecutor)driver;
	}
	
	private void setScreenSize(String size) {
		size = size.trim().toUpperCase();
		if(size.equals("MAX")) {
			driver.manage().window().maximize();
		}
		else if(size.contains("X")) {
			driver.manage().window().setSize(new Dimension(Integer.parseInt(size.split("X")[0])
					, Integer.parseInt(size.split("X")[1]))
				);
		}
		else {
		}
	}
	
	public void openPage(String pageName) {
		changeToElementFile(pageName);
		String urlStr = ELEMENT_FILE.getValue("url");
		try {
			URL url = new URL(urlStr);
			driver.get(urlStr);
		}catch(MalformedURLException ex) {
			System.out.printf("URL[%s] is not a valid url\n", urlStr);
		}catch(Exception e) {
			System.out.printf("Can't access to URL[%s]\n", urlStr);
		}
	}
	
	public void closePage() {
		driver.quit();
	}

	@Deprecated
	public void waitForElement(By eLocator) {
		try {
			waitForVisible(eLocator);
		}catch(NoSuchElementException e) {
			waitForPresence(eLocator);
		}
	}
	
	public void waitForVisible(By eLocator) {
		new WebDriverWait(driver, TimeForWait.VISIBILITY.getTime())
						.until(ExpectedConditions.visibilityOfElementLocated(eLocator));
	}
	
	public void waitForInvisible(By eLocator) {
		new WebDriverWait(driver, TimeForWait.INVISIBILITY.getTime())
						.until(ExpectedConditions.invisibilityOfElementLocated(eLocator));
	}
	
	public void waitForClickable(By eLocator) {
		new WebDriverWait(driver, TimeForWait.CLICKABLE.getTime())
						.until(ExpectedConditions.elementToBeClickable(eLocator));
	}
	
	public void waitForPresence(By eLocator) {
		new WebDriverWait(driver, TimeForWait.CLICKABLE.getTime())
						.until(ExpectedConditions.presenceOfElementLocated(eLocator));
	}
	
	@Deprecated
	public WebElement getElement(String pageName, String eName) {
		By eLocator = getByLocator(pageName, eName);
		waitForVisible(eLocator);
		return driver.findElement(eLocator);
	}
	
	private WebElement getElement(By eLocator) {
//		By eLocator = getByLocator(pageName, eName);	//Remove step to add more ext-wait
		waitForVisible(eLocator);
		return driver.findElement(eLocator);
	}
	
	public void click(String pageName, String eName) {
		By eLocator = getByLocator(pageName, eName);
		WebElement e = getElement(eLocator);
		waitForClickable(eLocator);
		try {
			e.click();
		}catch(WebDriverException ex) {
			actions.moveToElement(e).click().perform();
		}
	}
	
	public void input(String pageName, String eName, String value) {
		By eLocator = getByLocator(pageName, eName);
		WebElement e = getElement(eLocator);
		e.click();			
		e.clear();
		e.sendKeys(value);
	}
	
	public String getText(String pageName, String eName) {
		By eLocator = getByLocator(pageName, eName);
		WebElement e = getElement(eLocator);
		return e.getText();
	}
	
	public void verify_display(String pageName, String eName) {
		By eLocator = getByLocator(pageName, eName);
		WebElement e = getElement(eLocator);
		e.isDisplayed();
	}
	
	public void verifyDisplayWithText(String pageName, String eName, String expectText) {
		By eLocator = getByLocator(pageName, eName);
		WebElement e = getElement(eLocator);
		e.isDisplayed();
		Assert.assertEquals(e.getText(), expectText);
	}
	
	public void verifyAttribute(String pageName, String eName, String attName, String attValue) {
		By eLocator = getByLocator(pageName, eName);
		WebElement e = getElement(eLocator);
		String expectValue = e.getAttribute(attName);
		Assert.assertEquals(attValue, expectValue);
	}
	
	private By getByLocator(String pageName, String eName) {
		changeToElementFile(pageName);
		eName = eName.trim().toLowerCase().replaceAll(" ", "-");
		if (!ELEMENT_FILE.getValue(eName + "-xp").isEmpty()) 
			return By.xpath(ELEMENT_FILE.getValue(eName + "-xp"));
		else if (!ELEMENT_FILE.getValue(eName + "-id").isEmpty())
			return By.id(ELEMENT_FILE.getValue(eName + "-id"));
		else if (!ELEMENT_FILE.getValue(eName + "-name").isEmpty())
			return By.name(ELEMENT_FILE.getValue(eName + "-name"));
		else if (!ELEMENT_FILE.getValue(eName + "-css").isEmpty())
			return By.cssSelector(ELEMENT_FILE.getValue(eName + "-css"));
		else if (!ELEMENT_FILE.getValue(eName + "-cls").isEmpty())
			return By.className(ELEMENT_FILE.getValue(eName + "-cls"));
		else
			return null;
	}
	
	private void changeToElementFile(String pageName) {
		String fileName = pageName.trim().toUpperCase().replaceAll(" ", "_") + ".properties";
		String filePath = PathProvider.getElements(fileName);
		
//		if(!ELEMENT_FILE.equals(new ReadProperties(filePath)))    //Avoid creating a new Object every time getting an element
//			ELEMENT_FILE = new ReadProperties(filePath);
		
		if(!ELEMENT_FILE_PATH.equals(filePath)) {
			ELEMENT_FILE_PATH = filePath;
			ELEMENT_FILE = new ReadProperties(ELEMENT_FILE_PATH);
		}
	}
}

//============================= CONFIGURATION =============================

enum TimeForWait {
	VISIBILITY(30), PRESENCE(20), CLICKABLE(20), INVISIBILITY(30);

	private int time;

	private TimeForWait(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}

	public void setTimeSec(int time) {
		this.time = time;
	}
}
