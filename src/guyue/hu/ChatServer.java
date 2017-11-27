package guyue.hu;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		boolean connect = false;//客户端是否连接上服务器端
		ServerSocket serverSocket = null;
		String str = null;
		Socket socket = null;
		DataInputStream dis = null;
		
		try {
			serverSocket = new ServerSocket(18888);
		} catch (BindException e) {
			System.out.println("端口使用中...");
		} catch	(IOException e) {
			e.printStackTrace();
		}
		
		try {
			connect = true;
			while(connect) {
				boolean accepted = false;//服务器端是否接收
				socket = serverSocket.accept();
				dis = new DataInputStream(socket.getInputStream());
				accepted = true;
System.out.println("A client connect!");
				while(accepted) {
					str = dis.readUTF();
					System.out.println(str);
				}
			}
		} catch (EOFException e) {
			System.out.println("Client closed!");
		}
		catch (IOException e) {
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
