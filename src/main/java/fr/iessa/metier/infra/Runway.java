/**
 * 
 */
package fr.iessa.metier.infra;

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
	
	
	// Accessors
	
	
	public Runway(String nom, String qfu1, String qfu2,
			java.awt.Point[] extremite, String[] points) {

		_nom=nom;
		_qfuR.setType(TypeQFU.R);
		_qfuL.setType(TypeQFU.L);
		if (qfu1.substring(2,3) == "R")
		{
		_qfuR.setDegre(qfu1.substring(0,2));
		_qfuL.setDegre(qfu2.substring(0,2));
		
		}
		else
		{
		_qfuL.setDegre(qfu1.substring(0,2));
		_qfuR.setDegre(qfu2.substring(0,2));
		}
		
		_extremite=extremite;
		_listepoints=points;
	}

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
	public Point[] get_extremite() {
		return _extremite;
	}
	
}
