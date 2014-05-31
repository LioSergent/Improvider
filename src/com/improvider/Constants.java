package com.improvider;

/**Interface définissant des gammes et des sessions.
 * 
 * @author Lionel
 *
 */
public interface Constants {

	//Les penta mineures
	static double[] intervallesPentaMineure={1.5,1,1,1.5,1};
	public final Scale PENTA_DO_m=new Scale ("Penta Do mineure",com.improvider.NameNote.DO, intervallesPentaMineure );
	public final Scale PENTA_DOD_m=new Scale ("Penta Do dièse mineure",com.improvider.NameNote.DOD, intervallesPentaMineure );
	public final Scale PENTA_RE_m=new Scale ("Penta Ré mineure",com.improvider.NameNote.RE, intervallesPentaMineure );
	public final Scale PENTA_MI_m=new Scale ("Penta Mi mineure",com.improvider.NameNote.MI, intervallesPentaMineure );
	public final Scale PENTA_FA_m=new Scale ("Penta Fa mineure",com.improvider.NameNote.FA, intervallesPentaMineure );
	public final Scale PENTA_SOL_m=new Scale ("Penta Sol mineure",com.improvider.NameNote.SOL, intervallesPentaMineure );
	public final Scale PENTA_LA_m=new Scale ("Penta La mineure",com.improvider.NameNote.LA, intervallesPentaMineure );
	public final Scale PENTA_SI_m=new Scale ("Penta Si mineure",com.improvider.NameNote.SI, intervallesPentaMineure );

	//Les penta Majeures
	double[] intervallesPentaMajeure={1,1,1.5,1,1.5};
	public final static Scale PENTA_DO_M=new Scale ("Penta Do majeure",com.improvider.NameNote.DO, intervallesPentaMajeure );
	public final Scale PENTA_DOD_M=new Scale ("Penta Do dièse majeure",com.improvider.NameNote.DOD, intervallesPentaMajeure );
	public final Scale PENTA_RE_M=new Scale ("Penta Ré majeure",com.improvider.NameNote.RE, intervallesPentaMajeure );
	public final Scale PENTA_MI_M=new Scale ("Penta Mi majeure",com.improvider.NameNote.MI, intervallesPentaMajeure );
	public final Scale PENTA_FA_M=new Scale ("Penta Fa majeure",com.improvider.NameNote.FA, intervallesPentaMajeure );
	public final Scale PENTA_SOL_M=new Scale ("Penta Sol majeure",com.improvider.NameNote.SOL, intervallesPentaMajeure );
	public final Scale PENTA_LA_M=new Scale ("Penta La majeure",com.improvider.NameNote.LA, intervallesPentaMajeure );
	public final Scale PENTA_SI_M=new Scale ("Penta Si majeure",com.improvider.NameNote.SI, intervallesPentaMajeure );

	
	/*Les sessions de jeu! Toutes les infos necessaires. Attention cette objet n'est pas transmis, il correspon à un code, 1
	Pour la première, 2 pour la deuxième session... Ce code implicite est utilisé dans la méthode chargeSession du Main.
	L'entier codé (1,2 ,3..) est envoyé par ChoixAccompagnement pour transmettre le choix au Main
	*/
//public final Session barBluesAm=new Session ("Bar Blues Am", R.raw.barbluesaminor, PENTA_LA_m);
public final Session bluesSoulEm=new Session("Blues Soul Em", R.raw.bluessoulguitarbackingtrackineminor, PENTA_MI_m.addNoteReturn(com.improvider.NameNote.FAD));
public final Session hipHopCm=new Session("HipHop Style Cm", R.raw.freestylerapbeatinstrumentalincminor,PENTA_DO_m);
public final Session sadMelodicBm=new Session ("Sad Melodic Bm", R.raw.sadmelodic,PENTA_SI_m.addNoteReturn(com.improvider.NameNote.DOD));
//public final Session acousticPopGuitarBM=new Session("Acoustic Guitar BM", R.raw.acousticpopguitarbackingtrackinbmajor, PENTA_SI_M);
public final Session hardRockEm=new Session("Hard Rock Em", R.raw.hardrockguitarbackingtrackineminor, PENTA_MI_m.addNoteReturn(com.improvider.NameNote.FAD));
public final Session indieRockAm=new Session ("Indie Rock Am", R.raw.indierockaminor, PENTA_LA_m);
}
