package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.Game;
import entities.Crab;
import static utilz.Constants.EnemyConstants.*;

public class LoadSave {
	public static String PLAYER_ATLAS = "aek.png";
	public static String LEVEL_ATLAS = "sprite.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data.png";
//	public static final String LEVEL_ONE_DATA = "level_1.png";
//	public static final String LEVEL_TWO_DATA = "level_2.png";
//	public static final String LEVEL_THREE_DATA = "level_3.png";
	
	public static final String PLAYING_BG_IMG1 = "backsrceen1.png";
	public static final String PLAYING_BG_IMG2 = "backsrceen2.png";
	public static final String PLAYING_BG_IMG3 = "backsrceen3.png";
	
	public static final String CRAB_SPRITE = "ene.png";
	public static final String STATUS_BAR = "bar.png";
	
	// UI INTERFACE
	public static final String MENU_BACKGROUND = "m3.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTON = "b3.png";
	public static final String URM_BUTTON = "b4.png";
	public static final String VOLUME_BUTTONS = "b2.png";
	public static final String COMPLETED_IMG = "completed_sprite.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String DEATH_SCREEN = "m1.png";
	
	public static final String POTION_ATLAS = "heal.png";
	public static final String TRAP_ATLAS = "trap.png";
	public static final String OCTOPUS_ATLAS = "muak.png";
	public static final String INK_ATLAS = "ink.png";
	
	public static BufferedImage getSpriteAtlas(String filename) {
		BufferedImage img = null;
		try(InputStream is = LoadSave.class.getResourceAsStream("/"+filename)) {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static BufferedImage[] getAllLevels() {
		URL url = LoadSave.class.getResource("/maps");
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];
		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];
			}
		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;
	}
}
