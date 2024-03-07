package com.Ecomaxgo.maxpay.kiosk_status;

public class Config {


    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public static final String FILE_UPLOAD_URL = "http://192.168.0.104/AndroidFileUpload/fileUpload.php";
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
}
