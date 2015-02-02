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
import java.awt.event.ComponentListener;
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
import java.awt.image.VolatileImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import fr.iessa.controleur.Controleur;
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
public class PanelInfrastructure extends JPanel implements PropertyChangeListener, MouseListener, MouseWheelListener, Observer {

	private static final long serialVersionUID = 25499665468682529L;

	private Controleur _controleur;
	
	private Aeroport _aeroport;
	
	private Echelle _echelle;
	
	private InfrastructureDrawer _drawer = new InfrastructureDrawer();
	
	int _largeurImage;
	int _hauteurImage;
	
	/** Permet d'avoir la translation a faire apres un drag de la souris */
	Point2D.Double _whereMousePressed = new Point2D.Double();
	Point2D.Double _dxdyscroll = new Point2D.Double();
	private AffineTransform _mouseScroll = new AffineTransform();

	/** Indique si une charge lourde est en cours*/
	private ChargeEnCoursLayerUI _layerUI;

	private int _zoomLevel = 1;
	
	public PanelInfrastructure(Controleur controleur, Echelle echelle) {
        setLayout(new GridLayout(1,1));
        setBackground(Color.white);
        
		_controleur = controleur;
		
		//Acceleres le paint du component etant le fond d'ecran.
		setOpaque(true);
		
		//Pour une animation fluide il vaut mieux etre en double buffer.
		setDoubleBuffered(true);

		//observe le changement du modele via le controleur (MVC)
		_controleur.ajoutVue(this);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    _largeurImage = (int) screenSize.getWidth();
	    _hauteurImage = (int) screenSize.getHeight();
		
		addMouseListener(this);	
		addMouseWheelListener(this);  
	    addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resetImageCarte();	
			}
		});
	    

		_echelle = echelle;
		echelle.addObserver(this);
	    
	    
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 * Methode appelee lorque le controleur observe un changement d'etat du modele.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
			case CHARGEMENT_CARTE_FICHIER_DONE:
				//http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
				_aeroport = (Aeroport) evt.getNewValue();
				_echelle.setLimitesReelles(_aeroport.getMinX(), _aeroport.getMaxX()
						  , _aeroport.getMinY(), _aeroport.getMaxY());
			case CHARGEMENT_CARTE_FICHIER_ERREUR:
				if(_layerUI!=null)
					_layerUI.stop();
				repaint();
				break;
			case CHARGEMENT_CARTE_FICHIER_EN_COURS:
				if(_layerUI!=null)
					_layerUI.start();
			break;

		default:
			break;
		}
	}
	


	 
	 /**
	 * Dessin de l'aeroport en memoire tampon (memoire de la carte graphique)
	 */
	//private VolatileImage _imageCarteBuffered;
	private BufferedImage _imageCarteBuffered;
	
	/** Force le redessin de _imageCarteBuffered */
	private void resetImageCarte() {
		_imageCarteBuffered = null;
	}
	
	private class DessineCarteWorker extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			if (_imageCarteBuffered==null) // creation de l'image
				_imageCarteBuffered = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			
			reDessineCarteDansCarteGraphique(); 	
			return null;
		}
		
		@Override
		protected void done() {
			repaint();
		}
		
	}

	/** Redessine l'image de la carte dans la carte graphique */
	private void reDessineCarteDansCarteGraphique() {
		if(_aeroport != null)
		{	
			Graphics2D g2 = _imageCarteBuffered.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2.setClip(0,0, getWidth(), getHeight());
			_drawer.dessineAeroport(_aeroport, g2, _echelle.getAffineTransform());
			g2.dispose();
		}
	}
	
	int Cpt = 0;
	@Override
	public void paintComponent(Graphics g) {
		//Effacer le contenu pour les animations.
		System.err.println("CONTEO " + Cpt++);
		//VOLATILE IMAGE: http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
		if(_aeroport != null)
		{		
			if ( _imageCarteBuffered != null ){

				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setClip(0,0, getWidth(), getHeight());
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);

				g2.drawImage(_imageCarteBuffered, 0, 0, this);
			}else
			{	
				//BufferedImage temp = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
				//Graphics2D g2temp = temp.createGraphics();
				//_drawer.dessineAeroport(_aeroport, g2temp, _echelle.getAffineTransform());
				//g2.drawImage(temp, 0, 0, this);
				DessineCarteWorker worker = new DessineCarteWorker();
				worker.execute();
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//Rien a faire		
	}
			
	@Override
	public void mousePressed(MouseEvent e) {
		if(_aeroport != null)
		{
			_whereMousePressed.x = e.getPoint().getX();
			_whereMousePressed.y = e.getPoint().getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(_aeroport != null)
		{			
			Point2D.Double ecartRelatif = new Point2D.Double(e.getPoint().getX()-_whereMousePressed.getX()
													 		,e.getPoint().getY()-_whereMousePressed.getY() );
			
			_echelle.setScroll(ecartRelatif, getWidth(), getHeight());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//Rien a faire
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Rien a faire
	}

	public void setChargeEnCoursLayerUI(ChargeEnCoursLayerUI layerUI) {
		this._layerUI = layerUI;
		
	}

	/** TODO Rajouter image de la gestion du zoom pour la javadoc*/
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		if(_aeroport != null)
		{
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL)
			{	
				int rotation = e.getWheelRotation();
				if(rotation<0) // -> zoom
				{
					if(_zoomLevel < 20)
						_zoomLevel++;
				}
				else // -> de-zoom
				{
					if(_zoomLevel > 1)
						_zoomLevel--;
				}
			}
			
			_echelle.setZoomLevel(_zoomLevel, e.getPoint(), getWidth(), getHeight());
		}		
	}

	/**
	 * 
	 * @param o observe echelle
	 * @param arg la nouvelle transformation a appliquer
	 */
	@Override
	public void update(Observable o, Object arg) {
		resetImageCarte();
        repaint();				
	}

}
