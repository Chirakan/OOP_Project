package entities;

import static utilz.Constants.EnemyConstants.*;

import Main.Game;

public class Crab extends Enemy{

	public Crab(float x, float y) {
		super(x, y, CRAB_WIDTH, CRAB_HEIGHT, CRAB);
		initHitbox(x, y, (int)(23 * Game.SCALE), (int)(30 * Game.SCALE));
	}
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();
	}

	private void updateMove(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);
		}
			
		if(inAir)
			updateInAir(lvlData);
		else {
			switch (enemyState) {
			case IDLE:
				enemyState = RUN;
				break;
			case RUN:
				if (canSeePlayer(lvlData, player))
					turnTowardsPlayer(player);
				if (isPlayerCloseForAttack(player))
					newState(ATT);

				move(lvlData);
				break;
			}
		}
		
	}
}
