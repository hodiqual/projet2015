package fr.iessa.dao.infra;

import fr.iessa.dao.core.DAO;
import fr.iessa.metier.infra.Point;

import java.util.Scanner;

/** Classe qui permet de créer un objet Point à partir d'une ligne "P" du fichier
 * @author THOMAS Raimana
 * @version 1.0 
 * Exemple d'une ligne Point : P K05 0 2741,-243
 */

public class PointDAO implements DAO<Point> {

	@Override
	public Point charger(String ligneAtraiter) {
		String nom, type;
		int x, y;
		
		Scanner scan = new Scanner(ligneAtraiter);
		scan.useDelimiter(" |,|\n");
		scan.next(); //skip P
		nom = scan.next();
		type = scan.next();
		x = scan.nextInt();
		y = scan.nextInt();
		
		scan.close();
		
		Point p = new Point(nom, type, x, y);
		return p;
	}

}
