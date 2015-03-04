/**
 * 
 */
package fr.iessa.metier.trafic;

import java.awt.Point;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author hodiqual
 *
 */
public class PointFabrique {
	
	private static Map<String, Point> _sVersPoint = new Hashtable<>();
	
	private PointFabrique() {}
	
	
	/**
	 * @param s coordonnees entieres signees en String de format "xxxx,yyyy"
	 * @return l unique instance de la class Point correspondant aux coordonnees s
	 */
	public static Point get(String s) {
		Point uniquePoint = _sVersPoint.get(s);
		
		if(uniquePoint==null){
			String[] coord = s.split(",");
			uniquePoint = new Point( Integer.parseInt(coord[0]) 
					                ,Integer.parseInt(coord[1]) );
			_sVersPoint.put(s, uniquePoint);
		}
			
		return uniquePoint;
	}
	
	
	
}
