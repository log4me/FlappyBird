package logme;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ground implements GameObject{
	BufferedImage image;


	int x;
	int y;
	int width;
	int height;
	int screenWidth;
	int screenHeight;
	private final int XPOS;
	private final int  YPOS;
	public Ground(int screenWidth, int screenHeight, String ground) throws IOException {
		this.image = ImageIO.read(new File(ground));
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		XPOS = this.x = 0;
		YPOS = this.y = screenHeight - this.height - 22;
	}

	public void reset(){
		x = XPOS;
		y = YPOS;
	}
	public void paint(Graphics g) {
		g.drawImage(image, x, y, null);
	}
	@Override
	//UPdate ground by time
	public void step(float worldSpeed) {
		
		x -= worldSpeed;
		if (x <= -(width - 358)) {
			x = 0;
		}
	}
	@Override
	public void updateEvent() {
	}
}
