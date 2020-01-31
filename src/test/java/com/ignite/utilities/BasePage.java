package com.ignite.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.ignite.eif_pages.*;
import com.ignite.pojos.Parent;
import com.ignite.pojos.Student;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BasePage {

    public Parent myParent = new Parent();
    public Student myStudent = new Student();

    public Parent_Pg ppObj = new Parent_Pg();
    public Student_Pg spObj = new Student_Pg();
    public Schools_Pg sclpObj = new Schools_Pg();
    public Finish_Pg fpObj = new Finish_Pg();
    public Confirmation_Pg cpObj = new Confirmation_Pg();


    protected WebDriver driver;
    protected static ExtentReports report;
    protected static ExtentHtmlReporter htmlReporter;
    protected static ExtentTest extentLogger;

    @BeforeMethod
    public void setupMethod() {
        driver = Driver.getDriver();
        driver.manage().timeouts().implicitlyWait(13, TimeUnit.SECONDS);

        // driver.manage().window().maximize();

        driver.get(ConfigReader.getKey("noIntro"));
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotLocation = BrowserUtils.getScreenshot(result.getName());
            extentLogger.fail(result.getName());
            extentLogger.addScreenCaptureFromPath(screenshotLocation);
            extentLogger.fail(result.getThrowable());

        }else if (result.getStatus() == ITestResult.SKIP) {
            extentLogger.skip("Test Case Skipped: " + result.getName());
        }

//        Driver.closeDriver();
    }


    @BeforeTest
    public void setUpTest() {
        report = new ExtentReports();
        String filePath = "/test-output/report.html";
        htmlReporter = new ExtentHtmlReporter(filePath);

        report.attachReporter(htmlReporter);

        report.setSystemInfo("Environment", "Staging");
        report.setSystemInfo("Browser", ConfigReader.getKey("browser"));
        report.setSystemInfo("OS", System.getProperty("os.name"));
        htmlReporter.config().setDocumentTitle("Ignite - EIF Reports");
        htmlReporter.config().setReportName("Ignite TestNG - Automated Test Reports");
        htmlReporter.config().setTheme(Theme.DARK);
    }


    @AfterTest
    public void tearDownTest() {
        report.flush();
        clearTemp();
    }


    private void clearTemp() {
        myParent = new Parent();
        myStudent = new Student();

        ppObj = new Parent_Pg();
        spObj = new Student_Pg();
        sclpObj = new Schools_Pg();
        fpObj = new Finish_Pg();
        cpObj = new Confirmation_Pg();
    }

}
