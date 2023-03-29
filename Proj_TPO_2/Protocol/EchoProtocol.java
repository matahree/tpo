package Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EchoProtocol implements IProtocol {

    public static final String title = "ECHO";
    public static final String sep = " ";
    public static final String anchor_begin = "^";
    public static final String anchor_end = "$";
    public static final String symbol = "(.+)";
    public static final String echo_pattern = 
                                            anchor_begin 
                                            + title 
                                            + sep
                                            + symbol
                                            + anchor_end;

    private static final Pattern echo_regex;

    static{
        echo_regex = Pattern.compile(echo_pattern);
    }

    public String request(String serializeeRequest) throws Throwable {
        Matcher matcher = echo_regex.matcher(serializeeRequest);
        if  ( matcher.matches() == false ){
            throw new Throwable ("Invalid request");
        }
        String tx = matcher.group(1);
        return tx;
    }

    public String responce(String serializedRescponce) throws Throwable {
        return serializedRescponce;
    }
}
