package Main;

import java.awt.event.*;

import javax.swing.JFrame;

public class GameWindow{
	private JFrame fr;
	public GameWindow(GamePanel p) {
		fr = new JFrame();
		fr.add(p);
		fr.setResizable(false);
		fr.pack();
		fr.setLocationRelativeTo(null);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
		fr.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				p.getGame().windowFocusLost();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}
