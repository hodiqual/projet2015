/**
 * 
 */
package fr.iessa.vue;

import java.awt.Color;
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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.vue.infra.InfrastructureDrawer;

/**
 * Gere graphiquement le chargement de la plateforme,
 * le chargement du trafic
 * l'affichage de l'image de la plateforme et de son trafic
 * la navigation zoom et scroll sur l'affichage
 * @author hodiqual
 * 
 */
public class InfrastructurePanel extends JPanel implements PropertyChangeListener, MouseListener, MouseWheelListener {

	private static final long serialVersionUID = 25499665468682529L;

	Controleur _controleur;
	
	InfrastructureDrawer _drawer = new InfrastructureDrawer();
	AffineTransform _mouseScroll = new AffineTransform();
	
	Aeroport _aeroport;
	
	BufferedImage _carteEnFond = null;
	
	int _largeurImage;
	int _hauteurImage;
	
	/** Permet d'avoir la translation a faire apres un drag de la souris */
	Point2D.Double _whereMousePressed = new Point2D.Double();
	Point2D.Double _dxdyscroll = new Point2D.Double();

	/** Indique si un charge lourde est en cours*/
	private ChargeEnCoursLayerUI _layerUI;

	private int _zoomLevel = 1;
	
	public InfrastructurePanel(Controleur controleur) {
        setLayout(new GridLayout(1,1));
        setBackground(Color.white);
        
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
		addMouseWheelListener(this);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    _largeurImage = (int) screenSize.getWidth();
	    _hauteurImage = (int) screenSize.getHeight();
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
			_aeroport = (Aeroport) evt.getNewValue();

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
        
       
		if(_aeroport != null)
		{				 
		    
			//Cadrage et position avec clip ou getSubimage
			//Avec Clip
			g2.setClip(0,0, getWidth(), getHeight());
			_drawer.dessineAeroport(_aeroport, g2, _largeurImage, _hauteurImage, _mouseScroll);
			//System.out.println("ZOOOOOOM : " + _zoomLevel);
			//LibereMemoire.controleMemoire();
		}
    }

	@Override
	public void mouseClicked(MouseEvent e) {
			if(_aeroport == null)
			{
				_controleur.chargerCarte("lfpg.txt");
				if(_layerUI!=null)
					_layerUI.start();
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

		_dxdyscroll.x = Double.min(_dxdyscroll.x, _largeurImage-getWidth());
		_dxdyscroll.y = Double.min(_dxdyscroll.y, _hauteurImage-getHeight());
		
		_mouseScroll = new AffineTransform();
		_mouseScroll.translate(-(int)(_dxdyscroll.getX()), -(int)(_dxdyscroll.getY()));

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

	/** TODO Rajouter image  */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		if(_aeroport != null)
		{
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
			{	
				int rotation = e.getWheelRotation();
				if(rotation<0) // -> zoom
				{
					if(_zoomLevel < 10)
						_zoomLevel++;
				}
				else // -> de-zoom
				{
					if(_zoomLevel > 1)
						_zoomLevel--;
				}
			}

			
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        int widthS = (int) screenSize.getWidth();
	        int heightS = (int) screenSize.getHeight();
	        double oldLargeurImage = _largeurImage;
	        double oldHauteurImage = _hauteurImage;
	        
	        _largeurImage = (int)_zoomLevel*widthS;
	        _hauteurImage = (int)_zoomLevel*heightS; 
	        
	        double scaleX = _largeurImage/oldLargeurImage;
	        double scaleY = _hauteurImage/oldHauteurImage;
	        
	        _dxdyscroll.x = e.getX()*(scaleX-1) + scaleX*_dxdyscroll.x;
	        _dxdyscroll.y = e.getY()*(scaleY-1) + scaleY*_dxdyscroll.y;
	        
			_dxdyscroll.x = Double.max(_dxdyscroll.x, 0D);
			_dxdyscroll.y = Double.max(_dxdyscroll.y, 0D);

			_dxdyscroll.x = Double.min(_dxdyscroll.x, _largeurImage-getWidth());
			_dxdyscroll.y = Double.min(_dxdyscroll.y, _hauteurImage-getHeight());
			
			_mouseScroll = new AffineTransform();
			_mouseScroll.translate(-(int)(_dxdyscroll.getX()), -(int)(_dxdyscroll.getY()));
			
	        
	        repaint();
			
		}
			
		
	}

}
