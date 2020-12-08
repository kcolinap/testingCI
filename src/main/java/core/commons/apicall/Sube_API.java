package core.commons.apicall;

import api.apps.Apps;
import api.apps.android.nw.EndPointsPath;
import core.Util;
import core.commons.apicall.dtos.auth.AuthToken;
import core.commons.apicall.dtos.auth.PinValidate;
import core.commons.apicall.dtos.sube.DeleteSube;
import core.commons.apicall.dtos.sube.RecargaSube;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

@SuppressWarnings("unchecked")
public class Sube_API extends Auth_API {

    private static final String SUBE_ENDPOINT = "sube";

    public RecargaSube recarga_sube(String accountNumber) throws Exception {
        try {


            // Set the auth token
            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            //set the pin token
            PinValidate pinValidate = super.setPinToken();
            pin_token = pinValidate.getPin_token();

            RestAssured.baseURI = getEnvUrl(SUBE_ENDPOINT);


            //Body
            super.wipeRequestParams();


            requestParams.put("card_number", "6061267340141116");
            requestParams.put("amount", 6000);
            requestParams.put("account_number", accountNumber);
            requestParams.put("card_alias", Apps.util.generarInformacionPersonal("USERNAME"));

            RecargaSube responseData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.URL_SUBE_CHARGE.getText()).
                            then().
                            statusCode(200).
                            extract().as(RecargaSube.class);

            return responseData;

        } catch (Exception | Error e) {
            e.printStackTrace();
            throw e;
        }
    }

    public DeleteSube delete_sube(String idSube) throws Exception {
        try {


            // Set the auth token
            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            RestAssured.baseURI = getEnvUrl(SUBE_ENDPOINT);


            //Body
            super.wipeRequestParams();

            DeleteSube responseData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer "+ auth_token).
                            when().
                            delete(EndPointsPath.URL_DELETE_SUBE.getText()+"/"+idSube).
                            then().
                            statusCode(200).
                            extract().as(DeleteSube.class);

            return responseData;

        } catch (Exception | Error e) {
            e.printStackTrace();
            throw e;
        }
    }
}
