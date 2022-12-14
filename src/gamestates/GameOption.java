package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Main.Game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButton.*;

public class GameOption extends State implements Statemethods {

	private AudioOptions audioOptions;
	private BufferedImage optionsBackgroundImg, bg;
	private int bgX, bgY, bgW, bgH;
	private String[] bgarr = {LoadSave.PLAYING_BG_IMG1, LoadSave.PLAYING_BG_IMG2, LoadSave.PLAYING_BG_IMG3};
	private UrmButton menuB;

	public GameOption(Game game) {
		super(game);
		loadImgs();

		loadButton();
		audioOptions = game.getAudioOptions();
		
	}

	private void loadButton() {
		int menuX = (int) (387 * Game.SCALE);
		int menuY = (int) (325 * Game.SCALE);

		menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
	}

	private void loadImgs() {
		optionsBackgroundImg = LoadSave.getSpriteAtlas(LoadSave.OPTION_BACKGROUND);
		bg = LoadSave.getSpriteAtlas(bgarr[(int)(Math.random()*100%3)]);

		bgW = (int) (optionsBackgroundImg.getWidth() * Game.SCALE);
		bgH = (int) (optionsBackgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (33 * Game.SCALE);
	}
	
	@Override
	public void update() {
		menuB.update();
		audioOptions.update();

	}

	@Override
	public void draw(Graphics g) {
		drawBG(g);
		g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

		menuB.draw(g);
		audioOptions.draw(g);

	}
	public void drawBG(Graphics g) {
		g.drawImage(bg, 0 , 0, (int)(1788*1.5), (int)(Game.GAME_HEIGHT), null); // change BG
	}
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		} else
			audioOptions.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, menuB)) {
			if (menuB.isMousePressed())
				Gamestate.state = Gamestate.MENU;
		} else
			audioOptions.mouseReleased(e);

		menuB.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		menuB.setMouseOver(false);

		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			Gamestate.state = Gamestate.MENU;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}