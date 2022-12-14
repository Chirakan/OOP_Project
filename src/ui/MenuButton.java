package ui;

import gamestates.Gamestate;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.Buttons.*;
import utilz.LoadSave;

public class MenuButton {
	private int x, y, rowIndex, index;
	private int xCenter = B_WIDTH / 2;
	private Gamestate state;

	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;

	public MenuButton(int x, int y, int rowIndex, Gamestate state) {
		this.x = x;
		this.y = y;
		this.rowIndex = rowIndex;
		this.state = state;
		loadImgs();
		initBounds();
	}

	private void initBounds() {
		bounds = new Rectangle(x - xCenter, y, B_WIDTH, B_HEIGHT);
	}

	private void loadImgs() {
		imgs = new BufferedImage[3];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.MENU_BUTTONS);
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT,
					B_HEIGHT_DEFAULT);
	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], x - xCenter, y, B_WIDTH, B_HEIGHT, null);
	}

	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}

	public void applyGamestate() {
		Gamestate.state = state;
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getIndex() {
		return index;
	}

	public int getxCenter() {
		return xCenter;
	}

	public Gamestate getState() {
		return state;
	}

	public BufferedImage[] getImgs() {
		return imgs;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public Rectangle getBounds() {
		return bounds;
	}
}
