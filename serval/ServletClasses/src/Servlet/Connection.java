package Servlet;
import java.sql.*;

public class Connection {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "pass";
    private static java.sql.Connection con;
    static {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection() {
        System.out.println("CON NOT NULL");
    }


    public static java.sql.Connection getCon() {
        return con;
    }


}

