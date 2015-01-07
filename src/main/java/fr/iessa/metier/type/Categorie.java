package fr.iessa.metier.type;

import java.util.HashMap;
import java.util.Map;

/**
 * Décrit les différentes catégories d'avions vis a vis de leur taille
 * @author hodiqual
 *
 */
public enum Categorie {
	/** Gros avion: A380  */
	HIGH("H")
	
	/** Avion taille moyenne: A320*/ 
,	MEDIUM("M")

	/** Avion léger: CESNA*/
,	LIGHT("L");
	
	/** Abrévation de la catégories*/
	private String _abreviation;

	private Categorie(String categorie)
	{
		_abreviation = categorie;
	}
	
	/** Retourne l'abreviation de la categorie d'un avion */
	public String getAbreviation()
	{
		return _abreviation;
	}
	
	/**
	 * Initialise une map clé-valeur constante permettant de retourner une catégorie
	 * en fonction de son abreviation.
	 */
    private static final Map<String, Categorie> _abrevations = new HashMap<>();
    
    // L'initialisation
    static {
        for (Categorie categorie : values()) { // on parcourt toutes valeurs de direction
        	_abrevations.put(categorie._abreviation, categorie); //qu'on insere dans la map
        }
    }
	
    /** Récupération d'une catégorie par son abréviation */
	public static Categorie from(String abreviation)
	{
		return _abrevations.get(abreviation);
	}
	
	
	
	
}
