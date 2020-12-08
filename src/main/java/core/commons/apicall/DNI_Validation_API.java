package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import api.apps.android.nw.model.User;
import core.commons.apicall.dtos.auth.AuthToken;
import core.commons.apicall.dtos.dniValidation.DNIManualConfirm;
import core.commons.apicall.dtos.dniValidation.DNIUserStatus;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;

public class DNI_Validation_API extends Auth_API {

    private static final Logger logger = LogManager.getLogger();

    private static final String DNI_VALIDATION_ENDPOINT = "dni_validation";
    private static final String DNI_MANUAL_VALIDATION = "dni_manual_validation";

    public DNIUserStatus User_Status() throws Exception {
        try {

            //Setear authToken
            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            //Borrado de parametros para el body
            super.wipeRequestParams();

            RestAssured.baseURI = getEnvUrl(DNI_VALIDATION_ENDPOINT);

            DNIUserStatus responseData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            when().
                            get(EndPointsPath.DNI_USER_STATUS.getText()).
                            then().
                            statusCode(200).
                            extract().as(DNIUserStatus.class);

            return responseData;
        } catch (Exception | Error e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public void manual_validation(User user) throws Exception {
        try {

            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.YEAR, -20);

            //Borrado de parametros para el body
            super.wipeRequestParams();

            //Add parameters
            requestParams.put("dni", user.getId());
            requestParams.put("name", user.getName());
            requestParams.put("last_name", user.getLastName());
            String sexType = (user.getSexType().toUpperCase().equals("MALE")) ? "M" : "F";
            requestParams.put("sex_type", sexType);
            requestParams.put("birth_date", new java.sql.Date(cal.getTime().getTime()).toString());
            requestParams.put("nationality", "ARG");
            requestParams.put("cuil", user.getCuil());

            RestAssured.baseURI = getEnvUrl(DNI_MANUAL_VALIDATION);

            response  =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                    when().
                            put(EndPointsPath.DNI_VALIDATION_MANUAL_CONFIRM.getText() + user.getId()).
                    then().
                            statusCode(204).
                            extract().response();


        } catch (Exception | Error e) {
            logger.warn("User already validated");
        }
    }

}
