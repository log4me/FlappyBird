package logme;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird implements GameObject {
	//Bird position
	private int x;
	private int y;
	// Bird angle
	private double angle;
	// bird image
	private BufferedImage[] images;
	private BufferedImage image;
	// image index
	private int index;
	// gravity
	private final double gravity;
	private final double intervalTime;
	//begin veocity up
	private final double v0;
	//current veocity up
	private double speed;
	
	//size of bird, square
	private int width;
	private int height;
	private final double SPEED_BEGIN;
	private final int XPOS_BEGIN;
	private final int YPOS_BEGIN;
	public  Bird(String frame1, String frame2, String frame3) throws IOException {
		// TODO Auto-generated constructor stub
		this.gravity = 4.9f; 
		this.intervalTime = 0.2; 
		SPEED_BEGIN = this.v0 = 20; 
		XPOS_BEGIN = this.x = 100; 
		YPOS_BEGIN = this.y = 270; 
		// load pic
		this.images = new BufferedImage[3];
		this.images[0] = ImageIO.read(new File(frame1));
		this.images[1] = ImageIO.read(new File(frame2));
		this.images[2] = ImageIO.read(new File(frame3));
		image = images[0];
		this.index = 0;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	public void reset(){
		x = XPOS_BEGIN;
		y = YPOS_BEGIN;
		index = 0;
		angle = 0;
		speed = SPEED_BEGIN;
	}
	@Override
	public void step(float worldSpeed) {
		// begin velocity 
		double v0 = speed;
		// velocity after update
		
		double v = v0 - gravity * intervalTime;
		speed = v;
		
		// update bird
		double distance = v0 * intervalTime - 0.5 * gravity * intervalTime * intervalTime;
		y = Math.abs(y - (int) distance);
		angle = -Math.atan(distance / 8);
		index++;
		image = images[(index / 8) % 3];
	}

	public void updateEvent(){
		speed = v0;
	}
	public void paint(Graphics g) {
		//Draw bird
				Graphics2D g2 = (Graphics2D) g;
				
				g2.rotate(angle, this.x, this.y);
				
				int x = this.x - image.getWidth() / 2;
				int y = this.y - image.getHeight() / 2;
				g.drawImage(image, x, y, null);
				
				g2.rotate(-angle, this.x, this.y);
	}
	public boolean hit(Pipe pipe1, Pipe pipe2, Ground ground) {
		// hit the ground
		if (y + height >= ground.y) {
			return true;
		}
		// hit pipe
		return hit(pipe1) || hit(pipe2);
	}

	
	public boolean hit(Pipe col) {
		// AABB collision detect
		if (x > col.x - col.width / 2 - width / 2
				&& x < col.x + col.width / 2 + width / 2) {
			if (y > col.y - col.gap / 2 + height / 2
					&& y < col.y + col.gap / 2 - height / 2) {
				return false;
			}
			return true;
		}
		return false;
	}
	//pass the pipe , add score
	public boolean pass(Pipe pipe1, Pipe pipe2) {
		return pipe1.x == x || pipe2.x == x;
	}
}
