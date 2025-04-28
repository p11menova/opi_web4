package com.example.web4_2.tests;

import com.example.web4_2.pages.LoginPage;
import org.junit.jupiter.api.Test;
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
}
