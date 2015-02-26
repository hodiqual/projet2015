/**
 * 
 */
package fr.iessa.vue;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Panel lecture affichant une barre de lecture et des boutons permettant le rejeu du trafic.
 * @author duvernal
 *
 */
public class PanelLecture extends JPanel implements PropertyChangeListener  {
	
	/** Lac ouleur de l arriere-plan */
	private static final Color BG_COLOR = new Color(0x3B5998);
	/** Les boutons du player */
	private JButton play, forward, back;
	/** La barre de lecture */
	private JSlider timeline, speed;
	/** Le boolean lecture en cours pour synchronisation barre de lecture */
    private boolean syncTimeline=false;
    /** L'image du bouton "retour" */
    private static final ImageIcon BACK = new ImageIcon(Ressources.get(Ressources.LECTEUR_BACK));
    /** L'image du bouton "lecture" */
    private static final ImageIcon PLAY = new ImageIcon(Ressources.get(Ressources.LECTEUR_PLAY));
    /** L'image du bouton "pause" */
    private static final ImageIcon PAUSE = new ImageIcon(Ressources.get(Ressources.LECTEUR_PAUSE));    
    /** L'image du bouton "avance" */
    private static final ImageIcon FORWARD = new ImageIcon(Ressources.get(Ressources.LECTEUR_FORWARD));
    /** Le controleur du MVC */
    private Controleur _controleur;
    /** La valeur par defaut de l intervalle */
    final int INTERVALORIGINE = 60;
	
    

    /**
     * Constructeur PanelLecture.
     * 
     * @param controleur
     *            Le controleur
     */
	public PanelLecture(Controleur controleur)
	{
		
		
		super();
		
		_controleur = controleur;
		final ModeleEvent[] evts = { ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE, ModeleEvent.UPDATE_IS_TRAFIC_RUNNING, ModeleEvent.UPDATE_INSTANT, ModeleEvent.UPDATE_DUREE_INTERVALLE};
		_controleur.ajoutVue(this, evts);
		
		//////////////////////////////////////////////////////
		// Modifie l'aspect graphique de la barre de lecture
		//////////////////////////////////////////////////////
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
	                    
	    ///////////////////////////////////////////////////////////

	                    
	                    
	                    
	                    
		setOpaque(true);
		timeline = new JSlider(0,10000,0);		
		speed = new JSlider(-50,50,0);     
        speed.setOrientation(Adjustable.VERTICAL);
		play= new JButton();
		updateBoutonPlayPause();
		back= new JButton();
	    back.setIcon(BACK);
	    forward= new JButton();
	    forward.setIcon(FORWARD);


	    // GridBagLayout pour les elements du player
	    setLayout(new GridBagLayout());
		setBackground(BG_COLOR);
		GridBagConstraints c = new GridBagConstraints();

	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridy = 0;
	    c.gridx = 0;
	    c.ipadx=10;
	    c.ipady=50;
        add(speed,c);	
	    c.ipadx=5;
	    c.ipady=5;
	    c.gridx = 1;
        add(back,c);
	    c.gridx = 2;
        add(play,c);
	    c.gridx = 3;
        add(forward,c);
	    c.gridx = 4;
	    c.ipadx = 500;
		c.gridwidth = 500;
        add(timeline,c);


        

        
        timeline.putClientProperty("Nimbus.Overrides",sliderDefaults);
        timeline.putClientProperty("Nimbus.Overrides.InheritDefaults",false);
        speed.putClientProperty("Nimbus.Overrides",sliderDefaults);
        speed.putClientProperty("Nimbus.Overrides.InheritDefaults",false);
        addListeners();
        
        setEnabled(false);
        setVisible(false);
	}

	/** 
	 * Update du bouton play-pause
	 * affiche l'image pause si trafic en lecture et play si trafic en pause
	*/
private  void updateBoutonPlayPause()
{
	if(_controleur.isTraficRunning()) 		
		play.setIcon(PAUSE);
	else 
		play.setIcon(PLAY);
}

/** 
 * Les differents listeners du player
 * action sur la barre de lecture et sur les boutons (play-pause, back, forward)
*/
 private void addListeners() {
	 
	 
   
	      timeline.addMouseListener(new MouseAdapter() {
	      	private java.util.Timer t;
	    	private int secondes;
	    	  
	    	  public void mousePressed(MouseEvent e) {
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
		            	if((timeline.getValue())==0)
			            _controleur.setInstant((int)0);
			            else
			            _controleur.updateInstant((float)timeline.getValue()/10000*86400);
	                }
	            },0,100);
	            }
            
	            public void mouseReleased(MouseEvent e) {


	            	
	                if(t != null)
	                {
	                    t.cancel();
	                    t = null;
	        	       	syncTimeline=false;
	                }
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
                		if(secondes>2)
               	   _controleur.updateInstant((float)timeline.getValue()/10000*86400-100*(float)secondes);
                		else if(secondes<=2 && _controleur.isTraficRunning()==false)
                	_controleur.updateInstant(_controleur.getInstantPrecedant());
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
                    		if(secondes>2)
                    			_controleur.updateInstant((float)timeline.getValue()/10000*86400+100*(float)secondes);
                    		else if(secondes<=2 && _controleur.isTraficRunning()==false)
                    			_controleur.updateInstant(_controleur.getInstantSuivant());
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
    
    
    speed.addChangeListener(new ChangeListener() {
        
        public void stateChanged(ChangeEvent arg0) {

            if(!speed.getValueIsAdjusting())
            {               
                if((speed.getValue())==0)
                {
                	//Reinitialise la duree par defaut de l intervalle
                    _controleur.setDureeInterval(INTERVALORIGINE);
                }    
	            else if((speed.getValue())>0)
	            {
                	//Diminue la duree d intervalle -> traffic plus rapide
	                int faster = INTERVALORIGINE - speed.getValue();
	                _controleur.setDureeInterval(faster);
	            }
	            else if((speed.getValue())<0)
	            {
	              	//Augmente la duree d intervalle -> traffic plus lent
	                int slower = INTERVALORIGINE - speed.getValue();
	                _controleur.setDureeInterval(slower);
	            }
            }
            
        }
    });
    
    
	 }
	
 

 /** 
  * Ecoute les modifications du controleur
  * et applique les modifications sur la vue
 */
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
