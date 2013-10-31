package com.improvider;

public class Session {

	String name;
	String adresse;
	int tonique;
	boolean[] gamme;
	String genre;

	public void Session(String nom, String fichier, int fondamentale,
			boolean[] proposition) {
		name = nom;
		adresse = fichier;
		tonique = fondamentale;
		gamme = proposition;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getTonique() {
		return tonique;
	}

	public void setTonique(int tonique) {
		this.tonique = tonique;
	}

	public boolean[] getGamme() {
		return gamme;
	}

	public void setGamme(boolean[] gamme) {
		this.gamme = gamme;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}
