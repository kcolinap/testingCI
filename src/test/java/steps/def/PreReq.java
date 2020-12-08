package steps.def;

import api.apps.Apps;
import com.github.javafaker.App;
import core.commons.apicall.DNI_Validation_API;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class PreReq {

    private static final Logger logger = LogManager.getLogger();

    @Given("Find user type {string} and set preReq {string}")
    public void set_preReq(String kindUser, String req) throws Exception {
        try {

            //Find user
            Login.usuario = Apps.util.obtenerUsuario(kindUser);

            if(kindUser.toUpperCase().contentEquals("ORIGIN_USER_P2P_NUBI_SENDING") ||
                    kindUser.toUpperCase().contentEquals("ORIGIN_USER_P2P_BANK_SENDING") || kindUser.toUpperCase().contentEquals("ORIGIN_USER_P2P_NUBI_REQUEST")) {
                CommonStepsMovements.originUser = Login.usuario;

            }

            //Make sure login is not blocked
            Apps.dbQuery.update_has_invalidated_field(Login.usuario.getUsername(), false, 0);

            //If user returned not contains the specific prefix,
            //we change the password throught BE
            if (!(Login.usuario.getEmail().contains(Apps.util.getEmailPrefixWithoutPercent()))) {

                //Change password
                Apps.util.ChangePassword(Login.usuario);

                //Chage nubi password
                Apps.util.ChangeNubiPassword(Login.usuario);
            }

            //Set PReqReq
            /**PREREQUISITE VALUES:
             * IV: IDENTITY VALIDATION: make a request to validate dni
             */
            String[] arrayPre = req.split(",");
            for(String string : arrayPre){
                switch (string.toUpperCase()){
                    case "IV":
                        DNI_Validation_API dniApi = new DNI_Validation_API();
                        dniApi.manual_validation(Login.usuario);
                        break;
                }
            }

        }catch (Exception | Error e){
            logger.error(e);
            throw e;
        }
    }
}
