package ihm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.*;

import fr.iessa.controleur.Controleur;
import fr.iessa.dao.infra.InfrastructureDAO;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.vue.BoardPanel;
import fr.iessa.vue.infra.InfrastructureDrawer;

public class Application extends JPanel{
	
	

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
		frame.getContentPane().add(new Application());
	    frame.validate();
	    frame.setPreferredSize((new Dimension(300, 120)));
	    frame.pack();
	    frame.setVisible(true);
	}
	
	private int _largeurEcran, _hauteurEcran;
	
	Application()
	{
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        _largeurEcran = (int) screenSize.getWidth();
        _hauteurEcran = (int) screenSize.getHeight();		
	}
	
	@Override
    public void paintComponent(Graphics g) {
		//Effacer le contenu pour les animations.
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		
        // active le lissage des formes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                 RenderingHints.VALUE_RENDER_QUALITY);
        
        Aeroport aeroport = InfrastructureDAO.charger("lfpg.txt");
        
        
        InfrastructureDrawer dessinateur = new InfrastructureDrawer();
         
        BufferedImage image = new BufferedImage(_largeurEcran, _hauteurEcran, BufferedImage.TYPE_INT_RGB);
        
        dessinateur.dessineAeroport(aeroport, g2, _largeurEcran, _hauteurEcran);
		

		g2.drawImage(image, null, null);
			
		}
}
