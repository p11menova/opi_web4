package com.example.web4_2.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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
    private final By xButtonsContainer = By.cssSelector(".x-buttons");
    private final By radiusButtonsContainer = By.cssSelector(".radius-buttons");
    private final By checkButton = By.id("check-button");
    private final By clearButton = By.id("clear-button");
    private final By backButton = By.id("back-button");
    public final By resultsRows = By.cssSelector(".results-table tbody tr");


    public void selectX(String x) {
        WebElement btn = findButtonInside(xButtonsContainer, x);
        jsClick(btn);
        wait.until(ExpectedConditions.attributeContains(btn, "class", "selected"));
    }

    public void selectR(String r) {
        WebElement btn = findButtonInside(radiusButtonsContainer, r);
        jsClick(btn);
        wait.until(ExpectedConditions.attributeContains(btn, "class", "selected"));
    }

    public void enterY(String y) {
        WebElement inp = wait.until(ExpectedConditions.elementToBeClickable(By.id("y-coord")));
        inp.clear();

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input',{bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change',{bubbles:true}));",
                inp, y);

    }

    public void clickCheck() {
        jsClick(driver.findElement(checkButton));
    }
    public void clickClear(){
        jsClick(driver.findElement(clearButton));
    }
    public void clickBack(){
        jsClick(driver.findElement(backButton));
    }

    public boolean rowExists(String x, String y, String r) {
        for (WebElement row : driver.findElements(resultsRows)) {
            List<WebElement> td = row.findElements(By.tagName("td"));
            if (td.size() >= 3 &&
                    td.get(0).getText().trim().equals(x) &&
                    td.get(1).getText().trim().equals(y) &&
                    td.get(2).getText().trim().equals(r)) {
                return true;
            }
        }
        return false;
    }
    public int tableRowsNum(){
        return driver.findElements(resultsRows).size();
    }
    public void clickOnCanvas(double relX, double relY) {
        WebElement cv = driver.findElement(canvas);

        // габариты элемента
        Rectangle r = cv.getRect();
        int offsetX = (int) Math.round(r.getWidth()  * relX) - r.getWidth()/2;
        int offsetY = (int) Math.round(r.getHeight() * relY) - r.getHeight()/2;

        // «человеческий» клик — Actions + offset
        new Actions(driver)
                .moveToElement(cv, offsetX, offsetY)
                .click()
                .perform();
    }
    public void waitRowsGrew(int before) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.findElements(resultsRows).size() > before);
    }



    private WebElement findButtonInside(By container, String text) {
        return driver.findElement(container)
                .findElement(By.xpath(".//button[normalize-space(text())='" + text + "']"));
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void clickCheckAndWaitForRow() {
        int rowsBefore = driver.findElements(resultsRows).size();

        jsClick(driver.findElement(checkButton));

        wait.until(d -> d.findElements(resultsRows).size() > rowsBefore);
    }

}
