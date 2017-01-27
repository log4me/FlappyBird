package logme;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class SoundsManager {

	private AudioClip die;
	private AudioClip hit;
	private AudioClip score;
	private AudioClip wing;
	private static SoundsManager soundsManager;

	private SoundsManager() {

	}

	public static SoundsManager getInstance() {
		if (soundsManager == null) {
			soundsManager = new SoundsManager();
			soundsManager.init();
		}
		return soundsManager;
	}

	@SuppressWarnings("deprecation")
	private void init() {
		try {
			die = Applet.newAudioClip(new File("res/music/die.wav").toURL());
			hit = Applet.newAudioClip(new File("res/music/hit.wav").toURL());
			score = Applet.newAudioClip(new File("res/music/score.wav").toURL());
			wing = Applet.newAudioClip(new File("res/music/wing.wav").toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void die() {
		die.play();
	}

	public void hit() {
		hit.play();
	}

	public void wing() {
		wing.play();
	}

	public void score() {
		score.play();
	}
}
