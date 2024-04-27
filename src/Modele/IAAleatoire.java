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

class IAAleatoire extends IA {
	Random r;
	// Couleurs au format RGB (rouge, vert, bleu, un octet par couleur)
	final static int VERT = 0x00CC00;
	final static int MARRON = 0xBB7755;

	public IAAleatoire() {
		r = new Random();
	}

	@Override
	public Sequence<Coup> joue() {
		Sequence<Coup> resultat = Configuration.nouvelleSequence();
		Coup coup = null;
		boolean mur = true;
		int dL = 0, dC = 0;
		int nouveauL = 0;
		int nouveauC = 0;

		int pousseurL = niveau.lignePousseur();
		int pousseurC = niveau.colonnePousseur();
		// Mouvement du pousseur
		while (mur) {
			int direction = r.nextInt(2) * 2 - 1;
			if (r.nextBoolean()) {
				dL = direction;
			} else {
				dC = direction;
			}
			nouveauL = pousseurL + dL;
			nouveauC = pousseurC + dC;
			coup = niveau.deplace(dL, dC);
			if (coup == null) {
				if (niveau.aMur(nouveauL, nouveauC))
					Configuration.info("Tentative de déplacement (" + dL + ", " + dC + ") heurte un mur");
				else if (niveau.aCaisse(nouveauL, nouveauC))
					Configuration.info("Tentative de déplacement (" + dL + ", " + dC + ") heurte une caisse non déplaçable");
				else
					Configuration.erreur("Tentative de déplacement (" + dL + ", " + dC + "), erreur inconnue");
				dL = dC = 0;
			} else
				mur = false;
		}

		nouveauL += dL;
		nouveauC += dC;
		// Ajout des marques
		for (int l = 0; l < niveau.lignes(); l++)
			for (int c = 0; c < niveau.colonnes(); c++) {
				int marque = niveau.marque(l, c);
				if (marque == VERT)
					coup.ajouteMarque(l, c, 0);
			}
		coup.ajouteMarque(pousseurL, pousseurC, MARRON);
		while (niveau.estOccupable(nouveauL, nouveauC)) {
			int marque = niveau.marque(nouveauL, nouveauC);
			if (marque == 0)
				coup.ajouteMarque(nouveauL, nouveauC, VERT);
			nouveauL += dL;
			nouveauC += dC;
		}
		resultat.insereQueue(coup);
		return resultat;
	}
}
