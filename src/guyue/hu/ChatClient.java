package guyue.hu;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient {

	public static void main(String[] args) {
		MyFrame myFrame = new MyFrame();
		myFrame.launch();
		
	}

}

class MyFrame extends Frame {

	TextArea chatArea = null;
	TextField inputField = null;
//	String str = null;
	Socket socket = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	private boolean isReceive = false;

	public MyFrame() {
		super("Chat 1.0");
	}

	public void launch() {
		chatArea = new TextArea();
		inputField = new TextField();
		chatArea.setEditable(false);
		setBounds(100, 100, 400, 400);
		add(chatArea, BorderLayout.NORTH);
		add(inputField, BorderLayout.SOUTH);
		addWindowListener(new Closing());
		inputField.addActionListener(new EnterListener());
		pack();
		setVisible(true);
		connect();
		new Thread(new Receive()).start();
	}
	
	public void connect() {
		try {
			socket = new Socket("127.0.0.1", 18888);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			isReceive = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeAll() {
		//关闭所有的流
		try {
			isReceive = false;
			dos.close();
			dis.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private class Receive implements Runnable {
		//接收Server发过来的数据的线程
		@Override
		public void run() {
			try {
				while(isReceive) {
					String str = dis.readUTF();
System.out.println(str);
					chatArea.setText(chatArea.getText() + str + "\r\n");
				}
			} catch (SocketException e) {
				System.out.println("Client closed!");
			} catch (IOException e) {
					e.printStackTrace();
			}
			
		}
		
	}
	
	private class Closing extends WindowAdapter {
		//关闭客户端界面
		@Override
		public void windowClosing(WindowEvent arg0) {
			closeAll();
			System.exit(0);
		}

	}

	private class EnterListener implements ActionListener {
		//响应Enter
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String str = inputField.getText();
//			chatArea.setText(str);
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputField.setText("");
		}

	}
}
