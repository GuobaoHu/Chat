package guyue.hu;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
	String str = null;
	DataOutputStream dos = null;

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
	}
	
	public void connect() {
		try {
			Socket socket = new Socket("127.0.0.1", 18888);
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Closing extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent arg0) {
			System.exit(0);
		}

	}

	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			str = inputField.getText();
			chatArea.setText(str);
			try {
				dos.writeUTF(str);
				dos.flush();
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputField.setText("");
		}

	}
}
