package guyue.hu;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		pack();
		setVisible(true);
	}

	class Closing extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent arg0) {
			System.exit(0);
		}

	}
}
