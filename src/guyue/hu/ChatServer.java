package guyue.hu;

import java.io.*;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer {
	boolean connect = false;//客户端是否连接上服务器端
	ServerSocket serverSocket = null;
	List<Client> clients = new ArrayList<Client>();
	
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
				clients.add(c);
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
		private DataOutputStream dos = null;
		private boolean accepted = false;
		
		public Client(Socket socket) {
			this.socket = socket;
		}
		
		public void send(String str) {
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				accepted = true;
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				while(accepted) {
					String str = dis.readUTF();
System.out.println(str);
					for(int i=0; i<clients.size(); i++) {
						Client c = clients.get(i);
						c.send(str);
					}
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
					if(dos != null) {
						dos.close();
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
