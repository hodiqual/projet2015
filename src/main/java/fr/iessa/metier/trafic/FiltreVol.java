/**
 * 
 */
package fr.iessa.metier.trafic;

import java.util.function.Predicate;

/**
 * @author hodiqual
 *
 */
public class FiltreVol {
	
	private abstract class IFiltreDecorator implements Predicate<Vol>{
		IFiltreDecorator _autresFiltres;
		public IFiltreDecorator( IFiltreDecorator autreFiltre){
			_autresFiltres = autreFiltre;
		}
	}
	
	private class FiltreTypeVol extends IFiltreDecorator {
		
		public FiltreTypeVol(IFiltreDecorator autreFiltre) {
			super(autreFiltre);
		}

		@Override
		public boolean test(Vol t) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}

}
