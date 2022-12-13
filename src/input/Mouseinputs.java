package input;

import java.awt.event.*;

import Main.GamePanel;
import gamestates.Gamestate;

public class Mouseinputs implements MouseListener, MouseMotionListener{
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
//		p.setRectPos(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(Gamestate.state) {
		case MENU:
			p.getGame().getMenu().mouseClicked(e);
			break;
		case PLAYING:
			p.getGame().getPlay().mouseClicked(e);
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
