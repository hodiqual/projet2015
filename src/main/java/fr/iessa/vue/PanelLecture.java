/**
 * 
 */
package fr.iessa.vue;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.*;

/**
 * @author duvernal
 *
 */
public class PanelLecture extends JPanel {
	

	// ***************** A SURPPRIMER **************************
	private static final Color FG_COLOR = new Color(0xFFFFFF);
	private static final Color BG_COLOR = new Color(0x3B5998);
	private static final Color BORDER_COLOR = new Color(0x000000);
	private JButton play, forward, back;
	private JSlider timeline;
    private static final ImageIcon BACK = new ImageIcon("back.png");
    private static final ImageIcon PLAY = new ImageIcon("play.png");
    private static final ImageIcon PAUSE = new ImageIcon("pause.png");    
    private static final ImageIcon FORWARD = new ImageIcon("forward.png");
	// ***************** FIN A SURPPRIMER **************************
	
    


    
	public PanelLecture()
	{
		
		
		super();
	    UIDefaults sliderDefaults = new UIDefaults();

	    sliderDefaults.put("Slider.thumbWidth", 20);
	    sliderDefaults.put("Slider.thumbHeight", 20);
	    sliderDefaults.put("Slider:SliderThumb.backgroundPainter", new Painter<JComponent>() {
	        public void paint(Graphics2D g, JComponent c, int w, int h) {
	            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g.setStroke(new BasicStroke(2f));
	            g.setColor(Color.RED);
	            g.fillOval(1, 1, w-3, h-3);
	            g.setColor(Color.WHITE);
	            g.drawOval(1, 1, w-3, h-3);
	        }
	    });
	                    sliderDefaults.put("Slider:SliderTrack.backgroundPainter", new Painter<JComponent>() {
                    public void paint(Graphics2D g, JComponent c, int w, int h) {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setStroke(new BasicStroke(2f));
                        g.setColor(Color.GRAY);
                        g.fillRoundRect(0, 6, w-1, 8, 8, 8);
                        g.setColor(Color.WHITE);
                        g.drawRoundRect(0, 6, w-1, 8, 8, 8);
                    }
                });
		setOpaque(true);
		timeline = new JSlider(0,100,0);
		
		play= new JButton();
		play.setIcon(PLAY);
	    back= new JButton();
	    back.setIcon(BACK);
	    forward= new JButton();
	    forward.setIcon(FORWARD);

	    setLayout(new GridBagLayout());
		setBackground(BG_COLOR);
		GridBagConstraints c = new GridBagConstraints();

	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridy = 0;
	    
	    c.gridx = 0;
        add(back,c);
	    c.gridx = 1;
        add(play,c);
	    c.gridx = 2;
        add(forward,c);
	    c.gridx = 3;
	    c.ipadx = 400;
		c.gridwidth = 40;
        add(timeline,c);
        timeline.putClientProperty("Nimbus.Overrides",sliderDefaults);
        timeline.putClientProperty("Nimbus.Overrides.InheritDefaults",false);
	}

}
