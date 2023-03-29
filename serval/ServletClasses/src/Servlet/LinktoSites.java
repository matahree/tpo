package Servlet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class LinktoSites {                                                                                                                                   // I have map with 2 strings
    private static final Map<String, String> mapOfResources = new HashMap<>();
    private static LinktoSites linktoSites;

    static {                                                                                                                                              // getting a connection f
        try {
            java.sql.Connection con = Connection.getCon();
            ResultSet rs = con.createStatement().executeQuery("select * from resources");
            while (rs.next())
                mapOfResources.put(rs.getString("content"), rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private LinktoSites() {}

    public static LinktoSites getInstance(){
        if(linktoSites == null) linktoSites = new LinktoSites();
        return linktoSites;
    }

    public static Map<String, String> getMapOfResources() {
        return mapOfResources;
    }
}
