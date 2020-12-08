package api.interfaces;

import core.Util;

public interface Application {

    //Apps app = new Apps();

    void forceStop();

    void clearData();

    void grantContactPermissions();

    void revokeContactPermissions();

    void grantCameraPermissions();

    void revokeCameraPermissions();

    void resetApp();

    void closeApp();

    Object open();

    String packageID();



}
