/**
 * 
 */
package fr.iessa.controleur;

import fr.iessa.metier.trafic.Trafic;

/**
 * Contient le trafic afin que la reference soit la meme
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
	public synchronized Trafic getTrafic() {
		return _trafic;
	}

	/**
	 * @param _trafic the _trafic to set
	 */
	public synchronized void setTrafic(Trafic _trafic) {
		this._trafic = _trafic;
	}
	
}
