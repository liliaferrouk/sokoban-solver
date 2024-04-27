/*
 * Sokoban - Encore une nouvelle version (à but pédagogique) du célèbre jeu
 * Copyright (C) 2018 Guillaume Huard
 *
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).
 *
 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.
 *
 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.
 *
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */
package Controleur;

import Global.Configuration;
import Modele.Coup;
import Modele.IA;
import Structures.Sequence;

class AnimationJeuAutomatique extends Animation {
	IA joueur;
	Sequence<Coup> enAttente;

	AnimationJeuAutomatique(int lenteur, IA j, ControleurMediateur c) {
		super(lenteur, c);
		joueur = j;
		control = c;
	}

	@Override
	public void miseAJour() {
		if ((enAttente == null) || enAttente.estVide())
			enAttente = joueur.elaboreCoups();
		if ((enAttente == null) || enAttente.estVide())
			Configuration.erreur("Bug : l'IA n'a joué aucun coup");
		else
			control.joue(enAttente.extraitTete());
	}
}
