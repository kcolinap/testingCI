package suite;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

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
        plugin = {"pretty"}, //, "nubiReportServer.Reporter:"
        glue = {"steps/def"},
        tags = {"not @ignore", "@stage"},
        strict = true,
        monochrome = true
)

public class RegressionStageKlovSuite {

}
