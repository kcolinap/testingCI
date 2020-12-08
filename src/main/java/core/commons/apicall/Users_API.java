package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import api.apps.android.nw.model.User;
import core.commons.apicall.dtos.auth.AuthToken;
import core.commons.apicall.dtos.users.ValidateUser;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class Users_API extends Auth_API {

    private static final String USERS_ENDPOINT = "users";

    public ValidateUser patch_users(User user) throws Exception {
        try {

            //Setear authToken
            AuthToken authToken = super.setAuthTokenSpecificUser(user.getUsername());
            auth_token = authToken.getAccess_token();

            //Borrado de parametros para el body
            super.wipeRequestParams();

            //Payload params
            requestParams.put("occupation_code", "AUT");
            requestParams.put("obliged_subject", false);
            requestParams.put("ppe", false);
            requestParams.put("nationality", "032");

            RestAssured.baseURI = getEnvUrl(USERS_ENDPOINT);

            ValidateUser responseData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                    when().
                            patch(EndPointsPath.USERS.getText()).
                    then().
                            statusCode(200).
                            extract().as(ValidateUser.class);

            return responseData;
        } catch (Exception | Error e) {
            e.printStackTrace();
            throw new Exception();
        }
    }


}
