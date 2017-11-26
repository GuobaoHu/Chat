package guyue.hu;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
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
	TextArea inputArea = null;

	public MyFrame() {
		super("Chat 1.0");
	}

	public void launch() {
		chatArea = new TextArea("", 20, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		inputArea = new TextArea("", 5, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		chatArea.setEditable(false);
		add(chatArea, BorderLayout.NORTH);
		add(inputArea, BorderLayout.SOUTH);
		addWindowListener(new Closing());
		addKeyListener(new SendMessage());
		pack();
		setVisible(true);
	}

	class Closing extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent arg0) {
			System.exit(0);
		}

	}
	
	class SendMessage extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				try {
					Socket socket = new Socket("127.0.0.1", 18888);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					dos.writeUTF(inputArea.getText());
					chatArea.setText(inputArea.getText());
					
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
}
