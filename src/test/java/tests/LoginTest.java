package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import modules.LoginPage;

public class LoginTest {
	LoginPage loginPage;
	
	@DataProvider(name = "Invalid_Account")
	public static Object[][] account() {

		return new Object[][] { { "0198666999", "123456789" }, { "0198666123", "aaaaaaaaa" } };

	}
	
	@BeforeMethod
	void setup() {
		loginPage = new LoginPage();
		loginPage.openPage("chrome");
	}
	
	@Test(dataProvider = "Invalid_Account")
	void TC001(String username, String password) {
		loginPage.login(username, password);
		loginPage.verify_error_message("Số điện thoại hoặc mật khẩu không đúng, vui lòng đăng nhập lại.");
	}

	@Test(enabled = false)
	void TC002() {
		loginPage.login("0198666999", "1");
		loginPage.verify_error_message("Password: Mật khẩu phải có ít nhất 5 kí tự.");
	}
	
	
	@AfterMethod
	void tearDown() {
		loginPage.closePage();
	}

}
