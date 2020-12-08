package nubiReportServer;

import api.apps.android.Android;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberKlovAdapter;
import core.Util;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;


public class Reporter extends ExtentCucumberKlovAdapter {

	public Reporter(String arg) {
		super(arg);
	}

	@Override
	public String takeScreenshot() throws IOException {
		String fileLocation = "";
		String name = "";
		final File screenshot = ((TakesScreenshot) Android.driver).getScreenshotAs(OutputType.FILE);
		name = "screenshot".concat(Long.toString(System.currentTimeMillis()));
		fileLocation = getReportPath().concat(File.separator).concat(name);
		FileUtils.copyFile(screenshot, new File(fileLocation));
		return name;
	}

	@Override
	public String getReportPath() {
		return Util.getScreenShootDirectory();
	}

	@Override
	public Boolean isScreenshotDisable() {
		return false;
	}

}
