/**
 * 
 */
package fr.iessa.metier.infra;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Queue;

import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.Direction;

/**
 * @author hodiqual
 *
 */
public class Taxiway extends Ligne {
	
	/** Nom de la taxiway */
	String _nom;
	
	public Taxiway( String nom, int vitesseDeRoulage, Categorie categorie, Direction direction, Queue<Point> listePointsOrdonnee )
	{
		super(vitesseDeRoulage, categorie, direction, listePointsOrdonnee);
		_nom = nom;
	}

	/** initialise la  _lignePointAPoint en courbe de bezier*/
	private void initialiseLigneBrisee(Queue<Point> listePointsOrdonnee) {
		Point initialPoint = listePointsOrdonnee.poll();
		_lignePointAPoint.moveTo(initialPoint.getX(), initialPoint.getY());
		listePointsOrdonnee.forEach( (p) -> _lignePointAPoint.lineTo( p.getX(), p.getY() ) );
		/*TODO gerer les lignes if(!listePointsOrdonnee.isEmpty())
		{
			Point premierPoint = listePointsOrdonnee.poll();
			_lignePointAPoint.moveTo( premierPoint.getX(), premierPoint.getY() );
		}
		
		while(!listePointsOrdonnee.isEmpty())
		{
			Point2D pointCourant = _lignePointAPoint.getCurrentPoint();
			
			Point p1 = listePointsOrdonnee.poll();
			if( listePointsOrdonnee.isEmpty() )
			{
				_lignePointAPoint.lineTo( p1.getX(), p1.getY() );
			}
			else
			{
				Point p2 = listePointsOrdonnee.poll();
				if ( is3PointsAligned(pointCourant,p1,p2) )
				{
					_lignePointAPoint.lineTo( p2.getX(), p2.getY() );
				}
				else
				{
					
				}
				
				if( listePointsOrdonnee.isEmpty() )
					_lignePointAPoint.lineTo( p.getX(), p.getY() );
				
			}
			Point p3 = listePointsOrdonnee.poll();
		}
		_lignePointAPoint.curveTo(x1, y1, x2, y2, x3, y3); */
	}
	
	/** Verifie si les 3 points sont alignes en verifiant 
	 * l'aire du triangle formee par les 3 points est nulle ou non.
	 */
	private boolean is3PointsAligned(Point A, Point B, Point C)
	{
		return ( A.getX() * (B.getY() - C.getY()) + B.getX() * (C.getY() - A.getY()) + C.getX() * (A.getY() - B.getY()) ) / 2 == 0;
	}
}
