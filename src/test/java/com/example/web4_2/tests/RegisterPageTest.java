package com.example.web4_2.tests;

import com.example.web4_2.utils.DBUtils;
import com.example.web4_2.utils.PropertiesLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import com.example.web4_2.pages.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterPageTest extends BaseTest {

    @Test
    void successfulRegistration() throws InterruptedException {
        driver.get(baseUrl);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickSwitchToRegister();

        registerPage.register("reg-testpim", "reg-testpim", "reg-testpim");
        wait.until(ExpectedConditions.urlContains("/graph"));

        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/graph"));
    }

    @Test
    void emptyPassword(){
        driver.get(baseUrl);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickSwitchToRegister();

        registerPage.register("reg-testpim", "", "reg-testpim");

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        assertTrue(alertText.contains(PropertiesLoader.getProperty("alert.error.empty_password")));

    }
    @Test
    void differentPasswords(){
        driver.get(baseUrl);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickSwitchToRegister();

        registerPage.register("reg-testpim", "reg-testpim", "anotherpassword");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("register-error-msg")));

        assertTrue(Objects.requireNonNull(driver.findElement(By.id("register-error-msg")).getText().trim())
                .contains(PropertiesLoader.getProperty("error.different_passwords")));

    }

    @BeforeEach
    void createUser(org.junit.jupiter.api.TestInfo testInfo) {
        if (testInfo.getDisplayName().equals("userAlreadyExists()")) {
            DBUtils.createUserByUsername("reg-testpim");
        }
        super.setUp();
    }

    @Test
    void userAlreadyExists(){
        driver.get(baseUrl);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickSwitchToRegister();

        registerPage.register("reg-testpim", "reg-testpim", "reg-testpim");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("register-error-msg")));

        assertTrue(Objects.requireNonNull(driver.findElement(By.id("register-error-msg")).getText().trim())
                .contains(PropertiesLoader.getProperty("error.username_already_exists")));

    }

    @AfterEach
    void cleanUp(org.junit.jupiter.api.TestInfo testInfo) {
        if (testInfo.getDisplayName().equals("successfulRegistration()") || testInfo.getDisplayName().equals("userAlreadyExists()")) {
            DBUtils.deleteUserByUsername("reg-testpim");
        }
        super.tearDown();
    }

}