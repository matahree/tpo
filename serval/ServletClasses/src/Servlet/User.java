package Servlet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private static int lastIDUser;
    private static User user;
    static java.sql.Connection con = Connection.getCon();
    static {
        try {                                                                                                                                                // taking the connections
            ResultSet rs = con.createStatement().executeQuery("select * from users");
            while (rs.next())
                lastIDUser = rs.getInt("id_user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User() {
    }
                                                                                                                                                                 // inserting the user
    static void addUser(String login, String password){
        final String addUser = "insert into Users (id_user, login, password) values (?,?,?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(addUser)) {
            preparedStatement.setInt(1, ++lastIDUser);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User getInstanceOfUser(){
        if(user == null) user = new User();
        return user;
    }
}
