package com.example.web4_2.tests;

import com.example.web4_2.utils.DBUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import com.example.web4_2.pages.*;
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

    @AfterEach
    void cleanUp(org.junit.jupiter.api.TestInfo testInfo) {
        if (testInfo.getDisplayName().equals("successfulRegistration()")) {
            DBUtils.deleteUserByUsername("reg-testpim");
        }
        super.tearDown();
    }
}
