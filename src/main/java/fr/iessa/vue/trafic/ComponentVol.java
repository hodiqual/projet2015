package fr.iessa.vue.trafic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fr.iessa.metier.Instant;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.Echelle;

public class ComponentVol extends JComponent {

	private int width = 32;
	private int height = 32;
	
	private BufferedImage _imageCourante;
	private ShapeAvionFactory _imageFactory;
	private Rectangle2D _collisionHighlight;
	private Echelle _echelle;
	private Vol _vol;
	private Point2D.Double _coordCouranteDouble = new Point2D.Double();
	private Point2D.Double _coordSuivanteDouble = new Point2D.Double();
	private Point _coordCourante = new Point();
	private GeneralPath _cheminParcouru = new GeneralPath();
	
	public ComponentVol(Vol v, Echelle echelle) { 
		
		setOpaque(true);
		
		//setBackground(Color.red);
		//setBorder(BorderFactory.createTitledBorder("AVION"));
		//setBounds(0,0,width,height);

		_echelle = echelle;
		_vol = v;
		
		switch (v.getCategorie()) {
			case HIGH:
				_imageFactory = ShapeAvionFactory.HIGH_ORANGE;
				break;
			case MEDIUM:
				_imageFactory = ShapeAvionFactory.MEDIUM_BLUE;
				break;
			case LIGHT:
				_imageFactory = ShapeAvionFactory.LIGHT_VERT;
				break;
			default:
				break;
		}
		
		_imageCourante = _imageFactory.get(0);
		
		width = _imageCourante.getWidth();
		height = _imageCourante.getHeight(); 
		
		setMinimumSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));
		
		if(_vol.aDesCollisions())
			_collisionHighlight = new Rectangle(new Dimension(width, height));
		
		TreeMap<Instant,Point> coordOrdonne = new TreeMap<Instant,Point>(_vol.getInstantVersCoord());
		Point premierPoint = coordOrdonne.pollFirstEntry().getValue();
		_cheminParcouru.moveTo(premierPoint.x, premierPoint.y);
		coordOrdonne.values().forEach( p ->_cheminParcouru.lineTo(p.x, p.y));
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Avion clique: " + v.getId());
				System.out.println("isDisplayable: " + isDisplayable());
				System.out.println("Showing: " + isShowing());
				System.out.println("Hauteur: " + getHeight());
				System.out.println("Largeur: " + getWidth());
				System.out.println("Visible " + isVisible());
				setToolTipText("PROUT");
			}

		});
	}
	
	public void setX(int xPos){ 
		_coordCourante.x = xPos;
		setLocation(_coordCourante);
	}

	public int getX(){
	    return _coordCourante.x;
	}

	public void setY(int yPos){
		_coordCourante.y = yPos;
		setLocation(_coordCourante);
	}

	public int getY(){
	    return _coordCourante.y;
	}

	public int getWidth(){
		return width;
	} 

	public int getHeight(){
		return height;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
		Graphics2D g2 = (Graphics2D) g.create();
		if(_collisionHighlight!=null){
			Color colorToRestore = g2.getColor();
			g2.setColor(Color.red);
			g2.fill(_collisionHighlight);
			g2.setColor(colorToRestore);
		}
		g2.drawImage(_imageCourante, 0, 0, null);
	}
	
	 /**
     * Retourne l'angle entre la droite parallele a l'axe des x passant par depart et 
     * la droite (depart,arrivee).
     * @return l'angle en degre en 0 et 359 degres.
     */
    private double angle(Point2D.Double depart, Point2D.Double arrivee) {
        double dx = arrivee.x - depart.x;
        double dy = arrivee.y - depart.y;
        return Math.toDegrees(Math.atan2(-dy, dx)+2*Math.PI)%360;
    }
	
	public void update(JPanel panel){
		if(_vol.getCoordCourante() == null)
			panel.remove(this);
		if(_vol.getCoordCourante() != null)
		{
			if(!panel.isAncestorOf(this))
					panel.add(this);
				
			_echelle.getAffineTransform().transform(_vol.getCoordCourante(), _coordCouranteDouble);
			_coordCourante.setLocation(_coordCouranteDouble);
			
			//_cheminParcouruShape = _cheminParcouru.createTransformedShape(_echelle.getAffineTransform());
			
			
			//Determine le sprite a dessiner selon l'angle entre le point courant et le point suivant
			if(_vol.getCoordSuivante() != null) {
				_echelle.getAffineTransform().transform(_vol.getCoordSuivante(), _coordSuivanteDouble);
				_imageCourante = _imageFactory.get(angle(_coordCouranteDouble, _coordSuivanteDouble));
			}		
	
			setX(_coordCourante.x-width/2);
			setY(_coordCourante.y-height/2);
		}
	}
}