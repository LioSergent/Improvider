package com.improvider;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

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
	
	
	public abstract void chargeInstrument();
	public abstract void stopNote(final int Note);

	protected int C2, Cd2, D2, Dd2, E2, F2, Fd2, G2, Gd2, A2, Ad2, B2, C3, Cd3,
			D3, Dd3, E3, F3, Fd3, G3, Gd3, A3, Ad3, B3, C4, Cd4, D4, Dd4, E4,
			F4, Fd4, G4, Gd4, A4, Ad4, B4;

	public Instrument(Context context) {
        this.context=context;
		soundPool = new SoundPool(24, AudioManager.STREAM_MUSIC, 0);
		
		AudioManager audioManager = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		
		actualVolumeAudio = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		Log.d("ActualVOlume", String.valueOf(actualVolumeAudio));
		maxVolumeAudio = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		Log.d("maxVOlume", String.valueOf(maxVolumeAudio));

	
	}

	public int play(int soundID, float leftVolume, float rightVolume,
			int priority, int loop, float rate) {
		return soundPool.play(soundID, leftVolume, rightVolume, priority, loop,
				rate);
	}
	
	
	public int play(int soundID) {
		
		int a=soundPool.play(soundID, volumeSoundPool, volumeSoundPool, 1, 0,1f);		
		return a;
	}
	
	public void stopDirect(int Note) {
		soundPool.stop(Note);
	}
	
	public int[] returnTabSon() {
		int[] a= {C2, Cd2, D2, Dd2, E2, F2, Fd2, G2, Gd2, A2, Ad2, B2, C3, Cd3,
				D3, Dd3, E3, F3, Fd3, G3, Gd3, A3, Ad3, B3, C4, Cd4, D4, Dd4, E4,
				F4, Fd4, G4, Gd4, A4, Ad4, B4};
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

	
	public void setVolume(int a) {
		Log.d("a", String.valueOf(a));
		float c=(float) a/300;
		float d= (float) 3.2*a*a/10000;
		float b=c+d;
		Log.d("d", String.valueOf(d));
		Log.d("max", String.valueOf(maxVolumeAudio));
		volumeSoundPool=b*proportionVolumeInstrument*maxVolumeAudio;
		Log.d("VolumeSOundPool", String.valueOf(volumeSoundPool));
	}
}
