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
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				chatContent = dis.readUTF();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
