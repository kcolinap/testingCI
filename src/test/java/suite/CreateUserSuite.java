package suite;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//RegistrationR.class
//LoginR.class
//@RunWith(Suite.class)
//@Suite.SuiteClasses({RegistrationR.class, LoginR.class, MovementsR.class})
// "com.epam.reportportal.cucumber.ScenarioReporter"
// , "reportPortal.cucumber.ScenarioReporter"
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features/CreateUser/CreateUser.feature"},
        plugin = {"pretty"},
        glue = {"steps/def"},
        tags = {"not @ignore"},
        strict = true,
        monochrome = true
)

public class CreateUserSuite {

}
