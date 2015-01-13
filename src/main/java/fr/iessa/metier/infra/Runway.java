/**
 * 
 */
package fr.iessa.metier.infra;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import fr.iessa.metier.type.TypeQFU;


/**
 * Classe mère décrivant les pistes de la plateforme.
 * @author duvernal
 *
 */
public class Runway {

	/** Le nom de la piste */
	private String _nom;
	
	/** Le nom de la piste */
	private QFU _qfuL;
	
	/** Le nom de la piste */
	private QFU _qfuR;
	
	/** Les extremites de la piste */
	private java.awt.Point[] _extremite;
	
	/** La liste des points qui composent la piste */
	private String[] _listepoints;
	
	

	
	public Runway(String nom, String qfu1, String qfu2,
			java.awt.Point[] extremite, ArrayList<String> pointsList) {

		_nom=nom;
		
		if (qfu1.substring(2,3).equals("R"))
		{
		_qfuR = new QFU(qfu1.substring(0,2),TypeQFU.R);
		_qfuL = new QFU(qfu2.substring(0,2),TypeQFU.L);	
		}
		else
		{
		_qfuL = new QFU(qfu1.substring(0,2),TypeQFU.L);			
		_qfuR = new QFU(qfu2.substring(0,2),TypeQFU.R);
		}
		
		_extremite=extremite;
		_listepoints =(String[]) pointsList.toArray(new String[pointsList.size()]);
	}

	
	// Accessors
	
	/**
	 * @return the _nom
	 */
	public String get_nom() {
		return _nom;
	}

	/**
	 * @return the _QFUL
	 */
	public QFU get_QFUL() {
		return _qfuL;
	}
	
	/**
	 * @return the _QFUR
	 */
	public QFU get_QFUR() {
		return _qfuR;
	}
	
	/**
	 * @return the _extremite
	 */
	public Point get_extremite(int i) {
		
		return _extremite[i].getLocation();
	}
	
	/**
	 * @return the x coordinate of _extremite
	 */
	public double get_extremite_x(int i) {
		
		return _extremite[i].getX();
	}
	
	/**
	 * @return the y coordinate of _extremite
	 */
	public double get_extremite_y(int i) {
		
		return _extremite[i].getY();
	}
	/**
	 * @return the array of _listepoints
	 */
	public String[] get_listepoints() {
		return _listepoints;
	}
	/**
	 * @return a case of _listepoints
	 */
	public String get_listepoints(int i) {
		return _listepoints[i];
	}
}
