package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.Game;
import entities.Crab;
import static utilz.Constants.EnemyConstants.*;

public class LoadSave {
	public static String PLAYER_ATLAS = "Ethan.png";
	public static String LEVEL_ATLAS = "outside_sprites.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PLAYING_BG_IMG = "far.png";
	public static final String SAND_BG = "sand.png";
	public static final String FORE_BG = "foregound-merged.png";
	public static final String CRAB_SPRITE = "Crab.png";
	
	public static BufferedImage getSpriteAtlas(String filename) {
		BufferedImage img = null;
		try(InputStream is = LoadSave.class.getResourceAsStream("/"+filename)) {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static ArrayList<Crab> GetCrabs() {
		BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<Crab> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CRAB)
					list.add(new Crab(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;

	}
	
	public static int[][] getLevelData(){
		BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA); //ดึงรูปจากโฟลเดอร์ res
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for (int j = 0; j < img.getHeight(); j++) 
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48) // หากจำนวน sprite เกิน ให้รีเซ็ตค่า
					value = 0;
				lvlData[j][i] = value; //เก็บค่าสีไว้ใน array
			}
		return lvlData;
 
	}
}
