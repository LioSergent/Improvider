package com.improvider;

public interface ListSessions  {

	
	static double[] intervallesPentaMineure={1.5,1,1,1.5,1};
	public final Scale PENTA_DO_m=new Scale ("Penta Do mineure",com.improvider.NameNote.DO, intervallesPentaMineure );
	public final Scale PENTA_DOD_m=new Scale ("Penta Do dièse mineure",com.improvider.NameNote.DOD, intervallesPentaMineure );
	public final Scale PENTA_RE_m=new Scale ("Penta Ré mineure",com.improvider.NameNote.RE, intervallesPentaMineure );
	public final Scale PENTA_MI_m=new Scale ("Penta Mi mineure",com.improvider.NameNote.MI, intervallesPentaMineure );
	public final Scale PENTA_FA_m=new Scale ("Penta Fa mineure",com.improvider.NameNote.FA, intervallesPentaMineure );
	public final Scale PENTA_SOL_m=new Scale ("Penta Sol mineure",com.improvider.NameNote.SOL, intervallesPentaMineure );
	public final Scale PENTA_LA_m=new Scale ("Penta La mineure",com.improvider.NameNote.LA, intervallesPentaMineure );
	public final Scale PENTA_SI_m=new Scale ("Penta Si mineure",com.improvider.NameNote.SI, intervallesPentaMineure );

	
	
public final Session barBluesAm=new Session ("Bar Blues Am", R.raw.barbluesaminor, PENTA_LA_m);
/*
public final Session bluesSoulEm=new Session("Blues Soul Em", R.raw.bluessoulguitarbackingtrackineminor, com.improvider.PentaMineures.PENTA_MI_m);
public final Session hipHopCm=new Session("HipHop Style Cm", R.raw.freestylerapbeatinstrumentalincminor, com.improvider.PentaMineures.PENTA_DO_m);
public final Session acousticPopGuitarBM=new Session("Acoustic Guitar BM", R.raw.acousticpopguitarbackingtrackinbmajor, com.improvider.PentaMajeures.PENTA_SI_M);
public final Session hardRockEm=new Session("Hard Rock Em", R.raw.hardrockguitarbackingtrackineminor, com.improvider.PentaMineures.PENTA_MI_m);
*/
}

