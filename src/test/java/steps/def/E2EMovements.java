package steps.def;

import api.apps.android.Android;
import api.apps.android.nw.NubiWallet;
import io.cucumber.java.en.Then;
import org.junit.Test;

public class E2EMovements{

    private String balance;

    private static NubiWallet nubiWallet = Android.nubiWallet;
    private static String amountMoneyTo;

    public String getBalance(){

        return balance;
    }

    public void setName(String balance){
        this.balance = balance;
    }

    @Test


    /************************************************
     * *************    MOVEMENTS STEPS  ************
     ***********************************************/
    /** Bean to set/ get amount money to be send-request **/
    public void setAmountMoneyTo(String amount){

        this.amountMoneyTo = amount;
    }

    public String getAmountMoneyTo(){
        return this.amountMoneyTo;
    }

    @Then("Tap continue button")
    public void tapContinueButtonOnMovements(){
       // nubiWallet.movements.tapBtnSiguiente();
    }




}
