/**
 * 
 */
package fr.iessa.vue.infra;

import java.awt.Color;

/**
 * @author hodiqual
 *
 */
public enum PlateformeStd {
	TAXIWAY(Color.GRAY, 30f)
,   TAXIWAY_MARQUE_SOL(new Color(244,196,48), 0.5f)
,   TAXIWAY_MARQUE_SOL_BORDURE(Color.BLACK, 1f)

,	LIGNES(Color.LIGHT_GRAY, 60f)
,   LIGNES_MARQUE_SOL(new Color(244,196,48), 0.5f)
,   LIGNES_MARQUE_SOL_BORDURE(Color.BLACK, 1f)

,	PUSHBACK(Color.LIGHT_GRAY, 60f)
,   PUSHBACK_MARQUE_SOL(new Color(244,196,48), 0.5f)
,   PUSHBACK_MARQUE_SOL_BORDURE(Color.BLACK, 1f)

,	RUNWAY(Color.DARK_GRAY, 45.0f)

,	STAND(Color.GRAY, 8.0f)
,	DEICING(Color.GRAY, 8.0f);


	private PlateformeStd(Color couleur, float largeur){
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
	
	public Color couleur()
	{
		return _couleur;
	}
	
	public float largeur()
	{
		return _largeur;
	}
	
}
