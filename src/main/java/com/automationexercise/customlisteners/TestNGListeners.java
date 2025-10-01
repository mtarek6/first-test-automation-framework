package com.automationexercise.customlisteners;

import com.automationexercise.FileUtils;
import com.automationexercise.drivers.UITest;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.media.ScreenRecordManager;
import com.automationexercise.media.ScreenshotManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttachmentManager;
import com.automationexercise.utils.report.AllureConstants;
import com.automationexercise.utils.report.AllureEnvironmentManager;
import com.automationexercise.utils.report.AllureReportGenerator;
import com.automationexercise.validations.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements ISuiteListener, IExecutionListener, IInvokedMethodListener, ITestListener {
    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("Automation Exercise");
    }
    public void onExecutionStart() {
        LogsManager.info("Test Execution Started");
        cleanTestOutputDirectory();;
        LogsManager.info("Test output directories cleaned.");
        createTestOutputDirectory();
        LogsManager.info("Test output directories created.");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded.");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment variables set.");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution Finished");
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod()) {
            if(testResult.getInstance() instanceof UITest)
            {
                ScreenRecordManager.startRecording();
            }
            LogsManager.info("Test Case " + method.getTestMethod().getMethodName() +  "started" );
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod())
        {
            if(testResult.getTestClass().getRealClass().isAnnotationPresent(UITest.class))
            {
                ScreenRecordManager.stopRecording(testResult.getName());
                if(testResult.getInstance() instanceof WebDriverProvider provider)
                    driver = provider.getWebDriver();
                switch(testResult.getStatus()) {
                    case ITestResult.SUCCESS -> ScreenshotManager.takeFullPageScreenshot(driver, "passed-" + testResult.getName());
                    case ITestResult.FAILURE -> ScreenshotManager.takeFullPageScreenshot(driver, "failed-" + testResult.getName());
                    case ITestResult.SKIP    -> ScreenshotManager.takeFullPageScreenshot(driver, "skipped-" + testResult.getName());
                    default -> LogsManager.info("Test Case " + method.getTestMethod().getMethodName() +  "had an unknown result" );
                }
                AllureAttachmentManager.attachRecords(testResult.getName());
            }

            Validation.assertAll(testResult);

            AllureAttachmentManager.attachLogs();

            LogsManager.info("Test Case " + method.getTestMethod().getMethodName() +  "finished" );
        }
    }

    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test Case " +result.getMethod().getMethodName() + "passed");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test Case " +result.getMethod().getMethodName() + "failed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test Case " +result.getMethod().getMethodName() + "skipped");
    }

    //cleaning and creating logs directory before and after the whole execution (logs, screenshots, recording, allure results)
    private void cleanTestOutputDirectory() {
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));
        FileUtils.forceDeleteFile(new File(LogsManager.LOGS_PATH + File.separator + "logs.log"));
    }

    private void createTestOutputDirectory() {
        FileUtils.createDirectory(ScreenshotManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
        FileUtils.createDirectory("src/test/resources/downloads/");
    }
}
