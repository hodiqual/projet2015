/**
 * 
 */
package fr.iessa.vue;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 * @author duvernal
 *
 */
public class PanelLecture extends JPanel {
	
    private Timer t;
    private JPanel screenPanel,controlsPanel;
    private JSlider timeline;
    private JButton play,back,forward;
    private JToggleButton loop,mute;
    private JSlider speed;
    private Canvas scr;
    private final String[] mediaOptions = {""};
    private boolean syncTimeline=false;
    private boolean looping=false;
    private SimpleDateFormat ms;
    private int jumpLength=1000;
    private int  loopLength=6000;

	public PanelLecture()
	{
		super();
		setOpaque(true);
		setLayout(new BorderLayout());
//		setBackground(BG_COLOR);

        createUI();
        setLayout();
        scr.setVisible(true);
        setVisible(true);
	
	}

	  private void createUI() {
	        //setIconImage();
	        ms = new SimpleDateFormat("hh:mm:ss");
	        scr=new Canvas();
	        timeline = new JSlider(0,100,0);
	        timeline.setMajorTickSpacing(10);
	        timeline.setMajorTickSpacing(5);
	        timeline.setPaintTicks(true);
	        //TODO we need Icons instead
	        play= new JButton("play");
	        back= new JButton("<");
	        forward= new JButton(">");
	        loop= new JToggleButton("loop");
	        mute= new JToggleButton("mute");
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
	    }
	    
	    //creates a layout like the most mediaplayers are...
	    private void setLayout() {
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
	        mute.setSelected(false);
	    }

	
}
