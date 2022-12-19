package obj;

import Main.Game;

public class Cannon extends GameObject{
	
	private int tileY;

	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY = y / Game.TILES_SIZE;
		initHitbox(60, 40);
//		hitbox.x -= (int)(4*Game.SCALE);
//		hitbox.y;
	}
	
	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	public int getTileY() {
		return tileY;
	}

}
