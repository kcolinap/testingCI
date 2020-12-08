package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import core.commons.apicall.dtos.auth.AuthToken;
import io.restassured.RestAssured;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

@SuppressWarnings("unchecked")
public class PrepaidCard_API extends Auth_API {

    private static final String BFF = "bff";



    public JSONObject call_Prepaid_Card_Update(String status) {
        try {

            //Setear authToken
            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            RestAssured.baseURI = getEnvUrl(BFF);

            //wipe requestParams
            super.wipeRequestParams();

            //set request params
            requestParams.put("pin", "1244");
            requestParams.put("status", status);

            //make the request
            response =
                            given().
                                    header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                                    header("Authorization", "Bearer "+ auth_token).
                                    request().body(requestParams).
                                    when().
                                    patch(EndPointsPath.PREPAID_CARD_UPDATE.getText()).
                                    then().
                                    //statusCode(200).
                                            extract().response();

            return (JSONObject) parser.parse(response.getBody().asString());

            // System.out.print(response.getBody().asString());

        } catch (Exception | Error err) {
            err.printStackTrace();
        }
        return null;
    }
}
