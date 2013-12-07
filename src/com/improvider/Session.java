package com.improvider;

public class Session {
	private String adresse; // Adresse de l'extrait audio!
	private String nom;
//gamme et separations doivent �tre de la m�me taille!
	//La derni�re valeur de separations doit �tre �gale ou sup�rieure � la longueur de l'audio.
	private Scale gamme[];
	private int separations[];

	
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Scale[] getGamme() {
		return gamme;
	}

	public void setGamme(Scale[] gamme) {
		this.gamme = gamme;
	}

	public int[] getSeparations() {
		return separations;
	}

	public void setSeparations(int[] separations) {
		this.separations = separations;
	}

}
