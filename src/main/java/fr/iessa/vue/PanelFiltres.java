package fr.iessa.vue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import fr.iessa.controleur.Controleur;
import fr.iessa.controleur.ModeleEvent;
import fr.iessa.metier.trafic.FiltreVol;
import fr.iessa.metier.trafic.Trafic;
import fr.iessa.metier.trafic.Vol;
import fr.iessa.metier.type.Categorie;
import fr.iessa.metier.type.TypeVol;

public class PanelFiltres extends JPanel implements PropertyChangeListener {

	private Controleur _controleur;

	@SuppressWarnings({ "unchecked", "serial" })
	public PanelFiltres(Controleur controleur){
		_controleur = controleur;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new FiltreTypeVol());
		add(new FiltreCategorie());
		add(new FiltreCollision());
		add(new Combo());
		
		setEnabled(false);

		final ModeleEvent[] evts = { ModeleEvent.CHARGEMENT_TRAFIC_FICHIER_DONE };
		_controleur.ajoutVue(this, evts) ;	
		setMaximumSize(new Dimension(300,240));
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		for (Component child : getComponents()) {
			child.setEnabled(enabled);
		}
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setEnabled(true);
	}
	
	private class FiltreTypeVol extends JPanel implements PropertyChangeListener {
		JRadioButton _tous = new JRadioButton("DEP-ARR");
		JRadioButton _dep = new JRadioButton("DEP");
		JRadioButton _arr = new JRadioButton("ARR");
		
		private final ActionListener action = new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == _tous)
					_controleur.setFiltreTypeVol(null);
				else if (e.getSource() == _dep)
					_controleur.setFiltreTypeVol(TypeVol.DEP);
				else 
					_controleur.setFiltreTypeVol(TypeVol.ARR);
			}
		};
		
		public FiltreTypeVol(){
			setBorder(BorderFactory.createTitledBorder("Type de vol:"));
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(_tous);
			buttonGroup.add(_dep);
			buttonGroup.add(_arr);
			
			add(_tous);
			add(_dep);
			add(_arr);
			
			_tous.addActionListener(action);
			_dep.addActionListener(action);
			_arr.addActionListener(action);

			final ModeleEvent[] evts = { ModeleEvent.UPDATE_FILTRE_VOL };
			_controleur.ajoutVue(this, evts) ;	
		}
		
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			for (Component child : getComponents()) {
				child.setEnabled(enabled);
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			FiltreVol filtre = (FiltreVol) evt.getNewValue();
			TypeVol filtreType = filtre.getFiltreTypeVol();
			if(filtreType == null)
				_tous.setSelected(true);
			else
				switch (filtreType) {
					case ARR:
						_arr.setSelected(true);
						break;
					case DEP:
						_dep.setSelected(true);
						break;
					default:
						_tous.setSelected(true);
						break;
				}
		}
	}
	
	private class FiltreCategorie extends JPanel implements PropertyChangeListener {
		JRadioButton _all = new JRadioButton("Tout");
		JRadioButton _h = new JRadioButton("H");
		JRadioButton _m = new JRadioButton("M");
		JRadioButton _l = new JRadioButton("L");
		
		private final ActionListener action = new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == _all)
					_controleur.setFiltreCategorie(null);
				else if (e.getSource() == _h)
					_controleur.setFiltreCategorie(Categorie.HIGH);
				else if (e.getSource() == _m)
					_controleur.setFiltreCategorie(Categorie.MEDIUM);
				else 
					_controleur.setFiltreCategorie(Categorie.LIGHT);
			}
		};
		
		public FiltreCategorie(){
			setBorder(BorderFactory.createTitledBorder("Categorie de l'aeronef:"));
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(_all);
			buttonGroup.add(_h);
			buttonGroup.add(_m);
			buttonGroup.add(_l);
			
			add(_all);
			add(_h);
			add(_m);
			add(_l);
			
			_all.addActionListener(action);
			_h.addActionListener(action);
			_m.addActionListener(action);
			_l.addActionListener(action);

			final ModeleEvent[] evts = { ModeleEvent.UPDATE_FILTRE_VOL };
			_controleur.ajoutVue(this, evts) ;	
		}
		
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			for (Component child : getComponents()) {
				child.setEnabled(enabled);
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			FiltreVol filtre = (FiltreVol) evt.getNewValue();
			Categorie filtreCat = filtre.getFiltreCategorie();
			if(filtreCat==null)
				_all.setSelected(true);
			else
				switch (filtreCat) {
					case HIGH:
						_h.setSelected(true);
						break;
					case MEDIUM:
						_m.setSelected(true);
						break;
					case LIGHT:
						_l.setSelected(true);
						break;
					default:
						_all.setSelected(true);
						break;
				}
		}
	}
	
	private class FiltreCollision  extends JPanel implements PropertyChangeListener {

		JRadioButton _tous = new JRadioButton("Avec ou Sans");
		JRadioButton _avec = new JRadioButton("Avec");
		JRadioButton _sans = new JRadioButton("Sans");
		
		private final ActionListener action = new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == _tous)
					_controleur.setFiltreCollision(null);
				else if (e.getSource() == _avec)
					_controleur.setFiltreCollision(true);
				else 
					_controleur.setFiltreCollision(false);
			}
		};
		
		public FiltreCollision(){
			setBorder(BorderFactory.createTitledBorder("Vol avec collision detectee:"));
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(_tous);
			buttonGroup.add(_avec);
			buttonGroup.add(_sans);
			
			add(_tous);
			add(_avec);
			add(_sans);
			
			_tous.addActionListener(action);
			_avec.addActionListener(action);
			_sans.addActionListener(action);

			final ModeleEvent[] evts = { ModeleEvent.UPDATE_FILTRE_VOL };
			_controleur.ajoutVue(this, evts) ;	
		}
		
		@Override
		public void setEnabled(boolean enabled) {
			super.setEnabled(enabled);
			for (Component child : getComponents()) {
				child.setEnabled(enabled);
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			FiltreVol filtre = (FiltreVol) evt.getNewValue();
			Boolean filtreCollision = filtre.getFiltreCollision();
			if(filtreCollision == null)
				_tous.setSelected(true);
			else if (filtreCollision == true)
				_avec.setSelected(true);
			else
				_sans.setSelected(true);
		}
	}
	
	private class Combo extends JComboBox<Vol> implements PropertyChangeListener {
		@SuppressWarnings({ "serial", "unchecked" })
		public Combo() {
			setRenderer(new BasicComboBoxRenderer() {
	        	@SuppressWarnings("rawtypes")
				@Override
	        	public Component getListCellRendererComponent(JList list,
	                    Object value,
	                    int index,
	                    boolean isSelected,
	                    boolean cellHasFocus)
	                    {
	        				Component cmp = super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
	        			     if (value instanceof Icon) 
			    	             setIcon((Icon)value);
			    	         else if (value instanceof Vol)
			    	         {
			    	        	 Vol vol = (Vol)value;
			    	        	 setText((value == null) ? "" : vol.getId() + " " + vol.getTypeVol() + " " + vol.getPremierInstant());		
			    	         }
			    	         else
			    	             setText((value == null) ? "" : value.toString());			
	        				return cmp;
	                    }
	        });

			final ModeleEvent[] evts = { ModeleEvent.UPDATE_FILTRE_VOL };
			_controleur.ajoutVue(this, evts) ;	
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			FiltreVol filtre = (FiltreVol) evt.getNewValue();
			removeAllItems();
			for (Vol vol : filtre.getResult()) {
				addItem(vol);
			}
		}
	}

}
