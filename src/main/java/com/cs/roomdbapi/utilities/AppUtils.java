package com.cs.roomdbapi.utilities;

public class AppUtils {

    public static final int RESPONSE_CODE_SUCCESS = 101;
    public static final int RESPONSE_CODE_NO_DATA = 102;
    public static final int RESPONSE_CODE_FAIL = 103;

    public static final short NOTIFICATION_SUPPLIER_STATUS_QUEUED = 0;
    public static final short NOTIFICATION_SUPPLIER_STATUS_IN_PROGRESS = 1;
    public static final short NOTIFICATION_SUPPLIER_STATUS_FINISHED = 2;
    public static final short NOTIFICATION_SUPPLIER_STATUS_FAILED = 10;
    public static final short NOTIFICATION_SUPPLIER_STATUS_FAILED_NO_URL = 11;
    public static final short NOTIFICATION_SUPPLIER_STATUS_FAILED_NO_EMAIL = 12;

    public static final String RESPONSE_CODE_SUCCESS_MSG = "Successful operation";
    public static final String RESPONSE_CODE_NO_DATA_MSG = "No data found";

    public static final String TEST_NOTIFICATION_SUCCESS = "Successful sending test notification";
    public static final String TEST_NOTIFICATION_FAIL = "Failed to sending test notification";

    public static final String FAIL = "fail";
    public static final String SUCCESS = "success";

    public static final String BEARER = "Bearer";

    /*
     * Entity names
     */
    public static final String LANGUAGE = "Language";

    public static final String SUPPLIER = "Supplier";

    public static final String NOTIFICATION = "Notification";

    /*
     * Field names
     */
    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    /*
     * Defaults
     */
    public static final String DEFAULT_LANGUAGE_CODE = "en";
}
