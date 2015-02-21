package fr.iessa.vue.trafic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Collision;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.Echelle;

public class ComponentCollision extends JComponent {
	
	private final int _largeur = 10;
	private final int _hauteur = 10;
	private final Rectangle2D _shape;
	
	private final Collision _collision;
	private final Echelle _echelle;
	private final Map<Vol, ComponentVol> _volsADessiner;
	
	private final Point _coord = new Point();

	ComponentCollision(Collision collision, Controleur c, Echelle echelle, Map<Vol, ComponentVol> volsADessiner) {
		_volsADessiner = volsADessiner;
		_collision = collision;
		_echelle = echelle;
		
		setSize(_largeur, _hauteur);
		setMinimumSize(new Dimension(_largeur,_hauteur));
		setMaximumSize(new Dimension(_largeur,_hauteur));
		setPreferredSize(new Dimension(_largeur,_hauteur));
		
		_shape = new Rectangle(new Dimension(_largeur, _hauteur));
		
		addMouseListener( new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				c.stopTrafic();
				c.setInstant(_collision.getInstant().getSeconds());
				for (Vol vol : _collision.getVolsImpliques()) {
					_volsADessiner.get(vol).dispatchEvent(e);
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setToolTipText(_collision.toString());
			}
		});
		
		setVisible(false);
	}
	
	
	public void update(){
			final Point2D.Double _coordCouranteDouble = new Point2D.Double();
			_echelle.getAffineTransform().transform(_collision.getPointImpact(), _coordCouranteDouble);
			_coord.setLocation(_coordCouranteDouble);
			_coord.x -= _largeur/2;
			_coord.y -= _hauteur/2;
			setLocation(_coord);
		}

	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
		Graphics2D g2 = (Graphics2D) g.create();
			Color colorToRestore = g2.getColor();
			g2.setColor(Color.red);
			g2.fill(_shape);
			g2.setColor(colorToRestore);
	}
	
	public void setX(int xPos){ 
		_coord.x = xPos;
		setLocation(_coord.x,_coord.y);
	}
	
	public void setLocation(int x, int y){
		super.setLocation(_coord.x,_coord.y);
	}

	public int getX(){
	    return _coord.x;
	}

	public void setY(int yPos){
		_coord.y = yPos;
		setLocation(_coord.x,_coord.y);
	}
	
	public int getY(){
	    return _coord.y;
	}
	
	public Point getLocation(){
		return _coord;
	}

	public int getWidth(){
		return _largeur;
	} 

	public int getHeight(){
		return _hauteur;
	}

}
