package fr.iessa.metier.type;

import java.util.Hashtable;

/**
 * Décrit les direction possibles sur une ligne.
 * @author hodiqual
 *
 */
public enum Direction {
	/** Piste sens unique  */
	SINGLE("S","sens unique")
	
	/** Piste double sens*/ 
,	DOUBLE("D","double sens");

	/** Chaine de caracteres correspondante */
	private String _nom;
	
	/** Abrévation de la catégories*/
	private String _abreviation;

	/** Constructeur */
	private Direction(String abrevation, String nom)
	{
		_abreviation = abrevation;
		_nom = nom;
	}
	
	@Override
	public String toString()
	{
		return _nom;
	}
	
	/** Retourne l'abreviation de la direction */
	public String getAbreviation()
	{
		return _abreviation;
	}
	
	/**
	 * Initialise une map clé-valeur constante permettant de retourner une direction
	 * en fonction de son abreviation.
	 */
    private static final Hashtable<String, Direction> _mapAbrevDirection = new Hashtable<String, Direction>();
    
    // L'initialisation
    static {
        for (Direction direction : values()) { // on parcourt toutes valeurs de direction
        	_mapAbrevDirection.put(direction._abreviation, direction); //qu'on insere dans la map
        }
    }
	
    /** Récupération d'une direction par son abréviation */
	public static Direction from(String abrevation)
	{
		return _mapAbrevDirection.get(abrevation);
	}
	

	
}
