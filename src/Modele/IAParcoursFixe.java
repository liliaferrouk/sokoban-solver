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

class IAParcoursFixe extends IA {
	final static int ROUGE = 0xCC0000;
	boolean retour;

	Coup coupAvecMarque(int dL, int dC) {
		// Un coup dans la direction donnée
		Coup resultat = niveau.deplace(dL, dC);

		int pL = niveau.lignePousseur();
		int pC = niveau.colonnePousseur();
		// On change la marque de la nouvelle position du pousseur
		if (retour) {
			// Retrait de la marque
			resultat.ajouteMarque(pL, pC, 0);
		} else {
			// Ajout d'une marque
			resultat.ajouteMarque(pL, pC, ROUGE);
		}
		return resultat;
	}

	@Override
	public Sequence<Coup> joue() {
		Sequence<Coup> resultat = Configuration.nouvelleSequence();
		if (retour) {
			resultat.insereQueue(coupAvecMarque(0, 1));
			resultat.insereQueue(coupAvecMarque(1, 0));
			resultat.insereQueue(coupAvecMarque(1, 0));
			resultat.insereQueue(coupAvecMarque(1, 0));
			resultat.insereQueue(coupAvecMarque(0, 1));
			resultat.insereQueue(coupAvecMarque(0, 1));
			resultat.insereQueue(coupAvecMarque(0, 1));
			resultat.insereQueue(niveau.deplace(1, 0));
		} else {
			resultat.insereQueue(coupAvecMarque(-1, 0));
			resultat.insereQueue(coupAvecMarque(0, -1));
			resultat.insereQueue(coupAvecMarque(0, -1));
			resultat.insereQueue(coupAvecMarque(0, -1));
			resultat.insereQueue(coupAvecMarque(-1, 0));
			resultat.insereQueue(coupAvecMarque(-1, 0));
			resultat.insereQueue(coupAvecMarque(-1, 0));
			resultat.insereQueue(niveau.deplace(0, -1));
		}
		retour = !retour;
		return resultat;
	}
}
