package com.arvato.services.rest.Common.utils;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static com.arvato.services.rest.Common.Constants.*;
import static io.restassured.RestAssured.*;

public class BaseUtil {

    /*API base util*/
    public JsonPath postRequest(String object, int expectedStatusCode, String responseContentType, String authKey) {

        RequestSpecification spec = given()
                .headers("X-Auth-Key", authKey(authKey), "Content-Type", responseContentType)
                .contentType(ContentType.JSON)
                .body(object)
                .log().all();

        Response httpResponse = spec.post(endPointUri(TEST_API_ENDPOINT) + bankAccountUri(BANK_ACCOUNT_URI));

        if (expectedStatusCode != httpResponse.getStatusCode()) {
            httpResponse.prettyPrint();
        }

        System.out.println("Request statusCode is " + httpResponse.getStatusCode());

        return new JsonPath(httpResponse.asString());
    }


    public static String authKey(String key) {

        String authKey = System.getProperty("auth.key");
        if (authKey == null) {
            authKey = key;
        }
        return authKey;

    }

    public static String bankAccountUri(String accountUri) {

        String basePath = System.getProperty("request.uri");
        if (basePath == null) {
            basePath = accountUri;
        }
        return basePath;
    }

    public static String endPointUri(String endpoint) {

        String endPointPath = System.getProperty("endpoint.uri");
        if (endPointPath == null) {
            endPointPath = endpoint;
        }
        return endPointPath;
    }

    /*Web base util*/
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
