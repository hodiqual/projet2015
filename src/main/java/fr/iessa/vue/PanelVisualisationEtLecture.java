/**
 * 
 */
package fr.iessa.vue;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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
	 * Contient la vue du controle de rejeu
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
	
	
	public  PanelVisualisationEtLecture(){
		super();
		
		setOpaque(false);
		
		_lecteur = new PanelLecture();
		_lecteur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				//cacheTableauDeBord.run();
			}
		});
		
		_visualisation = new JPanel();
		_visualisation.setOpaque(false);
		
		_zoneDetection = new JPanel();
		_zoneDetection.setOpaque(false);
		_zoneDetection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				afficheTableauDeBord.run();
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
	private final Runnable afficheTableauDeBord = new Runnable() {@Override public void run() {

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
	private final Runnable cacheTableauDeBord = new Runnable() {@Override public void run() {
		PanelVisualisationEtLecture.this.createTransition()
			.push(new SLKeyframe(_principalCfg, 0.8f)
				.setEndSide(SLSide.BOTTOM, _lecteur)
				.setStartSide(SLSide.BOTTOM, _zoneDetection)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {

				}}))
			.play();
	}};
	
	
}
