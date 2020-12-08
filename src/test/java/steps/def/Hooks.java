package steps.def;

import api.apps.android.Android;
import com.epam.reportportal.service.ReportPortal;
import core.Util;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static io.cucumber.java.Status.FAILED;

public class Hooks {

    private static String testName;
    private static String id;
    public String getName(){
        return testName.replace(" ", "");
    }
    public String getId(){
        return id;
    }
    private static final Logger logger = LogManager.getLogger();

    @Before
    public void before(Scenario scenario){
        if ( Android.driver != null ) {
            if (Android.driver.isKeyboardShown())
                Android.driver.hideKeyboard();
            testName = scenario.getName();
            id = scenario.getId();
            logger.info("Ejecutando testCase: " + testName);
        }
    }

    private String getScreenshotFilename(String scenarioId, String pathToScreenshots) {
        String name = FilenameUtils.getBaseName(scenarioId) + "." + FilenameUtils.getExtension(scenarioId);

        File dir = new File(pathToScreenshots);
        if (!dir.exists())
            dir.mkdirs();

        DateFormat df = new SimpleDateFormat("yyyyMMddhh"); // add S if you need milliseconds
        String dirDateFilename = dir + File.separator + df.format(new Date()) + File.separator;
        File dirInside = new File(dirDateFilename);
        if (!dirInside.exists())
            dirInside.mkdirs();

        StringBuilder filename = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (c=='.' || Character.isJavaIdentifierPart(c)) {
                filename.append(c);
            }
        }

        return dirDateFilename + filename.toString();
    }

    @After
    public void after(Scenario scenario) throws IOException {

        if ( Android.driver != null ) {
            if (Android.driver.isKeyboardShown())
                Android.driver.hideKeyboard();

            logger.info(scenario.getStatus());
            if (FAILED.equals(scenario.getStatus())) {
                File scrFile = ((TakesScreenshot) Android.driver).getScreenshotAs(OutputType.FILE);
                String pathToScreenshots = Util.getScreenShootDirectory() + File.separator;

                String filename = getScreenshotFilename(scenario.getId(), pathToScreenshots);

                String outputFilePath = filename + ".png";
                FileUtils.copyFile(scrFile, new File(outputFilePath));

                ReportPortal.emitLog(scenario.getName(), "FAIL", Calendar.getInstance().getTime(), new File(outputFilePath));

            }
        }
    }
    
    // dont remove empty after and befor steps, they are necesaries for report server cucumber adapter 
    @BeforeStep
	public void beforeStep(Scenario scenario) {}
    @AfterStep
    public void afterStep(Scenario scenario) {}

}
