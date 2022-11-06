package org.letcode.BaseClass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import Utils.ReadExcel;

public class SeleniumbaseClass implements SeleniumInterface {
	
	RemoteWebDriver driver = null;
	WebDriverWait wait = null;
	SoftAssert soft;
	protected String filename = "";
	long implicitime = 10;
	long explicittime = 10;

	@DataProvider(name="exread")
	public String[][] excelread()
	{
		String [][] data = ReadExcel.getExcelData(filename);
		return data;
	}

	public void setUp(String url) {
		System.setProperty("webdriver.chrome.driver", "C://Users//DELL//Downloads//chromedriver_win32//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(implicitime,TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,explicittime);
		
	}

	public void setUp(Browserenum browsername, String url) {
		switch(browsername)
		{
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "C://Users//DELL//Downloads//chromedriver_win32//chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			driver = new FirefoxDriver();
			break;
		case EDGE:
			driver = new EdgeDriver();
			break;
			default:
				System.err.println("driver is not defined");
				break;
		}
		driver.manage().timeouts().implicitlyWait(implicitime,TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,explicittime);
		
	}

	public void close() {
		driver.close();
		
	}

	public void quit() {
		driver.quit();
		
	}

	public WebElement element(Locatorenum type, String value) {
		try {
			switch(type)
			{
			case id:
				return driver.findElement(By.id(value));
			case name:
				return driver.findElement(By.name(value));
			case xpath:
				return driver.findElement(By.xpath(value));
			case link:
				return driver.findElement(By.linkText(value));
				default:
					break;
			}
		} catch (NoSuchElementException e) {
			System.err.println("element not found: "+e.getMessage());
			throw new NoSuchElementException(e.getMessage());
		}
		catch (WebDriverException e) {
			System.err.println(e.getMessage());
			throw new WebDriverException(e.getMessage());
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public void switchtowindow(int i) {
		Set<String> set = driver.getWindowHandles();
		ArrayList<String> list = new ArrayList<String>(set);
		driver.switchTo().window(list.get(i));
		String title = driver.getTitle();
		//soft = new SoftAssert();
		Assert.assertEquals(title, "LetCode - Testing Hub");
		System.out.println("hard assert executed");
		//soft.assertAll();
		
	}

	public void selectbyvalue(WebElement ele, String value) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByValue(value);
		
	}

	private WebElement isElementVisible(WebElement ele) {
		WebElement element = wait.withMessage("Element is not visible").until(ExpectedConditions.visibilityOf(ele));
		return element;
	}

	public boolean isselected(WebElement ele)
	{
		String text = ele.getText();
		System.out.println(text);
		boolean select = ele.isSelected();
		System.out.println(select);
		return select;
	}
	public void selectbytext(WebElement ele, String text) {
		WebElement element = isElementVisible(ele);
		Select select = new Select(element);
		select.selectByVisibleText(text);
		WebElement elem = select.getFirstSelectedOption();
		isselected(elem);
		
	}

	public void selectbyindex(WebElement ele, int index) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByIndex(index);
		
	}

	public void click(WebElement ele) {
		 WebElement elment = wait.until(ExpectedConditions.elementToBeClickable(ele));
		  elment.click();
		
	}

	public void type(WebElement ele, String value) {
		try {
			WebElement element = isElementVisible(ele);
			element.clear();
			element.sendKeys(value);
		} catch (NullPointerException e) {
			System.err.println("nullpointer exception is occured: "+e.getMessage());
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	public void type(WebElement ele, String value, Keys keys) {
		WebElement element = isElementVisible(ele);
		element.clear();
		element.sendKeys(value, keys);
		
	}
	public void appendtext(WebElement ele, String value) {
		WebElement element = isElementVisible(ele);
		element.sendKeys(value);
		
	}

	public String gettitle() {
		return driver.getTitle();
	}

	public String geturl() {
		return driver.getCurrentUrl();
	}

	public boolean isdisplayed(WebElement ele) {
		return ele.isDisplayed();
	}

	public void takescreenshot(String file) throws IOException
	{
		File src = driver.getScreenshotAs(OutputType.FILE);
		File dest = new File("./Screenshots/"+file+".png");
		FileHandler.copy(src, dest);
	}
}
