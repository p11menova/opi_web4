package com.example.web4_2.pages;

import com.example.web4_2.utils.SlowWebDriverActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    private final By usernameField = By.id("login-username");
    private final By passwordField = By.id("login-password");
    private final By loginButton = By.id("login-button");


    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

//    public void loginUser(String username, String password) throws InterruptedException {
//        SlowWebDriverActions slow = new SlowWebDriverActions(driver);
//
//        slow.sendKeys(By.id("login-username"), "testpim");
//        slow.sendKeys(By.id("login-password"), "testpim");
//        slow.click(By.id("login-button"));
//    }
}
