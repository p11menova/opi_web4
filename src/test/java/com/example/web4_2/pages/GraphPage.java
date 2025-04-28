package com.example.web4_2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GraphPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public GraphPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private final By canvas = By.id("canvas");
    private final By yInput = By.id("y-coord");

    private final By xButtonsContainer = By.cssSelector(".x-buttons");
    private final By radiusButtonsContainer = By.cssSelector(".radius-buttons");

    private final By submitButton = By.id("check-button");
    private final By clearButton = By.id("clear-button");
    private final By backButton = By.id("back-button");

    private final By resultsTableRows = By.cssSelector(".results-table tbody tr");

    public void selectX(String xValue) {
        List<WebElement> xButtons = driver.findElement(xButtonsContainer).findElements(By.tagName("button"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(xButtonsContainer));

        for (WebElement button : xButtons) {
            System.out.println("X button text: " + "'"+button.getText()+"'"); // <-- Добавь печать

            if (button.getText().trim().equals(xValue)) {
                button.click();
                System.out.println("Clicked X: " +"'"+ xValue+"'");

                return;
            }
        }
        throw new RuntimeException("Кнопка X со значением " + xValue + " не найдена");
    }

    public void selectRadius(String rValue) {
        List<WebElement> rButtons = driver.findElement(radiusButtonsContainer).findElements(By.tagName("button"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(radiusButtonsContainer));

        for (WebElement button : rButtons) {
            if (button.getText().trim().equals(rValue)) {
                button.click();
                return;
            }
        }
        throw new RuntimeException("Кнопка Radius со значением " + rValue + " не найдена");
    }

    public void enterY(String y) {
        WebElement input = driver.findElement(yInput);
        wait.until(ExpectedConditions.visibilityOfElementLocated(yInput));

        System.out.println("Found Y input, entering: " + y);

        input.clear();
        input.sendKeys(y);

        // Очень важно: вызвать событие blur для Angular, иначе он не увидит изменения
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].blur();", input);

        // И ещё можно кидать событие input и change вручную для надёжности
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", input);
    }

    public void clickSubmit() {
        driver.findElement(submitButton).click();
    }

    public void clickClear() {
        driver.findElement(clearButton).click();
    }

    public void clickBack() {
        driver.findElement(backButton).click();
    }

    public List<WebElement> getResultsRows() {
        return driver.findElements(resultsTableRows);
    }

    public boolean checkResultExists(String x, String y, String r) {
        List<WebElement> rows = getResultsRows();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4) {
                String cellX = cells.get(0).getText().trim();
                String cellY = cells.get(1).getText().trim();
                String cellR = cells.get(2).getText().trim();
                if (cellX.equals(x) && cellY.equals(y) && cellR.equals(r)) {
                    return true;
                }
            }
        }
        return false;
    }


}
