package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods{
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg, bg;
	private int menuX, menuY, menuWidth, menuHeight;
	private String[] bgarr = {LoadSave.PLAYING_BG_IMG1, LoadSave.PLAYING_BG_IMG2, LoadSave.PLAYING_BG_IMG3};

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
	}
	
	private void loadBackground() {
		
		bg = LoadSave.getSpriteAtlas(bgarr[(int)(Math.random()*100%3)]);
		backgroundImg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (45 * Game.SCALE);

	}
	private void loadButtons() {

		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.STORY);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons) {
			mb.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		drawBG(g);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

		for (MenuButton mb : buttons)
			mb.draw(g);
//		g.setColor(Color.black);
//		g.drawString("MENU", Game.GAME_WIDTH / 2, 200);
	}
	
	public void drawBG(Graphics g) {
		g.drawImage(bg, 0 , 0, (int)(1788*1.5), (int)(Game.GAME_HEIGHT), null); // change BG
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				if(mb.getState() == Gamestate.PLAYING)
					game.getAudioPlayer().setLevelSong(game.getPlay().getLevelManager().getLvlIndex());
				break;
			}
		}
		resetButton();
	}
	
	private void resetButton() {
		for (MenuButton mb : buttons)
			mb.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);
		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Gamestate.state = Gamestate.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Gamestate.state = Gamestate.PLAYING;
		}
		
	}
	
	public MenuButton[] getButtons() {
		return buttons;
	}

	public BufferedImage getBackgroundImg() {
		return backgroundImg;
	}

	public int getMenuX() {
		return menuX;
	}

	public int getMenuY() {
		return menuY;
	}

	public int getMenuWidth() {
		return menuWidth;
	}

	public int getMenuHeight() {
		return menuHeight;
	}
}
