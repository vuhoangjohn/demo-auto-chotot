package modules;

import utils.SeleniumSupport;

public class LoginPage {
	SeleniumSupport support = null;
	String loginPage = "Login Page";
	
	public LoginPage() {
		support = new SeleniumSupport();
	}
	
	public void login(String username, String password) {
		support.input(loginPage, "Username", username);
		support.input(loginPage, "Password", password);
		support.click(loginPage, "Login Button");
	}
	
	public void openPage(String browser) {
		support.setup(browser);
		support.openPage(loginPage);
	}
	
	public void closePage() {
		support.closePage();
	}
	
	public void verify_error_message(String expectedText) {
		support.verifyDisplayWithText(loginPage, "Error Message", expectedText);
	}
	
	public void verify_placeholder_username(String placeholder) {
		support.verifyAttribute(loginPage, "Username", "placeholder", placeholder);
	}
	
	public void verify_placeholder_password(String placeholder) {
		support.verifyAttribute(loginPage, "Password", "placeholder", placeholder);
	}
	
	
}
