package guyue.hu;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(18888);
			Socket socket = null;
			String chatContent = null;
			while(true) {
				socket = serverSocket.accept();
System.out.println("A client connect!");
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String str = dis.readUTF();
				dis.close();
System.out.print(str);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
