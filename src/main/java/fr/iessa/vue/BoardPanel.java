/**
 * 
 */
package fr.iessa.vue;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

/**
 * @author hodiqual
 *
 */
public class BoardPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 25499665468682529L;

	Controleur _controleur;
	/**
	 * 
	 */
	public BoardPanel(Controleur controleur) {
		super();

        setLayout(new GridLayout(1,1));
		_controleur = controleur;
		// TODO Auto-generated constructor stub
		
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
		_controleur.ajoutVue(this);
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {
		case CHARGEMENT_CARTE_GRAPHIQUE_DONE:
			System.out.println("Je suis content");
			break;

		default:
			break;
		}
	}

}
