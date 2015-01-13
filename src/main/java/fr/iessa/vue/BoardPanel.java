/**
 * 
 */
package fr.iessa.vue;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/**
 * Gere graphiquement le chargement de la plateforme,
 * le chargement du trafic
 * l'affichage de l'image de la plateforme et de son trafic
 * la navigation zoom et scroll sur l'affichage
 * @author hodiqual
 * 
 */
public class BoardPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 25499665468682529L;

	Controleur _controleur;
	
	public BoardPanel(Controleur controleur) {
		super();

        setLayout(new GridLayout(1,1));
		_controleur = controleur;
		
		//Pour une animation fluide il vaut mieux etre en double buffer.
		setDoubleBuffered(true);
		
		JButton but = new JButton("Charge Moi");
		but.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				_controleur.chargerCarte("lfpg.txt");
			    System.out.println("Chargement en cours");
			}
		} );
		
		add(but);
		
		setVisible(true);
		
		//rendre sensible le controleur 
		_controleur.ajoutVue(this);
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 * Methode appelee lorque le controleur observe un changement d'etat du modele.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
		case CHARGEMENT_CARTE_GRAPHIQUE_DONE:
			//http://imss-www.upmf-grenoble.fr/prevert/Prog/Java/swing/image.html
			System.out.println("Je suis content");
			break;

		default:
			break;
		}
	}

}
