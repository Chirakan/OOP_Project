package obj;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import entities.Player;
import gamestates.Play;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.CanCannonSeePlayer;

public class ObjectManager {

	private Play playing;
	private BufferedImage[] potionImgs, spikeImgs, cannonImgs;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	
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
		cannons = newLevel.getCannons();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.getSpriteAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[4];
		for(int i = 0; i < potionImgs.length; i++)
			potionImgs[i] = potionSprite.getSubimage(64 * i, 0, 64, 64);
		
		BufferedImage spikeSprite = LoadSave.getSpriteAtlas(LoadSave.TRAP_ATLAS);
		spikeImgs = new BufferedImage[4];
		for(int i = 0; i < spikeImgs.length; i++)
			spikeImgs[i] = spikeSprite.getSubimage(64 * i, 0, 64, 64);
		
		BufferedImage cannonSprite = LoadSave.getSpriteAtlas(LoadSave.OCTOPUS_ATLAS);
		cannonImgs = new BufferedImage[7];
		for(int i = 0; i < cannonImgs.length; i++)
			cannonImgs[i] = cannonSprite.getSubimage(64 * i, 0, 64, 64);
		
	}
	
	public void update(int[][] lvlData, Player player) {
		for(Potion p: potions)
			if(p.isActive())
				p.update();
		
		for(Spike s: spikes)
			s.update();
		
		updateCannons(lvlData, player);
	}
	
	private boolean isPlayerInRange(Cannon c, Player player) {
		int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
		return absValue <= Game.TILES_SIZE * 5;
	}
	
	private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
		if(c.getObjType() == OCT_L) {
			if(c.getHitbox().x > player.getHitbox().x)
				return true;
		} else if(c.getHitbox().x < player.getHitbox().x)
			return true;
		return false;
	}
	
	private void updateCannons(int[][] lvlData, Player player) {
		for(Cannon c : cannons) {
			if (!c.doAnimation)
				if(c.getTileY() == player.getTileY())
					if(isPlayerInfrontOfCannon(c, player))
						if(CanCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY())) {
							shootCannon(c);
						}
			c.update();
		}	
		
	}

	private void shootCannon(Cannon c) {
		c.setAnimation(true);
		
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawTrap(g, xLvlOffset);
		drawCannons(g, xLvlOffset);
	}

	private void drawCannons(Graphics g, int xLvlOffset) {
		for(Cannon c : cannons) {
			int x = (int)(c.getHitbox().x - xLvlOffset);
			int width = OCT_WIDTH;
			if(c.getObjType() == OCT_R) {
				x += width;
				width *= -1;
			}
			g.drawImage(cannonImgs[c.getAniIndex()], 
					x, (int)(c.getHitbox().y), 
					width, (int)(OCT_HEIGHT), null);
		}
	}

	private void drawTrap(Graphics g, int xLvlOffset) {
		for (Spike s : spikes) {
			g.drawImage(spikeImgs[s.getAniIndex()], (int) (s.getHitbox().x - s.getxDrawOffset() - xLvlOffset), 
					(int) (s.getHitbox().y - s.getyDrawOffset()), 
					(int)(SPIKE_WIDTH * 1), 
					(int)(SPIKE_HEIGHT * 1),null);
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
		for(Cannon c : cannons)
			c.reset();
	}
}
