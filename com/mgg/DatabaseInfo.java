package com.mgg;

public class DatabaseInfo {

    /**
     * Connection parameters that are necessary for CSE's configuration
     */
    public static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static final String USERNAME = "ddiehl";
    public static final String PASSWORD = "phDTmjhQb1";
    public static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

}