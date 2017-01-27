package logme;

import java.awt.Graphics;

public interface GameObject {
	public void step(final float worldSpeed);
	public void paint(Graphics g);
	public void updateEvent();
	public void reset();
}
