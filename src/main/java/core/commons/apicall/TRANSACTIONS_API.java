package core.commons.apicall;

import api.apps.android.nw.EndPointsPath;
import core.commons.apicall.dtos.transactions.ExpiredTransactions;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class TRANSACTIONS_API extends Auth_API {

    private static final String BFF_ENDPOINT = "bff";

    public ExpiredTransactions changeTransactionsOutdatedToExpired() throws Exception {
        try {

            // Obtener el EndPoint
            RestAssured.baseURI = getEnvUrl(BFF_ENDPOINT);

            // Enviar el request y alojar el response en el DTO

            ExpiredTransactions obtainTransactionsExpired =
                    given().
                            header("Content-type", EndPointsPath.CONTENT_TYPE.getText()).
                            when().
                            patch(EndPointsPath.EXPIRE_OUTDATED.getText()).
                            then().
                            statusCode(200).
                            extract().as(ExpiredTransactions.class);

            return obtainTransactionsExpired;

        } catch (Exception | Error e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
