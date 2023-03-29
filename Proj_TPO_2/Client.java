import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import Protocol.AddProtocol;
import Protocol.EchoProtocol;


public class Client {
    
        private static final String server = "localhost";
        private SocketChannel _socketChannel;
        private ByteBuffer _byteBuffer;

        public Client(String server, int serverPort) throws IOException{
                _socketChannel = SocketChannel.open();
                SocketAddress socketAddress = new InetSocketAddress(server, serverPort);
                _socketChannel.connect(socketAddress);
                _byteBuffer = ByteBuffer.allocate(1024); 
        }

        private String execute (String op , String request) throws IOException{
            _byteBuffer.clear();
            _byteBuffer.put(op.getBytes());
            _byteBuffer.put(AddProtocol.sep.getBytes());
            _byteBuffer.put(request.getBytes());
            _byteBuffer.flip();
            _socketChannel.write(_byteBuffer);
            _byteBuffer.clear();
            _socketChannel.read(_byteBuffer);
            _byteBuffer.flip();
            return new String(_byteBuffer.array(), 0 , _byteBuffer.limit());
        }


        private void request (String opCode, String[]args) throws Throwable {
            try {
                String request = null;
                switch (opCode) {
                    case EchoProtocol.title:
                        StringBuffer buffer = new StringBuffer();
                        for (int i = 1 ; i < args.length; i++){
                            buffer.append(args[i]).append(" ");
                        }
                        request  = buffer.toString();
                        String s1 = execute(EchoProtocol.title, request);
                        System.out.println(s1);
                        break;
                    case AddProtocol.title:
                        request = args[1] + " " + args[2];
                        String s2 = execute(AddProtocol.title, request);
                        System.out.println(s2);
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            _socketChannel.close();
        }
        
        public static void main(String[] args) {
            try {
                Client client  = new Client(server, 10001);
                client.request(args[0], args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
}
