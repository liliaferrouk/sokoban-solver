package Modele;
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

import Global.Configuration;
import Structures.Sequence;

import java.util.Random;

class IATeleportations extends IA {
	Random r;

	public IATeleportations() {
		r = new Random();
	}

	@Override
	public Sequence<Coup> joue() {
		Sequence<Coup> resultat = Configuration.nouvelleSequence();
		int pousseurL = niveau.lignePousseur();
		int pousseurC = niveau.colonnePousseur();

		// Ici, a titre d'exemple, on peut construire une séquence de coups
		// qui sera jouée par l'AnimationJeuAutomatique
		int nb = r.nextInt(5)+1;
		Configuration.info("Entrée dans la méthode de jeu de l'IA");
		Configuration.info("Construction d'une séquence de " + nb + " coups");
		for (int i = 0; i < nb; i++) {
			// Mouvement du pousseur
			Coup coup = new Coup();
			boolean libre = false;
			while (!libre) {
				int nouveauL = r.nextInt(niveau.lignes());
				int nouveauC = r.nextInt(niveau.colonnes());
				if (niveau.estOccupable(nouveauL, nouveauC)) {
					Configuration.info("Téléportation en (" + nouveauL + ", " + nouveauC + ") !");
					coup.deplacementPousseur(pousseurL, pousseurC, nouveauL, nouveauC);
					resultat.insereQueue(coup);
					pousseurL = nouveauL;
					pousseurC = nouveauC;
					libre = true;
				}
			}
		}
		Configuration.info("Sortie de la méthode de jeu de l'IA");
		return resultat;
	}
}
