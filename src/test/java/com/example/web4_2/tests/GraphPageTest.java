package com.example.web4_2.tests;

import com.example.web4_2.pages.GraphPage;
import com.example.web4_2.utils.PropertiesLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GraphPageTest extends AuthorizedTest {

    @Test
    void successfulCheck() {
        GraphPage graph = new GraphPage(driver);
        String x = "1";
        String y = "0";
        String r = "1";

        graph.selectX(x);
        graph.enterY(y);
        graph.selectR(r);

        graph.clickCheckAndWaitForRow();

        assertTrue(graph.rowExists(x,y,r));
    }

    @Test
    void successfulClear() {
        GraphPage graphPage = new GraphPage(driver);
        graphPage.clickClear();
        wait.until(ExpectedConditions.numberOfElementsToBe(graphPage.resultsRows, 0));

        assertEquals(0, graphPage.tableRowsNum());
    }

    @Test
    void successfulGoBack(){
        GraphPage graphPage = new GraphPage(driver);
        graphPage.clickBack();
        wait.until(ExpectedConditions.urlMatches(baseUrl));


        assertEquals(driver.getCurrentUrl(), baseUrl + "/");
    }
    @Test
    void wrongYInput(){
        GraphPage graphPage = new GraphPage(driver);
        graphPage.enterY("282");
        graphPage.clickCheck();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        System.out.println(alertText);
        assertTrue(alertText.contains(PropertiesLoader.getProperty("alert.error.invalid_y")));
    }

    @Test
    void graphClick() throws InterruptedException {
        GraphPage graph = new GraphPage(driver);

        int rowsBefore = graph.tableRowsNum();

        graph.clickOnCanvas(0.3, 0.45);

        graph.waitRowsGrew(rowsBefore);

        assertTrue(graph.tableRowsNum() > rowsBefore);

    }





}
