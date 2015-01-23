/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.Hashtable;

/**
 * @author hodiqual
 *
 */
public class Vol {
	
	private String _id;
	
	private Hashtable<Instant, Point> _instantVersCoord = new Hashtable<>();
	
	public Vol(String id)
	{
		_id=id;
	}
	
	public void ajout(Instant temps, Point coord)
	{
		_instantVersCoord.put(temps, coord);
	}

	public Hashtable<Instant, Point> getInstantVersCoord() {
		return _instantVersCoord;
	}
	
	
	
}
