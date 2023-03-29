
package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ChatClient {
	
	private String host,
				id;
	private int port;
	private StringBuffer chartView = new StringBuffer();
	private SocketChannel  socket =  null; 
	
	public ChatClient(String host, int port, String id) {
		this.host = host;
		this.id = id;
		this.port = port;
	}
	
	
	public void login() {
			try {
				socket = SocketChannel.open(new InetSocketAddress(host, port));
				socket.configureBlocking(false);
			}catch(UnknownHostException unHostExc) {
				unHostExc.printStackTrace();
			}catch(IOException ioException) {
				ioException.printStackTrace();
			}
		send("logged in");
		
	}
	
	public void logout() {
		send("logged out");
		try {
			socket.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	private static Charset charset = Charset.forName("UTF-8");
	private static final int BYTE_BUFFER_SIZE = 1024;
	private ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);
	
	public void send(String request) {
// Write DOWN
		request = id + " " + request;
		ByteBuffer bufferRequest = charset.encode(CharBuffer.wrap(request));
		try {
			socket.write(bufferRequest);
			bufferRequest.flip();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
// Write UP		
// Read down
		try {
		while(socket.read(byteBuffer) > 0) { // or .hasRemaining()
			byteBuffer.flip(); // мб 
			CharBuffer charBuffer = charset.decode(byteBuffer);
			while(charBuffer.hasRemaining()) {
				chartView.append(charBuffer.get());
			}
		}
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
// Read Up
		
	}
	
	public String getChartView() {
		
		return chartView.toString();
	}
	
}
