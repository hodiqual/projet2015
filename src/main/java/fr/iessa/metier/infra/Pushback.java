/**
 * 
 */
package fr.iessa.metier.infra;

import java.awt.Point;
import java.util.Queue;

import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

/**
 * Zone de pushback caracterise par une vitesse de roulage negative.
 * @author hodiqual
 *
 */
public class Pushback extends Ligne {
	
	public Pushback( int vitesseDeRoulage, Categorie categorie, Direction direction, Queue<Point> listePointsOrdonnee )
	{
		super(vitesseDeRoulage, categorie, direction, listePointsOrdonnee);
	}
	
}
