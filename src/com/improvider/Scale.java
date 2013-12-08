package com.improvider;

public class Scale {

	public String name;
	public int tonique;

	public boolean[] usedValue;

	public boolean Do;
	public boolean Dod;
	public boolean Re;
	public boolean Red;
	public boolean Mi;
	public boolean Fa;
	public boolean Fad;
	public boolean Sol;
	public boolean Sold;
	public boolean La;
	public boolean Lad;
	public boolean Si;

	/**
	 * 
	 * @param name
	 * @param tonique
	 *            La tonique est à donner sous la forme DO, DOD, voir l'enum
	 *            NameNote
	 * @param do1
	 * @param dod
	 * @param re
	 * @param red
	 * @param mi
	 * @param fa
	 * @param fad
	 * @param sol
	 * @param sold
	 * @param la
	 * @param lad
	 * @param si
	 */
	public Scale(String name, NameNote TONIQUE, boolean do1, boolean dod,
			boolean re, boolean red, boolean mi, boolean fa, boolean fad,
			boolean sol, boolean sold, boolean la, boolean lad, boolean si) {
		super();
		this.name = name;
		this.tonique = calculateTonique(TONIQUE);
		Do = do1;
		Dod = dod;
		Re = re;
		Red = red;
		Mi = mi;
		Fa = fa;
		Fad = fad;
		Sol = sol;
		Sold = sold;
		La = la;
		Lad = lad;
		Si = si;

		usedValue = createUsedValue(do1, dod, re, red, mi, fa, fad, sol, sold,
				la, lad, si);

	}
	
	/**
	 * Constructeur prenant un tableau équivalent à do, dod, re, red...
	 * @param name
	 * @param tonique
	 * @param unusedValue
	 */
	public Scale(String name, NameNote tonique, boolean[] unusedValue) {
	this(name,tonique, unusedValue[0],unusedValue[1],unusedValue[2],unusedValue[3],unusedValue[4],unusedValue[5],
				unusedValue[6],unusedValue[7],
				unusedValue[8],unusedValue[9],unusedValue[10],unusedValue[11]);
	}
	
	/**
	 * Crée une gamme à partir de liste des intervalles, la première case d'intervalles
	 * doit donc être du type: 1.5 ton entre la tonique et le 2eme degré utilisé 
	 * (la tierce par exemple), puis 1 ton entre le 2d et le 3eme...
	 * @param name
	 * @param tonique
	 * @param intervalles
	 */	
	 public Scale(String name, NameNote tonique, double[] intervalles) {
		 this(name, tonique, fromIntervalsToUnusedValue(tonique,intervalles));
		
		
	}

	 private static boolean[] fromIntervalsToUnusedValue(NameNote tonique, double[] intervals ) {
		 
		 boolean[] unusedValue={false,false,false,false,false,false,false,false,false,false,false,false};
			int base=calculateUnusedTonique(tonique);
			int[] newIntervalles=new int[intervals.length];
			
			for (int i=0; i<intervals.length;i++) {
			newIntervalles[i]=(int) (intervals[i]*2)	;
			}
			
			unusedValue[base]=true;
			int sommeDeDegre=base;
			for (int i=0; i<newIntervalles.length;i++) {
				sommeDeDegre=sommeDeDegre+newIntervalles[i];
				if (sommeDeDegre>11) {
					sommeDeDegre=sommeDeDegre-12;
				}
				unusedValue[sommeDeDegre]=true;
				}
			
			return unusedValue;
	 }
	// Modèle de construction d'une gamme:
	//
	private int calculateTonique(NameNote nameNote) {
	int tonique=0;
	switch (nameNote)
	{
	    case DO:
	    tonique=0;
	    break;
	    
	      case DOD:
	      tonique=8;
          break;
   	      case RE:
		  tonique=1;
		  break;
		  case RED:
		  tonique=9;
	      break;
		  case MI:
	      tonique=2;
	      break;
	      case FA:
		  tonique=3;
		  break;
		  case FAD:
		  tonique=11;
		  break;
		  case SOL:
		  tonique=4;
		  break;
		  case SOLD:
		  tonique=12;
		  break;
		  case LA:
			  tonique=5;
			  break;
		  case LAD:
			  tonique=13;
			  break;
		  case SI:
			  tonique=6;
			  break;
    	  default:
    		  tonique=14;
	   
	}
	
	return tonique;
}

	private static int calculateUnusedTonique(NameNote nameNote) {
		int tonique=0;
		switch (nameNote)
		{
		    case DO:
		    tonique=0;
		    break;
		    
		      case DOD:
		      tonique=1;
	          break;
	   	      case RE:
			  tonique=2;
			  break;
			  case RED:
			  tonique=3;
		      break;
			  case MI:
		      tonique=4;
		      break;
		      case FA:
			  tonique=5;
			  break;
			  case FAD:
			  tonique=6;
			  break;
			  case SOL:
			  tonique=7;
			  break;
			  case SOLD:
			  tonique=8;
			  break;
			  case LA:
				  tonique=9;
				  break;
			  case LAD:
				  tonique=10;
				  break;
			  case SI:
				  tonique=11;
				  break;
	    	  default:
	    		  tonique=14;
		   
		}
		
		return tonique;
	}
	private boolean[] createUsedValue(boolean do1, boolean dod, boolean re,
			boolean red, boolean mi, boolean fa, boolean fad, boolean sol,
			boolean sold, boolean la, boolean lad, boolean si) {

		boolean[] toFill = new boolean[15];
		toFill[0] = Do;
		toFill[1] = Re;
		toFill[2] = Mi;
		toFill[3] = Fa;
		toFill[4] = Sol;
		toFill[5] = La;
		toFill[6] = Si;
		toFill[7] = false;
		toFill[8] = Dod;
		toFill[9] = Red;
		toFill[10] = false;
		toFill[11] = Fad;
		toFill[12] = Sold;
		toFill[13] = Lad;
		toFill[14] = false;
		return toFill;
	}

	// Méthodes: getters et setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTonique() {
		return tonique;
	}

	public void setTonique(int tonique) {
		this.tonique = tonique;
	}

	public boolean[] getUsedValue() {
		return usedValue;
	}

	public void setUsedValue(boolean[] value) {
		this.usedValue = value;
	}

}
