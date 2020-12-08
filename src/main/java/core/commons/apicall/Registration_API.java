package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import core.Util;
import core.commons.apicall.dtos.registration.*;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class Registration_API extends API_Util {


    private static final String REGISTRATION_ENDPOINT = "registration";

    public static String getRegistrationEndpoint() {
        return REGISTRATION_ENDPOINT;
    }

    public EmailRegistration emailRegistration(String email) throws Exception {
        try {

            //1. Obtener el endpoint
            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            //2. Hacer wipe a los parametros
            super.wipeRequestParams();

            //3. Construir los parametros a enviar en el request
            requestParams.put("email", email);
            requestParams.put("device_id", "1a0093f4-b4b6-4b88-9024-ANDROID-TESTS");
            requestParams.put("platform", "ANDROID");

            //4. Hacer el request
            EmailRegistration registerAnEmail =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.REGISTRATION_EMAIL.getText()).
                            then().
                           // statusCode(201).
                            extract().as(EmailRegistration.class);

            //5. Devolver el response en formato DTO
            return registerAnEmail;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

    public ConfirmarEmail confirm_email(String token) throws Exception {
        try {
            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            //Body
            wipeRequestParams();


            ConfirmarEmail responseData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            when().
                            get(EndPointsPath.REGISTRATION_CONFIRM_EMAIL.getText() + token).
                            then().
                            statusCode(200).
                            extract().as(ConfirmarEmail.class);


            return responseData;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw err;
        }
    }

    public RegistrationConfirmed registrationConfirmed(String token) throws Exception {
        try {
            //1. Obtener el endpoint
            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            //2. Hacer wipe a los parametros
            super.wipeRequestParams();

            //3. Hacer el request
            RegistrationConfirmed registrationStatus =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            when().
                            get(EndPointsPath.REGISTRATION_CONFIRMED.getText()).
                            then().
                            statusCode(200).
                            extract().as(RegistrationConfirmed.class);

            //4. Devolver el response en formato DTO
            return registrationStatus;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw err;
        }
    }

    public CUILReservation reserveAndValidateACuil(String token, String cuil) throws Exception {

        try {

            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            super.wipeRequestParams();

            requestParams.put("cuil", cuil);

            CUILReservation reserveAndValidateACuil =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.REGISTRATION_CUIL.getText()).
                            then().
                            statusCode(201).
                            extract().as(CUILReservation.class);

            return reserveAndValidateACuil;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

    public UsernameReservation usernameReservation(String token, String username, String password) throws Exception {

        try {

            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            super.wipeRequestParams();

            requestParams.put("username", username);
            requestParams.put("password", password);

            UsernameReservation usernameReservation =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.REGISTRATION_USERNAME.getText()).
                            then().
                            statusCode(201).
                            extract().as(UsernameReservation.class);

            return usernameReservation;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }


    public PhoneRegistration phoneRegistration(String token, String phoneNumber) throws Exception {

        try {

            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            super.wipeRequestParams();

            requestParams.put("phone_number", phoneNumber);

            PhoneRegistration phoneRegistration =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.REGISTRATION_PHONE_NUMBER.getText()).
                            then().
                            statusCode(201).
                            extract().as(PhoneRegistration.class);

            return phoneRegistration;
        } catch (Exception | Error err) {
            System.out.println("ESTE ES EL NUMERO DE TELEFONO QUE FALLO: " + phoneNumber);
            err.printStackTrace();
            throw new Exception();
        }
    }

    public PhoneConfirmation phoneConfirmation(String token, String phoneNumber, String code) throws Exception {

        try {

            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            super.wipeRequestParams();

            requestParams.put("phone_number", phoneNumber);
            requestParams.put("code", code);

            PhoneConfirmation phoneConfirmation =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.REGISTRATION_PHONE_NUMBER_CONFIRM.getText()).
                            then().
                            statusCode(200).
                            extract().as(PhoneConfirmation.class);

            return phoneConfirmation;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

    public PinRegistration pinRegistration(String token, String pin) throws Exception {

        try {

            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            super.wipeRequestParams();

            requestParams.put("pin", pin);

            PinRegistration pinRegistration =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.REGISTRATION_PIN_VALIDATE.getText()).
                            then().
                            statusCode(200).
                            extract().as(PinRegistration.class);

            return pinRegistration;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

    public CompleteRegistration completeRegistration(String token, String name, String lastName, String gender, String dni) throws Exception {

        try {

            RestAssured.baseURI = getEnvUrl(REGISTRATION_ENDPOINT);

            super.wipeRequestParams();

            requestParams.put("name", name);
            requestParams.put("last_name", lastName);
            requestParams.put("sex_type", gender);
            requestParams.put("dni", dni);
            requestParams.put("terms", true);

            CompleteRegistration completeRegistration =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.REGISTRATION_COMPLETE.getText()).
                            then().
                            statusCode(201).
                            extract().as(CompleteRegistration.class);

            return completeRegistration;
        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }


}
