package fr.iessa.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

import fr.iessa.metier.infra.Point;

public class Avion extends JComponent {
	
private int xPos = 50;
private int yPos = 50;
private int width = 50;
private int height = 50;
private Shape shape = new Rectangle(new Dimension(20,20));
private AffineTransform transform = new AffineTransform();
private String _nom;

public Avion(String nom){ 
	transform.rotate(45);
	shape = transform.createTransformedShape(shape);
	Point example = new Point("K8", "0", -4321, 234);
	transform.transform(example,example); // Appliquer le zoom, le decalage
	setLocation(xPos, yPos);
	//setBackground(Color.black);
	setOpaque(true);
	//setBorder(BorderFactory.createTitledBorder("AVION"));
	//setBounds(0,0,width,height);
	setMinimumSize(new Dimension(width,height));
	setMaximumSize(new Dimension(width,height));
	setPreferredSize(new Dimension(width,height));
	_nom = nom;
	
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
    this.xPos = xPos;
	setLocation(xPos, yPos);
}

public int getX(){
    return xPos;
}

public void setY(int yPos){
    this.yPos = yPos;
	setLocation(xPos, yPos);
}

public int getY(){
    return yPos;
}

public int getWidth(){
    return width;
} 

public int getHeight(){
    return height;
}

public void paintComponent(Graphics g){
    super.paintComponent(g);  
    //g.drawRect(xPos,yPos,width,height);
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setColor(Color.RED);
    g2.fill(shape);
    g2.setColor(Color.BLUE);
    g2.draw(shape);
}

}
