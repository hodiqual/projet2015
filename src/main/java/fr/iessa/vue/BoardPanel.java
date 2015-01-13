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

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/**
 * Gere graphiquement le chargement de la plateforme,
 * le chargement du trafic
 * l'affichage de l'image de la plateforme et de son trafic
 * la navigation zoom et scroll sur l'affichage
 * @author hodiqual
 * 
 */
public class BoardPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 25499665468682529L;

	Controleur _controleur;
	
	BufferedImage _carteEnFond = null;
	
	/** Permet d'avoir la translation à faire après un drag de la souris */
	Point2D.Double _dxdyscroll = new Point2D.Double();
	
	public BoardPanel(Controleur controleur) {
		super();

        setLayout(new GridLayout(1,1));
		_controleur = controleur;
		
		//Acceleres le paint du component:
		setOpaque(true);
		
		//Pour une animation fluide il vaut mieux etre en double buffer.
		setDoubleBuffered(true);
		
		JButton but = new JButton("Charge Moi");
		but.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				_controleur.chargerCarte("lfpg.txt");
			    System.out.println("Chargement en cours");
			}
		} );
		
		//add(but);
		
		addMouseListener(new MouseAdapter() {
			
			Point2D.Double _whereMousePressed = new Point2D.Double();
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				_dxdyscroll.x += e.getPoint().getX()-_whereMousePressed.getX();
				_dxdyscroll.y += e.getPoint().getY()-_whereMousePressed.getY();

				System.out.println( "mouseReleased: " + _dxdyscroll);
				
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				_whereMousePressed.x = e.getPoint().getX();
				_whereMousePressed.y = e.getPoint().getY();
				System.out.println( "mousePressed: " + _whereMousePressed); 
			}
			
		});
		
		addComponentListener(new ComponentAdapter() {
			@Override
		    public void componentResized(ComponentEvent e) {
		        // TODO sauvegarder le centre de l'image pour faire une translation apres
				// comme dans google maps
		    }
		});
		
		setVisible(true);
		
		//rendre sensible le controleur 
		_controleur.ajoutVue(this);
		
		
		System.out.println("Lancement chargement");
		_controleur.chargerCarte("lfpg.txt");
		

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
			System.out.println("Je suis content");
			_carteEnFond = (BufferedImage) evt.getNewValue();
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
		
        // active le lissage des formes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                 RenderingHints.VALUE_RENDER_QUALITY);
		
		if(_carteEnFond != null)
		{	
			System.out.println("DESSIN DEBUT!!!!");
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        int width = (int) screenSize.getWidth();
	        int height = (int) screenSize.getHeight();
	        
			AffineTransform at = new AffineTransform();
			at.translate(-_carteEnFond.getWidth()/2, -_carteEnFond.getHeight()/2);
			
			AffineTransform mouseScroll = new AffineTransform();
			
			mouseScroll.translate(_dxdyscroll.getX(), _dxdyscroll.getY());
			//g2.transform(at);
			System.out.println(mouseScroll);
			g2.transform(mouseScroll);
			g2.drawImage(_carteEnFond, 0, 0, null);
			System.out.println("DESSIN FIN!!!!");
		}
    }

}
