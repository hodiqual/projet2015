/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.List;

import fr.iessa.metier.Instant;

/**
 * Classe metier decrivant une collision
 * @author hodiqual
 *
 */
public class Collision implements Comparable<Collision>{
	
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
	
	/**
	 * Collision
	 * @param instant l'instant de l'impact
	 * @param point la coordonnee de l'impact
	 * @param vols les vols impliques dans la collision
	 */
	public Collision(Instant instant, Point point, List<Vol> vols) {
		_instant = instant;
		_pointImpact = point;
		_volsImpliques = vols;
	}
	
	@Override
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

	@Override
	public int compareTo(Collision o) {
		return _instant.compareTo(o.getInstant());
	}
}
