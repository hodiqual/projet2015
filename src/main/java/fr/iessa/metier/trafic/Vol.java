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
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 */
public class Vol {
	
	private TypeVol _typeVol;
	private String _id;
	private Categorie _categorie;
	private Map<Instant, Point> _instantVersCoord = new HashMap<Instant, Point>(10);
	private Instant _premierInstant;
	private boolean aDesCollisions = false;
	
	private Point _coordCourante;
	private Point _coordSuivante;
	
	public Vol(TypeVol typeVol, String id, Categorie categorie, Instant instant)
	{
		_typeVol = typeVol;
		_id = id;
		_categorie = categorie;
		_premierInstant = instant;
	}
	
	public void ajout(Instant temps, Point coord) {
		_instantVersCoord.put(temps, coord);
	}

	public Map<Instant, Point> getInstantVersCoord() {
		return _instantVersCoord;
	}
	
	public Point getCoord(Instant i ){
		return _instantVersCoord.get(i);
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
	 * @return the _premierInstant
	 */
	public Instant getPremierInstant() {
		return _premierInstant;
	}

	/**
	 * @return the _coordCourante
	 */
	public Point getCoordCourante() {
		return _coordCourante;
	}

	/**
	 * @return the _coordSuivante
	 */
	public Point getCoordSuivante() {
		return _coordSuivante;
	}
	
	public boolean estSurLaPlateforme(Instant instant) {
		return _instantVersCoord.containsKey(instant);
	}

	public void updateCoordCourantes(Instant instant) {
		if( instant == null )
			_coordCourante = null;
		else
		{
			_coordCourante = _instantVersCoord.get(instant);
			Instant instantSuivant = InstantFabrique.get(instant.getSeconds()+InstantFabrique._pasEntreInstant);
			_coordSuivante = _instantVersCoord.get(instantSuivant);
		}
	}
	
	public String toString()  {
		return getId();
	}

	/**
	 * @return the aDesCollisions
	 */
	public boolean aDesCollisions() {
		return aDesCollisions;
	}

	/**
	 * @param aDesCollisions the aDesCollisions to set
	 */
	public void setADesCollisions(boolean aDesCollisions) {
		this.aDesCollisions = aDesCollisions;
	}
	
}
