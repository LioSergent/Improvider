package com.improvider;

import android.content.Context;
import android.os.Handler;

public class InstruGuitar extends Instrument {

	public InstruGuitar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void chargeInstrument() {
		// TODO Auto-generated method stub
		sustain = false;
		proportionVolumeInstrument = (float) 0.15;
		volumeSoundPool = actualVolumeAudio * proportionVolumeInstrument
				/ maxVolumeAudio;

		// PremiÃ¨re Octave
		C2 = soundPool.load(context, R.raw.c2guitar, 1);
		Cd2 = soundPool.load(context, R.raw.cd2guitar, 1);
		D2 = soundPool.load(context, R.raw.d2guitar, 1);
		Dd2 = soundPool.load(context, R.raw.dd2guitar, 1);
		E2 = soundPool.load(context, R.raw.e2guitar, 1);
		F2 = soundPool.load(context, R.raw.f2guitar, 1);
		Fd2 = soundPool.load(context, R.raw.fd2guitar, 1);
		G2 = soundPool.load(context, R.raw.g2guitar, 1);
		Gd2 = soundPool.load(context, R.raw.gd2guitar, 1);
		A2 = soundPool.load(context, R.raw.a2guitar, 1);
		Ad2 = soundPool.load(context, R.raw.ad2guitar, 1);
		B2 = soundPool.load(context, R.raw.b2guitar, 1);

		// Seconde Octave
		C3 = soundPool.load(context, R.raw.c3guitar, 1);
		Cd3 = soundPool.load(context, R.raw.cd3guitar, 1);
		D3 = soundPool.load(context, R.raw.d3guitar, 1);
		Dd3 = soundPool.load(context, R.raw.dd3guitar, 1);
		E3 = soundPool.load(context, R.raw.e3guitar, 1);
		F3 = soundPool.load(context, R.raw.f3guitar, 1);
		Fd3 = soundPool.load(context, R.raw.fd3guitar, 1);
		G3 = soundPool.load(context, R.raw.g3guitar, 1);
		Gd3 = soundPool.load(context, R.raw.gd3guitar, 1);
		A3 = soundPool.load(context, R.raw.a3guitar, 1);
		Ad3 = soundPool.load(context, R.raw.ad3guitar, 1);
		B3 = soundPool.load(context, R.raw.b3guitar, 1);

		// Troisième octave
		C4 = soundPool.load(context, R.raw.c4guitar, 1);
		Cd4 = soundPool.load(context, R.raw.cd4guitar, 1);
		D4 = soundPool.load(context, R.raw.d4guitar, 1);
		Dd4 = soundPool.load(context, R.raw.dd4guitar, 1);
		E4 = soundPool.load(context, R.raw.e4guitar, 1);
		F4 = soundPool.load(context, R.raw.f4guitar, 1);
		Fd4 = soundPool.load(context, R.raw.fd4guitar, 1);
		G4 = soundPool.load(context, R.raw.g4guitar, 1);
		Gd4 = soundPool.load(context, R.raw.gd4guitar, 1);
		A4 = soundPool.load(context, R.raw.a4guitar, 1);
		Ad4 = soundPool.load(context, R.raw.ad4guitar, 1);
		B4 = soundPool.load(context, R.raw.b4guitar, 1);
	}

	@Override
	public void stopNote(final int Note) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (!sustain) {

			Handler lHandler = new Handler();

			lHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.25 * volumeSoundPool);

				}
			}, 10);

			Handler mHandler = new Handler();

			mHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.13 * volumeSoundPool);
				}
			}, 80);

			Handler nHandler = new Handler();

			nHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.002 * volumeSoundPool);
				}
			}, 150);

			Handler pHandler = new Handler();
			pHandler.postDelayed(new Runnable() {
				public void run() {
					doStuffbis(Note);
				}
			}, 1000);

		}
	}
}
