package core;

import api.apps.Apps;
import api.apps.android.Android;
import api.apps.android.nw.model.Account;
import api.apps.android.nw.model.User;
import core.commons.DBQuery;
import core.commons.apicall.Registration_API;
import core.commons.apicall.dtos.registration.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static api.apps.android.nw.NubiWallet.util;

public class UserGenerator {

    private static final Logger logger = LogManager.getLogger();

    private int prefix;
    private int gender;
    private String email;

    private Registration_API registration_api = new Registration_API();
    private DBQuery dbQuery = new DBQuery();
    private RegistrationConfirmed registrationConfirmed;
    private EmailRegistration emailRegistration;
    private CUILReservation cuilReservation;
    private PhoneRegistration phoneRegistration;
    private CompleteRegistration completeRegistration;

    private List<CompleteRegistration> createdUsers = new ArrayList<>();

    public List<CompleteRegistration> getCreatedUsers() {
        return createdUsers;
    }

    public void createNewUser(int cantUsers, String gender) throws Exception {

        try {

            switch (gender.toLowerCase()) {
                case "masculino":
                    prefix = 20;
                    this.gender = 0;
                    break;
                case "femenino":
                    prefix = 27;
                    this.gender = 1;
                    break;
            }

            for (int i = 1; i <= cantUsers; i++) {

                //1. Se genera un email y se confirma mediante un token.
                emailRegistrationAndConfirmation();
                //2. Se valida si se hizo efectivo la confirmación de email
                confirmRegistrationProcess();

                if (registrationConfirmed.getEmail_confirmed()) {

                    //3. Se reserva el numero de CUIL del nuevo usuario
                    cuilReservation();
                    //4. Se registra el username del nuevo usuario
                    usernameRegistration();
                    //5. Se registra el numero de telefono del nuevo usuario
                    phoneRegistration();
                    //6. Se confirma el numero de telefono ingresando el codigo sms
                    phoneConfirmation();
                    //7. Se registra el pin del nuevo usuario
                    pinRegistration();
                    //8. Se completa el registro enviando nombre, apellido, sexo y dni
                    completeRegistration();

                    String password = Apps.util.getProperty("credentials", "user.password", "properties");
                    List<Account> userAccounts = Apps.dbQuery.getAccountFromUser(completeRegistration.getUser_data().getId(),
                            completeRegistration.getUser_data().getEmail(),
                            password,
                            "", "");
                    logger.info("USUARIO DE FILA -> " + i + ": "
                            + "\r\nUsername: " + completeRegistration.getUser_data().getUsername()
                            + "\r\nPhoneNumber: " + completeRegistration.getUser_data().getPhone_number()
                            + "\r\nUserId: " + completeRegistration.getUser_data().getId()
                            + "\r\nEmail: " + completeRegistration.getUser_data().getEmail()
                            + "\r\nName: " + completeRegistration.getUser_data().getName()
                            + "\r\nLast Name: " + completeRegistration.getUser_data().getLast_name()
                            + "\r\nCuil: " + completeRegistration.getUser_data().getCuil()
                            + "\r\nAccount Number: " + userAccounts.get(0).getNumber()
                            + "\r\nGender: " + gender.toLowerCase()
                            + "\r\n\n");

                    //logger.info("Usuario " + i + ": " + completeRegistration.getUser_data().getUsername() + " " + completeRegistration.getUser_data().getPhone_number());
                    //logger.info("Account: " + Apps.dbQuery.getAccountFromUser(com).get(0).toString());

                } else {
                    logger.info("No se pudo confirmar el proceso de registro");
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

    }

    public void emailRegistrationAndConfirmation() throws Exception {

        email = util.gerenarEmail();
        emailRegistration = registration_api.emailRegistration(email);
        registration_api.confirm_email(dbQuery.getAccesToken(email));
    }

    public int calcularSoloPrefijo(int gender, String dni) throws SQLException {
        return util.calcularSoloPrefijo(gender,dni);
    }

    public void confirmRegistrationProcess() throws Exception {
        registrationConfirmed = registration_api.registrationConfirmed(emailRegistration.getId_token());
    }

    private void cuilReservation() throws Exception {
        String cuil = util.calcularCuil(this.gender, prefix, "cuil");
        cuilReservation = registration_api.reserveAndValidateACuil(registrationConfirmed.getId_token(), cuil);
    }

    public void usernameRegistration() throws Exception {
        String username = util.generateAnUsernameFromAnEmail(email);
        registration_api.usernameReservation
                (registrationConfirmed.getId_token(), username, util.getProperty("credentials", "user.password", "properties"));
    }

    private void phoneRegistration() throws Exception {
        String phoneNumber;

            phoneNumber = Apps.util.generateAPhoneNumber();


        phoneRegistration = registration_api.phoneRegistration(registrationConfirmed.getId_token(), phoneNumber);
    }

    public void phoneConfirmation() throws Exception {
        String smsCode = dbQuery.getCodigoSms(phoneRegistration.getPhone_number());
        registration_api.phoneConfirmation(registrationConfirmed.getId_token(), phoneRegistration.getPhone_number(), smsCode);
    }

    public void pinRegistration() throws Exception {
        String pin =
                util.getProperty("credentials", "pin1", "properties") +
                        util.getProperty("credentials", "pin2", "properties") +
                        util.getProperty("credentials", "pin3", "properties") +
                        util.getProperty("credentials", "pin4", "properties");

        registration_api.pinRegistration(registrationConfirmed.getId_token(), pin);
    }

    private void completeRegistration() throws Exception {

        Random rand = new Random();

        String name = "Android";
        String lastName = "UITest";
        String dni = turnCuilIntoDni();
        String sex_type;

        switch (this.gender) {
            default:
            case 0:
                sex_type = "M";
                break;
            case 1:
                sex_type = "F";
                break;
        }

        completeRegistration =
                registration_api.completeRegistration(registrationConfirmed.getId_token(), name, lastName, sex_type, dni);

        createdUsers.add(completeRegistration);
    }

    private String turnCuilIntoDni() {
        return cuilReservation.getCuil().substring(2, cuilReservation.getCuil().length() - 1);
    }


    public void addNewUsersToDevice(CompleteRegistration contactData){
        ADB adb = Android.adb;

        String phoneNumber = contactData.getUser_data().getPhone_number();
        String name = contactData.getUser_data().getName();
        String lastName = contactData.getUser_data().getLast_name();

        adb.addContact(phoneNumber, name, lastName);

    }

    public void addNewUsersToDevice(User contactData){
        ADB adb = Android.adb;

        String phoneNumber = contactData.getPhoneNumber();
        String name = contactData.getName();
        String lastName = contactData.getLastName();

        adb.addContact(phoneNumber, name, lastName);

    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getPrefix() {
        return this.prefix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return this.gender;
    }

    public RegistrationConfirmed getRegistrationConfirmed() {
        return registrationConfirmed;
    }

    public CompleteRegistration getCompleteRegistration() {
        return completeRegistration;
    }

    public void cuilReservation(String cuil) throws Exception {
        cuilReservation = registration_api.reserveAndValidateACuil(registrationConfirmed.getId_token(), cuil);
    }

    public void phoneRegistration(String phoneNumber) throws Exception {
        phoneRegistration = registration_api.phoneRegistration(registrationConfirmed.getId_token(), phoneNumber);
    }

    public void usernameRegistration(String username, String password) throws Exception {
        registration_api.usernameReservation
                (registrationConfirmed.getId_token(), username, password);
    }

    public void completeRegistration(String firstName, String lastName, String dni) throws Exception {
        String sex_type;
        switch (this.gender) {
            default:
            case 0:
                sex_type = "M";
                break;
            case 1:
                sex_type = "F";
                break;
        }

        completeRegistration =
                registration_api.completeRegistration(registrationConfirmed.getId_token(), firstName, lastName, sex_type, dni);

        createdUsers.add(completeRegistration);
    }

    public void emailRegistrationAndConfirmation(String email) throws Exception {
        emailRegistration = registration_api.emailRegistration(email);
        registration_api.confirm_email(dbQuery.getAccesToken(email));
    }

}
