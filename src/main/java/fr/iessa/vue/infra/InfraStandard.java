/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Color;

/**
 * @author hodiqual
 *
 */
public enum InfraStandard {
	TAXIWAY(Color.GRAY, 8.0f)
,   TAXIWAY_MARQUE_SOL(Color.GRAY, 8.0f)
,	RUNWAY(Color.GRAY, 8.0f)
,	RUNWAY_MARQUE_SOL(Color.GRAY, 8.0f)
,	STAND(Color.GRAY, 8.0f)
,	DEICING(Color.GRAY, 8.0f);

	
	private InfraStandard(Color couleur, float largeur){
		_largeur = largeur;
		_couleur = couleur;
	}
	
	private float _largeur;
	private Color _couleur;
	
	/** Permet de changer la couleur standard*/
	public void setColor( Color couleur )
	{
		_couleur = couleur;
	}
	
}
