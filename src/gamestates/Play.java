package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;

public class Play extends State implements Statemethods{

	private Player player;
	private LevelManager levelmanager;
	private EnemyManager enermymanager;
	
	private int xLvlOffset;
	private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.getLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
	
	private BufferedImage backgroundImg;
	private BufferedImage sand;
	private BufferedImage fore;
	
	public Play(Game game) {
		super(game);
		initClasses();
		
		backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		sand = LoadSave.getSpriteAtlas(LoadSave.SAND_BG);
		fore = LoadSave.getSpriteAtlas(LoadSave.FORE_BG);
	}

	private void initClasses() {
		levelmanager = new LevelManager(game);
		enermymanager = new EnemyManager(this);
		player = new Player(150, 200, (int) (64* Game.SCALE), (int) (64 * Game.SCALE));
		player.loadLvlData(levelmanager.getCurrentLevel().getLevelData());
	}

	@Override
	public void update() {
		levelmanager.update();
		player.update();
		enermymanager.update();
		checkCloseToBorder();
		
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;
		
		if(diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if(diff < leftBorder)
			xLvlOffset += diff - leftBorder;
		
		if(xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if(xLvlOffset < 0)
			xLvlOffset = 0;
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, (int)(Game.GAME_WIDTH), null);
		drawSand(g);
		
		levelmanager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enermymanager.draw(g, xLvlOffset);
		
	}

	private void drawSand(Graphics g) {
		for(int j = 0; j < 4; j++)
			g.drawImage(sand, j * SAND_WIDTH - (int)(xLvlOffset * 0.2), (int)(SAND_HEIGHT * Game.SCALE), SAND_WIDTH, (int)(SAND_HEIGHT * 1.3), null);
		
		for(int i = 0;i < 5; i++) {
//			g.drawImage(fore, FORE_WIDTH * i - (int) (xLvlOffset * 0.4), (int)(FORE_HEIGHT* Game.SCALE)+60, FORE_WIDTH, FORE_HEIGHT, null);
			g.drawImage(fore, FORE_WIDTH * i - (int) (xLvlOffset * 0.4), (int)(FORE_HEIGHT* Game.SCALE)-135, FORE_WIDTH, (int)(FORE_HEIGHT*2.2), null);
		}
			
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_BACK_SPACE:
			Gamestate.state = Gamestate.MENU;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}
}
