package utilz;

import Main.Game;

public class Constants {
	
	public static class EnemyConstants{
		public static final int CRAB = 0;
		
		public static final int IDLE = 0;
		public static final int RUN = 1;
		public static final int DEAD = 2;
		public static final int ATT = 3;
		public static final int HIT = 4;

		public static final int CRAB_WIDTH_DEFAULT = 64;
		public static final int CRAB_HEIGHT_DEFAULT = 64;
		
		public static final int CRAB_WIDTH = (int)(CRAB_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRAB_HEIGHT = (int)(CRAB_HEIGHT_DEFAULT * Game.SCALE);
//		public static final int crabbies = (int)(CRAB_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int CRAB_DRAWOFFSET_X = (int)(26 * Game.SCALE);
		public static final int CRAB_DRAWOFFSET_Y = (int)(9 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case CRAB:
				switch (enemy_state) {
				case IDLE:
				case RUN:
				case HIT:
				case DEAD:
					return 4;
				case ATT:
					return 5;
				}
			}
			return 0;
		} public static int getMaxHealth(int enemy_type) {
			switch(enemy_type) {
			case CRAB:
				return 10;
			default:
				return 10;
			}
		}
		
		public static int getEnemyDmg(int enemy_type) {
			switch(enemy_type) {
			case CRAB:
				return 15;
			default:
				return 0;
			}
		}
	}

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
		public static class PauseButton{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT*Game.SCALE);
		}
		public static class URMButton{
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE = (int)(URM_SIZE_DEFAULT*Game.SCALE);
		}
		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}
	
	public static class Environment {
		public static final int SAND_WIDTH_DEFAULT = 256;
		public static final int SAND_HEIGHT_DEFAULT = 192;
		public static final int FORE_WIDTH_DEFAULT = 512;
		public static final int FORE_HEIGHT_DEFAULT = 192;


		public static final int SAND_WIDTH = (int) (SAND_WIDTH_DEFAULT * Game.SCALE);
		public static final int SAND_HEIGHT = (int) (SAND_HEIGHT_DEFAULT * Game.SCALE);
		public static final int FORE_WIDTH = (int) (FORE_WIDTH_DEFAULT * Game.SCALE);
		public static final int FORE_HEIGHT = (int) (FORE_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int RIGHT = 1;
		public static final int UP = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int RUN = 1;
		public static final int ATT_1 = 2;
		public static final int JUMP = 1;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		public static int GetSpriteAmount(int player_action) { //คืนค่าจำนวนเฟรมทั้งหมดในแต่ละ action 
			switch (player_action) {
			case RUN:
				return 8;
			case IDLE:
				return 7;
			case HIT:
				return 4;
			case DEAD:
				return 8;
			case ATT_1:
			default:
				return 6;
			}
		}
	}
}
