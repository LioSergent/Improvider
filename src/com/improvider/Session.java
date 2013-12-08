package com.improvider;

import android.os.Parcel;
import android.os.Parcelable;

public class Session {
	
	public String nom;
	private int adresse; // Adresse de l'extrait audio!
	private static int[] separationBidon={100000,100000};
//gamme et separations doivent �tre de la m�me taille!
	//La derni�re valeur de separations doit �tre �gale ou sup�rieure � la longueur de l'audio. En nombre de secondes
	private Scale[] scale;
	private int[] separations;

	
	
	public Session(String nom, int adresse, Scale[] gamme, int[] separations) {
		super();
		this.nom = nom;
		this.adresse = adresse;
		this.scale = gamme;
		this.separations = separations;
	}

	public Session(String nom, int adresse, Scale preGamme) {
		this(nom, adresse, fromPreGammeToGamme(preGamme), separationBidon);
	
		
	}
	
	public static Scale[] fromPreGammeToGamme(Scale preGamme) {
		Scale[] gamme={preGamme, preGamme};
return gamme;
	}
	
	public int getAdresse() {
		return adresse;
	}

	public void setAdresse(int adresse) {
		this.adresse = adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Scale[] getScale() {
		return scale;
	}

	public void setscale(Scale[] gamme) {
		this.scale = gamme;
	}

	public int[] getSeparations() {
		return separations;
	}

	public void setSeparations(int[] separations) {
		this.separations = separations;
	}
	
public boolean[][] getAllUsedValue() {
	boolean[][] aRetourner =new boolean[scale.length][15];
	for (int i=0; i<scale.length;i++) {
	aRetourner[i]=scale[i].getUsedValue();
	}
	return aRetourner;
	}
}
