package Servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/database")

public class DataBaseView extends HttpServlet {                                                                   // printing the database prints all the users : name password and ID
    public DataBaseView() {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rep) {
        try(var writer = rep.getWriter()){
            writer.print("<html><body><form> Users in database: <br>");
            java.sql.Connection con = Connection.getCon();
            ResultSet rs = con.createStatement().executeQuery("select * from users");
            while (rs.next())
                writer.print(
                        "id: " + rs.getInt(1) + "<br>"
                                + "name: " + rs.getString("login") + "<br>"
                                + "password" + rs.getString("password") + "<br>\n");
            writer.print("</form></html></body>");
        } catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }
}
