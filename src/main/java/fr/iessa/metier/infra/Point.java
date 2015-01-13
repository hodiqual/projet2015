
package fr.iessa.metier.infra;
import fr.iessa.metier.type.TypePoint;

/** Classe Point
 * @author thomasra
 * @version 1.0 
 */

public class Point extends java.awt.Point {
	/** Attributs */
	private String _nom;
	private TypePoint _type;

	/** Constructeur */
	public Point (String nom, String type, int x, int y) {
		super(x,y);
		_nom = nom;
		_type = TypePoint.from(type);
	}

	public String get_nom() {
		return _nom;
	}
	
	public TypePoint get_type() {
		return _type;
	}
	
}
