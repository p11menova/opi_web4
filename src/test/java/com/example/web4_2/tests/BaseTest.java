package com.example.web4_2.tests;

import com.example.web4_2.utils.PropertiesLoader;
import com.example.web4_2.utils.SlowWebDriverActions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected String baseUrl;
    protected WebDriverWait wait;
    protected SlowWebDriverActions slow ;


    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        slow = new SlowWebDriverActions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();

        baseUrl = PropertiesLoader.getProperty("base.url");

    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
