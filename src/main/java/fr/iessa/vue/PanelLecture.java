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

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		label.setVisible(true);
		
		// ***************** FIN A SUPPRIMER **************************

	/*
        scr=new Canvas();
        timeline = new JSlider(0,100,0);
        timeline.setMajorTickSpacing(10);
        timeline.setMajorTickSpacing(5);
        timeline.setPaintTicks(true);
        //TODO we need Icons instead
        play= new JButton(tr("play"));
        back= new JButton("<");
        forward= new JButton(">");
        loop= new JToggleButton(tr("loop"));
        mute= new JToggleButton(tr("mute"));
        speed = new JSlider(-200,200,0);
        speed.setMajorTickSpacing(100);
        speed.setPaintTicks(true);          
        speed.setOrientation(Adjustable.VERTICAL);
        Hashtable labelTable = new Hashtable ();
        labelTable.put( new Integer( 0 ), new JLabel("1x") );
        labelTable.put( new Integer( -200 ), new JLabel("-2x") );
        labelTable.put( new Integer( 200 ), new JLabel("2x") );
        speed.setLabelTable( labelTable );
        speed.setPaintLabels(true);
        
        this.setLayout(new BorderLayout());
        screenPanel=new JPanel();
        screenPanel.setLayout(new BorderLayout());
        controlsPanel=new JPanel();
        controlsPanel.setLayout(new FlowLayout());
        add(screenPanel,BorderLayout.CENTER);
        add(controlsPanel,BorderLayout.SOUTH);
        //fill screen panel
        screenPanel.add(scr,BorderLayout.CENTER);
        screenPanel.add(timeline,BorderLayout.SOUTH);
        screenPanel.add(speed,BorderLayout.EAST);
        controlsPanel.add(play);
        controlsPanel.add(back);
        controlsPanel.add(forward);
        controlsPanel.add(loop);
        controlsPanel.add(mute);
        loop.setSelected(false);
        mute.setSelected(false);*/
	}

}
