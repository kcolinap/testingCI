package core;

import core.commons.MongoQuery;
import core.managers.ServerManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class ADB {

    private String ID;
    private static String outputCommand = null;

    public ADB(String deviceID) {
        ID = deviceID;
    }

    public static String command(String command) {
        //MyLogger.log.debug("Formatting ADB Command: "+command);
        if (command.startsWith("adb"))
            command = command.replace("adb ", ServerManager.getAndroidHome() + "/platform-tools/adb ");
        else throw new RuntimeException("This method is designed to run ADB commands only!");
        //MyLogger.log.debug("Formatted ADB Command: "+command);
        String output = ServerManager.runCommand(command);
        outputCommand = output;
        //MyLogger.log.debug("Output of the ADB Command: "+output);
        if (output == null) return "";
        else return output.trim();
    }

    public void openAppsActivity(String packageID) {
        String commandString = "adb -s " + ID + " shell monkey -p " + packageID + " -c android.intent.category.LAUNCHER 1";
        command(commandString);
    }

    public static ArrayList getConnectedDevices() {
        ArrayList devices = new ArrayList();
        String output = command("adb devices");
        for (String line : output.split("\n")) {
            line = line.trim();
            if (line.endsWith("device")) devices.add(line.replace("device", "").trim());
        }
        return devices;
    }

    public ArrayList getInstalledPackages() {
        ArrayList packages = new ArrayList();
        String[] output = command("adb -s " + ID + " shell pm list packages").split("\n");
        for (String packageID : output)
            packages.add(packageID.replace("package:", "").trim());
        return packages;
    }

    public void uninstallApp(String packageID) {

        command("adb -s " + ID + " uninstall " + packageID);
    }

    public void forceStopApp(String packageID) {

        //command("adb -s "+ID+" shell am force-stop "+packageID);
        command("adb shell killall -9 " + packageID);
    }

    public void clearAppsData(String packageID) {
        command("adb shell pm clear " + packageID);
    }

    public void grantContactPermission(String packageID) {

        command("adb -s " + ID + " shell pm grant " + packageID + " android.permission.READ_CONTACTS");

    }

    public void revokeContactPermission(String packageID) {

        command("adb -s " + ID + " shell pm revoke " + packageID + " android.permission.READ_CONTACTS");

    }

    public void grantCameraPermission(String packageID) {

        command("adb -s " + ID + " shell pm grant " + packageID + " android.permission.CAMERA");

    }

    public void revokeCameraPermission(String packageID) {

        command("adb -s " + ID + " shell pm revoke " + packageID + " android.permission.CAMERA");

    }

    public String getAndroidVersionAsString() {
        String output = command("adb -s " + ID + " shell getprop ro.build.version.release");
        //if(output.length() == 3) output+=".0";
        return output;
    }

    public void setTexto(String texto) {
        command("adb -s " + ID + " shell input text " + texto);
        //Android.driver.hideKeyboard();
    }

    public void setMonto(Double monto) {
        command("adb -s " + ID + " shell input text " + monto);
        //Android.driver.hideKeyboard();
    }

    // Add Contact using ADB command
    public void addContact(String phoneNumber, String name, String lastname) {

        Pattern pattern = Pattern.compile(" _id=(.*), m");
        Matcher matcher;


        //Wipe contacts list
        //delete_phone_contacts();

        //Create a generic contact
        command("adb shell content insert --uri content://com.android.contacts/raw_contacts --bind account_type:s:vnd.sec.contact.phone --bind account_name:s:vnd.sec.contact.phone");

        //Command to get the contact id
        command("adb shell content query --uri content://com.android.contacts/raw_contacts");
        matcher = pattern.matcher(outputCommand);
        matcher.find();
        String id = matcher.group().substring(5,7);
        if(id.contains(","))
            id = id.substring(0,1);


        //Add contact name
        command("adb shell content insert --uri content://com.android.contacts/data --bind raw_contact_id:i:"+id+" --bind mimetype:s:vnd.android.cursor.item/name --bind data2:s:"+name+" --bind mimetype:s:vnd.android.cursor.item/name --bind data3:s:"+lastname);

        //Add last name
        //command("adb shell content insert --uri content://com.android.contacts/data --bind raw_contact_id:i:"+id+" --bind mimetype:s:vnd.android.cursor.item/name --bind data2:s:"+lastname);
        //Add contact phone
        command("adb shell content insert --uri content://com.android.contacts/data --bind raw_contact_id:i:"+id+" --bind mimetype:s:vnd.android.cursor.item/phone_v2 --bind data1:s:"+phoneNumber+" --bind mimetype:s:vnd.android.cursor.item/phone_v2 --bind data2:i:2");

    }

    public void delete_phone_contacts(){
        command("adb shell pm clear com.android.providers.contacts");
    }

    public int getAndroidVersion() {
        return Integer.parseInt(getAndroidVersionAsString().replaceAll("\\.", ""));
    }

}
