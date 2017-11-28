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
		
		//每连接上一个客户端，则起一个线程，并收集对应的线程类
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
			//在构造方法里面初始化输入输出管道
			this.socket = socket;
			try {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				accepted = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void send(String str) {
			//发送数据
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			//线程启动时接收客户端数据并发送到其他客户端
			try {
				while(accepted) {
					String str = dis.readUTF();
System.out.println(str);
					for(int i=0; i<clients.size(); i++) {
						Client c = clients.get(i);
						c.send(str);
					}
					
					//用Iterator在循环时会锁定Client对象，效率不高
					/*for(Iterator<Client> ite = clients.iterator(); ite.hasNext(); ) {
						Client c = ite.next();
						c.send(str);
					}*/
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
