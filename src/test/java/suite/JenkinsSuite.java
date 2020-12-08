package suite;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


//@RunWith(Suite.class)
//@Suite.SuiteClasses({Registration.class})
//"com.epam.reportportal.cucumber.ScenarioReporter"
// ,
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "classpath:features/[F001]Registration/",
                "classpath:features/[F002]Login/",
                "classpath:features/[F003]Dashboard/",
                "classpath:features/[F004]Contactos/",
                "classpath:features/[F005]Operaciones/",
                "classpath:features/[F006]Perfil/",
                "classpath:features/[F007]Sube/",
                "classpath:features/[F008]PagoDeServicios/",
                "classpath:features/[F009]RecargaCelular/",
                "classpath:features/[F010]TarjetaPrepaga/"
        },
        plugin = {"pretty", "reportPortal.cucumber.ScenarioReporter"},
        glue = {"steps/def"},
        strict = true,
        tags = {"@regression", "not @ignore"},
        monochrome = true
)

public class JenkinsSuite {

}
