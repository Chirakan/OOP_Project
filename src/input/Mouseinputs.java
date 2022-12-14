package input;

import java.awt.event.*;

import Main.GamePanel;
import gamestates.Gamestate;

public class Mouseinputs implements MouseListener, MouseMotionListener {
	private GamePanel p;

	public Mouseinputs(GamePanel p) {
		this.p = p;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			p.getGame().getMenu().mouseMoved(e);
			break;
		case PLAYING:
			p.getGame().getPlay().mouseMoved(e);
			break;
		default:
			break;
		}	
		}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
		case PLAYING:
			p.getGame().getPlay().mouseClicked(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			p.getGame().getMenu().mousePressed(e);
			break;
		case PLAYING:
			p.getGame().getPlay().mousePressed(e);
			break;
		default:
			break;
		}	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case MENU:
			p.getGame().getMenu().mouseReleased(e);
			break;
		case PLAYING:
			p.getGame().getPlay().mouseReleased(e);
			break;
		default:
			break;
		}	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
