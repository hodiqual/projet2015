/**
 * 
 */
package fr.iessa.vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import fr.iessa.controleur.Controleur;
import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;

/**
 * @author hodiqual
 *
 */
public class PanelVisualisationEtLecture extends SLPanel {
	
	/**
	 * Contient la vue du controle de lecture
	 */
	private PanelLecture _lecteur;
	
	/**
	 * Panel transparent
	 */
	private JPanel _visualisation;
	
	/**
	 * Panel sensible au mouseOver pour afficher le rejeu
	 */
	private JPanel _zoneDetection;
	
	/**
	 * Configuration d'affichage du panel de visualisation seul.
	 */
	private final SLConfig _principalCfg; 
	
	/**
	 * Configuration d'affichage du panel de visualisation
	 * avec le panel de lecture.
	 */	
	private final SLConfig _avecLecteurCfg;
	
	/**
	 * Action a faire lorque que le bouton de tabDeBord est clique.
	 */
	private Runnable _actionBoutonTabDeBord;
	
	/**
	 * Temps au bout duquel le lecteur ne soit plus affiche.
	 */
	private final int tempsAffichageLecteur = 100;
	
	public  PanelVisualisationEtLecture(Controleur controleur){
		super();
		
		setOpaque(false);
		
		_lecteur = new PanelLecture(controleur);
		_lecteur.addMouseListener(new MouseAdapter () {		
			private Timer timer ;	
			
			{
				//Cache le lecteur au bout deux secondes hors du lecteur
				timer = new Timer(tempsAffichageLecteur, _cacheLecteurWrapper);
				timer.setRepeats(false);
				timer.stop();
			}
 
			@Override
			public void mouseEntered(MouseEvent e) {
				timer.stop();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!_lecteur.contains(e.getPoint()))
				 {
					timer.start();
				 }
			}
			
		});
		
		_visualisation = new JPanel();
		_visualisation.setOpaque(false);
		_visualisation.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton boutonTabDeBord = new JButton("<<");
		boutonTabDeBord.setOpaque(false);
		boutonTabDeBord.setContentAreaFilled(false);
		boutonTabDeBord.setBorderPainted(false);
		boutonTabDeBord.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = new Thread(_actionBoutonTabDeBord);
				thread.start();
				if(boutonTabDeBord.getText().equals("<<"))
					boutonTabDeBord.setText(">>");
				else
					boutonTabDeBord.setText("<<");
			}
		});
		_visualisation.add(new LabelHorloge(controleur));
		_visualisation.add(boutonTabDeBord);
		
		_zoneDetection = new JPanel();
		_zoneDetection.setOpaque(false);
		_zoneDetection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				_afficheLecteur.run();
			}
		});
		
		//Affiche seulement la visualisation
		_principalCfg  = new SLConfig(this)
								.row(1f).row(40).col(1f)
								.place(0, 0, _visualisation)
								.place(1, 0, _zoneDetection);
		
		//Affiche la visualisation avec le lecteur
		_avecLecteurCfg  = new SLConfig(this)
								.row(1f).row(100).col(1f)
								.place(0, 0, _visualisation)
								.place(1, 0, _lecteur);
		
		this.setTweenManager(SLAnimator.createTweenManager());
		this.initialize(_principalCfg);
	}
	


	/**
	 * Animation d'affichage du lecteur
	 */
	private final Runnable _afficheLecteur = new Runnable() {@Override public void run() {

		PanelVisualisationEtLecture.this.createTransition()
			.push(new SLKeyframe(_avecLecteurCfg, 0.8f)
				.setEndSide(SLSide.BOTTOM, _zoneDetection)
				.setStartSide(SLSide.BOTTOM, _lecteur)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {

				}}))
			.play();
	}};

	/**
	 * Animation de cacher le lecteur
	 */
	private final Runnable _cacheLecteur = new Runnable() {@Override public void run() {
		PanelVisualisationEtLecture.this.createTransition()
			.push(new SLKeyframe(_principalCfg, 0.8f)
				.setEndSide(SLSide.BOTTOM, _lecteur)
				.setStartSide(SLSide.BOTTOM, _zoneDetection)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {

				}}))
			.play();
	}};
	
	/**
	 * Permet a l'animation _cacheLecteur de demarrer sous l'action d'un timer
	 */
	private final ActionListener _cacheLecteurWrapper = new ActionListener() {	
		@Override
		public void actionPerformed(ActionEvent e) {
				_cacheLecteur.run();
		}
	};


	/**
	 * @param _actionBoutonTabDeBord the _actionBoutonTabDeBord to set
	 */
	public void set_actionBoutonTabDeBord(Runnable _actionBoutonTabDeBord) {
		this._actionBoutonTabDeBord = _actionBoutonTabDeBord;
	}
	
	
}
