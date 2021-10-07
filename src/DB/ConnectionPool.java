package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

//we handle all the connection no more then 10.....
public class ConnectionPool {
    private static final int NUM_OF_CONS = 10;
    private static ConnectionPool instance = null;
    private Stack<Connection> connections = new Stack<>();

    /**
     * A constructor that runs the method openAllConnections, that opens 10 connections.
     *
     * @throws SQLException - Incorrect sql command.
     */
    private ConnectionPool() throws SQLException {
        //open all connections
        openAllConnections();
    }

    /**
     * Gets a ConnectionPool instance.
     *
     * @return - A ConnectionPool instance.
     */
    public static ConnectionPool getInstance() {
        //before locking the critical code...
        if (instance == null) {
            //create the connection pool
            synchronized (ConnectionPool.class) {
                //before creating the code.....
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * Gets a connection from the stack "connections". If the stack is empty, it waits
     * until a connection is returned to the stack, and then it takes it.
     *
     * @return - A Connection object.
     * @throws InterruptedException - The thread is interrupted.
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                //wait until we will get a connection back
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * Returns a connection to the stuck "connections", and notify it.
     *
     * @param connection - A ConnectionPool object.
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            //notify that we got back a connection from the user...
            connections.notify();
        }
    }

    /*
        this method will open 10 connection in advanced
        @throws SQLException
     */

    /**
     * Loads the configuration file and update the data for connection, then makes
     * the connection.
     *
     * @throws SQLException - Incorrect sql command.
     */
    private void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONS; index += 1) {
            //load the configuration file and update the data for connection
            // DatabaseManager.getConfiguration();
            //make the connection ......
            Connection connection = DriverManager.getConnection(DatabaseManager.url, DatabaseManager.username, DatabaseManager.password);
            connections.push(connection);
        }
    }

    /**
     * Waits until all the connections return to the stack, then removes them all,
     * and makes instance null.
     *
     * @throws InterruptedException
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONS) {
                connections.wait();
            }
            connections.removeAllElements();
            instance = null;
        }
    }
}


