package fr.iessa.metier.infra;

import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.Queue;

import com.sun.javafx.geom.Path2D;

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
	
	/** La direction sens unique ou double sens*/
	protected Direction _direction;
	
	/** Contructeur */
	public Ligne( int vitesseDeRoulage, Categorie categorie, Direction direction, Queue<Point> listePointsOrdonnee )
	{
		_vitesseDeRoulage = vitesseDeRoulage;
		_categorie = categorie;
		_direction = direction;
		_lignePointAPoint = new GeneralPath(Path2D.WIND_NON_ZERO,listePointsOrdonnee.size());
		
		initialiseLigneBrisee(listePointsOrdonnee);
	}

	/** initialise la  _lignePointAPoint en ligne brisee*/
	private void initialiseLigneBrisee(Queue<Point> listePointsOrdonnee) {
		listePointsOrdonnee.forEach( (p) -> _lignePointAPoint.lineTo( p.getX(), p.getY() ) );		
	}
	
	// Accessors
	
	/**
	 * @return the _lignePointAPoint
	 */
	public GeneralPath get_lignePointAPoint() {
		return _lignePointAPoint;
	}

	/**
	 * @return the _vitesseDeRoulage
	 */
	public int get_vitesseDeRoulage() {
		return _vitesseDeRoulage;
	}

	/**
	 * @return the _categorie
	 */
	public Categorie get_categorie() {
		return _categorie;
	}

	/**
	 * @return the _direction
	 */
	public Direction get_direction() {
		return _direction;
	}
	
}
