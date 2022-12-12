package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Main.Game;

public class LoadSave {
	public static String PLAYER_ATLAS = "Ethan.png";
	public static String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	
	public static BufferedImage getSpriteAtlas(String filename) {
		BufferedImage img = null;
		try(InputStream is = LoadSave.class.getResourceAsStream("/"+filename)) {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static int[][] getLevelData(){
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA); //ดึงรูปจากโฟลเดอร์ res

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
