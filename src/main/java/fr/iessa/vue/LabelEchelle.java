/**
 * 
 */
package fr.iessa.vue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/**
 * Classe affichant une barre adapte de l echelle.
 * @author duvernal
 *
 */
public class LabelEchelle extends JComponent implements PropertyChangeListener, Observer {
	
	/** La constante pour la largeur d'origine */
	final int LARGEURORGINE = 96;
	/** La constante pour marge de la barre d'echelle */
	final int MARGIN = 50;
	/** La largeur a afficher de la barre d'echelle */
	int width = LARGEURORGINE;
	/** La hauteur de la barre d'echelle */
	int height = 10;
	/** Les bandes noires de la barre d'echelle */
    int strip = width/8;
	/** L'image creee a afficher */
	private BufferedImage bufferedImage;
   
	
    /**
     * Constructeur LabelMetre.
     * 
     * @param echelle
     *            L'echelle de la vue
     * @param controleur
     *            Le controleur
     */
	public LabelEchelle(Controleur controleur, Echelle echelle)
	{
		setOpaque(true);

		setPreferredSize(new Dimension(width+MARGIN,height));		
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
		
		final ModeleEvent[] evts = {ModeleEvent.UPDATE_INSTANT};
		controleur.ajoutVue(this,  evts) ;
		echelle.addObserver(this);
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(Color.BLACK);     
        g2.drawRect(0, 0, width-1, height-1);
        float dash[] = { strip};
        g2.setStroke(new BasicStroke(10f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, strip, dash, 0.0f));
        g2.drawLine(strip, 0, width, 0);
        g2.drawLine(0, 10, width, 10);  
        g2.drawImage(bufferedImage, 0, 0, null);
    }

	
	/**
	 * @param o observe echelle
	 * @param arg la nouvelle transformation a appliquer
	 */
	@Override
	public void update(Observable o, Object arg) {
			AffineTransform transformationCourante = (AffineTransform) arg;
			double factor = transformationCourante.getScaleX();
			double  longueur = 96/factor; 
	        double resteop = longueur%100;            
	        double resteop2 = longueur%1000;     
				if (longueur>=1000)
				{
					width = LARGEURORGINE+(int)resteop2/100;
				}
				else if (longueur<=1000 && longueur>=100)
				{
		            if (resteop<50)
	            	{
		            	width = LARGEURORGINE-(int)resteop/10;
	            	}
		            else
	            	{
		            	width = LARGEURORGINE+(int)resteop/10;
	            	}
				}
				else if (longueur<100)
				{
					width = LARGEURORGINE;
	           
				}
				strip = width/8;
				repaint();	
				
				}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

				
	

}



