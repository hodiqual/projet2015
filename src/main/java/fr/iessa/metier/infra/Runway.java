/**
 * 
 */
package fr.iessa.metier.infra;

import java.util.ArrayList;
import java.awt.Point;

/**
 * Classe mère décrivant les pistes de la plateforme.
 * @author duvernal
 *
 */
public class Runway {

	/** Le nom de la piste */
	protected String _nom;
	
	/** Le nom de la piste */
	protected QFU _qfuL;
	
	/** Le nom de la piste */
	protected QFU _qfuR;
	
	protected Point[] _extremite;
	
	ArrayList<Point> _listepoints = new ArrayList();
	
}
