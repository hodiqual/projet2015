package fr.iessa.vue.trafic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import fr.iessa.metier.Instant;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.Echelle;
import fr.iessa.vue.Ressources;

public class ComponentVol extends JComponent {

	private int width = 32;
	private int height = 32;
	// ***********************************  flyweight pour echelle ********************************** //
	private Shape shape = new Rectangle(new Dimension(32,32));
	private Shape shapeADessiner;
	
	private BufferedImage _imageCourante;
	private ShapeAvionFactory _imageFactory;
	
	private Echelle _echelle;
	private Vol _vol;
	private Point2D.Double _coordCouranteDouble = new Point2D.Double();
	private Point2D.Double _coordSuivanteDouble = new Point2D.Double();
	private Point _coordCourante = new Point();
	private GeneralPath _cheminParcouru = new GeneralPath();
	private AffineTransform rotation = new AffineTransform();
	
	public ComponentVol(Vol v, Echelle echelle) { 
		
		_imageFactory = ShapeAvionFactory.HIGH_BLUE;
		_imageCourante = _imageFactory.get(0);
		
		width = _imageCourante.getWidth();
		height = _imageCourante.getHeight(); 
		
		//setBackground(Color.black);
		setOpaque(true);
		//setBorder(BorderFactory.createTitledBorder("AVION"));
		//setBounds(0,0,width,height);
		shape = AffineTransform.getRotateInstance(Math.PI/4).createTransformedShape(shape);
		setMinimumSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));

		_vol = v;
		TreeMap<Instant,Point> coordOrdonne = new TreeMap<Instant,Point>(_vol.getInstantVersCoord());

		Point premierPoint = coordOrdonne.pollFirstEntry().getValue();
		_cheminParcouru.moveTo(premierPoint.x, premierPoint.y);
		coordOrdonne.values().forEach( p ->_cheminParcouru.lineTo(p.x, p.y));
		_echelle = echelle;
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
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
		Stroke strokeToRestore = g2.getStroke();
		Color colorToRestore = g2.getColor();
		g2.setColor(Color.ORANGE);
		//g2.fill(shapeADessiner);
		g2.setColor(Color.BLUE);
		//g2.draw(shapeADessiner);
		g2.drawImage(_imageCourante, 0, 0, null);
		//g2.setColor(Color.RED);
		//g2.setStroke(new BasicStroke(11));
		//g2.draw(_cheminParcouruShape);
		
		g2.setStroke(strokeToRestore);
		g2.setColor(colorToRestore);
		
		
	}
	
	  /**
     * Retourne l'angle entre la droite parallele ˆ l'axe des x passant par depart et 
     * la droite (depart,arrivee).
     * @return the angle in radians (between -pi and pi) 
     */
    private double angle(Point2D.Double depart, Point2D.Double arrivee) {
        double dx = arrivee.x - depart.x;
        double dy = arrivee.y - depart.y;
        return Math.toDegrees(Math.atan2(dy, dx));
    }

	private Shape _cheminParcouruShape;
	
	public void update(JPanel panel){
		System.out.println(this + "UPDATE");
		if(_vol.getCoordCourante() == null)
			panel.remove(this);
		if(_vol.getCoordCourante() != null)
		{
			if(!panel.isAncestorOf(this))
					panel.add(this);
				
			_echelle.getAffineTransform().transform(_vol.getCoordCourante(), _coordCouranteDouble);
			//_cheminParcouruShape = _cheminParcouru.createTransformedShape(_echelle.getAffineTransform());
			_coordCourante.setLocation(_coordCouranteDouble);
			
			if(_vol.getCoordSuivante() != null) {
				_echelle.getAffineTransform().transform(_vol.getCoordSuivante(), _coordSuivanteDouble);
				//rotation.setToRotation( angle(_coordCouranteDouble, _coordSuivanteDouble) );
				//shapeADessiner = rotation.createTransformedShape(shape);
				_imageCourante = _imageFactory.get(angle(_coordCouranteDouble, _coordSuivanteDouble));
			}		
	
			setX(_coordCourante.x-width/2);
			setY(_coordCourante.y-height/2);
		}
	}
}