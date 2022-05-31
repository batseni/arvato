package com.arvato.services.rest.Common.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;

import static com.arvato.services.rest.Common.utils.WebBaseUtil.chrome_driver;

public class WebPortalUtil {

    public static void WhenCustomerSelectsCreateAccountLink() {
        chrome_driver.findElement(By.id("login-create-account")).click();
    }

    public static void AndCustomerInsertsEmailAddress() {
        chrome_driver.findElement(By.id("NewAccountEmail")).sendKeys("test@gmail.com");
        WebElement createAccount = chrome_driver.findElement(By.id("SubmitCreateAccount"));
        createAccount.click();
    }

    public static void ThenCustomerIsRedirectedToAuthenticationConfirmationPage() {
        chrome_driver.getCurrentUrl().contains("authentication/confirmationemailsent");
    }

    public static void AndYouGotMailMessageIsShown() {
        chrome_driver.findElement(By.className("login-panel__confirmation"));
        isTextPresent("You've got mail");
    }

    public static void WhenCustomerSelectsLoginWithEmailAndPassword() {
        chrome_driver.findElement(By.id("emailandpasswordTabLink")).click();
    }

    public static void AndCustomerInsertsInvalidEmailAddress() {
        chrome_driver.findElement(By.id("EmailAndPasswordModel_Email")).sendKeys("kfdlshjfsh;");
        chrome_driver.findElement(By.id("EmailAndPasswordBtn")).click();
    }

    public static void ThenErrorValidationMessageIsShown() {
        chrome_driver.findElement(By.id("EmailAndPasswordModelError")).getText().
                equals("Sorry, this does not seem to be a valid emailaddress, please try again");
    }

    public static void AndCustomerInsertsEmailAddressAndPassword() {
        chrome_driver.findElement(By.id("EmailAndPasswordModel_Email")).sendKeys("test@gmail.com");
        chrome_driver.findElement(By.id("EmailAndPasswordModel_Password")).sendKeys("1234");
        chrome_driver.findElement(By.id("EmailAndPasswordBtn")).click();
    }

    public static void ThenCustomerIsRedirectedToTheEmailandpasswordErrorPage() {
        chrome_driver.getCurrentUrl().contains("type=emailandpassword&error=20");
    }

    public static void AndErrorMessageIsShown() {
        chrome_driver.findElement(By.className("notifications")).getText().
                equals("The specified credentials are incorrect, make sure you typed them correctly");
    }

    public static void isTextPresent(String text){
        try{
            chrome_driver.getPageSource();
        }
        catch(Exception ignored){
        }
    }

    public void standartTestTearDown() throws Exception {
        TakesScreenshot scrShot =((TakesScreenshot)chrome_driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(SrcFile , new File("C:\\dev\\arvato\\screenshot.png"));
    }

}
