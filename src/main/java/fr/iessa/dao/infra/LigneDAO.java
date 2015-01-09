/**
 * 
 */
package fr.iessa.dao.infra;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import fr.iessa.dao.core.DAO;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

/**
 * @author hodiqual
 *
 */
public class LigneDAO implements DAO<Ligne> {

	@Override
	/**
	 * Charge en memoire une ligne de texte decrivant une ligne 
	 * ligneAtraiter: decrit le ligne de la maniere suivante
	 * "L _ 8 H D -3873,512;-3863,523;-3856,531;-3850,537;-3845,543;-3840,550;-3834,559;-3827,572"
	 */
	public Ligne charger(String ligneAtraiter) 
	{
		Scanner scan = new Scanner(ligneAtraiter);
		scan.useDelimiter(" |\n");
		scan.next(); //skip L
		scan.next(); //skip _
		
		Ligne result =  new Ligne( chargerVitesse(scan),
								   chargerCategorie(scan), 
								   chargerDirection(scan), 
								   chargerPoints(scan) );
		
		scan.close();
		return result;
	}
	
	public int chargerVitesse(Scanner scan)
	{
		return scan.nextInt();
	}
	
	public Categorie chargerCategorie(Scanner scan)
	{
		return Categorie.from(scan.next());
	}
	
	public Direction chargerDirection(Scanner scan)
	{
		return Direction.from(scan.next());
	}
	
	public Queue<Point> chargerPoints(Scanner scan)
	{
		Queue<Point> result = new LinkedList<Point>();
		scan.useDelimiter(" |,|;|\n");
		while(scan.hasNext())
		{
			result.add( new Point(scan.nextInt(), scan.nextInt() ) );
		}
		return result;
	}

}
