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

    Avion redSquare1 = new Avion();
    Avion redSquare2 = new Avion();
    Timer timer = new Timer(1000, this);

    public TraficPanel() {

        setBorder(BorderFactory.createLineBorder(Color.orange));

        setOpaque(false);
        
        timer.start();

    }

    private void moveSquare(int x, int y, Avion redSquare){

        // Current square state, stored as final variables 
        // to avoid repeat invocations of the same methods.
        final int CURR_X = redSquare.getX();
        final int CURR_Y = redSquare.getY();
        final int CURR_W = redSquare.getWidth();
        final int CURR_H = redSquare.getHeight();
        final int OFFSET = 1;

        if ((CURR_X!=x) || (CURR_Y!=y)) {

            // The square is moving, repaint background 
            // over the old square location. 
            repaint(CURR_X,CURR_Y,CURR_W+OFFSET,CURR_H+OFFSET);

            // Update coordinates.
            redSquare.setX(x);
            redSquare.setY(y);

            // Repaint the square at the new location.
            repaint(redSquare.getX(), redSquare.getY(), 
                    redSquare.getWidth()+OFFSET, 
                    redSquare.getHeight()+OFFSET);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.drawString("This is my custom Panel!",10,20);

        redSquare1.paintSquare(g);
        redSquare2.paintSquare(g);
    }

	@Override
	public void actionPerformed(ActionEvent e) {

        moveSquare(redSquare1.getX()+10,redSquare1.getY()+10,redSquare1);
        moveSquare(redSquare2.getX()+10,redSquare2.getY()+2 ,redSquare2);
		
	}  

}
