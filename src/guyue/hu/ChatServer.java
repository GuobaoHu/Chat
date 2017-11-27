package guyue.hu;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) {
		boolean connect = false;//�ͻ����Ƿ������Ϸ�������
		
		try {
			ServerSocket serverSocket = new ServerSocket(18888);
			connect = true;
			Socket socket = null;
			String str = null;
			DataInputStream dis = null;
			while(connect) {
				boolean accepted = false;//���������Ƿ����
				socket = serverSocket.accept();
				dis = new DataInputStream(socket.getInputStream());
				accepted = true;
System.out.println("A client connect!");
				while(accepted) {
					str = dis.readUTF();
					System.out.println(str);
				}
				dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
