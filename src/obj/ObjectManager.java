package obj;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import entities.Player;
import gamestates.Play;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Projectiles.*;

public class ObjectManager {

	private Play playing;
	private BufferedImage[] potionImgs, spikeImgs, cannonImgs;
	private BufferedImage cannonBallImg, signImg;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private ArrayList<Sign> sign;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	
	public ObjectManager(Play playing) {
		this.playing = playing;
		loadImgs();
	}
	
	public void checkSpikesTouched(Player p) {
		for (Spike s : spikes)
			if (s.getHitbox().intersects(p.getHitbox()))
				p.kill();
	}
	public void checkSignTouched(Rectangle2D.Float hitbox) {
		for (Sign s : sign)
			if (hitbox.intersects(s.getHitbox())) {
				playing.setLvlCompleted(true);
			}
			
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
		sign = newLevel.getSign();
		projectiles.clear();
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

		cannonBallImg = LoadSave.getSpriteAtlas(LoadSave.INK_ATLAS);
		signImg = LoadSave.getSpriteAtlas(LoadSave.SIGN);
	}
	
	public void update(int[][] lvlData, Player player) {
		for(Potion p: potions)
			if(p.isActive())
				p.update();
		
		for(Spike s: spikes)
			s.update();
		
		updateCannons(lvlData, player);
		updateProjectiles(lvlData, player);
	}

	private void updateProjectiles(int[][] lvlData, Player player) {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.updatePos();
				if (p.getHitbox().intersects(player.getHitbox())) {
					player.changeHealth(-25);
					p.setActive(false);
				} else if (IsProjectileHittingLevel(p, lvlData))
					p.setActive(false);
			}
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
		for (Cannon c : cannons) {
			if (!c.doAnimation)
				if (c.getTileY() == player.getTileY())
					if (isPlayerInRange(c, player))
						if (isPlayerInfrontOfCannon(c, player))
							if (CanCannonSeePlayer(lvlData, player.getHitbox(), c.getHitbox(), c.getTileY()))
								c.setAnimation(true);

			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0)
				shootCannon(c);
		}
		
	}

	private void shootCannon(Cannon c) {
		int dir = 1;
		if (c.getObjType() == OCT_L)
			dir = -1;

		projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawTrap(g, xLvlOffset);
		drawCannons(g, xLvlOffset);
		drawSign(g, xLvlOffset);
		drawProjectiles(g, xLvlOffset);
	}

	private void drawProjectiles(Graphics g, int xLvlOffset) {
		for (Projectile p : projectiles)
			if (p.isActive())
				g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
		
	}
	private void drawSign(Graphics g, int xLvlOffset) {
		for(Sign s : sign) {
			g.drawImage(signImg, (int) (s.getHitbox().x - s.getxDrawOffset() - xLvlOffset), 
					(int) (s.getHitbox().y - s.getyDrawOffset()), 
					(int)(SIGN_WIDTH * 1), 
					(int)(SIGN_HEIGHT * 1),null);
		}
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
					x, (int)(c.getHitbox().y - (Game.SCALE*15)), 
					width, (int)(OCT_HEIGHT*1.5), null);
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
