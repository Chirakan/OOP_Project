package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
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

	public void update(int[][] lvlData, Player player) {
		for(Crab c : crabbies)
			if(c.isActive())
				c.update(lvlData, player);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawCrab(g, xLvlOffset);
	}
	
	private void drawCrab(Graphics g, int xLvlOffset) {
		for(Crab c : crabbies) {
			if(c.isActive())
				g.drawImage(crabarr[c.getEnemyState()][c.getAniIndex()], (int)c.getHitbox().x - xLvlOffset - CRAB_DRAWOFFSET_X + c.flipX(), (int)c.getHitbox().y - CRAB_DRAWOFFSET_Y, CRAB_WIDTH * c.flipW(), CRAB_HEIGHT - 25, null);
			c.drawHitbox(g, xLvlOffset);
			c.drawAttackBox(g, xLvlOffset);
		}
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for(Crab c: crabbies) {
			if(c.isActive())
				if(attackBox.intersects(c.getHitbox()));{
					c.hurt(10);
					return;
				}
		}
	}

	private void loadEnemyImg() {
		crabarr = new BufferedImage[5][5];
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.CRAB_SPRITE);
		for (int j = 0; j < crabarr.length; j++)
			for (int i = 0; i < crabarr[j].length; i++)
				crabarr[j][i] = temp.getSubimage(i * CRAB_WIDTH_DEFAULT, j * CRAB_HEIGHT_DEFAULT, CRAB_WIDTH_DEFAULT, CRAB_HEIGHT_DEFAULT);
		
	}
	
	public void resetAllEnemies() {
		for (Crab c : crabbies)
			c.resetEnemy();
	}
}
