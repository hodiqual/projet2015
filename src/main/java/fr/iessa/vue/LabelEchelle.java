/**
 * 
 */
package fr.iessa.vue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/**
 * @author duvernal
 *
 */
public class LabelEchelle extends JComponent implements PropertyChangeListener {


	private int width = 96;
	private int height = 10;
	
	private BufferedImage bufferedImage;
    private static final Stroke STROKE = new BasicStroke(160);
    private static final Font FONT = new Font("Sans Serif", Font.BOLD, 12);
    /**
     * Horizontal margin between scale bar and right border of the map
     */
    private static final int MARGIN_X = 0;
    /**
     * Vertical margin between scale bar and bottom border of the map
     */
    private static final int MARGIN_Y = 0;
    private static final int DESIRED_SCALE_BAR_WIDTH = 16;
   // public static final UnitSystem unitSystem = UnitSystem.Metric;
	
	

	public LabelEchelle(Controleur controleur)
	{
		
		setOpaque(true);

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		setMinimumSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));
		
		
		BufferedImage bufferedImage = new BufferedImage(200, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
  
			
		
		
		final ModeleEvent[] evts = {ModeleEvent.UPDATE_INSTANT};
		controleur.ajoutVue(this,  evts) ;
	}

	
    
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		Graphics2D g2 = (Graphics2D) g.create();
		
        // fill it with the translucent green
        g2.setColor(Color.BLACK);
        float dash[] = { 12f};
        g2.setStroke(new BasicStroke(10f, BasicStroke.CAP_BUTT,
		        BasicStroke.JOIN_MITER, 12, dash, 0.0f));
        g2.drawLine(12, 0, 96, 0);
        g2.drawLine(0, 10, 96, 10);


        String value = "essai";
        g2.setFont(FONT);
        g2.drawString(value, 0, 0);
		
        
        
        g2.drawImage(bufferedImage, 0, 0, null);
    }

	
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	//	setText(evt.getNewValue().toString());
	}

}



