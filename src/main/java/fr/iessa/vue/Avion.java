package fr.iessa.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Avion {
	
private int xPos = 50;
private int yPos = 50;
private int width = 20;
private int height = 20;
private Rectangle2D rect = new Rectangle(new Dimension(width,height));

public Avion(){ 
	rect.setRect(xPos, yPos, width, height);
}


public void setX(int xPos){ 
    this.xPos = xPos;
    rect.setRect(xPos, yPos, width, height);
}

public int getX(){
    return xPos;
}

public void setY(int yPos){
    this.yPos = yPos;
    rect.setRect(xPos, yPos, width, height);
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

public void paintSquare(Graphics g){
    g.setColor(Color.RED);
    g.fillRect(xPos,yPos,width,height);
    g.setColor(Color.BLACK);
    //g.drawRect(xPos,yPos,width,height);
    Graphics2D g2 = (Graphics2D) g.create();
    g2.draw(rect);
}

}
