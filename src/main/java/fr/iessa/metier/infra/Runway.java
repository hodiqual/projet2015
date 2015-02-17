/**
 * 
 */
package fr.iessa.metier.infra;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.sun.javafx.geom.Path2D;

import fr.iessa.metier.type.TypeQFU;


/**
 * Classe mere decrivant les pistes de la plateforme.
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
	
	/** La piste construite a partir des points */
	private GeneralPath _runwayPointAPoint;
	
	
	/** 
	 * Charge les runways en tant que objets metiers
	 */
	
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

	/**
	 * initialise les runways
	 */
	public void initialisePath(Map<String, fr.iessa.metier.infra.Point> mapPoints) {
		_runwayPointAPoint = new GeneralPath(Path2D.WIND_NON_ZERO,_listepoints.length);

       	for (int i=0;i<_listepoints.length;i++){
       		Point point = mapPoints.get(_listepoints[i]);
       		if(i==0)
       			_runwayPointAPoint.moveTo(point.getX(), point.getY());
       		else
       			_runwayPointAPoint.lineTo(point.getX(), point.getY());
			}			
	}
	/**
	 * @return the angle of the runway in radians
	 * @param i
	 * Angle +Pi/2 ou -Pi/2 par rapport a l orientation de la piste
	 */
	public double getAngle(double i) {
	    double angle = -Math.atan2(_extremite[1].getY() - _extremite[0].getY(), _extremite[1].getX() - _extremite[0].getX());
	    angle +=i;
	    if(angle < 0){
	        angle += 2*Math.PI;
	    }

	    return angle;
	}
	/**
	 * @return the path of the Runway in GeneralPath (Shape)
	 */
	public Shape get_runwayPointAPoint() {
		return _runwayPointAPoint;
	}
}
