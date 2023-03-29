import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.function.Consumer;
import Exception.HandleException;
import Protocol.AddProtocol;
import Protocol.EchoProtocol;
import Protocol.IProtocol;


public class Server {

    private ServerSocketChannel _serverChannel;
    private Selector _selector;
    private ByteBuffer _byteBuffer;
    private ProtocolMap _protocolMap;
    
    private Server(int port) throws Throwable {
        try {
            _protocolMap  = new ProtocolMap();
            _byteBuffer = ByteBuffer.allocate(1024);
            _serverChannel = ServerSocketChannel.open();
            SocketAddress address = new InetSocketAddress(port);
            _serverChannel.socket().bind(address);
            _selector = Selector.open();
            configureChannel(_serverChannel, SelectionKey.OP_ACCEPT, this::handleConnection);
            init();
        } 
        catch (Exception e) {
            throw e;
        }
    }

    public void init() throws Throwable{
        _protocolMap.mapProtocol(AddProtocol.title, new AddProtocol());
        _protocolMap.mapProtocol(EchoProtocol.title, new EchoProtocol());
    }

    @SuppressWarnings({"unchecked"})
    private void handleOperationsSet () throws Throwable{
        try{
            while(true){
                _selector.select();
                Iterator<SelectionKey> iterator = _selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    Consumer<SelectionKey> handle = (Consumer<SelectionKey>)key.attachment();
                    handle.accept(key);
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            _serverChannel.close();
        }
    }

    
    private void handleRequest(SelectionKey key) {
        try (SocketChannel channel = (SocketChannel) key.channel()) {
            _byteBuffer.clear();
            channel.read(_byteBuffer); 
            _byteBuffer.flip();
            String request = new String(_byteBuffer.array(), 0, _byteBuffer.limit());
            int index = request.indexOf(" ");
            IProtocol protocol  = _protocolMap.getProtocol(request.substring(0, index));
            String response = protocol.request(request);
            _byteBuffer.clear();
            _byteBuffer.put(response.getBytes());
            _byteBuffer.flip();
            channel.write(_byteBuffer);
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    private void handleConnection(SelectionKey key){
        try {
            SocketChannel clieChannel  = _serverChannel.accept();
            configureChannel(clieChannel, SelectionKey.OP_READ, this::handleRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SelectionKey configureChannel(SelectableChannel channel , int operation , 
                            Consumer<SelectionKey> consume) throws HandleException {
        try
        {
            channel.configureBlocking(false);
            return channel.register(_selector, operation,  consume);
        } 
        catch (Exception e) {
            throw new HandleException(e);
        }
    }
    
    public static void main(String[] args) {
        try 
        {
            new Server(10001).handleOperationsSet();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}