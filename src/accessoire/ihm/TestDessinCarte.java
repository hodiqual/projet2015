package ihm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.*;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.LibereMemoire;
import fr.iessa.dao.infra.InfrastructureDAO;
import fr.iessa.metier.infra.Aeroport;
import fr.iessa.vue.Echelle;
import fr.iessa.vue.PanelInfrastructure;
import fr.iessa.vue.infra.InfrastructureDrawer;

public class TestDessinCarte extends JPanel{

	public static void main(String[] args) throws FileNotFoundException, NoSuchElementException {
		
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
		

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int largeurEcran = (int) screenSize.getWidth();
        int hauteurEcran = (int) screenSize.getHeight();
        System.out.println("Largeur " + largeurEcran + " Hauteur " + hauteurEcran);
        
		JFrame frame = new JFrame("A REFAIRE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TestDessinCarte());
	    frame.validate();
	    frame.setPreferredSize((new Dimension(echelle.getDestLargeur(), echelle.getDestHauteur())));
	    frame.pack();
	    frame.setVisible(true);
	}
	
	
	private BufferedImage _image;
	
	TestDessinCarte() throws FileNotFoundException, NoSuchElementException
	{
        chargeEtDessineAeroport();
	}
	
	private static Aeroport aeroport;
	private static Echelle echelle;
	
	static {
		try {
			aeroport = InfrastructureDAO.charger("lfpg.txt");
		} catch (FileNotFoundException | NoSuchElementException e) {
			e.printStackTrace();
		}
		
		echelle = new Echelle();
		echelle.setLimitesReelles(aeroport.getMinX(), aeroport.getMaxX(), aeroport.getMinY(), aeroport.getMaxY());

	     int zoomLevel = 20;
	     echelle.setZoomLevel(zoomLevel, new Point() , echelle.getDestLargeur(), echelle.getDestHauteur());
	    
	}
	
	void chargeEtDessineAeroport() throws FileNotFoundException, NoSuchElementException
	{

	     

	}
	
	@Override
    public void paintComponent(Graphics g) {



        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int largeurEcran = (int) screenSize.getWidth();
        int hauteurEcran = (int) screenSize.getHeight();
       
	     InfrastructureDrawer dessinateur = new InfrastructureDrawer(); _image 
	     	= new BufferedImage(echelle.getDestLargeur(), echelle.getDestHauteur(), 
	     						BufferedImage.TYPE_BYTE_GRAY);
	     Graphics2D g2img = (Graphics2D) _image.getGraphics();
	     // active le lissage des formes
	     g2img.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                 RenderingHints.VALUE_ANTIALIAS_ON);
	     g2img.setRenderingHint(RenderingHints.KEY_RENDERING,
	                 RenderingHints.VALUE_RENDER_QUALITY);
	        
	     dessinateur.dessineAeroport(aeroport, g2img, echelle.getAffineTransform());	
	     
	     LibereMemoire.free();
	     LibereMemoire.controleMemoire();
		//Effacer le contenu pour les animations.
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		
        // active le lissage des formes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                 RenderingHints.VALUE_RENDER_QUALITY);
        
		g2.drawImage(_image, 0, 0, null);
			
		}
}
