package com.arvato.services.rest.Common.utils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class Listeners implements ITestListener {


    public void onTestStart(ITestResult result) {

            System.out.println("New Test Started" +result.getName());

        }

        public void onTestSuccess(ITestResult result) {

            System.out.println("Test Successfully Finished" +result.getName());

        }

        public void onTestFailure(ITestResult result) {
            System.out.println("***** Error "+result.getName()+" test has failed *****");
            String methodName=result.getName().toString().trim();
            ITestContext context = result.getTestContext();
            WebDriver driver = (WebDriver)context.getAttribute("driver");
            takeScreenShot(methodName, driver);


        }

        public void onTestSkipped(ITestResult result) {

            System.out.println("Test Skipped" +result.getName());

        }

        public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

            System.out.println("Test Failed but within success percentage" +result.getName());

        }

        public void onStart(ITestContext context) {


            System.out.println("This is onStart method" +context.getOutputDirectory());

        }

        public void onFinish(ITestContext context) {

            System.out.println("This is onFinish method" +context.getPassedTests());
            System.out.println("This is onFinish method" +context.getFailedTests());
        }

    public void takeScreenShot(String methodName, WebDriver driver) {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("C:\\dev\\arvato" + methodName+ ".png"));
            System.out.println("***Placed screen shot in " + "C:\\dev\\arvato" + " ***");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
