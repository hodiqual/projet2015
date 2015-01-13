
package fr.iessa.metier.type;

import java.util.Map;
import java.util.Hashtable;

/** TypePoint qui contiend les trois types possibles d'un point
 * @author THOMAS Raimana
 * @version 1.0 
 */

public enum TypePoint {
	/** Aire de parking  */
	STAND ("0"),
	
	/** Zone de dégele */ 
	DEICING ("1"),

	/** Point d'intersection d'une piste */
	RUNWAY_POINT ("2");
	
	/** Identifiant */
	private String _ident;
	
	/**
	 * Création d'une Map constante qui contiend les trois types de point en fonction de leur identifiant.
	 */
    private static final Map<String, TypePoint> _types = new Hashtable<>();
    static {
        for (TypePoint t : TypePoint.values()) {
        	_types.put(t._ident, t);
        }
    }
	
	/** Constructeur */
	private TypePoint (String n) {
		_ident = n;
	}
	
    /** Fonction qui renvoie un TypePoint en fonction de son identifiant. */
	public static TypePoint from(String n) {
		return _types.get(n);
	}
	
}
