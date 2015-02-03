/**
 * 
 */
package fr.iessa.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author hodiqual
 *
 */
public class PanelTableauDeBord extends JPanel {


	// ***************** A SURPPRIMER **************************
	private static final Color FG_COLOR = new Color(0xFFFFFF);
	private static final Color BG_COLOR = Color.RED;
	private static final Color BORDER_COLOR = new Color(0x000000);
	private final JLabel label = new JLabel();
	// ***************** FIN A SURPPRIMER **************************
	
	public PanelTableauDeBord() {
		super();
		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(BG_COLOR);
		
		// ***************** A SURPPRIMER **************************
		label.setForeground(FG_COLOR);
		label.setFont(new Font("Sans", Font.BOLD, 90));
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("TABLEAU DE BORD");
		// ***************** FIN A SUPPRIMER **************************
	}
	

}
