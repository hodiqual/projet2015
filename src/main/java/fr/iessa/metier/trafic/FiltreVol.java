/**
 * 
 */
package fr.iessa.metier.trafic;

import java.util.Set;
import java.util.function.Predicate;

import fr.iessa.metier.Instant;
import fr.iessa.metier.Instant.InstantFabrique;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * Permet de filtrer les vols selon des criteres de recherche.
 * @author hodiqual
 *
 */
public class FiltreVol {
	
	/**
	 * Le trafic contenant les vols a filtrer.
	 */
	private Trafic _trafic;
	
	/**
	 * Filtre sur les typeVol
	 */
	private TypeVol filtreTypeVol = null;
	
	/**
	 * Filtre sur les categories
	 */
	private Categorie filtreCategorie = null;
	
	/**
	 * Filtre sur les collisions
	 */
	private Boolean filtreCollision = null;
	
	/**
	 * Filtre sur les premiers instants
	 */
	private Instant filtrePremierInstant = null;
	
	/**
	 * Resultat du filtre.
	 */
	private Set<Vol> result = null;
	
	/**
	 * Le predicat dynamique pour filtrer les vols
	 */
	private Predicate<Vol> _filtre;
	
	/**
	 * Constructeur
	 * @param trafic qui contient les vols a filtrer.
	 */
	public FiltreVol(Trafic trafic) {
		_trafic = trafic;
		computeResult();
	}
	
	/**
	 * Predicat true en point de terminaison du pattern Decorator
	 * @author hodiqual
	 */
	private class Filtre implements Predicate<Vol>{
		@Override
		public boolean test(Vol t) {
			return true;
		}
	}
	
	/**
	 * Implementation du pattern decorator
	 * @author hodiqual
	 *
	 */
	private abstract class IFiltreDecorator implements Predicate<Vol>{
		protected Predicate<Vol> _autreFiltre;
		
		public IFiltreDecorator(Predicate<Vol> autreFiltre){
			_autreFiltre = autreFiltre;
		}
		
	}
	
	/**
	 * Permet de filtrer selon le type de vol
	 * @author hodiqual
	 *
	 */
	private class FiltreTypeVol extends IFiltreDecorator {
		private TypeVol _typeVol;	
		public FiltreTypeVol(Predicate<Vol> autreFiltre, TypeVol typeVol) {
			super(autreFiltre);
			_typeVol = typeVol;
		}

		@Override
		public boolean test(Vol v) {
			return _autreFiltre.test(v) && v.getTypeVol() == _typeVol;
		}
	}
	
	/**
	 * Permet de filtrer selon la categorie
	 * @author hodiqual
	 *
	 */
	private class FiltreCategorie extends IFiltreDecorator {
		private Categorie _cat;	
		public FiltreCategorie(Predicate<Vol> autreFiltre, Categorie cat) {
			super(autreFiltre);
			_cat = cat;
		}

		@Override
		public boolean test(Vol v) {
			return _autreFiltre.test(v) && v.getCategorie() == _cat;
		}
	}
	
	/**
	 * Permet de filtrer selon le vol est implique dans une collision
	 * @author hodiqual
	 *
	 */
	private class FiltreCollision extends IFiltreDecorator {
		private boolean _aColision;	
		public FiltreCollision(Predicate<Vol> autreFiltre, boolean aCollision) {
			super(autreFiltre);
			_aColision = aCollision;
		}

		@Override
		public boolean test(Vol v) {
			return _autreFiltre.test(v) && v.aDesCollisions() == _aColision;
		}
	}
	
	/**
	 * Permet de filtrer selon le premier instant
	 * @author hodiqual
	 *
	 */
	private class FiltrePremierInstant extends IFiltreDecorator {
		private Instant _premierInstant;	
		public FiltrePremierInstant(Predicate<Vol> autreFiltre, Instant premierInstant) {
			super(autreFiltre);
			_premierInstant = premierInstant;
		}

		@Override
		public boolean test(Vol v) {
			return _autreFiltre.test(v) && v.getPremierInstant().compareTo(_premierInstant) >= 0;
		}
	}
	
	
	/**
	 * Filtre les vols selon les criteres.
	 */
	private void computeResult() {
		_filtre = new Filtre();
		
		if(filtreTypeVol != null )
			_filtre = new FiltreTypeVol(_filtre, filtreTypeVol);
		if(filtreCategorie != null )
			_filtre = new FiltreCategorie(_filtre, filtreCategorie);
		if(filtreCollision != null )
			_filtre = new FiltreCollision(_filtre, filtreCollision);
		if(filtrePremierInstant != null )
			_filtre = new FiltrePremierInstant(_filtre, filtrePremierInstant);
		
		result = _trafic.getVols(_filtre);
	}
	
	/**
	 * 
	 * @return les vols filtres.
	 */
	public Set<Vol> getResult() {
		return result;
	}

	/**
	 * @return the filtreTypeVol
	 */
	public TypeVol getFiltreTypeVol() {
		return filtreTypeVol;
	}

	/**
	 * @param filtreTypeVol the filtreTypeVol to set
	 */
	public void setFiltreTypeVol(TypeVol filtreTypeVol) {
		this.filtreTypeVol = filtreTypeVol;
		computeResult();
	}

	/**
	 * @return the filtreCategorie
	 */
	public Categorie getFiltreCategorie() {
		return filtreCategorie;
	}

	/**
	 * @param filtreCategorie the filtreCategorie to set
	 */
	public void setFiltreCategorie(Categorie filtreCategorie) {
		this.filtreCategorie = filtreCategorie;
		computeResult();
	}

	/**
	 * @return the filtreCollision
	 */
	public Boolean getFiltreCollision() {
		return filtreCollision;
	}

	/**
	 * @param filtreCollision the filtreCollision to set
	 */
	public void setFiltreCollision(Boolean filtreCollision) {
		this.filtreCollision = filtreCollision;
		computeResult();
	}
	
	/**
	 * 
	 * @return filtrePremierInstant
	 */
	public Instant getFiltrePremierInstant(){
		return filtrePremierInstant;
	}

	/**
	 * 
	 * @param filtrePremierInstant le filtrePremierInstant a set.
	 */
	public void setFiltrePremierInstant(Instant filtrePremierInstant) {
		if(filtrePremierInstant.getSeconds() == InstantFabrique.getMinimumInstant().getSeconds())
			this.filtrePremierInstant = null;
		else
			this.filtrePremierInstant = filtrePremierInstant;
		computeResult();
	}

}
