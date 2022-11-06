package org.letcode.BaseClass;

import java.io.IOException;

import org.openqa.selenium.WebElement;

public interface SeleniumInterface {
	
void setUp(String url);
	
	void setUp(Browserenum browsername, String url);
	
	void close();
	
	void quit();
	
	WebElement element (Locatorenum type, String value);
	
	void switchtowindow(int i);
	
	void selectbyvalue(WebElement ele, String value);
	
	void selectbytext(WebElement ele, String text);
	
	void selectbyindex(WebElement ele, int index);
	
	void click(WebElement ele);
	
	void type(WebElement ele, String value);
	
	void appendtext(WebElement ele,  String value);
	
	String gettitle();
	
	String geturl();
	
	boolean isdisplayed(WebElement ele);
	
	void takescreenshot(String file) throws IOException;

}
