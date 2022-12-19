package obj;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Player;
import gamestates.Play;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

	private Play playing;
	private BufferedImage[] potionImgs;
	private BufferedImage[] spikeImgs;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	
	public ObjectManager(Play playing) {
		this.playing = playing;
		loadImgs();
	}
	
	public void checkSpikesTouched(Player p) {
		for (Spike s : spikes)
			if (s.getHitbox().intersects(p.getHitbox()))
				p.kill();
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
		potions = new ArrayList<>(newLevel.getPotions());
		spikes = newLevel.getSpikes();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[4];
		for(int i = 0; i < potionImgs.length; i++)
			potionImgs[i] = potionSprite.getSubimage(64 * i, 0, 64, 64);
		
		BufferedImage spikeSprite = LoadSave.getSpriteAtlas(LoadSave.TRAP_ATLAS);
		spikeImgs = new BufferedImage[4];
		for(int i = 0; i < spikeImgs.length; i++)
			spikeImgs[i] = potionSprite.getSubimage(64 * i, 0, 64, 64);
		
	}
	
	public void update() {
		for(Potion p: potions)
			if(p.isActive())
				p.update();
		
		for(Spike s: spikes)
			s.update();
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawTrap(g, xLvlOffset);
	}

	private void drawTrap(Graphics g, int xLvlOffset) {
		for (Spike s : spikes) {
			g.drawImage(potionImgs[s.getAniIndex()], (int) (s.getHitbox().x - s.getxDrawOffset() - xLvlOffset), 
					(int) (s.getHitbox().y - s.getyDrawOffset()), 
					(int)(SPIKE_WIDTH * 0.5), 
					(int)(SPIKE_HEIGHT * 0.5),null);
		}
		
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
	
	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for (Potion p : potions)
			p.reset();
	}
}
