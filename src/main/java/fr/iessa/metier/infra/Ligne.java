package fr.iessa.metier.infra;

import java.awt.geom.GeneralPath;

import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

/**
 * Classe mère decrivant les lignes brisées
 * @author hodiqual
 *
 */
public class Ligne {
	
	/** La ligne brisée construite à partir des coordonnees */
	protected GeneralPath _lignePointAPoint;
	
	/** Vitesse de roulage admise en m/s (valeur negative possible)*/
	protected int _vitesseDeRoulage;
	
	/** La categorie d'avions admis sur la ligne */
	protected Categorie _categorie;
	
	/** La direction single ou double */
	protected Direction _direction;
	

}
