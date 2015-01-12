/**
 * 
 */
package fr.iessa.dao.infra;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import fr.iessa.dao.core.DAO;
import fr.iessa.metier.infra.QFU;
import fr.iessa.metier.infra.Runway;


/**
 * @author duvernal
 *
 */
public class RunwayDAO implements DAO<Runway> {

	/**
	 * @see fr.iessa.dao.core.DAO#charger(java.lang.String)
	 */
	@Override
	/**
	 * Charge en memoire une ligne de texte decrivant une ligne 
	 * ligneAtraiter: decrit un runway de la maniere suivante
	 * "R 09L-27R 09L 27R -1682,1660;1009,1888 Z1;Z2;Z3;Z4;Z5;Z6;Z7;Z8"
	 */

	public Runway charger(String ligneAtraiter) {
		
		int i=0;
		String nom, qfu1,qfu2;
		java.awt.Point[] extremite = null;
		String[] points = null;
		Scanner scan = new Scanner(ligneAtraiter);
		scan.useDelimiter(" |;|\n");
		scan.next(); //skip R
		nom = scan.next();
		qfu1 = scan.next();
		qfu2 = scan.next();
		scan.useDelimiter(";|\n");
		extremite[0] = new Point(scan.nextInt(), scan.nextInt());
		extremite[1] = new Point(scan.nextInt(), scan.nextInt());
		
		while(scan.hasNext())
		{
			points[i] = scan.next();
			i++;
		}
		
		Runway r =  new Runway(nom, qfu1, qfu2,	extremite, points);
		scan.close();
		return r;
	}
	
}
