package Protocol;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddProtocol implements IProtocol{
    
    public static final String title = "ADD";
    public static final String sep = " ";
    public static final String anchor_begin = "^";
    public static final String anchor_end = "$";
    public static final String integer_pattern = "([0-9]+)";
    public static final String add_pattern = 
                                            anchor_begin 
                                            + title 
                                            + sep
                                            + integer_pattern
                                            + sep
                                            + integer_pattern
                                            + anchor_end;

    private static final Pattern add_request;

    static{
        add_request = Pattern.compile(add_pattern);
    }

    public String request(String seriallizedRequest) throws Throwable{
        Matcher matcher = add_request.matcher(seriallizedRequest);
        if  ( matcher.matches() == false )
        {
            throw new Throwable ("Invalid request");
        }
        BigInteger first_param = new BigInteger(matcher.group(1));
        BigInteger second_param = new BigInteger(matcher.group(2));
        return first_param.add(second_param).toString();
    }

    public String responce(String rescponce) throws Throwable {
        return null;
    }
}
