package core.commons.apicall;

import api.apps.Apps;
import api.apps.android.nw.model.User;
import api.apps.android.nw.NubiWallet;
import core.Util;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class API_Util {

    private static final Logger logger = LogManager.getLogger();
    private static Util util = NubiWallet.util;
    public String environment = NubiWallet.environment;
    public String apiUrl;
    public String usuario, pass, userId;

    RestAssured restAssured;
    JSONObject requestParams = new JSONObject();
    Response response;
    JSONParser parser = new JSONParser();
    JSONObject jsonObject;


    //Constantes
    private final static String PIN = "1111";

    public String getEnvUrl(String endpoint) {
        String port = util.getProperty("endpoints", environment + "." + endpoint, "properties");

        //TODO: Quitar validacion para el puerto cuando implementen BFF en dev
        if(!endpoint.toUpperCase().contentEquals("LMBMOCK") && !util.getEnv().equals(Util.DEV))
            port = "7000";

        if(endpoint.toUpperCase().contentEquals("DNI_MANUAL_VALIDATION"))
            apiUrl = Apps.util.getProperty("endpoints", environment + ".dni_manual_validation", "properties");

        if (port != "")
            if (!environment.contentEquals(Util.STAGING)  )//&& !environment.contentEquals(Util.DEV)
                return apiUrl + ":" + port;
        return apiUrl;
    }


    public API_Util() {
        load_properties();
    }

    public void load_properties() {
        apiUrl = Apps.util.getProperty("endpoints", environment + ".apiUrl", "properties");

        User user = NubiWallet.usuario;
        if (user != null) {
            usuario = user.getEmail();
            userId = user.getId();
            pass = user.getRawPassword();
        }
    }

    public void wipeRequestParams() {
        if (!requestParams.isEmpty())
            requestParams.clear();
    }
}
