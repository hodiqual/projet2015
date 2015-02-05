/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.List;
import java.util.Map;

import fr.iessa.metier.infra.Aeroport;
import fr.iessa.metier.infra.Ligne;
import fr.iessa.metier.infra.Point;
import fr.iessa.vue.Echelle;

/**
 * @author hodiqual
 *
 */
public class InfrastructureDrawer {
	
	private static LignesDrawer lignesDrawer = new LignesDrawer();
	
	private static PointsDrawer pointsDrawer = new PointsDrawer();
	
	private static RunwayDrawer runwaysDrawer = new RunwayDrawer();

	public void dessineAeroport(Aeroport aeroport, Graphics2D g2, AffineTransform echelle)
	{	
		// 1. Sauvegarde la transformation courante et la couleur
		AffineTransform transformToRestore = g2.getTransform();

		// 2. Appliquer la transformation
		g2.transform(echelle);
		
		// 3. Faire les dessins
		lignesDrawer.dessine(aeroport, g2);
		runwaysDrawer.dessine(aeroport, g2);

		lignesDrawer.dessineMarquageAuSol(aeroport, g2);
		pointsDrawer.dessine(aeroport, g2);
		
		// 4. Restaure la transformation initiale.
		g2.setTransform(transformToRestore);
	}
}
