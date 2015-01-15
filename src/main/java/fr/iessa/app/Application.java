package fr.iessa.app;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.*;

import fr.iessa.controleur.Controleur;
import fr.iessa.vue.ChargeEnCoursLayerUI;
import fr.iessa.vue.InfrastructurePanel;
import fr.iessa.vue.MainLayeredPanel;

public class Application {

	public static void main(String[] args) {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            //UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    //TODO Log4j -- Nimbus pas possible
			System.out.println("AEROPORT");
		}
		
        //Creer et fait apparaitre l'application dans le thread EDT
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		JFrame frame = new JFrame("800x600 800x600 800x600");
        	    frame.setPreferredSize((new Dimension(800, 600)));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                //Create and set up the content pane.
                frame.getContentPane().add(new MainLayeredPanel());

        	    frame.validate();
        	    frame.pack();
        	    frame.setVisible(true);
            }
        });
	}

}
