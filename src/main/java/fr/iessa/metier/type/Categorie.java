package fr.iessa.metier.type;

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
	
	/** Abbrévation de la catégories*/
	private String _categorie;

	private Categorie(String categorie)
	{
		_categorie = categorie;
	}
	
	@Override
	public String toString()
	{
		return _categorie;
	}
}
