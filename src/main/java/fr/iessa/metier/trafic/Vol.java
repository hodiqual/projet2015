/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import fr.iessa.metier.Instant;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 */
public class Vol {
	
	private TypeVol _typeVol;
	private String _id;
	private Categorie _categorie;
	private Map<Instant, Point> _instantVersCoord = new HashMap(10);
	
	private Point _coordCourante;
	
	public Vol(TypeVol typeVol, String id, Categorie categorie)
	{
		_typeVol = typeVol;
		_id = id;
		_categorie = categorie;
	}
	
	public void ajout(Instant temps, Point coord) {
		_instantVersCoord.put(temps, coord);
	}

	public Map<Instant, Point> getInstantVersCoord() {
		return _instantVersCoord;
	}

	/**
	 * @return the _typeVol
	 */
	public TypeVol getTypeVol() {
		return _typeVol;
	}

	/**
	 * @return the _id
	 */
	public String getId() {
		return _id;
	}

	/**
	 * @return the _categorie
	 */
	public Categorie getCategorie() {
		return _categorie;
	}

	/**
	 * @return the _coordCourante
	 */
	public Point getCoordCourante() {
		return _coordCourante;
	}
	
	public boolean estSurLaPlateforme(Instant instant) {
		return _instantVersCoord.containsKey(instant);
	}

	public void updateCoordCourantes(Instant instant) {
		if( instant == null )
			_coordCourante = null;
		else
			_coordCourante = _instantVersCoord.get(instant);
	}
	
}
