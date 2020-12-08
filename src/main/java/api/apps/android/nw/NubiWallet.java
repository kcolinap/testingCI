package api.apps.android.nw;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.model.Account;
import api.apps.android.nw.model.PrepaidCard;
import api.apps.android.nw.model.Sube;
import api.apps.android.nw.model.transactions.Transaction;
import api.apps.android.nw.model.User;
import api.interfaces.Application;
import core.Util;
import core.commons.MongoQuery;
import core.commons.apicall.Auth_API;
import core.commons.apicall.dtos.auth.POSTPasswordRecover;
import core.commons.apicall.dtos.auth.PatchPasswordRecover;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class NubiWallet implements Application {

    public static Util util = Apps.util;

    //public Movimientos movements = new Movimientos();
    //public LastMovements lastMovements = new LastMovements();
   // public PagoServicios pagoServicios = new PagoServicios();


    public NubiWalletUiObjects uiObjects = new NubiWalletUiObjects();

    public List<User> usuarios = new ArrayList<>();

    public static User usuario = null;

    public static String environment = Util.TEST;
    public static String mode = Util.LOCAL;

    public static int INICIATED_APP = 0;


    @Override
    public void forceStop() {
        Android.adb.forceStopApp(packageID());
    }

    @Override
    public void clearData() {
       // Android.adb.clearAppsData(packageID());
        Android.driver.resetApp();
    }

    @Override
    public Object open() {
        switch (mode) {

                //Android.driver.launchApp();
               // break;
            case Util.LOCAL:
            case Util.BROWSERSTACK:
            case Util.KOBITON:
            case Util.SOUCELABS:
                Android.driver.launchApp();
                break;
        }
        return null;
    }

    @Override
    public String packageID() {
        switch (environment) {
            case Util.DEV:
                return "com.tunubi.wallet.dev";
            case Util.STAGE:
                return "com.tunubi.wallet";
            case Util.TEST:
            case Util.STAGING:
            default:
                return "com.tunubi.wallet.qa";
        }
    }

    @Override
    public void grantContactPermissions() {
        Android.adb.grantContactPermission(packageID());
    }

    @Override
    public void revokeContactPermissions() {
        Android.adb.revokeContactPermission(packageID());
    }

    @Override
    public void grantCameraPermissions() {
        Android.adb.grantCameraPermission(packageID());
    }

    @Override
    public void revokeCameraPermissions() {

        Android.adb.revokeCameraPermission(packageID());
    }

    /*****
     * RESET APP
     */
    @Override
    public void resetApp() {
        try {
            Android.driver.resetApp();
        } catch (Exception e) {
            throw e;
        }
    }

    /*****
     * CLOSE APP
     */
    @Override
    public void closeApp() {

        ((AndroidDriver)Android.driver).closeApp();

    }

    // -------------------------------  METODOS PARA MODELO DE USUARIO ----------------------------
    public void setUserData(User usr) {
        usuario = usr;
    }

    public void loadUsuarios(int cantUsuarios) throws Exception {
        usuarios = util.obtenerUsuarios(cantUsuarios);
    }

    public User getNubiUser() throws Exception {
        Optional<User> optional = usuarios
                .stream()
                .filter(m -> m.getNubiContacts() != null && m.getNubiContacts().size() > 0)
                .findAny();

        if (optional.isPresent())
            return optional.get();

        //si no hay ningun usuario precargado con contacto nubi, retorno cualquiera de ellos
        return Apps.util.obtenerUsuario("ANY");

    }

    public User getBankUser() throws Exception {
        Optional<User> usuario = usuarios
                .stream()
                .filter(u -> !u.getUsername().contentEquals(""))
                .filter(u -> u.getBankContacts() != null && u.getBankContacts().size() > 0)
                .findAny();

        if (usuario.isPresent())
            return usuario.get();

        // Pedir por un usuario con contacto bancario.
        MongoQuery mongo = new MongoQuery();
        return mongo.getUserWithContacts(3);

    }

    public User getNubiBankUser() throws Exception {
        Optional<User> usuario = usuarios
                .stream()
                .filter(u -> !u.getUsername().contentEquals(""))
                .filter(u -> u.getNubiBankContacts() != null && u.getNubiBankContacts().size() > 0)
                .findAny();

        if (usuario.isPresent())
            return usuario.get();


        // Pedir por un usuario con contacto nubi y bancario.
        MongoQuery mongo = new MongoQuery();
        return mongo.getUserWithContacts(2);

    }



    public User getUserWithCVU() {
        for (User user : usuarios) {
            for (Account account : user.getAcounts()) {
                if (account != null && !account.getCvu().isEmpty()) {
                    return user;
                }
            }
        }

        // Generar un usuario BANCARIO en este punto, agregarlo a la lista y retornarlo.
        return null;
    }

    public User getUserWithNOCVUBancario() throws IOException {
        for (User user : usuarios) {
            for (Account account : user.getAcounts()) {
                if (account != null && account.getCvu() != null && account.getCvu().isEmpty()) {
                    if (user.getBankContacts().size() > 0) {
                        return user;
                    }
                }
            }
        }

        // Generar un usuario BANCARIO sin CVU en este punto, agregarlo a la lista y retornarlo.
        return null;
    }

    public User getUserWithNOCVUNubi() throws IOException {
        for (User user : usuarios) {
            for (Account account : user.getAcounts()) {
                if (account != null && account.getCvu() != null && account.getCvu().isEmpty()) {
                    if (user.getNubiBankContacts().size() > 0) {
                        return user;
                    }
                }
            }
        }

        // Generar un usuario BANCARIO sin CVU en este punto, agregarlo a la lista y retornarlo.
        return null;
    }

    public User getUserWithoutSube() throws Exception {
        List<Sube> subeList;
        for (User user : usuarios) {
            user.setSubeList(Apps.dbQuery.getSubeFromUser(user.getId()));
            subeList = user.getSubeList();
            if (subeList.size() == 0) {
                for (Account account : user.getAcounts()) {
                    if (account != null && account.getBalance() > 0) {
                        return user;
                    }
                }
            }
        }

        User user = util.getUserWithoutSubeCard();
        if (user != null)
            return user;

        // Generar un usuario SIN sube, agregarlo a la lista y retornarlo.
        return null;
    }

    public User getUserWithSube() throws Exception {

        List<Sube> subeList;

        for (User user : usuarios) {
            user.setSubeList(Apps.dbQuery.getSubeFromUser(user.getId()));
            subeList = user.getSubeList();
            if (subeList.size() != 0)
               return user;

        }

        User user = util.getUserWithSubeCard();
        if (user != null)
            return user;

        // Generar un usuario SIN sube, agregarlo a la lista y retornarlo.
        return null;
    }

    public User getLoanUser(){
        Optional<User> usuario = usuarios
                .stream()
                .filter(u -> !u.getUsername().contentEquals(""))
                .filter(User::getLendingCandidate)
                .findAny();

        return usuario.orElse(null);
    }

    /**
     * Metodo para añadir los usuarios que califican para prestamos a la lista de usuarios
     * IMPORTANTE: los mismos al 29/06/2020 estan hardcodeados y siempre son los mismos (nubi49 y betotest001)
     * eventualmente se incorporará el sistema de puntaje y habrá que refactorizar este metodo.
     * @throws Exception
     */
    public void addLoanUsers() throws Exception {
        User loanUser1 = util.obtenerUsuarioById("9c99c79e-fb54-412d-8a01-b7bc564fca70"); //nubi49
        User loanUser2 = util.obtenerUsuarioById("5b66ab87-ff34-4d48-9008-b1e403ce5a3d"); //betotest001
        usuarios.add(loanUser1);
        usuarios.add(loanUser2);
    }

    public static Predicate<Account> hasPrepaidCard(String kind, String status) {
        return a -> a != null && a.getPrepaidCard() != null && a.getPrepaidCard().getKind().equals(kind) && a.getPrepaidCard().getStatus().equals(status);
    }

    public static Predicate<Account> noPrepaidCard() {
        return a -> a != null && a.getPrepaidCard() == null;
    }

    public static User filterUsersPrepaidCard(List<User> usuarios, Predicate<Account> predicate) {

        Optional<User> optional = usuarios
                .stream()
                .filter(u -> {
                    try {
                        if (u.getAcounts() != null) {
                            Optional<Account> account = u.getAcounts()
                                    .stream()
                                    .filter(predicate)
                                    .findAny();
                            if (account.isPresent())
                                return true;
                            else
                                return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                    return false;
                })
                .findAny();

        if (optional.isPresent())
            return optional.get();

        return null;
    }

    public User getUserWithoutPrepaidCard() throws Exception {
        User userToReturn = util.getPrefixedUserWithoutPrepaidCard();
        if (userToReturn != null)
            if (userToReturn.getEmail().contains(util.getEmailPrefixWithoutPercent()))
                usuarios.add(userToReturn);
        // Generar usuario con tarjeta prepaga con ese KIND y STATUS que vienen en este punto, agregarlo a la lista de usuarios.
        return userToReturn;
    }

    public User getUserWithPrepaidCard(String kind, String status) throws Exception {
        User userToReturn = filterUsersPrepaidCard(usuarios, hasPrepaidCard(kind, status));
        if (userToReturn == null) {
            // Buscar uno en la BD
            PrepaidCard prepaidCard = util.getPrefixedPrepaidCard(kind, status);
            if (prepaidCard != null) {
                userToReturn = util.obtenerUsuarioById(prepaidCard.getUserId());
                if (userToReturn != null)
                    usuarios.add(userToReturn);
                    /*if (userToReturn.getEmail().contains(util.getEmailPrefixWithoutPercent())) {
                        usuarios.add(userToReturn);
                    }else{
                        //CHange the password in order to let login
                        Auth_API auth_api;
                        POSTPasswordRecover dtoPasswordRecover;
                        PatchPasswordRecover dtoPatch;

                        try{
                           Apps.util.ChangePassword(userToReturn);

                           usuarios.add(userToReturn);

                        }catch (AssertionError e){

                        }
                    }*/
            }
        }

        // Generar usuario con tarjeta prepaga con ese KIND y STATUS que vienen en este punto, agregarlo a la lista de usuarios.
        return userToReturn;
    }

    public Transaction getTransaction(String listType, String transactionType) throws Exception {
        MongoQuery mongoQuery = new MongoQuery();

        List<Transaction> listaTransacciones = mongoQuery.getTransactionsList(listType, usuario.getId());

        Optional<Transaction> transaction = listaTransacciones
                .stream()
                .filter(m -> m.getTransactionId() != null && m.getTransactionType().contentEquals(transactionType))
                .findAny();

        if (transaction.isPresent())
            return transaction.get();

        return null;
    }

}
