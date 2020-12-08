package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import core.commons.apicall.dtos.dealer_api.DealerApi;
import io.restassured.RestAssured;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

@SuppressWarnings("unchecked")
public class Dealer_API extends API_Util {

    private static final String LMBMOCK = "lmbmock";


    public static String
            CASHIN_FROM_CVU = "CASHIN_FROM_CVU",
            CASHIN_FROM_CBU = "CASHIN_FROM_CBU",
            CASHOUT_AUTH_GP = "CASHOUT_AUTH_GP",
            CASHOUT_WITHDRAW_GP = "CASHOUT_WITHDRAW_GP",//Asi se pasa en el servicio
            CASHOUT_WITHDRAW_PC = "CASHOUT_WITHDRAW_PC"; // asi se guarda en BD


    public String getCashoutWithdrawGp() {
        return CASHOUT_WITHDRAW_GP;
    }

    public String getCashoutWithdrawPc() {
        return CASHOUT_WITHDRAW_PC;
    }

    public String getCashoutAuthGp() {
        return CASHOUT_AUTH_GP;
    }

    public JSONObject call_CashoutGP(String transactionType, String status, String idusuario, String withTaxes, String amount) {
        try {

            RestAssured.baseURI = getEnvUrl(LMBMOCK);

            //wipe requestParams
            super.wipeRequestParams();

            int metodo = 1;

            switch (transactionType.toUpperCase()) {
                case "CASHOUT_AUTH_GP":
                    requestParams.put("transaction_type", CASHOUT_AUTH_GP);
                    metodo = 1;
                    break;
                case "CASHOUT_WITHDRAW_GP":
                    requestParams.put("transaction_type", CASHOUT_WITHDRAW_GP);
                    metodo = 1;
                    break;

                default:
                    break;
            }

            //Objeto para almacenar el Json de PARAMS
            JSONObject params = new JSONObject();
            params.put("userFrom", "WALLETID::" + idusuario);

            // Amount should be a double with two decimals: "126.00"
            params.put("amount", amount);

            //Verificamos el status, va dentro del objeto params del bodyRequest
            //Solo aplica para el status RECHAZADO
            if (status.toUpperCase().contentEquals("RECHAZADO"))
                params.put("status", "ERROR");


            requestParams.put("params", params);

            if (withTaxes.toUpperCase().contentEquals("CON_IMPUESTO")){

                JSONObject taxes = new JSONObject();
                JSONArray arra = new JSONArray();
                    JSONObject auxTax1 =  new JSONObject();
                    JSONObject auxTax2 =  new JSONObject();
                    JSONObject auxTax3 =  new JSONObject();

                    auxTax1.put("code","tax_ar_iva");
                    auxTax1.put("amount",3.14);

                    auxTax2.put("code", "tax_ar_iibb");
                    auxTax2.put("amount", 5.14);

                    auxTax3.put("code", "tax_ar_ley27541");
                    auxTax3.put("amount", 6.14);
                arra.add(0,auxTax1);
                arra.add(1, auxTax2);
                arra.add(2, auxTax3);

                requestParams.put("taxes", arra);

            }

            //Body
            switch (metodo) {
                case 1:
                    response =
                            given().
                                    header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                                    request().body(requestParams).
                                    when().
                                    post(getPathUrl(status)).
                                    then().
                                    //statusCode(200).
                                            extract().response();
                    break;
                case 2:
                    response =
                            given().
                                    header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                                    request().body(requestParams).
                                    when().
                                    get(getPathUrl(status)).
                                    then().
                                    extract().response();
                    break;
            }
            // de additionalData -> fees [ amount : "LO Q HAY Q SUMARLE AL MONTO."]
            return (JSONObject) parser.parse(response.getBody().asString());

            // System.out.print(response.getBody().asString());

        } catch (Exception | Error err) {
            err.printStackTrace();
        }
        return null;
    }

    public DealerApi call_CashoutGP_Suscriptions(String transactionType, String storeName, String idusuario, String withTaxes, String amount) {
        try {

            RestAssured.baseURI = getEnvUrl(LMBMOCK);

            //wipe requestParams
            super.wipeRequestParams();

            int metodo = 1;

            switch (transactionType.toUpperCase()) {
                case "CASHOUT_AUTH_GP":
                    requestParams.put("transaction_type", CASHOUT_AUTH_GP);
                    metodo = 1;
                    break;
                case "CASHOUT_WITHDRAW_GP":
                    requestParams.put("transaction_type", CASHOUT_WITHDRAW_GP);
                    metodo = 1;
                    break;

                default:
                    break;
            }

            //Objeto para almacenar el Json de PARAMS
            JSONObject params = new JSONObject();
            params.put("userFrom", "WALLETID::" + idusuario);

            // Amount should be a double with two decimals: "126.00"
            params.put("amount", amount);

            //Agregar el additional data
            if(!storeName.contentEquals(""))
                params.put("additionalData","{\"commerce\":{\"code\":\"12\",\"name\":\"" + storeName + "\",\"mcc\":\"10\",\"postal_code\":\"654345678\"}}");
                //params.put("additionalData","{\\\"commerce\\\":{\\\"code\\\":\\\"12\\\",\\\"name\\\":\\\"jeueueuec\\\",\\\"mcc\\\":\\\"10\\\",\\\"postal_code\\\":\\\"654345678\\\"}}");

            requestParams.put("params", params);

            if (withTaxes.toUpperCase().contentEquals("CON_IMPUESTO")){

                JSONObject taxes = new JSONObject();
                JSONArray arra = new JSONArray();
                JSONObject auxTax1 =  new JSONObject();
                JSONObject auxTax2 =  new JSONObject();
                JSONObject auxTax3 =  new JSONObject();

                auxTax1.put("code","tax_ar_iva");
                auxTax1.put("amount",3.14);

                auxTax2.put("code", "tax_ar_iibb");
                auxTax2.put("amount", 5.14);

                auxTax3.put("code", "tax_ar_ley27541");
                auxTax3.put("amount", 6.14);
                arra.add(0,auxTax1);
                arra.add(1, auxTax2);
                arra.add(2, auxTax3);

                requestParams.put("taxes", arra);

            }

            DealerApi responseData = null;
            //Body
            switch (metodo) {
                case 1:
                    responseData =
                            given().
                                    header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                                    request().body(requestParams).
                                    when().
                                    post(getPathUrl("")).
                                    then().
                                    //statusCode(200).
                                            extract().as(DealerApi.class);
                    break;
            }
            // de additionalData -> fees [ amount : "LO Q HAY Q SUMARLE AL MONTO."]
            return responseData; //(JSONObject) parser.parse(response.getBody().asString());

            // System.out.print(response.getBody().asString());

        } catch (Exception | Error err) {
            err.printStackTrace();
        }
        return null;
    }

    public JSONObject call_cashoutGP_fee(String transactionIdParent, String idusuario, String amount) {
        try {
            RestAssured.baseURI = getEnvUrl(LMBMOCK);

            //wipe requestParams
            super.wipeRequestParams();

            requestParams.put("transaction_type", CASHOUT_AUTH_GP);

            //Objeto para almacenar el Json de PARAMS
            JSONObject params = new JSONObject();
            params.put("userFrom", "WALLETID::" + idusuario);
            params.put("transactionParentId", transactionIdParent);

            // Amount should be a double with two decimals: "126.00"
            params.put("amount", amount);

            requestParams.put("params", params);

            response =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            request().body(requestParams).
                            when().
                            post(EndPointsPath.URL_CASHOUT_GP.getText()).
                            then().
                            //statusCode(200).
                                    extract().response();

            return (JSONObject) parser.parse(response.getBody().asString());

        } catch (Exception | Error err) {
            err.printStackTrace();
        }

        return null;
    }

    public String getPathUrl(String status) {

        String auxPathurl = "";

        if (!status.toUpperCase().contentEquals("CANCELADO")) {
            auxPathurl = EndPointsPath.URL_CASHOUT_GP.getText();
        } else {
            auxPathurl = EndPointsPath.URL_CASHOUT_GP_CANCEl.getText();
        }

        return auxPathurl;
    }
}
