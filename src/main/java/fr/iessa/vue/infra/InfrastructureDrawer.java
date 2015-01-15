/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.List;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Ligne;

/**
 * @author hodiqual
 *
 */
public class InfrastructureDrawer {
	
	private static LignesDrawer lignesDrawer = new LignesDrawer();
	
	private Aeroport _aeroport = null;
	
	public double _minReelX, _maxReelX, _minReelY, _maxReelY;
	
	private int _largeurImage, _hauteurImage;
	
	public AffineTransform _transfoAffine;
	
	public void dessineAeroport(Aeroport aeroport, Graphics2D g2, double largeurImage, double hauteurImage )
	{
		// 1. Calcul ou recalcul de la transformation affine si necessaire
		if( aeroport != _aeroport || largeurImage != _largeurImage || hauteurImage != _hauteurImage)
		{
			calculeTransposition(aeroport, largeurImage, hauteurImage);
		}
		
		// 2. Sauvegarde la transformation courante
		AffineTransform transformToRestore = g2.getTransform();
		
		// 3. Appliquer la transformation
		g2.transform(_transfoAffine);
		
		// 4. Faire les dessins
		lignesDrawer.dessine(aeroport, g2);
		
		// 5. restaure la transformation initialle.
		g2.setTransform(transformToRestore);
	}

	/** 
	 * Calcul la transformation affine pour transposer des coordonnees du monde reel
	 * aux coordonnees systeme ecran.
	 * 
	 * @see http://www.ukonline.be/programmation/java/tutoriel/chapitre10/page6.php
	 * 
	 */
	private void calculeTransposition(Aeroport aeroport, double largeurImage, double hauteurImage) {
		
		//faut-il recalculer les _minReelX, _maxReelX, _minReelY, _maxReelY
		if(aeroport != _aeroport)
		{			
			_aeroport = aeroport;
			
			_minReelX = _minReelY = Integer.MAX_VALUE;
			_maxReelX = _maxReelY = Integer.MIN_VALUE;
			 
	        trouverLimitesReels(aeroport.get_lignes());
	        trouverLimitesReels(aeroport.get_taxiway());
	        trouverLimitesReels(aeroport.get_pushbacks());
		}
		
		double xScale = largeurImage / (_maxReelX-_minReelX);
        double yScale = hauteurImage / (_maxReelY-_minReelY);
        
        _transfoAffine = new AffineTransform();
        
        _transfoAffine.translate(-xScale*_minReelX, yScale*_maxReelY);
        _transfoAffine.scale(xScale, -yScale);
		
	}

	private void trouverLimitesReels(List<? extends Ligne> lignes) {
       
		for (Ligne ligne : lignes) {
			GeneralPath path = ligne.get_lignePointAPoint();
			double l_minReelX = path.getBounds().getMinX();
			double l_maxReelX = path.getBounds().getMaxX();
			double l_minReelY = path.getBounds().getMinY();
			double l_maxReelY = path.getBounds().getMaxY();
			
			_minReelX = Double.min(_minReelX, l_minReelX);
			_minReelY = Double.min(_minReelY, l_minReelY);
			_maxReelX = Double.max(_maxReelX, l_maxReelX);
			_maxReelY = Double.max(_maxReelY, l_maxReelY);	
		}
	}
	
	

}
