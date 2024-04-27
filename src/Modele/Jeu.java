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

import Patterns.Observable;

public class Jeu extends Observable {
	Niveau n;
	LecteurNiveaux l;

	public Jeu(LecteurNiveaux lect) {
		l = lect;
		prochainNiveau();
	}

	public Niveau niveau() {
		return n;
	}

	public Coup elaboreCoup(int x, int y) {
		return n.elaboreCoup(x, y);
	}

	public void joue(Coup c) {
		n.joue(c);
		metAJour();
	}

	public void prochainNiveau() {
		n = l.lisProchainNiveau();
	}

	public boolean niveauTermine() {
		return n.estTermine();
	}

	public boolean jeuTermine() {
		return n == null;
	}

	public int lignePousseur() {
		return n.lignePousseur();
	}

	public int colonnePousseur() {
		return n.colonnePousseur();
	}
}
