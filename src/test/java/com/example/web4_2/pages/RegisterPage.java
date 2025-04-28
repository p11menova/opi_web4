package com.example.web4_2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;



    private final By usernameField = By.id("register-username");
    private final By passwordField = By.id("register-password");
    private final By repeatPasswordField = By.id("register-repeated-password");
    private final By registerButton = By.id("register-button");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickSwitchToRegister() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-username")));

        By fromLogToRegButton = By.id("from-login-to-reg");
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromLogToRegButton)).click();
    }
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
    public void enterRepeatPassword(String password) {
        driver.findElement(repeatPasswordField).sendKeys(password);
    }
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void register(String username, String password, String repeatPassword) {
        enterUsername(username);
        enterPassword(password);
        enterRepeatPassword(repeatPassword);
        clickRegisterButton();
    }
}
