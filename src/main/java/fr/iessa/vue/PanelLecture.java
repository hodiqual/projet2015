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
 * @author duvernal
 *
 */
public class PanelLecture extends JPanel {
	

	// ***************** A SURPPRIMER **************************
	private static final Color FG_COLOR = new Color(0xFFFFFF);
	private static final Color BG_COLOR = new Color(0x3B5998);
	private static final Color BORDER_COLOR = new Color(0x000000);
	private final JLabel label = new JLabel();
	// ***************** FIN A SURPPRIMER **************************
	
	public PanelLecture()
	{
		super();
		setOpaque(true);
		setLayout(new BorderLayout());
		setBackground(BG_COLOR);
		
		// ***************** A SURPPRIMER **************************
		label.setForeground(FG_COLOR);
		label.setFont(new Font("Sans", Font.BOLD, 90));
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("REJEU");
		// ***************** FIN A SUPPRIMER **************************
	}

}
