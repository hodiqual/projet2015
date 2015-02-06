/**
 * 
 */
package fr.iessa.metier.trafic;

import java.util.Set;
import java.util.function.Predicate;

import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

/**
 * @author hodiqual
 *
 */
public class FiltreVol {
	
	private Trafic _trafic;
	
	private TypeVol filtreTypeVol = null;
	private Categorie filtreCategorie = null;
	private Boolean filtreCollision = null;
	
	private Set<Vol> result = null;
	
	private Predicate<Vol> _filtre;
	
	public FiltreVol(Trafic trafic) {
		_trafic = trafic;
		computeResult();
	}
	
	private class Filtre implements Predicate<Vol>{
		@Override
		public boolean test(Vol t) {
			return true;
		}
	}
	
	private abstract class IFiltreDecorator implements Predicate<Vol>{
		protected Predicate<Vol> _autreFiltre;
		
		public IFiltreDecorator(Predicate<Vol> autreFiltre){
			_autreFiltre = autreFiltre;
		}
		
	}
	
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
	
	
	
	private void computeResult() {
		_filtre = new Filtre();
		
		if(filtreTypeVol != null )
			_filtre = new FiltreTypeVol(_filtre, filtreTypeVol);
		if(filtreCategorie != null )
			_filtre = new FiltreCategorie(_filtre, filtreCategorie);
		if(filtreCollision != null )
			_filtre = new FiltreCollision(_filtre, filtreCollision);
		
		result = _trafic.getVols(_filtre);
	}
	
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

}
