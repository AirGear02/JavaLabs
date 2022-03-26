package db;

import db.enums.OrderDirectionEnum;

import java.sql.*;

public class DB {
    private final String HOST="localhost";
    private final String PORT="3306";
    private final String DB_NAME="books";
    private final String USERNAME="root";
    private final String PASSWORD="";

    private Connection getDbConn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME,
                USERNAME, PASSWORD);
        return con;
    }

    public ResultSet getBooks(int startYear, String operator, OrderDirectionEnum orderDirection) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM books WHERE year" + operator + startYear;
        if (orderDirection != null) {
            query += " ORDER BY name " + orderDirection;
        }
        query += ";";
        Connection connection = getDbConn();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
