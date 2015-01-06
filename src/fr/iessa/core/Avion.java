package fr.iessa.core;

public class Avion {
	
	private String _nom;
	private String _prout;
	
	public Avion(String nom)
	{
		_nom = nom;
	}
	
	/**
	 * @return the _nom
	 */
	public String getNom() {
		return _nom;
	}

	/**
	 * @param _nom the _nom to set
	 */
	public void setNom(String _nom) {
		this._nom = _nom;
	}
	

}
