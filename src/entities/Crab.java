package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;

import Main.Game;

public class Crab extends Enemy{
	
	private Rectangle2D.Float attackBox;
	private int attackBoxOffsetX;

	public Crab(float x, float y) {
		super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
		initHitbox(x, y, (int)(32 * Game.SCALE), (int)(19 * Game.SCALE));
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int)(35 * Game.SCALE), (int)(19 * Game.SCALE));
		attackBoxOffsetX = (int)(Game.SCALE * 25);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}

	private void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate)
			firstUpdateCheck(lvlData);
		if(inAir)
			updateInAir(lvlData);
		else {
			switch (enemyState) {
			case IDLE:
				newState(RUN);
				break;
			case RUN:
				if (canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
				if (isPlayerCloseForAttack(player))
					newState(ATT);
				}
				move(lvlData);
				break;
			case ATT:
				if (aniIndex == 0)
					attackChecked = false;
				if (aniIndex == 3 && !attackChecked)
					checkPlayerHit(attackBox, player);
				
				break;
			case HIT:
				
				break;
			}
		}
		
	}
	
	public void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.RED);
		g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
	}
	
	public int flipX() {
		if(walkDir == RIGHT)
			return width;
		else
			return 0;
	}
	
	public int flipW() {
		if(walkDir == RIGHT)
			return -1;
		else
			return 1;
	}
}
