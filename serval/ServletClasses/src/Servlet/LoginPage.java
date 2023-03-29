package Servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/firstp")
public class LoginPage extends HttpServlet {                                                                    // button for the log out and when we log out we will directed the page
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rep){
        try(var writer = rep.getWriter()){
            writer.print("<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>UserDecide</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <form >\n" +
                    "        login : <input type=\"text\" name=\"login\"><br>\n" +
                    "        password : <input type=\"text\" name=\"password\"><br>\n" +
                    "        <button formaction=\"/logged\" formmethod=\"get\" type=\"submit\">Proceed</button>\n" +
                    "    </form>\n" +
                    "</body>\n" +
                    "</html>");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
