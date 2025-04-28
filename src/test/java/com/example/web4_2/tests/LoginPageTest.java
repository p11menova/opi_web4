package com.example.web4_2.tests;

import com.example.web4_2.pages.LoginPage;
import com.example.web4_2.utils.PropertiesLoader;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest extends BaseTest {

    @Test
    void successfulLogin() throws InterruptedException {
        driver.get(baseUrl);

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("testpim", "testpim");

        wait.until(ExpectedConditions.urlContains("/graph"));
        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/graph"));
    }

    @Test
    void wrongPassword() throws InterruptedException {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("testpim", "wrongpassword");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-error-msg")));

        assertTrue(Objects.requireNonNull(driver.findElement(By.id("login-error-msg")).getText().trim())
                .contains(PropertiesLoader.getProperty("error.wrong_password")));

    }

    @Test
    void noSuchUser(){
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("no_such_user", "wrongpassword");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-error-msg")));

        assertTrue(Objects.requireNonNull(driver.findElement(By.id("login-error-msg")).getText().trim())
                .contains(PropertiesLoader.getProperty("error.no_such_user")));
    }

    @Test
    void emptyInput(){
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("", "");
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        assertTrue(alertText.contains(PropertiesLoader.getProperty("alert.error.empty_username")));

    }


}
