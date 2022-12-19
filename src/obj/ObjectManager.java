package obj;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Play;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

	private Play playing;
	private BufferedImage[] potionImgs;
	private ArrayList<Potion> potions;
	
	public ObjectManager(Play playing) {
		this.playing = playing;
		loadImgs();
	}
	
	public void checkObjectTouched(Rectangle2D.Float hitbox) {
		for (Potion p : potions)
			if (p.isActive()) {
				if (hitbox.intersects(p.getHitbox())) {
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
	}
	
	public void applyEffectToPlayer(Potion p) {
		if (p.getObjType() == RED_POTION)
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
	}
	
	public void loadObjects(Level newLevel) {
		potions = newLevel.getPotions();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[4];
		for(int i = 0; i < potionImgs.length; i++)
			potionImgs[i] = potionSprite.getSubimage(64 * i, 0, 64, 64);
		
	}
	
	public void update() {
		for(Potion p: potions)
			if(p.isActive())
				p.update();
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
	}

	private void drawPotions(Graphics g, int xLvlOffset) {
		for (Potion p : potions)
			if (p.isActive()) {
				g.drawImage(potionImgs[p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), 
						(int) (p.getHitbox().y - p.getyDrawOffset()), 
						(int)(POTION_WIDTH * 0.5), 
						(int)(POTION_HEIGHT * 0.5),null);
			}
		
	}
}
