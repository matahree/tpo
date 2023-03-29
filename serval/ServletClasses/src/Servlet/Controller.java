package Servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/logged")

public class Controller extends HttpServlet {                                                                                                // taking login and password from htlm file
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User.addUser(login, password);                                                                          // I add a new user, login and password I dont check if user already exist
        ServletContext context = getServletContext();
        var dispatcher = context.getRequestDispatcher("/view");
        dispatcher.forward(req,res);
    }

    public Controller() {
    }
}
