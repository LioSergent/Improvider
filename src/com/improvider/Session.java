package com.improvider;

/**
 * La classe session produit des objets capables de transmettre toutes les informations nécessaire pour 
 * charger une session particulière du jeu.
 * @author Lionel
 *
 */
public class Session {

	public String nom;
	private int adresse; // Adresse de l'extrait audio!
	private static int[] separationBidon = { 100000, 100000 };
	// gamme et separations doivent être de la même taille!
	// La dernière valeur de separations doit être égale ou supérieure à la
	// longueur de l'audio. En nombre de secondes
	private Scale[] scale;
	private int[] separations;

	/**
	 * Constructeur utilisable pour des backing complexes
	 * @param nom
	 * @param adresse
	 * @param gamme
	 * @param separations
	 */
	public Session(String nom, int adresse, Scale[] gamme, int[] separations) {
		super();
		this.nom = nom;
		this.adresse = adresse;
		this.scale = gamme;
		this.separations = separations;
	}

	/** Constructeur à utiliser pour des backings n'utilisant d'une seule gamme
	 * 
	 * @param nom
	 * @param adresse
	 * @param preGamme
	 */
	public Session(String nom, int adresse, Scale preGamme) {
		this(nom, adresse, fromPreGammeToGamme(preGamme), separationBidon);

	}

	public static Scale[] fromPreGammeToGamme(Scale preGamme) {
		Scale[] gamme = { preGamme, preGamme };
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
		boolean[][] aRetourner = new boolean[scale.length][15];
		for (int i = 0; i < scale.length; i++) {
			aRetourner[i] = scale[i].getUsedValue();
		}
		return aRetourner;
	}
}
