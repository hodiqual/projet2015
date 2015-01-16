/**
 * 
 */
package fr.iessa.vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolTip;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.LibereMemoire;
import fr.iessa.controleur.ModeleEvent;

/**
 * Gere graphiquement le chargement de la plateforme,
 * le chargement du trafic
 * l'affichage de l'image de la plateforme et de son trafic
 * la navigation zoom et scroll sur l'affichage
 * @author hodiqual
 * 
 */
public class InfrastructurePanel extends JPanel implements PropertyChangeListener, MouseListener {

	private static final long serialVersionUID = 25499665468682529L;

	Controleur _controleur;
	
	BufferedImage _carteEnFond = null;
	
	/** Permet d'avoir la translation a faire apres un drag de la souris */
	Point2D.Double _whereMousePressed = new Point2D.Double();
	Point2D.Double _dxdyscroll = new Point2D.Double();

	/** Indique si un charge lourde est en cours*/
	private ChargeEnCoursLayerUI _layerUI;

	private int _zoomLevel;
	
	public InfrastructurePanel(Controleur controleur) {
        setLayout(new GridLayout(1,1));
		_controleur = controleur;
		
		//Acceleres le paint du component:
		setOpaque(true);
		
		//Pour une animation fluide il vaut mieux etre en double buffer.
		setDoubleBuffered(true);
		
		addComponentListener(new ComponentAdapter() {
			@Override
		    public void componentResized(ComponentEvent e) {
		        // TODO sauvegarder le centre de l'image pour faire une translation apres
				// comme dans google maps
		    }
		});

		//rendre sensible le controleur 
		_controleur.ajoutVue(this);
		addMouseListener(this);		
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 * Methode appelee lorque le controleur observe un changement d'etat du modele.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
		case CHARGEMENT_CARTE_GRAPHIQUE_DONE:
			//http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
			System.out.println("Je suis content Vue");
			_carteEnFond = (BufferedImage) evt.getNewValue();

			if(_layerUI!=null)
				_layerUI.stop();
			
			repaint();
			break;

		default:
			break;
		}
	}
	
	//Faire le scroll de la map (pas le zoom) avec la librairie pour faire l'animation
	
	@Override
    public void paintComponent(Graphics g) {
		//Effacer le contenu pour les animations.
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		
		//VOLATILE IMAGE: http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
		
        // active le lissage des formes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                 RenderingHints.VALUE_RENDER_QUALITY);
		if(_carteEnFond != null)
		{	
			//Cadrage et position avec clip ou getSubimage
			//Avec Clip
			g2.setClip(0,0, getWidth(), getHeight());
			g2.drawImage(_carteEnFond, -(int)(_dxdyscroll.getX()), -(int)(_dxdyscroll.getY()), null);
			//ou
			//BufferedImage..getSubimage(x, y, width, height)
			//g2.drawImage(_carteEnFond.getSubimage((int)(_dxdyscroll.getX()),(int)(_dxdyscroll.getY()), getWidth(), getHeight()), null, null);

		}
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println( "mouseClicked: " + this); 
		if(_carteEnFond == null)
		{
			_controleur.chargerCarte("lfpg.txt");
			if(_layerUI!=null)
				_layerUI.start();
		}
		else
		{
			_zoomLevel++;
			_zoomLevel%=6;
		}
			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		_whereMousePressed.x = e.getPoint().getX();
		_whereMousePressed.y = e.getPoint().getY();
		System.out.println( "mousePressed: " + _whereMousePressed); 
	}

	@Override
	public void mouseReleased(MouseEvent e) {				
		_dxdyscroll.x -= e.getPoint().getX()-_whereMousePressed.getX();
		_dxdyscroll.y -= e.getPoint().getY()-_whereMousePressed.getY();
	
		_dxdyscroll.x = Double.max(_dxdyscroll.x, 0D);
		_dxdyscroll.y = Double.max(_dxdyscroll.y, 0D);

		_dxdyscroll.x = Double.min(_dxdyscroll.x, _carteEnFond.getWidth()-getWidth());
		_dxdyscroll.y = Double.min(_dxdyscroll.y, _carteEnFond.getHeight()-getHeight());
	
		System.out.println( "mouseReleased: " + _dxdyscroll);
	
		repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setChargeEnCoursLayerUI(ChargeEnCoursLayerUI layerUI) {
		this._layerUI = layerUI;
		
	}

}
