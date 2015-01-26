/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.Hashtable;

import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 */
public class Vol {
	
	private TypeVol _typeVol;
	private String _id;
	private Categorie _categorie;
	private Hashtable<Instant, Point> _instantVersCoord = new Hashtable<>();
	
	public Vol(TypeVol typeVol, String id, Categorie categorie)
	{
		_typeVol = typeVol;
		_id = id;
		_categorie = categorie;
	}
	
	public void ajout(Instant temps, Point coord) {
		_instantVersCoord.put(temps, coord);
	}

	public Hashtable<Instant, Point> getInstantVersCoord() {
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
	 * @return the _instantVersCoord
	 */
	public Hashtable<Instant, Point> get_instantVersCoord() {
		return _instantVersCoord;
	}
	
	public boolean estSurLaPlateforme(Instant instant) {
		return _instantVersCoord.containsKey(instant);
	}
	
}
