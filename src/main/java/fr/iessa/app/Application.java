package fr.iessa.app;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.*;

public class Application {

	public static void main(String[] args) {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    //TODO Log4j -- Nimbus pas possible
			System.out.println("AEROPORT");
		}
		
		JFrame frame = new JFrame("A REFAIRE");
	    //frame.setMinimumSize(new Dimension(800, 600));
	    frame.add(new JButton("PROUT"));
	    frame.add(new JButton("PROUT"));
	    frame.validate();
	    frame.setVisible(true);
	}

}
