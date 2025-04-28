package com.example.web4_2.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SlowWebDriverActions {
    private final WebDriver driver;
    private final long delayMs = 250; // задержка в миллисекундах

    public SlowWebDriverActions(WebDriver driver) {
        this.driver = driver;
    }

    public void sendKeys(By locator, String text) throws InterruptedException {
        WebElement element = driver.findElement(locator);
        element.sendKeys(text);
        Thread.sleep(delayMs);
    }

    public void click(By locator) throws InterruptedException {
        WebElement element = driver.findElement(locator);
        element.click();
        Thread.sleep(delayMs);
    }

    public void open(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(delayMs);
    }
}
