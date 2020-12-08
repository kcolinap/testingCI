package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import core.Util;
import core.commons.apicall.dtos.auth.*;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

@SuppressWarnings("unchecked")
public class Auth_API extends API_Util {

    private Util util = new Util();
    private static final String AUTH_ENDPOINT = "auth";

    String auth_token = null;
    String pin_token = null;
    final String PIN = "0000";


    /**
     * POST /auth/login
     */
    public AuthToken setAuthToken() throws Exception {
        try {

            RestAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Wipe requestParams
            super.wipeRequestParams();

            //Body
            requestParams.put("user_identifier", usuario);
            requestParams.put("password", pass);

            AuthToken responsedata =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.AUTH_LOGIN.getText()).
                            then().
                            //statusCode(200).
                            extract().as(AuthToken.class);


        /*    //Parseo del response
            jsonObject = (JSONObject) parser.parse(response.getBody().asString());
            auth_token = jsonObject.get("access_token").toString();*/
            return responsedata;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /** SET AUTH TOKEN TO AN SPECIFIC user **/
    public AuthToken setAuthTokenSpecificUser(String user) throws Exception {
        try {

            RestAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Wipe requestParams
            super.wipeRequestParams();

            //Body
            requestParams.put("user_identifier", user);
            if (pass == null)
                requestParams.put("password", util.getProperty("credentials","user.password", "properties"));
            else
                requestParams.put("password", pass);

            AuthToken responsedata =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            request().body(requestParams).
                    when().
                            post(EndPointsPath.AUTH_LOGIN.getText()).
                    then().
                            //statusCode(200).
                            extract().as(AuthToken.class);

            return responsedata;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * POST /auth/pin/validate
     *
     * @throws Exception
     */
    public PinValidate setPinToken() throws Exception {
        try {

            // Set the auth token
            AuthToken authToken = setAuthToken();
            auth_token = authToken.getAccess_token();

            restAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Wipe requestParams
            super.wipeRequestParams();

            //Body
            requestParams.put("pin", PIN);

            //Request
            PinValidate responseData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.PIN_VALIDATE.getText()).
                            then().
                            statusCode(200).
                            extract().as(PinValidate.class);

          /*  //Parseo del response
            jsonObject = (JSONObject) parser.parse(response.getBody().asString());
            pin_token = jsonObject.get("pin_token").toString();*/

            return responseData;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * POST
     */
    public POSTPasswordRecover post_password_recover(String email) throws Exception {
        try {

            //Wipe requestParams
            super.wipeRequestParams();

            RestAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Body
            requestParams.put("email", email);

            POSTPasswordRecover responsedata =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.RECOVER_PASSWORD .getText()).
                            then().
                            //statusCode(200).
                                    extract().as(POSTPasswordRecover.class);

            return responsedata;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * POST
     */
    public PatchPasswordRecover patch_password_recover(String token) throws Exception {
        try {

            //Wipe requestParams
            super.wipeRequestParams();

            RestAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Body
            requestParams.put("new_password", "Test-0000");

            PatchPasswordRecover responsedata =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("RecoveryToken", token).
                            request().body(requestParams).
                            when().
                            patch(EndPointsPath.PATCH_RECOVER_PASSWORD .getText()).
                            then().
                            //statusCode(200).
                                    extract().as(PatchPasswordRecover.class);

            return responsedata;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * POST /auth/login
     */
    public ChangePin change_pin() throws Exception {
        try {

            // Set the auth token
            AuthToken authToken = setAuthToken();
            auth_token = authToken.getAccess_token();

            //Wipe requestParams
            super.wipeRequestParams();

            RestAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Body
            requestParams.put("current_pin", "0000");
            requestParams.put("new_pin", "1111");

            ChangePin responsedata =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.CHANGE_PIN .getText()).
                            then().
                            //statusCode(200).
                            //statusCode(400).
                            extract().as(ChangePin.class);

            return responsedata;


        } catch (Exception e) {
            return null;
        }
    }

    /**
     * PATCH PIN RECOVER
     */
    public PatchPinRecover patch_pin_recover() throws Exception {
        try {

            // Set the auth token
            AuthToken authToken = setAuthToken();
            auth_token = authToken.getAccess_token();

            //Wipe requestParams
            super.wipeRequestParams();

            RestAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Body

            PatchPinRecover responsedata =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                            when().
                            patch(EndPointsPath.PATCH_RECOVER_PIN .getText()).
                            then().
                            //statusCode(200).
                            extract().as(PatchPinRecover.class);

            return responsedata;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * POST pin recover
     */
    public PostPinRecover post_pin_recover(String code) throws Exception {
        try {

            // Set the auth token
            AuthToken authToken = setAuthToken();
            auth_token = authToken.getAccess_token();

            //Wipe requestParams
            super.wipeRequestParams();

            RestAssured.baseURI = getEnvUrl(AUTH_ENDPOINT);

            //Body
            requestParams.put("code", code);
            requestParams.put("pin", "1111");

            PostPinRecover responsedata =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.PATCH_RECOVER_PIN .getText()).
                            then().
                            //statusCode(200).
                             extract().as(PostPinRecover.class);

            return responsedata;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
