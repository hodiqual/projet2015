package fr.iessa.vue;

import javax.swing.JButton;

import fr.iessa.controleur.Controleur;
import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;

/**
 * 
 * @author hodiqual
 * Panel qui gere les annimations d'apparition du panel de tableau de bord
 * et du rejeu.
 */
public class PanelDesControles extends SLPanel {
	
	
	private final PanelTableauDeBord _tableauDeBord;
	private final PanelVisualisationEtLecture _visualisationEtLecture;
	
	JButton _boutonTab;
	private Runnable _nextAction;
	
	/**
	 * Configuration d'affichage du panel de visualisation/rejeu.
	 */
	private final SLConfig _principalCfg; 
	
	/**
	 * Configuration d'affichage du panel de visualisation/rejeu
	 * avec le tableau de bord.
	 */	
	private final SLConfig _avecTableauCfg;
	
	public PanelDesControles(Controleur controleur) {
		super();
		setOpaque(false);
		_tableauDeBord = new PanelTableauDeBord(controleur);
		_visualisationEtLecture = new PanelVisualisationEtLecture(controleur);
		_visualisationEtLecture.setOpaque(false);
		_visualisationEtLecture.set_actionBoutonTabDeBord(afficheTableauDeBord);
		
		//Affiche seulement la visualisation et le rejeu
		_principalCfg  = new SLConfig(this)
								.row(1f).col(1f)
								.place(0, 0, _visualisationEtLecture);
		
		//Affiche la visualisation, le rejeu (resizable) et le tableau de bord (fixe 250 px)
		_avecTableauCfg =  new SLConfig(this)
								.row(1f).col(1f).col(300)
								.place(0, 0, _visualisationEtLecture)
								.place(0, 1, _tableauDeBord);
		
		this.setTweenManager(SLAnimator.createTweenManager());
		this.initialize(_principalCfg);
	}

	/**
	 * Animation d'affichage du tableau de bord
	 */
	private final Runnable afficheTableauDeBord = new Runnable() {@Override public void run() {

		PanelDesControles.this.createTransition()
			.push(new SLKeyframe(_avecTableauCfg, 0.8f)
				.setStartSide(SLSide.RIGHT, _tableauDeBord)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					_visualisationEtLecture.set_actionBoutonTabDeBord(cacheTableauDeBord);
				}}))
			.play();
	}};

	/**
	 * Animation de cacher du tableau de bord
	 */
	private final Runnable cacheTableauDeBord = new Runnable() {@Override public void run() {
		PanelDesControles.this.createTransition()
			.push(new SLKeyframe(_principalCfg, 0.8f)
				.setEndSide(SLSide.RIGHT, _tableauDeBord)
				.setCallback(new SLKeyframe.Callback() {@Override public void done() {
					_visualisationEtLecture.set_actionBoutonTabDeBord(afficheTableauDeBord);
				}}))
			.play();
	}};
	
}
