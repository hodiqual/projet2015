/**
 * 
 */
package fr.iessa.controleur;

import fr.iessa.metier.trafic.Trafic;

/**
 * Contient le trafic afin que la référence soit la meme
 * pour le controleur et la vue.
 * 
 * @author hodiqual
 *
 */
public class TraficConteneur {
	
	private Trafic _trafic = null;

	/**
	 * @return the _trafic
	 */
	public synchronized Trafic get_trafic() {
		return _trafic;
	}

	/**
	 * @param _trafic the _trafic to set
	 */
	public synchronized void set_trafic(Trafic _trafic) {
		this._trafic = _trafic;
	}
	
}
