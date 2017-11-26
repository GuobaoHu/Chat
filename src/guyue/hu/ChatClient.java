package guyue.hu;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;

public class ChatClient {

	public static void main(String[] args) {
		MyFrame myFrame = new MyFrame();
		myFrame.launch();
	}

}

class MyFrame extends Frame {
	public MyFrame() {
		super("Chat 1.0");
	}
	
	public void launch() {
		TextArea chatArea = new TextArea("", 20, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		TextArea inputArea = new TextArea("", 5, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		chatArea.setEditable(false);
		add(chatArea, BorderLayout.NORTH);
		add(inputArea, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}
}
