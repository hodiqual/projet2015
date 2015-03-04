/**
 * 
 */
package fr.iessa.controleur;

/**
 * Enumere les changements de modele que le Controleur peut publier aux observers.
 * @author hodiqual
 *
 */
public enum ModeleEvent {
	/** Chargement du fichier de la plateforme reussi */
	CHARGEMENT_CARTE_FICHIER_DONE
	/** Chargement du fichier de la plateforme echoue */
,	CHARGEMENT_CARTE_FICHIER_ERREUR
	/** Chargement du fichier de la plateforme en cours */
,   CHARGEMENT_CARTE_FICHIER_EN_COURS


	/** Chargement du fichier du trafic reussi */
,	CHARGEMENT_TRAFIC_FICHIER_DONE
	/** Chargement du fichier du trafic echoue */
,	CHARGEMENT_TRAFIC_FICHIER_ERREUR
	/** Chargement du fichier du trafic en cours */
,   CHARGEMENT_TRAFIC_FICHIER_EN_COURS

	/** L'instant courant a change ainsi que tous les attributs relatifs a l'instant courant */
,   UPDATE_INSTANT
	/** L'etat de la simulation a change */
,   UPDATE_IS_TRAFIC_RUNNING
	/** La duree entre deux pas de temps a ete modifiee */
,	UPDATE_DUREE_INTERVALLE

	/** Le filtre de recherche a ete modifie et la liste est prete */
, 	UPDATE_FILTRE_VOL

	/** Erreur de sauvegarde du rapport de collision */
,   SAUVEGARDE_COLLISION_ERREUR
	/** Sauvegarde du rapport de collision reussie */
,   SAUVEGARDE_COLLISION_DONE

	/** Les collisions sont visibles */
,	SHOW_COLLISION
	/** Les collisions sont non-visibles */
,	HIDE_COLLISION
;
}
