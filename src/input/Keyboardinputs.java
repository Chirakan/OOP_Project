package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.GamePanel;

public class Keyboardinputs implements KeyListener{
	private GamePanel p;

	public Keyboardinputs(GamePanel p) {
		this.p = p;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			p.getGame().getPlayer().setUp(true);
			break;
		case KeyEvent.VK_A:
			p.getGame().getPlayer().setLeft(true);
			break;
		case KeyEvent.VK_S:
			p.getGame().getPlayer().setDown(true);
			break;
		case KeyEvent.VK_D:
			p.getGame().getPlayer().setRight(true);
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			p.getGame().getPlayer().setUp(false);
			break;
		case KeyEvent.VK_A:
			p.getGame().getPlayer().setLeft(false);
			break;
		case KeyEvent.VK_S:
			p.getGame().getPlayer().setDown(false);
			break;
		case KeyEvent.VK_D:
			p.getGame().getPlayer().setRight(false);
			break;
		}
	}

}
