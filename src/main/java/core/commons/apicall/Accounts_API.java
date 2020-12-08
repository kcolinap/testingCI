package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import core.commons.apicall.dtos.accounts.Balance;
import core.commons.apicall.dtos.auth.AuthToken;
import io.restassured.RestAssured;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class Accounts_API extends Auth_API {

    private static final String ACCOUNTS_ENDPOINT = "accounts";

    public JSONArray getUserAccounts() {
        try {

            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();


            RestAssured.baseURI = getEnvUrl(ACCOUNTS_ENDPOINT);


            response =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            when().
                            get(EndPointsPath.ACCOUNTS_BALANCE.getText()).
                            then().
                            //statusCode(200).
                            extract().response();

            jsonObject = (JSONObject) parser.parse(response.getBody().asString());
            //System.out.print(response.getBody().asString());

            return (JSONArray) jsonObject.get("accounts");


        } catch (Exception | Error e) {
            e.printStackTrace();
        }
        return null;
    }

    public Balance getBalanceAccounts(String userEmail) throws Exception{
        try{
            AuthToken authToken = super.setAuthTokenSpecificUser(userEmail);
            auth_token = authToken.getAccess_token();

            RestAssured.baseURI = getEnvUrl(ACCOUNTS_ENDPOINT);

            Balance responseData =
                given().
                    header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                    header("Authorization", "Bearer " + auth_token).
                when().
                    get(EndPointsPath.ACCOUNTS_BALANCE.getText()).
                then().
                    statusCode(200).
                    extract().as(Balance.class);

            return responseData;

        }catch (Exception | Error e){
            throw e;
        }
    }
}
