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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.metier.trafic.Collision;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.vue.Echelle;
import fr.iessa.vue.Ressources;

import javax.imageio.ImageIO;

public class ComponentCollision extends JComponent {
	
	private static BufferedImage _icon;
	static  {
		try {
			_icon = ImageIO.read(Ressources.get(Ressources.PUNAISE_COLLISION));
		} catch (IOException e) {
			_icon = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = _icon.createGraphics();
			g2.setColor(Color.RED);
			g2.fillRect(0, 0, 10, 10);
			e.printStackTrace();
		}
	}
	private final int _largeur = _icon.getWidth();
	private final int _hauteur = _icon.getHeight();
	
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
			_echelle.getAffineTransform().transform(_collision.getPointImpact(), 
														_coordCouranteDouble);
			_coord.setLocation(_coordCouranteDouble);
			_coord.x -= _largeur;
			_coord.y -= _hauteur;
			setLocation(_coord);
		}

	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(_icon,0,0,null);
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
