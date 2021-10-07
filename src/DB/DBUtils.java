package DB;


import Beans.Company;
import Beans.Coupon;
import DBDAO.CouponsDBDAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Map;

public class DBUtils {
    private static Connection connection = null;
    private static ResultSet resultSet = null;

    /**
     * Runs sql query and receives results into ResultSet.
     *
     * @param sql - The String sql command.
     * @return - ResultSet.
     */
    public static ResultSet runQueryWithResult(String sql) {
        //drop table , delete , alter
        try {
            //taking a connection from connection pool
            connection = ConnectionPool.getInstance().getConnection();
            //run the sql command
            PreparedStatement statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    /**
     * Runs sql and parameters query and receives results into ResultSet.
     *
     * @param query  - The String sql command.
     * @param params - The parameters (placed inside the sql command).
     * @return - ResultSet.
     */
    public static ResultSet runQueryWithResult(String query, Map<Integer, Object> params) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            //למדה אקספרשיין כי אנו לא רוצים לפתוח עוד מחלקה ושיטות רק בשביל הפעולה שלהלן
            params.forEach((key, value) -> {
                try {
                    //int, string, date, boolean, double, float
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof java.util.Date) {
                        //convert our date to localdate so we can use it as date in sql
                        LocalDate myDate = ((java.util.Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        //convert our local date to java date :)
                        statement.setDate(key, java.sql.Date.valueOf(myDate));
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (boolean) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (float) value);
                    } else if (value instanceof Timestamp) {
                        statement.setTimestamp(key, (Timestamp) value);
                    }
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            });
            resultSet = statement.executeQuery();
            return resultSet;
        } catch (Exception err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return null;
    }

    /**
     * Runs sql and parameters query WITHOUT receiving results.
     *
     * @param query  - The String sql command.
     * @param params - The parameters (placed inside the sql command).
     */
    public static void runQuery(String query, Map<Integer, Object> params) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            //למדה אקספרשיין כי אנו לא רוצים לפתוח עוד מחלקה ושיטות רק בשביל הפעולה שלהלן
            params.forEach((key, value) -> {
                try {
                    //int, string, date, boolean, double, float
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String) {
                        statement.setString(key, String.valueOf(value));
                    } else if (value instanceof java.util.Date) {
                        //convert our date to localdate so we can use it as date in sql
                        LocalDate myDate = ((java.util.Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        //convert our local date to java date :)
                        statement.setDate(key, java.sql.Date.valueOf(myDate));
                    } else if (value instanceof Boolean) {
                        statement.setBoolean(key, (boolean) value);
                    } else if (value instanceof Double) {
                        statement.setDouble(key, (double) value);
                    } else if (value instanceof Float) {
                        statement.setFloat(key, (float) value);
                    } else if (value instanceof Timestamp) {
                        statement.setTimestamp(key, (Timestamp) value);
                    } else if (value instanceof Enum) {
                        statement.setString(key, String.valueOf(value));
                    }
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            });
            statement.execute();
        } catch (Exception err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    /**
     * Runs sql query WITHOUT receiving results.
     *
     * @param query - The String sql command.
     */
    public static void runQuery(String query) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (Exception err) {
            System.out.println(err.getMessage());
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
}
