package com.improvider;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Classe abstraite décrivant le comportement des instrument comme InstruPiano,
 * InstruGuitar...
 * 
 * @author Lionel
 * 
 */
public abstract class Instrument {

	public SoundPool soundPool;
	public Context context;

	public boolean sustain;
	public float maxVolumeAudio;
	public float actualVolumeAudio;
	/**
	 * Entre 0.10 et 0.30
	 */
	public float proportionVolumeInstrument;
	public float volumeSoundPool;

	/**
	 * Méthode abstraite permettant d'associer tel instrument au clavier
	 * 
	 */
	public abstract void chargeInstrument();

	/**
	 * Méthode abstraite servant à forcer la définition de la façon dont les
	 * notes de tel instrument doit s'arrêter
	 * 
	 * @param Note
	 */
	public abstract void stopNote(final int Note);

	protected int C2, Cd2, D2, Dd2, E2, F2, Fd2, G2, Gd2, A2, Ad2, B2, C3, Cd3,
			D3, Dd3, E3, F3, Fd3, G3, Gd3, A3, Ad3, B3, C4, Cd4, D4, Dd4, E4,
			F4, Fd4, G4, Gd4, A4, Ad4, B4;

	public Instrument(Context context) {
		this.context = context;
		soundPool = new SoundPool(36, AudioManager.STREAM_MUSIC, 0);

		AudioManager audioManager = (AudioManager) this.context
				.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

		actualVolumeAudio = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		maxVolumeAudio = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

	}

	/**
	 * Méthode appelée par la classe piano
	 * 
	 * @param soundID
	 * @param leftVolume
	 * @param rightVolume
	 * @param priority
	 * @param loop
	 * @param rate
	 * @return
	 */
	public int play(int soundID, float leftVolume, float rightVolume,
			int priority, int loop, float rate) {
		return soundPool.play(soundID, leftVolume, rightVolume, priority, loop,
				rate);
	}

	public int play(int soundID) {

		int a = soundPool.play(soundID, volumeSoundPool, volumeSoundPool, 1, 0,
				1f);
		return a;
	}

	public void stopDirect(int Note) {
		soundPool.stop(Note);
	}

	public int[] returnTabSon() {
		int[] a = { C2, Cd2, D2, Dd2, E2, F2, Fd2, G2, Gd2, A2, Ad2, B2, C3,
				Cd3, D3, Dd3, E3, F3, Fd3, G3, Gd3, A3, Ad3, B3, C4, Cd4, D4,
				Dd4, E4, F4, Fd4, G4, Gd4, A4, Ad4, B4 };
		return a;
	}

	protected void doStuff(int Note, float vr) {
		soundPool.setVolume(Note, vr, vr);

	}

	protected void doStuffbis(int Note) {
		soundPool.stop(Note);
	}

	public void release() {
		soundPool.release();
	}

	public boolean getSustain() {
		return sustain;
	}

	public void setSustain(boolean sustain) {
		this.sustain = sustain;
	}

	/**
	 * Méthode servant pour la seekBar de volume de l'instrument.
	 * 
	 * @param a
	 */
	public void setVolume(int a) {
		float c = (float) a / 300;
		float d = (float) 3.2 * a * a / 10000;
		float b = c + d;
		volumeSoundPool = b * proportionVolumeInstrument * maxVolumeAudio;

	}
}
