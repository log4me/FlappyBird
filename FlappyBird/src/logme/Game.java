package logme;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;
public class Game extends JPanel {

	private Pipe Pipe1;
	private Pipe Pipe2;
	private Bird []birds;
	private Bird currentBird;
	private Ground ground;
	private BufferedImage []background;
	private BufferedImage currentBackground;
	private BufferedImage gameoverImg;
	private BufferedImage startImg;
	private final static int screenWidth = 325;
	private final static int screenHeight = 500;
	private boolean start;
	private int score;
	private int higScore = 0;
	private int volecity;
	private boolean gameOver;
	private final int FPS;
	private final int DELAY_TIME;
	private SoundsManager soundsManager;
	private Random random = new Random();
	private final int PIPE_BEGIN=320;
	private final int PIPE_WIDTH = 50;
	private final int PIPE_GAP = 180;
	public Game() throws IOException {
	
		//load Resource
		background = new BufferedImage[2];
		background[0] = ImageIO.read(new File("res/image/bg_day.png"));
		background[1] = ImageIO.read(new File("res/image/bg_night.png"));
		currentBackground = background[random.nextInt(2)];
		gameoverImg = ImageIO.read(new File("res/image/gameover.png"));
		startImg = ImageIO.read(new File("res/image/start.png"));
		birds = new Bird[2];
		birds[0] = new Bird("res/image/blue2.png", "res/image/blue2.png", "res/image/blue1.png");
		birds[1] = new Bird("res/image/orange1.png", "res/image/orange2.png", "res/image/orange3.png");
		currentBird = birds[random.nextInt(2)];
		Pipe1 = new Pipe(PIPE_BEGIN + PIPE_WIDTH, screenWidth, screenHeight, "res/image/pipe.png");
		Pipe2 = new Pipe(PIPE_BEGIN + PIPE_WIDTH + PIPE_GAP, screenWidth, screenHeight, "res/image/pipe.png");
		ground = new Ground(screenWidth, screenHeight, "res/image/ground.png");
		score = higScore = 0;
		gameOver = false;
		start = false;
		volecity = 0;
		FPS = 60;
		DELAY_TIME = 1000 / FPS;
		soundsManager = SoundsManager.getInstance();
	}
	public void action() throws Exception {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (gameOver) {
					start = false;
					gameOver = false;
					currentBird = birds[random.nextInt(2)];
					currentBackground = background[random.nextInt(2)];
					currentBird.reset();
					Pipe1.reset();
					Pipe2.reset();
					return;
				}
				start = true;
				if(volecity == 0){
					volecity = 2;
					score = 0;
				}
				currentBird.updateEvent();
				soundsManager.wing();
			}
		});
		requestFocus();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (gameOver) {
						score = higScore;
						return;
					}
					start = true;
					score = 0;
					if(volecity == 0){
						volecity = 2;
					}
					
					currentBird.updateEvent();
					soundsManager.wing();
					
				}
			}
		});
		//main loop
		while (true) {
			long startTime = System.currentTimeMillis(); 
			if (start && !gameOver) {
				currentBird.step(volecity);
				Pipe1.step(volecity);
				Pipe2.step(volecity);
				
				// collision detect
				if (currentBird.pass(Pipe1, Pipe2)) {
					soundsManager.score();
					score++;
					volecity = (200 + score) / 100;
				}
				if (currentBird.hit(Pipe1, Pipe2, ground)) {
					soundsManager.hit();
					start = false;
					gameOver = true;
					if(score > higScore){
						higScore = score;
					}
					volecity = 0;
					soundsManager.die();
					
				}
				
			}
			ground.step(volecity);
			repaint();
			long runTime = System.currentTimeMillis() - startTime; 
			if(runTime < DELAY_TIME){
				Thread.sleep(DELAY_TIME - runTime);
			}
		}

	}

	@Override
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints qualityHints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);
		
		g.drawImage(currentBackground, 0, 0, null);
		//Draw Pipe
		Pipe1.paint(g);
		Pipe2.paint(g);
		
		ground.paint(g);
		// Draw Score
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(score + "", 30, 50);
		// Draw bird
		currentBird.paint(g);
		// Game over
		if (gameOver) {
			g.drawImage(gameoverImg, 0, 0, null);
			return;
		}
		if (!start) {
			g.drawImage(startImg, 0, 0, null);
		}
	}

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Flappy Bird");
		Game Game = new Game();
		frame.add(Game);
		frame.setSize(screenWidth, screenHeight);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		Game.action();
	}
}
