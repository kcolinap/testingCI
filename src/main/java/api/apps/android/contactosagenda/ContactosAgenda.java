package api.apps.android.contactosagenda;

import api.apps.android.contactosagenda.samsung.HomeSamsung;

public class ContactosAgenda  {

    //public HomeMoto homeMoto = new HomeMoto();
    public HomeSamsung homeSamsung = new HomeSamsung();

/*
    @Override
    public void forceStop() {

        try {
            Android.adb.forceStopApp(motoPackageID());
        } catch (Exception e) {
            Android.adb.forceStopApp(samsungPackageID());
        }

    }


    @Override
    public void clearData() {

        Android.adb.clearAppsData(motoPackageID());
    }

    @Override
    public String open() {

        int aux;
        Activity activity;
        String bb = "";
        do {
            try {
                activity = new Activity(motoPackageID(), activityID());
                //Android.driver.startActivity(activity);

                Android.adb.openAppsActivity(motoPackageID());
                bb = "moto";
                if (Android.driver.currentActivity().contains("nubi")) {
                    Android.adb.openAppsActivity(samsungPackageID());
                    bb = "samsung";
                }


            } catch (Exception e) {
                continue;
            }

        } while (Android.driver.currentActivity().contains("nubi"));
        return bb;
    }

    @Override
    public String packageID() {
        return null;
    }

    @Override
    public void grantContactPermissions() {
        Android.adb.grantContactPermission(motoPackageID());
    }

    @Override
    public void revokeContactPermissions() {
        Android.adb.revokeContactPermission(motoPackageID());
    }

    @Override
    public void grantCameraPermissions() {
        Android.adb.grantCameraPermission(motoPackageID());
    }

    @Override
    public void revokeCameraPermissions() {
        Android.adb.revokeCameraPermission(motoPackageID());
    }

    @Override
    public String activityID() {
        return "com.android.contacts.activities.PeopleActivity";
    }


    @Override
    public String motoPackageID() {
        return "com.android.contacts";
    }

    public String samsungPackageID() {
        return "com.samsung.android.contacts";
    }*/

}
