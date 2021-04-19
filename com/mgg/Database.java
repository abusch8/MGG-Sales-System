package com.mgg;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.sql.*;

public class Database {

    private static final Logger LOGGER = LogManager.getLogger(LoadData.class);

    static {
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);
    }

    /**
     * Connection parameters that are necessary for CSE's configuration
     */
    private static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static final String USERNAME = "ddiehl";
    private static final String PASSWORD = "phDTmjhQb1";
    private static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

    /**
     * This method connects to the database, the userinfo can be modified in the DatabaseInfo class
     *
     * @return conn - the connection to the database.
     */
    public static Connection connect() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error(String.format("Failed to establish connection to database @ %s", Database.URL));
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * Disconnects us from the database.
     *
     * @param rs   ResultSet
     * @param ps   preparedStatement
     * @param conn connection
     */
    public static void disconnect(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

}