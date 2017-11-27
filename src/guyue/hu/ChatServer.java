package guyue.hu;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	boolean connect = false;//客户端是否连接上服务器端
	ServerSocket serverSocket = null;
	
	public static void main(String[] args) {
		new ChatServer().launch();
	}
	
	public void launch() {
		try {
			serverSocket = new ServerSocket(18888);
		} catch (BindException e) {
			System.out.println("端口使用中...");
			System.exit(0);
		} catch	(IOException e) {
			e.printStackTrace();
		}
		
		try {
			connect = true;
			while(connect) {
				Socket socket = serverSocket.accept();
System.out.println("A client connect!");
				Client c = new Client(socket);
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Client implements Runnable {
		private Socket socket;
		private DataInputStream dis = null;
		private boolean accepted = false;
		private String str = null;
		
		public Client(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				accepted = true;
				dis = new DataInputStream(socket.getInputStream());
				while(accepted) {
					str = dis.readUTF();
					System.out.println(str);
				}
			} catch (EOFException e) {
				System.out.println("Client closed!");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dis != null) {
						dis.close();
					}
					if(socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
