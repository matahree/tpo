import java.util.HashMap;
import java.util.Map;

import Protocol.IProtocol;

public class ProtocolMap {
    
    private final Map<String , IProtocol> map = new HashMap<>();

    public void mapProtocol (String key , IProtocol protocol) throws Throwable
    {
        if (map.containsKey(key)){
            throw new Throwable("Protocol already has been adeded to map");
        }
        map.put(key, protocol);
    }

    public IProtocol getProtocol (String key) throws Throwable {
        if (map.containsKey(key) == false){
            throw new Throwable("No such protocol");
        }
        return map.get(key);
    }
}
