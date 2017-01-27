package logme;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


/**
 * x, y 是空隙中间位置.
 */
public class Pipe implements GameObject{
	BufferedImage image;
	// 以柱子的中间作为柱子的位置
	int x;
	int y; 
	int width;
	private int height;
	int gap = 109;
	private int screenWidth;
	private int screenHeight;
	private int pipeSize;
	private boolean grow;
	private Random r = new Random();
	private int growSpeed;
	private final int XPOS_BEGIN;
	private final int YPOS_BEGIN;
	//grow pos
	double threesold;
	public Pipe(int x, int screenWidth, int screenHeight, String pipe) throws IOException {
		this.image = ImageIO.read(new File(pipe));
		this.width = image.getWidth();
		this.height = image.getHeight();
		//gap is 109
		this.gap = 109;
		XPOS_BEGIN = this.x = x;
		//up and down is 140
		this.pipeSize = 140;
		YPOS_BEGIN = this.y = r.nextInt(pipeSize) + pipeSize;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		grow = r.nextBoolean();
		grow = true;
		growSpeed = r.nextInt(5) - 2;
		threesold = r.nextDouble();
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		x = XPOS_BEGIN;
		y = YPOS_BEGIN;
	}
	@Override
	public void step(float worldSpeed) {
		x -= worldSpeed;
		if(grow && x < screenWidth * threesold && y > pipeSize && y < pipeSize * 2){
			y += growSpeed;
		}
		if (x <= -width) {
			grow = r.nextBoolean();
			growSpeed = r.nextInt(5) - 2;
			threesold = r.nextDouble();
			x = screenWidth + width / 2;
			y = r.nextInt(140) + 140;
		}
	}

	public void paint(Graphics g) {
		g.drawImage(image, x - width / 2, y - height / 2, null);
	}


	@Override
	public void updateEvent() {
		
	}
}
