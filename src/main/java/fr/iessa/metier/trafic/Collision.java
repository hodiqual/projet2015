/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.List;

import fr.iessa.metier.Instant;

/**
 * @author hodiqual
 *
 */
public class Collision {
	
	/**
	 * Instant ou la collision se passe.
	 */
	private final Instant _instant;

	/**
	 * Point d'impact de la collision.
	 */
	private final Point _pointImpact;

	/**
	 * Vols impliques dans la collision.
	 */
	private final List<Vol> _volsImpliques;
	
	public Collision(Instant instant, Point point, List<Vol> vols) {
		_instant = instant;
		_pointImpact = point;
		_volsImpliques = vols;
	}
	
	public String toString()
	{
		return "[" + _instant + " - " + _instant.getSeconds() + "s]" + " Collision entre " + _volsImpliques 
					+ " aux coordonnees " + "x=" + _pointImpact.getX() + " y=" + _pointImpact.getY()  ;
	}

	/**
	 * @return the _instant
	 */
	public Instant getInstant() {
		return _instant;
	}

	/**
	 * @return the _pointImpact
	 */
	public Point getPointImpact() {
		return _pointImpact;
	}

	/**
	 * @return the _volsImpliques
	 */
	public List<Vol> getVolsImpliques() {
		return _volsImpliques;
	}
}
