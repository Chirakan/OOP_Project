package Main;

import java.awt.*;
import javax.swing.JPanel;
import input.Keyboardinputs;
import input.Mouseinputs;
import static Main.Game.GAME_WIDTH;
import static Main.Game.GAME_HEIGHT;
public class GamePanel extends JPanel{
	private Mouseinputs mouse;
	private Game game;

	public GamePanel(Game game) {
		mouse = new Mouseinputs(this);
		this.game = game;
		
		setPanelSize();
		this.addKeyListener(new Keyboardinputs(this));
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}
	
	
	public void updateGame() {

	}

	private void setPanelSize() { // set หน้าต่าง Panel
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		this.setPreferredSize(size);
	}
	

	public void paintComponent(Graphics g) { //เอาภาพที่นำมาใช้นำมาแสดงบน Panel
		super.paintComponent(g);
		
//		g.setColor(Color.white);
//		for (int i = 0; i < 64; i++)
//			for (int j = 0; j < 40; j++)
//				g.fillRect(i * 20, j * 20, 20, 20);

		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}

}
