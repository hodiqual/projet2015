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
	
	private final Instant _instant;
	private final Point _pointImpact;
	private final List<Vol> _volsConcernes;
	
	public Collision(Instant instant, Point point, List<Vol> vols) {
		_instant = instant;
		_pointImpact = point;
		_volsConcernes = vols;
	}
	
	public String toString()
	{
		return "[" + _instant + " - " + _instant.getSeconds() + "s]" + " Collision entre " + _volsConcernes 
					+ " aux coordonnees " + "x=" + _pointImpact.getX() + " y=" + _pointImpact.getY()  ;
	}
}
