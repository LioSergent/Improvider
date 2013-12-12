package com.improvider;

import android.content.Context;
import android.os.Handler;

public class InstruPiano extends Instrument {

	public InstruPiano(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void chargeInstrument() {
		sustain = true;
		proportionVolumeInstrument = (float) 0.25;
		volumeSoundPool = proportionVolumeInstrument / maxVolumeAudio;

		// PremiÃ¨re Octave
		C2 = soundPool.load(context, R.raw.c2piano, 1);
		Cd2 = soundPool.load(context, R.raw.c2dpiano, 1);
		D2 = soundPool.load(context, R.raw.d2piano, 1);
		Dd2 = soundPool.load(context, R.raw.dd2piano, 1);
		E2 = soundPool.load(context, R.raw.e2piano, 1);
		F2 = soundPool.load(context, R.raw.f2piano, 1);
		Fd2 = soundPool.load(context, R.raw.fd2piano, 1);
		G2 = soundPool.load(context, R.raw.g2piano, 1);
		Gd2 = soundPool.load(context, R.raw.gd2piano, 1);
		A2 = soundPool.load(context, R.raw.a2piano, 1);
		Ad2 = soundPool.load(context, R.raw.ad2piano, 1);
		B2 = soundPool.load(context, R.raw.b2piano, 1);

		// Seconde Octave
		C3 = soundPool.load(context, R.raw.c3piano, 1);
		Cd3 = soundPool.load(context, R.raw.cd3piano, 1);
		D3 = soundPool.load(context, R.raw.d3piano, 1);
		Dd3 = soundPool.load(context, R.raw.dd3piano, 1);
		E3 = soundPool.load(context, R.raw.e3piano, 1);
		F3 = soundPool.load(context, R.raw.f3piano, 1);
		Fd3 = soundPool.load(context, R.raw.fd3piano, 1);
		G3 = soundPool.load(context, R.raw.g3piano, 1);
		Gd3 = soundPool.load(context, R.raw.gd3piano, 1);
		A3 = soundPool.load(context, R.raw.a3piano, 1);
		Ad3 = soundPool.load(context, R.raw.ad3piano, 1);
		B3 = soundPool.load(context, R.raw.b3piano, 1);

		// Troisième octave
		C4 = soundPool.load(context, R.raw.c4piano, 1);
		Cd4 = soundPool.load(context, R.raw.cd4piano, 1);
		D4 = soundPool.load(context, R.raw.d4piano, 1);
		Dd4 = soundPool.load(context, R.raw.dd4piano, 1);
		E4 = soundPool.load(context, R.raw.e4piano, 1);
		F4 = soundPool.load(context, R.raw.f4piano, 1);
		Fd4 = soundPool.load(context, R.raw.fd4piano, 1);
		G4 = soundPool.load(context, R.raw.g4piano, 1);
		Gd4 = soundPool.load(context, R.raw.gd4piano, 1);
		A4 = soundPool.load(context, R.raw.a4piano, 1);
		Ad4 = soundPool.load(context, R.raw.ad4piano, 1);
		B4 = soundPool.load(context, R.raw.b4piano, 1);

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
			}, 20);

			Handler mHandler = new Handler();

			mHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.15 * volumeSoundPool);
				}
			}, 180);

			Handler nHandler = new Handler();

			nHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.003 * volumeSoundPool);
				}
			}, 300);

			Handler pHandler = new Handler();
			pHandler.postDelayed(new Runnable() {
				public void run() {
					doStuffbis(Note);
				}
			}, 1000);

		}
	}

}
