package obj;

import java.awt.geom.Rectangle2D;

import Main.Game;
import static utilz.Constants.Projectiles.*;

public class Projectile {
	private Rectangle2D.Float hitbox;
	private int dir;
	private boolean active = true;

	public Projectile(int x, int y, int dir) {
		int xOffset = (int) (15 * Game.SCALE);
		int yOffset = (int) (10 * Game.SCALE);

		if (dir == 1)
			xOffset = (int) (25 * Game.SCALE);

		hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, (int)(1*CANNON_BALL_WIDTH), (int)(1*CANNON_BALL_HEIGHT));
		this.dir = dir;
	}

	public void updatePos() {
		hitbox.x += dir * SPEED;
	}

	public void setPos(int x, int y) {
		hitbox.x = x;
		hitbox.y = y;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
}
