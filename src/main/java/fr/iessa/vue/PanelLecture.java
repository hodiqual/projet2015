/**
 * 
 */
package fr.iessa.vue;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;

import javax.swing.*;


/**
 * @author duvernal
 *
 */
public class PanelLecture extends JPanel implements PropertyChangeListener  {
	

	// ***************** A SURPPRIMER **************************
	private static final Color FG_COLOR = new Color(0xFFFFFF);
	private static final Color BG_COLOR = new Color(0x3B5998);
	private static final Color BORDER_COLOR = new Color(0x000000);
	private JButton play, forward, back;
	private JSlider timeline;
    private boolean syncTimeline=false;
    private static final ImageIcon BACK = new ImageIcon("back.png");
    private static final ImageIcon PLAY = new ImageIcon("play.png");
    private static final ImageIcon PAUSE = new ImageIcon("pause.png");    
    private static final ImageIcon FORWARD = new ImageIcon("forward.png");
    private Controleur _controleur;
	// ***************** FIN A SURPPRIMER **************************
	
    


    
	public PanelLecture(Controleur controleur)
	{
		
		
		super();
		
		_controleur = controleur;
		final ModeleEvent[] evts = { ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE, ModeleEvent.UPDATE_IS_TRAFIC_RUNNING, ModeleEvent.UPDATE_INSTANT, ModeleEvent.UPDATE_DUREE_INTERVALLE};
		_controleur.ajoutVue(this, evts);
		
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
		timeline = new JSlider(0,10000,0);		
		play= new JButton();
		updateBoutonPlayPause();
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
        addListeners();
        
        setEnabled(false);
        setVisible(false);
	}

	
private  void updateBoutonPlayPause()
{
	if(_controleur.isTraficRunning()) 		
		play.setIcon(PAUSE);
	else 
		play.setIcon(PLAY);
}

	
 private void addListeners() {
	 
	      timeline.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	            		syncTimeline=true;
	            }
	            public void mouseReleased(MouseEvent e) {
	            	if((timeline.getValue())==0){
	            	_controleur.setInstant((int)0); }
	            	else{
	                    _controleur.updateInstant((float)timeline.getValue()/10000*86400); }
	            		syncTimeline=false;
	            }
	  
	 
	       }
	      
	    		  
	 );
	        
	        
    play.addActionListener(new ActionListener() {
        
        public void actionPerformed(ActionEvent arg0) {

            if(_controleur.isTraficRunning()) 
            	_controleur.stopTrafic(); 
            else 
            	_controleur.runTrafic();              
        }
    });
    
    
    
    
    back.addMouseListener(new MouseAdapter() {



    	
    	private java.util.Timer t;
    	private int secondes;
        public void mousePressed(MouseEvent e)
        {
        	syncTimeline=true;
            if(t == null)
            {
                t = new java.util.Timer();
                secondes = 0;
                
            }
            t.scheduleAtFixedRate(new TimerTask()
            {
                public void run()
                {
                	secondes++;                	
                	if ((timeline.getValue()/10000*86400+100*secondes)>=0){
               	   _controleur.updateInstant((float)timeline.getValue()/10000*86400-100*(float)secondes);
                	}
                	else{
                		_controleur.updateInstant((float)0);                
                }
                	timeline.setValue((Math.round((float)_controleur.getInstantCourant()-(float)0.1)*10000/86400));	
                	
                }
            },0,100);
        }

        public void mouseReleased(MouseEvent e)
        {
            if(t != null)
            {
                t.cancel();
                t = null;
    	       	syncTimeline=false;
            }
        }
    	
   
    });
    
    

    forward.addMouseListener(new MouseAdapter() {



        	
        	private java.util.Timer t;
        	private int secondes;
            public void mousePressed(MouseEvent e)
            {
            	syncTimeline=true;
                if(t == null)
                {
                    t = new java.util.Timer();
                    secondes = 0;
                    
                }
                t.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                    	secondes++;                	
                    	if ((timeline.getValue()/10000*86400+100*secondes)<=86400){
                   	   _controleur.updateInstant((float)timeline.getValue()/10000*86400+100*(float)secondes);
                    	}
                    	else{
                    		_controleur.updateInstant((float)86390);                
                    }
                    	timeline.setValue((Math.round((float)_controleur.getInstantCourant()+(float)0.1)*10000/86400));	
                    	
                    }
                },0,100);
            }

            public void mouseReleased(MouseEvent e)
            {
                if(t != null)
                {
                    t.cancel();
                    t = null;
        	       	syncTimeline=false;
                }
            }
        	
       
		
    });
    
	 }
	
 

 

	public void propertyChange(PropertyChangeEvent evt) {

		
		switch (ModeleEvent.valueOf(evt.getPropertyName())) {

		case CHARGEMENT_TRAFIC_FICHIER_DONE:
			setEnabled(true);
	        setVisible(true);
			break;
			
		case UPDATE_INSTANT:
			if (!syncTimeline) {
			timeline.setValue(Math.round((float)_controleur.getInstantCourant()*10000/86400));}
			break;
		
		case UPDATE_IS_TRAFIC_RUNNING:
			updateBoutonPlayPause();
			break;

			
		case UPDATE_DUREE_INTERVALLE:
			break;

		default:
			break;
	}
	}

}
