/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
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
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.vue.ChargeEnCoursLayerUI;
import fr.iessa.vue.Echelle;

/**
 * Gere graphiquement le chargement de la plateforme,
 * l'affichage de l'image de la plateforme
 * la navigation zoom et scroll sur l'affichage
 * @author hodiqual
 * 
 */
public class PanelPlateforme extends JPanel implements PropertyChangeListener, MouseListener, MouseWheelListener, Observer {

	private static final long serialVersionUID = 25499665468682529L;
	
	private Aeroport _aeroport = null;
	
	private Echelle _echelle;
	
	private PlateformeDrawer _drawer = new PlateformeDrawer();
	
	/** Permet d'avoir la translation a faire apres un drag de la souris */
	Point2D.Double _whereMousePressed = new Point2D.Double();
	Point2D.Double _dxdyscroll = new Point2D.Double();

	/** Indique si une charge lourde est en cours*/
	private ChargeEnCoursLayerUI _layerUI;

	private int _zoomLevel = 1;
	
	public PanelPlateforme(Controleur controleur, Echelle echelle) {
        setLayout(new GridLayout(1,1));
        setBackground(Color.white);

		//Acceleres le paint du component etant le fond d'ecran.
		setOpaque(true);
		
		//Pour une animation fluide il vaut mieux etre en double buffer.
		setDoubleBuffered(true);

		//observe le changement du modele via le controleur (MVC)
		final ModeleEvent[] evts = { ModeleEvent.CHARGEMENT_CARTE_FICHIER_EN_COURS
								    , ModeleEvent.CHARGEMENT_CARTE_FICHIER_DONE
								    , ModeleEvent.CHARGEMENT_CARTE_FICHIER_ERREUR};
		controleur.ajoutVue(this,  evts) ;
		
		_echelle = echelle;
		echelle.addObserver(this);
		
		setAeroport(controleur.getAeroport());
		
		addMouseListener(this);	
		addMouseWheelListener(this);  
	    addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				_echelle.setScroll(new Point2D.Double(), getWidth(), getHeight());
			}
		});
	}
	
	private void setAeroport(Aeroport aeroport) {
		_aeroport = aeroport;
		if(aeroport!=null) {
			_echelle.setLimitesReelles(_aeroport.getMinX(), _aeroport.getMaxX()
										, _aeroport.getMinY(), _aeroport.getMaxY());
			_echelle.setScroll(new Point2D.Double(), getWidth(), getHeight());
		}
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
				setAeroport((Aeroport) evt.getNewValue());
				
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
	 * Reference sur le thread qui est en train de dessiner l'aeroport en memoire.
	 */
	private DessineCarteWorker _workerEncours = null;
	
	/**
	 * Reference sur le thread qui est en train de dessiner une prévisualisation
	 * de l'aeroport en memoire.
	 */
	private DessineCarteWorker _workerPreviewEncours = null;
	
	 /**
	 * Dessin de l'aeroport en memoire tampon
	 */
	private BufferedImage _imageCarteBuffered;
	
	 /**
	 * Dessin d'une previsualisation de l'aeroport en memoire tampon (sans anti-aliasing)
	 */
	private BufferedImage _imageCartePreview;
	
	/**
	 * @param _imageCarteEstPrete est true si _imageCarteBuffered est prete a etre affichee
	 */
	private boolean _imageCarteEstPrete = false;
	
	/** Force le redessin de _imageCarteBuffered */
	private void resetImageCarte() {
		if(_workerEncours!=null && !_workerEncours.isDone())
			_workerEncours.cancel(true);
		
		if(_workerPreviewEncours!=null  && !_workerPreviewEncours.isDone())
			_workerPreviewEncours.cancel(true); 
		
		_workerPreviewEncours = new DessineCarteWorker(false);
		_workerPreviewEncours.execute();
		
		_workerEncours = new DessineCarteWorker(true);
		_workerEncours.execute();
	}
	
	private class DessineCarteWorker extends SwingWorker<BufferedImage, Void> {
		
		private boolean _avecAntialiasing;
		
		DessineCarteWorker(boolean avecAntialiasing) {
			_avecAntialiasing = avecAntialiasing;
		}
		
		@Override
		protected BufferedImage doInBackground() throws Exception {
			return reDessineCarteDansBuffer(_avecAntialiasing); 
		}
		
		@Override
		protected void done() {
			try {
				BufferedImage imageToBuffer = get();
				if(_avecAntialiasing)
				{
					_imageCarteBuffered = imageToBuffer;
					_imageCarteEstPrete = true;
				}	
				else
				{
					_imageCartePreview = imageToBuffer;
					_imageCarteEstPrete = false;
				}	
				
			} catch (CancellationException | InterruptedException | ExecutionException e) {
				//e.printStackTrace();
			}
			repaint();
		}
		
	}

	/** Dessine la carte dans un buffer */
	private BufferedImage reDessineCarteDansBuffer( boolean avecAntialiasing ) {

		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		if(_aeroport != null)
		{	
			Graphics2D g2 = image.createGraphics();
			if( avecAntialiasing )
			{
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
			}
			g2.setClip(0,0, getWidth(), getHeight());
			_drawer.dessineAeroport(_aeroport, g2, _echelle.getAffineTransform());
			g2.dispose();
		}
		
		return image;
	}
	
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		
		if(_imageCarteEstPrete)
			g2.drawImage(_imageCarteBuffered, 0, 0, this);
		else
			g2.drawImage(_imageCartePreview, 0, 0, this);
		//VOLATILE IMAGE: http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
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
	 * @param o observe echelle
	 * @param arg la nouvelle transformation a appliquer
	 */
	@Override
	public void update(Observable o, Object arg) {
		resetImageCarte();
        repaint();				
	}

}
