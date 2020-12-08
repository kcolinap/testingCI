package core.commons.apicall;

import api.apps.android.Android;
import api.apps.android.nw.EndPointsPath;
import api.apps.android.nw.model.Account;
import api.apps.android.nw.model.User;
import core.commons.apicall.dtos.accounts.Balance;
import core.commons.apicall.dtos.auth.AuthToken;
import core.commons.apicall.dtos.p2p.P2PRequest;
import io.restassured.RestAssured;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class P2P_API extends Auth_API {

    private static final String P2P_ENDPOINT = "p2p";
    private static final String BFF_ENDPOINT = "bff";

    /**
     * URLs para enviar y solicitar dinero P2P
     */
    public static final String
            URL_P2P_REQUEST = "/p2p/requests",
            URL_P2P_SENDINGS = "/p2p/sendings";

    public P2PRequest createANewMoneyRequest(User originUser, User destinationuser, String Amount, String Currency) throws Exception {
        try {

            //Get number account origin user
            Accounts_API accounts_api = new Accounts_API();
            Balance balance = accounts_api.getBalanceAccounts(originUser.getEmail());

            String accountNumber = String.valueOf(((LinkedHashMap) balance.getAccounts().get(0)).get("number"));

            //Setear authToken
            AuthToken authToken = super.setAuthTokenSpecificUser(originUser.getEmail());
            auth_token = authToken.getAccess_token();

            // Hacer clean a los Request Params
            super.wipeRequestParams();

            // Obtener el EndPoint
            RestAssured.baseURI = getEnvUrl(P2P_ENDPOINT);


            // Crear los paramentros a enviar en el body
            requestParams.put("origin_account", accountNumber);
            requestParams.put("destination_user_id", destinationuser.getId());
            requestParams.put("amount", Integer.valueOf(Amount));
            requestParams.put("category", "OTHERS");
            requestParams.put("comment", "Request from BE for automation UI");
            requestParams.put("currency", "ARS");

            // Enviar el request y alojar el response en el DTO

            P2PRequest obtainRequestData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.URL_P2P_REQUEST.getText()).
                            then().
                            statusCode(201).
                            extract().as(P2PRequest.class);

            return obtainRequestData;

        } catch (Exception | Error e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
