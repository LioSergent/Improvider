package com.improvider;

import android.content.Context;
import android.os.Handler;

public class InstruOrgan extends Instrument {

	public InstruOrgan(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void chargeInstrument() {
		// TODO Auto-generated method stub
		sustain = false;
		proportionVolumeInstrument = (float) 0.12;
		volumeSoundPool = actualVolumeAudio * proportionVolumeInstrument
				/ maxVolumeAudio;

		// PremiÃ¨re Octave
		C2 = soundPool.load(context, R.raw.c2organ, 1);
		Cd2 = soundPool.load(context, R.raw.cd2organ, 1);
		D2 = soundPool.load(context, R.raw.d2organ, 1);
		Dd2 = soundPool.load(context, R.raw.dd2organ, 1);
		E2 = soundPool.load(context, R.raw.e2organ, 1);
		F2 = soundPool.load(context, R.raw.f2organ, 1);
		Fd2 = soundPool.load(context, R.raw.fd2organ, 1);
		G2 = soundPool.load(context, R.raw.g2organ, 1);
		Gd2 = soundPool.load(context, R.raw.gd2organ, 1);
		A2 = soundPool.load(context, R.raw.a2organ, 1);
		Ad2 = soundPool.load(context, R.raw.ad2organ, 1);
		B2 = soundPool.load(context, R.raw.b2organ, 1);

		// Seconde Octave
		C3 = soundPool.load(context, R.raw.c3organ, 1);
		Cd3 = soundPool.load(context, R.raw.cd3organ, 1);
		D3 = soundPool.load(context, R.raw.d3organ, 1);
		Dd3 = soundPool.load(context, R.raw.dd3organ, 1);
		E3 = soundPool.load(context, R.raw.e3organ, 1);
		F3 = soundPool.load(context, R.raw.f3organ, 1);
		Fd3 = soundPool.load(context, R.raw.fd3organ, 1);
		G3 = soundPool.load(context, R.raw.g3organ, 1);
		Gd3 = soundPool.load(context, R.raw.gd3organ, 1);
		A3 = soundPool.load(context, R.raw.a3organ, 1);
		Ad3 = soundPool.load(context, R.raw.ad3organ, 1);
		B3 = soundPool.load(context, R.raw.b3organ, 1);

		// Troisième octave
		C4 = soundPool.load(context, R.raw.c4organ, 1);
		Cd4 = soundPool.load(context, R.raw.cd4organ, 1);
		D4 = soundPool.load(context, R.raw.d4organ, 1);
		Dd4 = soundPool.load(context, R.raw.dd4organ, 1);
		E4 = soundPool.load(context, R.raw.e4organ, 1);
		F4 = soundPool.load(context, R.raw.f4organ, 1);
		Fd4 = soundPool.load(context, R.raw.fd4organ, 1);
		G4 = soundPool.load(context, R.raw.g4organ, 1);
		Gd4 = soundPool.load(context, R.raw.gd4organ, 1);
		A4 = soundPool.load(context, R.raw.a4organ, 1);
		Ad4 = soundPool.load(context, R.raw.ad4organ, 1);
		B4 = soundPool.load(context, R.raw.b4organ, 1);
	}

	@Override
	public void stopNote(final int Note) {
		// TODO Auto-generated method stub
		if (!sustain) {

			Handler lHandler = new Handler();

			lHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.25 * volumeSoundPool);

				}
			}, 30);

			Handler mHandler = new Handler();

			mHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.13 * volumeSoundPool);
				}
			}, 200);

			Handler nHandler = new Handler();

			nHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.03 * volumeSoundPool);
				}
			}, 350);

			Handler pHandler = new Handler();
			pHandler.postDelayed(new Runnable() {
				public void run() {
					doStuffbis(Note);
				}
			}, 1000);

		}
	}
}
