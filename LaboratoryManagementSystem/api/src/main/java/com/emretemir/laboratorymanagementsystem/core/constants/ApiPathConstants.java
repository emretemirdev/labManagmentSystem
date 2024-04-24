package com.emretemir.laboratorymanagementsystem.core.constants;

public final class ApiPathConstants {


    private ApiPathConstants() {
        throw new IllegalStateException("Utility class");
    }

    //REPORT CONTROLLER PATHS
    public static final String REPORT_BASE_URL = "/report";

    public static final String DELETE_REPORT = "/delete/{id}";
    public static final String GET_ALL_REPORTS = "/all";
    public static final String GET_REPORT_BY_ID = "/{id}";
    public static final String CREATE_REPORT = "/create";
    public static final String UPDATE_REPORT = "/{id}";
    public static final String SEARCH_REPORT = "/search";

    //USER CONTROLLER PATHS

    public static final String USER_BASE_URL = "/auth";
    public static final String GET_USER_INFO = "/getUserInfo/{userId}";
    public static final String ADD_NEW_USER = "/addNewUser";
    public static final String GENERATE_TOKEN = "/generateToken";

    //NOTIFICATION CONTROLLER PATHS

    public static final String NOTIFICATION_BASE_URL = "/notification";
}
