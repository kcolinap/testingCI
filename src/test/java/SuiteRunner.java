import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.NubiWallet;
import core.Util;
import core.managers.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.StoppedByUserException;
import suite.*;

public class SuiteRunner {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception{
        try {

            DriverManager.loadConfigProp("AndroidApp.properties");

            /**
             * POSIBLE VALUES:
             * t : Test
             * d : Dev
             * s : Stage
             * sg: Staging (Pre Prod)
             */
           Apps.util.setGlobalEnvironment(args[1], args[2]);
            String env = Apps.util.getEnv();
            // Get max 3 users from DB
            //if(!args[0].toLowerCase().contentEquals("cu") && !args[0].toLowerCase().contentEquals("cuff"))
              // Android.nubiWallet.loadUsuarios(1);

            //System.out.println("Cantidad de usuarios encontrados: " + Android.app.nubiWallet.usuarios.size());

            Android.nubiWallet.usuarios.add(Apps.util.obtenerUsuarioByUsername("sgiuser142516"));//user8329w2 sgiuser579997


            /**
             * POSIBLE VALUES: local, kobiton and browserstack
             * local: Will use the local configuration and local devices
             * kobiton: will use kobiton service (kobiton.com) CHANGE FILE PROPERTIES in resources/properties/kobiton.properties
             * browserstack: will use browserstack service (browserstack.com) CHANGE FILE PROPERTIES in resources/properties/browserstack.properties
             */
            if(!args[0].toLowerCase().contentEquals("cu") && !args[0].toLowerCase().contentEquals("cuff"))
                DriverManager.createDriver(args[2]);

            //Set value to iniciated app in order to avoid restart the app again when the first test case was executed
            NubiWallet.INICIATED_APP = 1;

            /**
             * POSIBLE VALUES:
             *    r for regression
             *    j full jenkins
             *    jm jenkins manual
             *    cu for create users
             *    cuff for create users from file
             */
            switch (args[0]){
                case "r":
                    logger.info("Inicio de suite REGRESION en ambiente " + env.toUpperCase());
                    JUnitCore.runClasses(RegressionSuite.class);
                    break;
                case "j":
                    logger.info("Inicio de suite para Jenkins en ambiente " + env.toUpperCase());
                    JUnitCore.runClasses(JenkinsSuite.class);
                    break;
                case "klov":
                    logger.info("Inicio de Regression para Jenkins en ambiente " + env.toUpperCase());
                    JUnitCore.runClasses(RegressionStageKlovSuite.class);
                    break;
                case "sgsmoke":
                        logger.info("Inicio de SMOKE en Jenkins en ambiente " + env.toUpperCase());
                        JUnitCore.runClasses(SmokeStageKlovSuite.class);
                    break;
                case "jm":
                    logger.info("Inicio de suite para Jenkins MANUAL en ambiente " + env.toUpperCase());
                    JUnitCore.runClasses(JenkinsManualSuite.class);
                    break;
                case "cu":
                    logger.info("Inicio de suite para Crear Usuarios en ambiente " + env.toUpperCase());
                    JUnitCore.runClasses(CreateUserSuite.class);
                    break;
                case "cuff":
                    logger.info("Inicio de suite para Crear Usuarios desde CSV en ambiente " + env.toUpperCase());
                    JUnitCore.runClasses(CreateUserFromFileSuite.class);
                    break;
            }

        }catch (Exception | Error e){
            //NubiWallet.util.showTrackTraceError("No se pudo ejecutar la suite", e);
            logger.error(e.toString());
            throw e;
        }finally {
            DriverManager.killDriver();
            logger.info("Ejecucion terminada");
        }
    }
}
