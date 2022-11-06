package org.letcode.TestClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.letcode.BaseClass.Locatorenum;
import org.letcode.BaseClass.SeleniumbaseClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class LetCodeTest extends SeleniumbaseClass {
	

	Properties property;
	ExtentSparkReporter reporter;
	ExtentReports extent;
	ExtentTest test;
	
	@BeforeTest
	public void setfilename()
	{
		filename = "MyData";
	}

	@BeforeMethod
	void launch() throws FileNotFoundException, IOException
	{
		property = new Properties();
		property.load(new FileInputStream("./Inputs.properties"));
		reporter = new ExtentSparkReporter("./TestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		setUp(property.getProperty("url"));
	}
	
	@Test(dataProvider = "exread")
	public void RunTest(String data[]) throws InterruptedException, IOException
	{
		test = extent.createTest("TC01 First Login Test Case");
		test.assignAuthor("sarathkumar");
		test.assignCategory("Regression Testing");
		click(element(Locatorenum.link,"Log in"));
		test.pass("Loggging In");
		type(element(Locatorenum.name,"email"),data[0]);
		test.pass("Email id is entered");
		type(element(Locatorenum.name,"password"),data[1]);
		test.pass("Password is entered");
		click(element(Locatorenum.xpath,"//button[text()='LOGIN']"));
		test.pass("Login button is clicked");
		Thread.sleep(3000);
		takescreenshot(property.getProperty("filename"));
	}

	@AfterMethod
	void cleanup()
	{
		extent.flush();
		quit();
	}

}
