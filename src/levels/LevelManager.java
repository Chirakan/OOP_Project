package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import gamestates.Gamestate;
import utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	public void loadNextLevel() {
		lvlIndex++;
		if (lvlIndex >= levels.size()) {
			lvlIndex = 0;
			Gamestate.state = Gamestate.STORY;
		}

		Level newLevel = levels.get(lvlIndex);
		game.getPlay().getEnermymanager().loadEnemies(newLevel);
		game.getPlay().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlay().setMaxLvlOffsetX(newLevel.getLvlOffset());
		game.getPlay().getObjManager().loadObjects(newLevel);
	}

	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.getAllLevels();
		for (BufferedImage img : allLevels)
			levels.add(new Level(img));
		
	}
	

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS); //ดึงรูปจากโฟเดอร์ res มาใช้
		levelSprite = new BufferedImage[48]; // สร้าง Buffer สำหรับเก็บรูป 
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i; //กำหนก index ในการเก็บรูป จากซ้ายไปขวา บนลงล่าง
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}
	
	public void update() {

	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	public int getLvlIndex(){
		return lvlIndex;
	}
	public int getAmountOfLevels() {
		return levels.size();
	}
}
