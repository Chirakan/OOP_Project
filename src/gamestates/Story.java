package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import utilz.LoadSave;

public class Story extends State implements Statemethods{
	
	private BufferedImage[] bg;
	private int index = 0;

	public Story(Game game) {
		super(game);
		bg = new BufferedImage[2];
		bg[0] = LoadSave.getSpriteAtlas(LoadSave.STORY_1);
		bg[1] = LoadSave.getSpriteAtlas(LoadSave.STORY_2);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bg[index], 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(index == 0)
				this.setGameState(Gamestate.PLAYING);
			else
				this.setGameState(Gamestate.MENU);
			setIndex(++index);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(index == 0)
				this.setGameState(Gamestate.PLAYING);
			else
				this.setGameState(Gamestate.MENU);
			setIndex(++index);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(index == 0)
				this.setGameState(Gamestate.PLAYING);
			else
				this.setGameState(Gamestate.MENU);
			setIndex(++index);
		}
	}
	
	public void setIndex(int index) {
		if(index > 1)
			this.index = 0;
		else
			this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

}
