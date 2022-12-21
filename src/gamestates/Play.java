package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import Main.Game;
import audio.AudioPlayer;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import obj.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import static utilz.LoadSave.*;
import utilz.LoadSave;

public class Play extends State implements Statemethods{

	private Player player;
	private LevelManager levelmanager;
	private EnemyManager enermymanager;
	private ObjectManager objmanager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private Story story;
	private boolean paused = false;
	private int xLvlOffset;
	private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;
	private BufferedImage backgroundImg;
	
	private boolean gameOver;
	private boolean lvlCompleted = false;
	private boolean playerDying = false;
	
	public Play(Game game) {
		
		super(game);
		
		initClasses();
		
		calcLvlOffset();
		loadStartLevel();
		
	}

	public void loadNextLevel() {
		resetAll();
		
		levelmanager.loadNextLevel();
		if(levelmanager.getLvlIndex() == 1) {
			backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG2);
		}
		if(levelmanager.getLvlIndex() == 2) {
			backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG3);
		}
		if(levelmanager.getLvlIndex() == 0) {
			backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG1);
		}
//		player.setSpawn(levelmanager.getCurrentLevel().getPlayerSpawn());
	}
	private void loadStartLevel() {
		enermymanager.loadEnemies(levelmanager.getCurrentLevel());
		backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BG_IMG1);
		objmanager.loadObjects(levelmanager.getCurrentLevel());
	}
	private void calcLvlOffset() {
		maxLvlOffsetX = levelmanager.getCurrentLevel().getLvlOffset();		
	}
	
	
	private void initClasses() {
		levelmanager = new LevelManager(game);
		enermymanager = new EnemyManager(this);
		objmanager = new ObjectManager(this);
		player = new Player(150, 200, (int) (40* Game.SCALE * 1.5), (int) (64 * Game.SCALE * 1.1), this);
		player.loadLvlData(levelmanager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
	}

	@Override
	public void update() {
		if (paused) {
			pauseOverlay.update();
		} else if (lvlCompleted) {
			levelCompletedOverlay.update();
		}else if(gameOver) {
			gameOverOverlay.update();
		}else if(playerDying){
			player.update();
		}
		else{
			levelmanager.update();
			objmanager.update(levelmanager.getCurrentLevel().getLevelData(), player);
			player.update();
			enermymanager.update(levelmanager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		}

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
		drawBG(g);
		
		levelmanager.draw(g, xLvlOffset);
		
		player.render(g, xLvlOffset);
		enermymanager.draw(g, xLvlOffset);
		objmanager.draw(g, xLvlOffset);
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)
			gameOverOverlay.draw(g);
		else if(lvlCompleted)
			levelCompletedOverlay.draw(g);
	}

	public void drawBG(Graphics g) {
		for(int j = 0;j < (int)(1788*1.5/Game.GAME_WIDTH) + 1; j++)
			g.drawImage(backgroundImg, 0 + (int)(j*1788*1.5)-(int)(xLvlOffset * 0.5), 0, (int)(1788*1.5), (int)(Game.GAME_HEIGHT), null); // change BG
	}
	
	public void resetAll() {
		gameOver = false;
		paused = false;
		playerDying = false;
		lvlCompleted = false;
		player.resetAll();
		enermymanager.resetAllEnemies();
		objmanager.resetAllObjects();
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enermymanager.checkEnemyHit(attackBox);
	}
	
	public void checkPotionTouched(Rectangle2D.Float hitbox) {
		objmanager.checkObjectTouched(hitbox);
		
	}
	public void checkSignTouched(Rectangle2D.Float hitbox){
		objmanager.checkSignTouched(hitbox);
	}
	public void checkSpikesTouched(Player p) {
		objmanager.checkSpikesTouched(p);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		} else
			gameOverOverlay.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);
		} else
			gameOverOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseMoved(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		} else
			gameOverOverlay.mouseMoved(e);
	}
	
	public void mouseDragged(MouseEvent e) {
		if (!gameOver) 
			if (paused)
				pauseOverlay.mouseDragged(e);
			
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
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
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
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
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}
	public void setMaxLvlOffsetX(int maxLvlOffsetX) {
		this.maxLvlOffsetX = maxLvlOffsetX;
	}
	
	
	public EnemyManager getEnermymanager() {
		return enermymanager;
	}

	public void setEnermymanager(EnemyManager enermymanager) {
		this.enermymanager = enermymanager;
	}
	
	public ObjectManager getObjManager() {
		return this.objmanager;
	}
	
	public int getMaxLvlOffsetX() {
		return maxLvlOffsetX;
	}
	public boolean getLvlCompleted() {
		return lvlCompleted;
	}

	public void setLvlCompleted(boolean lvlCompleted) {
		this.lvlCompleted = lvlCompleted;
		if(lvlCompleted)
				getGame().getAudioPlayer().playEffect(AudioPlayer.LVL_COMPLETED);
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void setPlayerDying(boolean playerDying) {
		this.playerDying = playerDying;
	}
	
	public LevelManager getLevelManager() {
		return levelmanager;
	}
}
