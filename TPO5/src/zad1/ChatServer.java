
package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatServer {
		private String host;
		private int port; 
		private Thread serverThread;
		private ServerSocketChannel serverSocketChannel;
		private boolean serverIsRunning = true;
		private StringBuffer logServer = new StringBuffer();
		private Map<String,StringBuffer> waitingForSendingMessage = new HashMap<String, StringBuffer>();
		
		public ChatServer(String host, int port) {
			this.host = host;
			this.port = port;
			
		}
		
		
		public void startServer() {
			serverThread = new Thread( ()-> { 
				try {
					serverSocketChannel = ServerSocketChannel.open();
					serverSocketChannel.bind(new InetSocketAddress(host, port));
					
					serverSocketChannel.configureBlocking(false);
					
					Selector selector = Selector.open();
					
					SelectionKey serverSelectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//| SelectionKey.
					
					while(serverIsRunning) {
					try {
						selector.select();
						Set selectedKeys = selector.selectedKeys();
						Iterator iterator = selectedKeys.iterator();
						while(iterator.hasNext()) {
							SelectionKey  key = (SelectionKey)iterator.next(); 
							iterator.remove();
							
							if(key.isAcceptable()) {
								SocketChannel clientSocketChannel = serverSocketChannel.accept();  
								
								clientSocketChannel.configureBlocking(false);
								clientSocketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
								continue;
							} //else
							if(key.isReadable()) {
								SocketChannel keySocketChannel = (SocketChannel)key.channel();
								serviceRequest(keySocketChannel);
							} // else
							if(key.isWritable()) {
								SocketChannel keySocketChannel = (SocketChannel)key.channel();
								serviceResponde(keySocketChannel);
							}
							
						}
						
						
					}catch(Exception exception) {
						exception.printStackTrace();
						continue;
					}	
						
				}
					
			    serverSocketChannel.close() ;
				 serverSocketChannel.socket().close();
				 selector.close();	
					
				}catch(IOException ioe){
					ioe.printStackTrace();
					
				}
				
				
			});
			
			serverThread.start();
			
		}
		
		
		private static Charset charset = Charset.forName("UTF-8");
		private static final int BYTE_BUFFER_SIZE = 1024;

		 private ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);

		 private StringBuffer requestStringBuffer = new StringBuffer();
		
		private void serviceRequest(SocketChannel socketChannel) {
			
			requestStringBuffer.setLength(0);
			byteBuffer.clear();
			try {
				if(socketChannel.read(byteBuffer) < 0) return;
				byteBuffer.flip();
				CharBuffer charBuffer = charset.decode(byteBuffer);
				while(charBuffer.hasRemaining()){
					requestStringBuffer.append(charBuffer.get());
				}
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
			
		// parameters[0] = idClient
		// String parameters[]	 = requestStringBuffer.toString().split(" ");
		String request = requestStringBuffer.toString().trim();
		logServer.append(  LocalTime.now() + " " + request + "\n");		
		
		if(request.endsWith("logged in")) {
			addNewClient(request, socketChannel.socket().getInetAddress().toString(), "" + socketChannel.socket().getPort());
		} 
		addWaitingForSendMessageToAll(request);
		
		if(request.endsWith("logged out")) {
			// writeRespondes
			deleteClient(request, socketChannel.socket().getInetAddress().toString(), "" + socketChannel.socket().getPort());
			try {
				socketChannel.close();
				socketChannel.socket().close();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
			
		}
		
		private void addNewClient(String request, String iP, String port) {
			String idClient = iP + " " + port + " " + request.split(" ")[0];
			StringBuffer notSendedMessage = new StringBuffer(); // + "\n");
			waitingForSendingMessage.put(idClient, notSendedMessage);			
		}
		
		private void deleteClient(String request, String iP, String port)  {
			String idClient = iP + " " + port + " " + request.split(" ")[0];
			waitingForSendingMessage.remove(idClient);
		} 
		
		
		private void addWaitingForSendMessage(String request, String iP, String port) {
			String idClient = iP + " " + port + " " + request.split(" ")[0];
			StringBuffer notSendedMessage = new StringBuffer();
			notSendedMessage = waitingForSendingMessage.get(idClient);
			waitingForSendingMessage.remove(idClient);
			notSendedMessage.append(request + "\n");
			
			waitingForSendingMessage.put(idClient, notSendedMessage);
			
		}
		
		public void addWaitingForSendMessageToAll(String request) {
			for(StringBuffer stringBuffer : waitingForSendingMessage.values()) {
				stringBuffer.append(request); // + "\n");				
			}
			
		}
		
		
		private void serviceResponde(SocketChannel socketChannel) {
			
			
		}
		
		
		
		public void stopServer() {
			serverIsRunning = false;	
			serverThread.interrupt();
		}
		
		
		public String getServerLog() {
			
			return logServer.toString();
		}
		
}
