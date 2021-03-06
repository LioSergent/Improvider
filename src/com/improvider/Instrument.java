package com.improvider;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.Builder;
import android.media.AudioAttributes;
import android.os.Build;

/**
 * Classe abstraite d�crivant le comportement des instrument comme InstruPiano,
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
	
	public final int NUMBER_NOTES=36;
	public final int MAX_STREAMS=12;
	/**
	 * Entre 0.10 et 0.30
	 */
	public float proportionVolumeInstrument;
	public float volumeSoundPool;

	/**
	 * M�thode abstraite permettant d'associer tel instrument au clavier
	 * 
	 */
	public abstract void chargeInstrument();

	/**
	 * M�thode abstraite servant � forcer la d�finition de la fa�on dont les
	 * notes de tel instrument doit s'arr�ter
	 * 
	 * @param Note
	 */
	public abstract void stopNote(final int Note, final float touchedVolume);

	protected int C2, Cd2, D2, Dd2, E2, F2, Fd2, G2, Gd2, A2, Ad2, B2, C3, Cd3,
			D3, Dd3, E3, F3, Fd3, G3, Gd3, A3, Ad3, B3, C4, Cd4, D4, Dd4, E4,
			F4, Fd4, G4, Gd4, A4, Ad4, B4;

	public Instrument(Context context) {
		this.context = context;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			soundPool=createSoundPoolWithBuilder();
		} else{
			soundPool=createSoundPoolWithConstructor();
		}

		AudioManager audioManager = (AudioManager) this.context
				.getSystemService(Context.AUDIO_SERVICE);
	
		actualVolumeAudio = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		maxVolumeAudio = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

	}

	/**
	 * M�thode appel�e par la classe piano
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
 
	
	public int play(int soundID, float touchedProportionVolume) {
		
		float meanTimeVolume=(float) volumeSoundPool*touchedProportionVolume;
		
		int a = soundPool.play(soundID,meanTimeVolume , meanTimeVolume, 1, 0,
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
	
	public void changeVolumeNote(int soundID, float newPropVolume){
		float newVolume=(float) volumeSoundPool*newPropVolume;
		soundPool.setVolume(soundID, newVolume, newVolume);
	}

	/**
	 * M�thode servant pour la seekBar de volume de l'instrument.
	 * 
	 * @param a
	 */
	public void setVolume(int a) {
		float c = (float) a / 300;
		float d = (float) 3.2 * a * a / 10000;
		float b = c + d;
		volumeSoundPool = b * proportionVolumeInstrument * maxVolumeAudio;

	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	protected SoundPool createSoundPoolWithBuilder(){
	    AudioAttributes attributes = new AudioAttributes.Builder()
	        .setUsage(AudioAttributes.USAGE_GAME)
	        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
	        .build();
	 
	    return new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(MAX_STREAMS).build();
	}
	
	@SuppressWarnings("deprecation")
	protected SoundPool createSoundPoolWithConstructor(){
	    return new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
	}


}
