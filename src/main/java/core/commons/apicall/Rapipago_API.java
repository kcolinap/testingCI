package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import core.commons.apicall.dtos.auth.AuthToken;
import core.commons.apicall.dtos.auth.PinValidate;
import core.commons.apicall.dtos.rapipago.CellphoneRecharge;
import core.commons.apicall.dtos.rapipago.CodeAndNameCompany;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.Comparator;

import static io.restassured.RestAssured.given;

@SuppressWarnings("unchecked")
public class Rapipago_API extends Auth_API {

    private static final String BFF = "bff";

    private static String
        company_code_claro = "2222",
        modality_id = "34367264113500000124";


    public CellphoneRecharge cellphone_recharge(String number, String company, String amount) throws Exception {
        try {

            //Setear authToken
            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            //set the pin token
            PinValidate pinValidate = super.setPinToken();
            pin_token = pinValidate.getPin_token();

            RestAssured.baseURI = getEnvUrl(BFF);
            String code;

            // Hacer clean a los Request Params
            super.wipeRequestParams();


            switch (company.toUpperCase()){
                case "CLARO":
                    requestParams.put("company_code", company_code_claro);
                    break;
            }

            requestParams.put("amount", Integer.parseInt(amount));
            requestParams.put("modality_id", modality_id);
            requestParams.put("recharge_number", number);

            // Enviar el request y alojar el response en el DTO

            CellphoneRecharge responseData =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            header("Authorization", "Bearer " + auth_token).
                            header("Pin", pin_token).
                            request().body(requestParams).
                    when().
                            post(EndPointsPath.CELLPHONE_RECHARGE.getText()).
                    then().
                            statusCode(201).
                            extract().as(CellphoneRecharge.class);

            return responseData;

        } catch (Exception | Error e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public CodeAndNameCompany[] getAvailableCompanies() throws Exception {

        try{

            //Setear authToken
            AuthToken authToken = super.setAuthToken();
            auth_token = authToken.getAccess_token();

            RestAssured.baseURI = getEnvUrl(BFF);

            Response response = given().
                    header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                    header("Authorization", "Bearer " + auth_token).
                    when().
                    get(EndPointsPath.AVAILABLE_COMPANIES.getText());

            CodeAndNameCompany[] codeAndNmaeCompanyList = response.jsonPath().getObject("", CodeAndNameCompany[].class);

            Arrays.sort(codeAndNmaeCompanyList, Comparator.comparing(CodeAndNameCompany::getCompany_description));

            return codeAndNmaeCompanyList;

        }
        catch (Exception | Error e){
            e.printStackTrace();
            throw new Exception();
        }
    }

}
