package com.arvato.services.rest.Common.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class WebBaseUtil {

    public static WebDriver chrome_driver = new ChromeDriver();
    public static String baseURL = "https://ac.myafterpay.com/en-se";

    public static void GivenCustomerIsInLoginPage(){
        chrome_driver.navigate().to(baseURL);
    }

    public static void AndPrivacySettingsAccepted() {
        WebElement shadowHost = chrome_driver.findElement(By.cssSelector("#usercentrics-root"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        WebElement shadowContent = shadowRoot.findElement(By.
                cssSelector("div[data-testid=uc-buttons-container] > button[data-testid=uc-accept-all-button]"));
        shadowContent.click();
    }

    public static void AndGivenWebPageIsClosed(){
        chrome_driver.quit();
    }

    public static void setDriverAndOpenBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\dev\\arvato\\chromedriver.exe");
        chrome_driver.get(baseURL);
        chrome_driver.manage().window().maximize();
        chrome_driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
