package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public static int MENU_1 = 0;
	public static int LEVEL_1 = 1;
	public static int LEVEL_2 = 2;

	public static int DIE = 0;
	public static int JUMP = 1;
	public static int GAMEOVER = 2;
	public static int LVL_COMPLETED = 3;
	public static int ATTACK_ONE = 4;
	public static int ATTACK_TWO = 5;
	public static int ATTACK_THREE = 6;

	private Clip[] songs, effects; //Clip เป็นตัวจัดการเกี่ยวกับเสีบง หยุด เล่น ฯลฯ ใช้ได้แค่ไฟล์ .wav
	private int currentSongId;
	private float volume = 0.5f;
	private boolean songMute, effectMute;
	private Random rand = new Random();
	
	public AudioPlayer() {
		loadSongs();
		loadEffects();
		playSong(MENU_1);
	}

	private void loadSongs() { //เก็บเพลงไว้ใน arr ของ Clip ซึ่งจะโหลดโดยใช้ method getClip
		String[] names = { "menu", "level1", "level2" };
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
	}

	private void loadEffects() { // โหลดเสียงประกอบ
		String[] effectNames = { "die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3" };
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);
		
		updateEffectsVolume();
		
	}

	private Clip getClip(String name) { //ดึงไฟล์ .wav มาใช้
		URL url = getClass().getResource("/audio/" + name + ".wav");

		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}

	public void stopSong() {
		if (songs[currentSongId].isActive()) //เช็คว่ามีที่กำลังใช้อยู่มั้ย
			songs[currentSongId].stop(); //หยุดเพลง
	}

	public void setLevelSong(int lvlIndex) {
		if (lvlIndex % 2 == 0)
			playSong(LEVEL_1);
		else
			playSong(LEVEL_2);
	}

	public void lvlCompleted() {
		stopSong();
		playEffect(LVL_COMPLETED);
	}

	public void playAttackSound() {
		int start = 4;
		start += rand.nextInt(3);
		playEffect(start);
	}

	public void playEffect(int effect) {
		effects[effect].setMicrosecondPosition(0); // เซ็ทให้จุดเล่น อยู่จุดเริ่มต้น
		effects[effect].start();
	}

	public void playSong(int song) {
		stopSong();

		currentSongId = song; //เปลี่ยนเพลง
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0); //เซ็ทให้เล่นตั้งแต่ต้นเพลง
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY); //เล่นวนไปเรื่อยๆ
	}

	public void toggleSongMute() {
		this.songMute = !songMute;
		for (Clip c : songs) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);
		}
	}

	public void toggleEffectMute() {
		this.effectMute = !effectMute;
		for (Clip c : effects) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
		if (!effectMute)
			playEffect(JUMP);
	}

	private void updateSongVolume() { //ปรับเสียง

		FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);

	}

	private void updateEffectsVolume() {
		for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}
}
