package Servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/viewp")

public class ShowLinks extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rep) {
        try(var writer = rep.getWriter()){
            Map<String, String> mapOfResources = LinktoSites.getMapOfResources();                                                                         // for the map we print name
            writer.print("<html><body><form> Available resources: <br>");
            for (var res : mapOfResources.keySet())
                writer.print("<a href = \"" + res + "\">" + mapOfResources.get(res) + "</a><br>");
            writer.print("<button formaction=\"/database\" formmethod=\"post\" type=\"submit\">Show database</button><br>");
            writer.print("<button formaction=\"/first\" formmethod=\"post\" type=\"submit\">Log out</button>");
            writer.print("</form></html></body>");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ShowLinks() {
    }
}
