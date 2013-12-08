package com.improvider;

public interface PentaMajeures {
	double[] intervallesPentaMineure={1,1,1.5,1,1.5};
	public final static Scale PENTA_DO_M=new Scale ("Penta Do majeure",com.improvider.NameNote.DO, intervallesPentaMineure );
	public final Scale PENTA_DOD_M=new Scale ("Penta Do dièse majeure",com.improvider.NameNote.DOD, intervallesPentaMineure );
	public final Scale PENTA_RE_M=new Scale ("Penta Ré majeure",com.improvider.NameNote.RE, intervallesPentaMineure );
	public final Scale PENTA_MI_M=new Scale ("Penta Mi majeure",com.improvider.NameNote.MI, intervallesPentaMineure );
	public final Scale PENTA_FA_M=new Scale ("Penta Fa majeure",com.improvider.NameNote.FA, intervallesPentaMineure );
	public final Scale PENTA_SOL_M=new Scale ("Penta Sol majeure",com.improvider.NameNote.SOL, intervallesPentaMineure );
	public final Scale PENTA_LA_M=new Scale ("Penta La majeure",com.improvider.NameNote.LA, intervallesPentaMineure );
	public final Scale PENTA_SI_M=new Scale ("Penta Si majeure",com.improvider.NameNote.SI, intervallesPentaMineure );
}
