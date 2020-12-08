package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import api.apps.android.nw.model.User;
import com.mongodb.util.JSON;
import core.commons.apicall.dtos.auth.AuthToken;
import core.commons.apicall.dtos.contacts.ContactsSync;
import core.commons.apicall.dtos.contacts.CreateBankingContact;
import io.restassured.RestAssured;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Contacts_API extends Auth_API {

    private static final String CONTACTS_ENDPOINT = "contacts";

    /**
     * API call para agregarle un contacto bancario a un usuario NUBI
     * @param token = jwtToken
     * @param cbuNumber = Numero de CBU / CVU
     * @param accountType = CBU / CVU
     * @param ownerName = Nombre del dueño de la cuenta
     * @param bankName = Nombre del banco
     * @param cuit = Numero de CUIT
     * @return DTO con datos del contacto bancario
     * @throws Exception
     */
    public CreateBankingContact createBankingContact(String token, String cbuNumber, String accountType, String ownerName, String bankName, String cuit) throws Exception {
        try {

            //1. Obtener el endpoint
            RestAssured.baseURI = getEnvUrl(CONTACTS_ENDPOINT);

            //2. Hacer wipe a los parametros
            super.wipeRequestParams();

            //3. Construir los parametros a enviar en el request
            requestParams.put("banking_key", cbuNumber);
            requestParams.put("banking_key_type", accountType);
            requestParams.put("owner_name", ownerName);
            requestParams.put("bank_name", bankName);
            requestParams.put("cuit", cuit);


            //4. Hacer el request
            CreateBankingContact bankingContact =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                    when().
                            post(EndPointsPath.BANKING.getText()).
                    then().
                            statusCode(201).
                    extract().as(CreateBankingContact.class);

            //5. Devolver el response en formato DTO
            return bankingContact;

        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * API call para agregarle un contacto bancario al usuario NUBI por defecto con datos de un usuario por defecto.
     * @return DTO con datos del contacto bancario
     * @throws Exception
     */
    public CreateBankingContact createBankingContact() throws Exception {
        try {

            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            //1. Obtener el endpoint
            RestAssured.baseURI = getEnvUrl(CONTACTS_ENDPOINT);

            //2. Hacer wipe a los parametros
            super.wipeRequestParams();

            //3. Construir los parametros a enviar en el request
            requestParams.put("banking_key", "2990000000011335410019");
            requestParams.put("banking_key_type", "CBU");
            requestParams.put("owner_name", "FLORES LUIS MIGUEL");
            requestParams.put("bank_name", "BANCO COMAFI");
            requestParams.put("cuit", "20110452609");


            //4. Hacer el request
            CreateBankingContact bankingContact =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.BANKING.getText()).
                            then().
                            statusCode(201).
                            extract().as(CreateBankingContact.class);

            //5. Devolver el response en formato DTO
            return bankingContact;

        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * API call para agregarle un contacto bancario al usuario NUBI pasado en el token con datos de un usuario por defecto.
     * @return DTO con datos del contacto bancario
     * @throws Exception
     */
    public CreateBankingContact createBankingContact(String token) throws Exception {
        try {

            //1. Obtener el endpoint
            RestAssured.baseURI = getEnvUrl(CONTACTS_ENDPOINT);

            //2. Hacer wipe a los parametros
            super.wipeRequestParams();

            //3. Construir los parametros a enviar en el request
            requestParams.put("banking_key", "2990000000011335410019");
            requestParams.put("banking_key_type", "CBU");
            requestParams.put("owner_name", "FLORES LUIS MIGUEL");
            requestParams.put("bank_name", "BANCO COMAFI");
            requestParams.put("cuit", "20110452609");


            //4. Hacer el request
            CreateBankingContact bankingContact =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + token).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.BANKING.getText()).
                            then().
                            statusCode(201).
                            extract().as(CreateBankingContact.class);

            //5. Devolver el response en formato DTO
            return bankingContact;

        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * API call para agregarle un contacto bancario al usuario NUBI pasado en el token con datos de un usuario por defecto.
     * @return DTO con datos del contacto bancario
     * @throws Exception
     */
    public void contactsSync(User user) throws Exception {
        try {
            HashMap<String, JSONObject> map = new HashMap<>();
            JSONArray array = new JSONArray();
            JSONObject object = new JSONObject();

            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            //1. Obtener el endpoint
            RestAssured.baseURI = getEnvUrl(CONTACTS_ENDPOINT);

            //2. Hacer wipe a los parametros
            super.wipeRequestParams();

            //Fill json array
            object.put("phone_number", user.getPhoneNumber());
            object.put("phone_name", user.getName() + " " + user.getLastName());

            array.add(0, object);

            //map.put("phone_contacts", object);


            //3. Construir los parametros a enviar en el request
            requestParams.put("phone_contacts", array);



            //4. Hacer el request
            response =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            request().body(requestParams).
                            when().
                            patch(EndPointsPath.CONTACTS_SYNC.getText()).
                            then().
                            statusCode(200).
                            extract().response();

            //5. Devolver el response en formato DTO
            //return contactsSync;

        } catch (Exception | Error err) {
            err.printStackTrace();
            throw new Exception();
        }
    }

}
