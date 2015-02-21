package fr.iessa.vue.trafic;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

import sun.security.action.GetLongAction;
import fr.iessa.metier.Instant;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.Echelle;

public class ComponentVol extends JComponent {

	private int _largeur = 32;
	private int _hauteur = 32;
	
	private BufferedImage _imageCourante;
	private ShapeAvionFactory _imageFactory;
	private Rectangle2D _collisionHighlight;
	private Color _highlightColor;
	private Echelle _echelle;
	private Vol _vol;
	private Point2D.Double _coordCouranteDouble = new Point2D.Double();
	private Point2D.Double _coordSuivanteDouble = new Point2D.Double();
	private Point _coordCourante = new Point();
	private GeneralPath _cheminParcouru = new GeneralPath();
	private Shape _cheminParcouruShape;
	private ClickComponentVolListener _clickListener;
	
	public ComponentVol(Vol v, Echelle echelle) { 
		
		setOpaque(true);

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
		
		_largeur = _imageCourante.getWidth();
		_hauteur = _imageCourante.getHeight(); 
		
		setSize(_largeur, _hauteur);
		setMinimumSize(new Dimension(_largeur,_hauteur));
		setMaximumSize(new Dimension(_largeur,_hauteur));
		setPreferredSize(new Dimension(_largeur,_hauteur));
		
		if(_vol.aDesCollisions())
			_collisionHighlight = new Rectangle(new Dimension(_largeur, _hauteur));	
		
		TreeMap<Instant,Point> coordOrdonne = new TreeMap<Instant,Point>(_vol.getInstantVersCoord());
		Point premierPoint = coordOrdonne.pollFirstEntry().getValue();
		_cheminParcouru.moveTo(premierPoint.x, premierPoint.y);
		coordOrdonne.values().forEach( p ->_cheminParcouru.lineTo(p.x, p.y));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setToolTipText(v.getId());
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(_clickListener!=null)
					_clickListener.componentVolClicked(ComponentVol.this);
			}
		});
	}
	
	public void setX(int xPos){ 
		_coordCourante.x = xPos;
		setLocation(_coordCourante.x,_coordCourante.y);
	}
	
	public void setLocation(int x, int y){
		super.setLocation(_coordCourante.x,_coordCourante.y);
	}

	public int getX(){
	    return _coordCourante.x;
	}

	public void setY(int yPos){
		_coordCourante.y = yPos;
		setLocation(_coordCourante.x,_coordCourante.y);
	}
	
	public int getY(){
	    return _coordCourante.y;
	}
	
	public Point getLocation(){
		return _coordCourante;
	}

	public int getWidth(){
		return _largeur;
	} 

	public int getHeight(){
		return _hauteur;
	}
	
	public Vol getVol() {
		return _vol;
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
		
		if(_highlightColor != null)
		{
			final Shape shape = new Rectangle(new Dimension(_largeur, _hauteur));
			final Stroke stroke = new BasicStroke(8);
			Color colorToRestore = g2.getColor();
			g2.setColor(_highlightColor);
			g2.setStroke(stroke);
			g2.draw( shape );
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
			
			if(_highlightColor != null)
				_cheminParcouruShape = _cheminParcouru.createTransformedShape(_echelle.getAffineTransform());
			
			
			//Determine le sprite a dessiner selon l'angle entre le point courant et le point suivant
			if(_vol.getCoordSuivante() != null) {
				_echelle.getAffineTransform().transform(_vol.getCoordSuivante(), _coordSuivanteDouble);
				_imageCourante = _imageFactory.get(angle(_coordCouranteDouble, _coordSuivanteDouble));
			}		
	
			setX(_coordCourante.x-_largeur/2);
			setY(_coordCourante.y-_hauteur/2);	
		}
		
		revalidate();
		repaint();
	}

	/**
	 * @param _highlightColor the _highlightColor to set
	 */
	public void setHighlightColor(Color highlightColor) {
		this._highlightColor = highlightColor;
		
		if(_highlightColor != null && _cheminParcouruShape == null)
			_cheminParcouruShape = _cheminParcouru.createTransformedShape(_echelle.getAffineTransform());
		
		repaint();
		Container parent = getParent();
		if(parent!=null)
			getParent().repaint();
	}

	public boolean isHighlighted() {
		return _highlightColor != null;
	}

	public void drawChemin(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Color colorToRestore = g2.getColor();
		g2.setColor(_highlightColor);
		g2.setStroke(new BasicStroke(5));
		if(_cheminParcouruShape != null)
		{
			g2.draw(_cheminParcouruShape);
		}
		g2.setColor(colorToRestore);
	}
	
	public void setClickListener(ClickComponentVolListener l) {
		_clickListener = l;
	}
}
