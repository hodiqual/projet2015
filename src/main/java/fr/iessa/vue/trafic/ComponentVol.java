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
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fr.iessa.metier.Instant;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.Echelle;

public class ComponentVol extends JComponent {

	private int width = 50;
	private int height = 50;
	// ***********************************  flyweight pour echelle ********************************** //
	private Shape shape = new Rectangle(new Dimension(20,20));
	
	private Echelle _echelle;
	private String _nom;
	private Vol _vol;
	private Point2D.Double _coordCouranteDouble = new Point2D.Double();
	private Point _coordCourante = new Point();
	private GeneralPath _cheminParcouru = new GeneralPath();

	public ComponentVol(Vol v, Echelle echelle) { 

		setLocation(10, 10);
		//setBackground(Color.black);
		setOpaque(true);
		//setBorder(BorderFactory.createTitledBorder("AVION"));
		//setBounds(0,0,width,height);
		setMinimumSize(new Dimension(width,height));
		setMaximumSize(new Dimension(width,height));
		setPreferredSize(new Dimension(width,height));
		_nom = v.getId();
		_vol = v;
		TreeMap<Instant,Point> coordOrdonne = new TreeMap<Instant,Point>(_vol.getInstantVersCoord());
		Point premierPoint = coordOrdonne.pollFirstEntry().getValue();
		_cheminParcouru.moveTo(premierPoint.x, premierPoint.y);
		coordOrdonne.values().forEach( p ->_cheminParcouru.lineTo(p.x, p.y));
		_echelle = echelle;
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Avion clique: " + _nom);
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
		g2.fill(shape);
		g2.setColor(Color.BLUE);
		g2.draw(shape);

		//g2.setColor(Color.RED);
		//g2.setStroke(new BasicStroke(11));
		//g2.draw(_cheminParcouruShape);
		
		g2.setStroke(strokeToRestore);
		g2.setColor(colorToRestore);
		
		
	}

	private Shape _cheminParcouruShape;
	
	public void update(JPanel panel){
		if(_vol.getCoordCourante() == null)
			panel.remove(this);
		if(_vol.getCoordCourante() != null)
		{
			if(!panel.isAncestorOf(this))
				panel.add(this);
			
		_echelle.getAffineTransform().transform(_vol.getCoordCourante(), _coordCouranteDouble);
		//_cheminParcouruShape = _cheminParcouru.createTransformedShape(_echelle.getAffineTransform());
		_coordCourante.setLocation(_coordCouranteDouble);

		setX(_coordCourante.x);
		setY(_coordCourante.y);
}
	}
}