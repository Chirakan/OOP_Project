package utilz;

import Main.Game;

public class Constants {
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
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
		public static final int ATT_2 = 3;
		public static final int ATT_3 = 4;
		public static final int JUMP = 5;
		public static final int HIT = 6;
		public static final int DYING = 7;
		
		public static int GetSpriteAmount(int player_action) { //คืนค่าจำนวนเฟรมทั้งหมดในแต่ละ action 
			switch (player_action) {
			case RUN:
				return 8;
			case IDLE:
				return 13;
			case HIT:
				return 4;
			case JUMP:
				return 6;
			case DYING:
				return 7;
			case ATT_1:
			case ATT_2:
			case ATT_3:
			default:
				return 10;
			}
		}
	}
}
