package com.improvider;

import java.util.HashMap;
import java.util.Map;

/**NameNote contient les noms des notes, et leur correspondance en entier qui servira pour le Piano
 * 
 * @author Lionel
 *
 */
public enum NameNote {
	DO(0),DOD(8),RE(1),RED(9),MI(2),FA(3),FAD(11),SOL(4),SOLD(12),LA(5),LAD(13),SI(6);
	
	public int nameNote;
	
	
	private NameNote(final int note) {
		nameNote=note;
		
	}
	
  
    /*
    public static int positionNote() {
        return nameNote;
    }
    */
}
