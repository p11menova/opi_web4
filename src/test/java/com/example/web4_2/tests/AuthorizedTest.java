package com.example.web4_2.tests;

import com.example.web4_2.pages.LoginPage;
import com.example.web4_2.pages.RegisterPage;
import com.example.web4_2.utils.DBUtils;
import com.example.web4_2.utils.PropertiesLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class AuthorizedTest extends BaseTest {
    @BeforeEach
    void loginAndGoToGraphPage() {
        driver.get(baseUrl);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickSwitchToRegister();
        registerPage.register(PropertiesLoader.getProperty("test_user"),
                PropertiesLoader.getProperty("test_user_password"),
                PropertiesLoader.getProperty("test_user_password"));

        wait.until(driver -> driver.getCurrentUrl().contains("/graph"));

    }

    @AfterEach
    void deleteUser() throws InterruptedException {
        super.tearDown();
        DBUtils.deleteUserByUsername(PropertiesLoader.getProperty("test_user"));
    }
}
