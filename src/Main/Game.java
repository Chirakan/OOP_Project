package Main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gamestates.GameOption;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Play;
import ui.AudioOptions;

import utilz.LoadSave;

public class Game implements Runnable{
	private GameWindow window;
	private GamePanel p;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Play playing;
	private Menu menu;
	private GameOption option;
	private AudioOptions audioOptions;
	private AudioPlayer audioPlayer;


	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.75f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE); //32
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; // 32 * 26 = 832
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;// 32 * 14 = 448
	
	public static void main(String[] args) {
		Game game = new Game();
		game.startGameLoop();
	}
	
	public Game() {

		initClasses();
		
		
		p = new GamePanel(this);
		window = new GameWindow(p);
		p.setFocusable(true);
		p.requestFocus();
		
		startGameLoop();
	}
	
	private void initClasses() {
		audioOptions = new AudioOptions(this);
		audioPlayer = new AudioPlayer();
		menu = new Menu(this);
		playing = new Play(this);
		option = new GameOption(this);
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		switch(Gamestate.state) {
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTIONS:
				option.update();
				break;
			case QUIT:
			default:
				System.exit(0);
				break;
			}
	}
	
	public void render(Graphics g) {
		switch(Gamestate.state) {
			case MENU:
				menu.draw(g);
				break;
			case PLAYING:
				playing.draw(g);
				break;
			case OPTIONS:
				option.draw(g);
				break;
			default:
				break;
			}
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				p.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}
	}
	
	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Play getPlay() {
		return playing;
	}
	public AudioOptions getAudioOptions() {
		return audioOptions;
	}
	public GameOption getOption() {
		return option;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
}

