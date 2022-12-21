package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.GamePanel;
import gamestates.Gamestate;

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
		switch(Gamestate.state) {
		case MENU:
			p.getGame().getMenu().keyPressed(e);;
			break;
		case PLAYING:
			p.getGame().getPlay().keyPressed(e);
			break;
		case STORY:
			p.getGame().getStory().keyPressed(e);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(Gamestate.state) {
		case MENU:
			p.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			p.getGame().getPlay().keyReleased(e);
			break;
		default:
			break;
		}
	}

}
