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

    public static final String DESCRIPTION = "Description";

    public static final String PROPERTY = "Property";

    public static final String PROPERTY_INFO = "Property info";

    public static final String PROPERTY_TYPE = "Property type";

    public static final String IDENTIFIER_SOURCE = "Identifier source";

    public static final String PROPERTY_IDENTIFIER = "Property Identifier";

    public static final String MEDIA = "Media";

    public static final String PRODUCT = "Product";

    public static final String BASKET = "Basket";

    public static final String SELLABLE_UNIT = "Sellable Unit";

    public static final String BRAND = "Brand";

    public static final String RATE_PLAN = "Rate Plan";

    public static final String SELLABLE_UNIT_TYPE = "Sellable Unit Type";

    public static final String PHONE_TYPE = "Phone type";

    public static final String EMAIL_TYPE = "Email type";

    public static final String PRICING_MODEL = "Pricing model";

    public static final String PRICING_MODEL_TYPE = "Pricing model type";

    public static final String PRICING_MODEL_STANDARD = "Standard pricing model";

    public static final String PRICING_MODEL_DERIVED = "Derived pricing model";

    public static final String PRICING_MODEL_OCCUPANCY_BASED = "Occupancy based pricing model";
    
    public static final String PRICING_MODEL_LENGTH_OF_STAY = "Length of stay pricing model";

    public static final String STATE = "State";

    public static final String COUNTRY = "Country";

    public static final String GEO_CODE = "Geo Code";

    public static final String POINT_OF_INTEREST = "Point Of Interest";

    public static final String CATEGORY_CODE = "Category Code";

    public static final String PREDEFINED_TAG = "Predefined Tag";

    public static final String MEDIA_TYPE = "Media Type";

    public static final String MEDIA_ATTRIBUTE_TYPE = "Media Attribute Type";

    public static final String DESCRIPTION_TYPE = "Description Type";

    public static final String NAME_TYPE = "Name Type";

    public static final String LICENSE_TYPE = "License Type";

    public static final String CURRENCY = "Currency";

    public static final String SUPPLIER = "Supplier";

    public static final String NOTIFICATION = "Notification";

    /*
     * Field names
     */
    public static final String ID = "id";

    public static final String PROPERTY_ID = "propertyId";

    public static final String CULTSWITCH_ID = "CultSwitchId";

    public static final String SUPPLIER_UNIT_ID = "SupplierUnitId";

    public static final String CODE = "code";

    public static final String ABBREVIATION = "abbreviation";

    public static final String NAME = "name";

    public static final String SOURCE_ID = "sourceId";

    /*
     * Defaults
     */
    public static final String DEFAULT_LANGUAGE_CODE = "en";

    public static final String DEFAULT_LOGO_MEDIA_TYPE_CODE = "img";

    public static final String DEFAULT_MEDIA_DESCRIPTION_TYPE_CODE = "med";

    public static final String DEFAULT_POI_DESCRIPTION_TYPE_CODE = "poi";

    public static final String DEFAULT_SELLABLE_UNIT_DESCRIPTION_TYPE_CODE = "su";

    public static final String DEFAULT_PROPERTY_DESCRIPTION_TYPE_CODE = "prpt";

    public static final String DEFAULT_BASKET_DESCRIPTION_TYPE_CODE = "bsk";

    public static final String DEFAULT_SELLABLE_UNIT_NAME_TYPE_CODE = "su";

    public static final String ROOM_DB_IDENTIFIER_SOURCE_ABBR = "rdb";

    public static final String CULT_SWITCH_IDENTIFIER_SOURCE_ABBR = "cs";

    public static final String PRICING_MODEL_CODE_STD = "std"; // Standard pricing model

    public static final String PRICING_MODEL_CODE_DRV = "drv"; // Derived pricing model

    public static final String PRICING_MODEL_CODE_OCC = "occ"; // Occupancy based pricing model

    public static final String PRICING_MODEL_CODE_LEN = "len"; // Length of stay pricing model

}
