package obj;

import Main.Game;

public class Spike extends GameObject{

	public Spike(int x, int y, int objType) {
		super(x, y, objType);
		
		initHitbox(56, 59);
		xDrawOffset = (int)(Game.SCALE * 8);
		yDrawOffset = (int)(Game.SCALE * 5);
		hitbox.y += yDrawOffset;
	}
	
	public void update() {
		updateAnimationTick();
	}

}
