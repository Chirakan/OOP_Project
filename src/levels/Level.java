package levels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import entities.Crab;
import obj.Cannon;
import obj.Potion;
import obj.Spike;
import utilz.HelpMethods;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetCrabs;
public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crab> crabs;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createPotions();
		createSpikes();
		createCannons();
//		createBG();
		calcLvlOffsets();
	}
	
	private void createCannons() {
		cannons = HelpMethods.GetCannons(img);
		
	}

	private void createSpikes() {
		spikes = HelpMethods.GetSpikes(img);
	}

	private void createPotions() {
		potions = HelpMethods.GetPotions(img);
	}

	private void createBG() {
		
		
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		crabs = GetCrabs(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
		
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData() {
		return lvlData;
	}
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<Crab> getCrabs() {
		return crabs;
	}
	
	public ArrayList<Potion> getPotions() {
		return potions;
	}
	
	public ArrayList<Spike> getSpikes() {
		return spikes;
	}
	
	public ArrayList<Cannon> getCannons() {
		return cannons;
	}
}
