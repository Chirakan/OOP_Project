package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Play;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
	
	private Play playing;
	private BufferedImage[][] crabarr;
	private ArrayList<Crab> crabbies = new ArrayList<>();
	
	public EnemyManager(Play playing) {
		this.playing = playing;
		loadEnemyImg();
		addEnemies();
	}
	
	private void addEnemies() {
		crabbies = LoadSave.GetCrabs();
	}

	public void update() {
		for(Crab c : crabbies)
			c.update();
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawCrab(g,xLvlOffset);
	}
	
	private void drawCrab(Graphics g, int xLvlOffset) {
		for(Crab c : crabbies) {
			g.drawImage(crabarr[c.getEnemyState()][c.getAniIndex()], (int)c.getHitbox().x - xLvlOffset, (int)c.getHitbox().y, CRAB_WIDTH, CRAB_HEIGHT, null);
		}
			
		
	}

	private void loadEnemyImg() {
		crabarr = new BufferedImage[4][4];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CRAB_SPRITE);
		for(int i = 0; i < crabarr.length; i++)
			for(int j = 0; j< crabarr[i].length; j++)
				crabarr[i][j] = temp.getSubimage(j * CRAB_WIDTH_DEFAULT, j * CRAB_HEIGHT_DEFAULT, CRAB_WIDTH_DEFAULT, CRAB_HEIGHT_DEFAULT);
		
	}
}
