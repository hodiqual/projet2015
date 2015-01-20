package fr.iessa.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TraficPanel extends JPanel implements ActionListener{

    Avion redSquare1 = new Avion("Avion 1");
    Avion redSquare2 = new Avion("Avion 2");
    Avion redSquare3 = new Avion("Avion 3");
    Avion redSquare4 = new Avion("Avion 4");
    Avion redSquare5 = new Avion("Avion 5");
    Avion redSquare6 = new Avion("Avion 6");
    Avion redSquare7 = new Avion("Avion 7");
    Avion redSquare8 = new Avion("Avion 8");
    Avion redSquare9 = new Avion("Avion 9");
    Timer timer = new Timer(1000, this);

    public TraficPanel() {

        setBorder(BorderFactory.createLineBorder(Color.orange));

        setOpaque(false);
        
        add(redSquare1);
        add(redSquare2);
        add(redSquare3);
        add(redSquare4);
        add(redSquare5);
        add(redSquare6);
        add(redSquare7);
        //add(redSquare8);
        //add(redSquare9);
        
        timer.start();

    }

    private void moveSquare(int x, int y, Avion redSquare){

        // Current square state, stored as final variables 
        // to avoid repeat invocations of the same methods.
        final int OFFSET = 1;

            // Update coordinates.
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
            repaint();
           /* repaint(redSquare.getX(), redSquare.getY(), 
                    redSquare.getWidth()+OFFSET, 
                    redSquare.getHeight()+OFFSET); */
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawString("This is my custom Panel!",10,20);

        //redSquare1.paintSquare(g);
        //redSquare2.paintSquare(g);
    }

    public int  cpt = 0;
	@Override
	public void actionPerformed(ActionEvent e) {

        moveSquare(redSquare1.getX()+10,redSquare1.getY()+10,redSquare1);
        moveSquare(redSquare2.getX()+10,redSquare2.getY()+2 ,redSquare2);
        moveSquare(redSquare3.getX()+3,redSquare3.getY()+3 ,redSquare3);
        moveSquare(redSquare4.getX()+4,redSquare4.getY()+4 ,redSquare4);
        moveSquare(redSquare5.getX()+5,redSquare5.getY()+5 ,redSquare5);
        moveSquare(redSquare6.getX()+6,redSquare6.getY()+6 ,redSquare6);
        moveSquare(redSquare7.getX()+7,redSquare7.getY()+7 ,redSquare7);
        
        cpt++;
        
        if(cpt==9)
        	add(redSquare9);
        
        if(cpt==15)
        	remove(redSquare1);
        
        if(cpt==20)
        	add(redSquare1);
        
        if(cpt>9)
            moveSquare(redSquare9.getX()+9,redSquare9.getY()+9 ,redSquare9);
        
        
        
		
	}  

}
