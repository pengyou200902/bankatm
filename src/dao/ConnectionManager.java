package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instance = null;
    
    private Connection connection;
    private String host, port, database, username, password;

    public static ConnectionManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionManager();
            instance.connect();
        }

        return instance;
    }

    public void connect() throws SQLException {
        String connectionString = "jdbc:postgresql://"
                + host + ":" + port + "/" + database;

        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        connection = DriverManager
            .getConnection(connectionString,
            username, password);

        System.out.println("Opened database successfully");
    }

    public boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, database, tableName, new String[] {"TABLE"});
        
        return resultSet.next();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private ConnectionManager() {
        setDefaults();
    }

    private void setDefaults() {
        host = "localhost";
        port = "5432";
        database = "postgres";
        username = "postgres";
        password = "bankatm";
    }
}
