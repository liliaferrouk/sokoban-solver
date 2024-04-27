package Global;
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

import Structures.Sequence;
import Structures.SequenceListe;
import Structures.SequenceTableau;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Configuration {
	static final int silence = 1;
	public static final String typeInterface = "Graphique";
	static final String typeSequences = "Liste";
	public static final double vitesseAnimations = 0.15;
	public static final int lenteurPas = 15;
	public static final boolean animations = true;
	public static final String IA = "Solution"; //Aleatoire | Teleportations | ParcoursFixe | Solution
	public static int lenteurJeuAutomatique = 15;

	public static InputStream ouvre(String s) {
		InputStream in = null;
		try {
			in = new FileInputStream("res/" + s);
		} catch (FileNotFoundException e) {
			erreur("impossible de trouver le fichier " + s);
		}
		return in;
	}

	public static void affiche(int niveau, String message) {
		if (niveau > silence)
			System.err.println(message);
	}

	public static void info(String s) {
		affiche(1, "INFO : " + s);
	}

	public static void alerte(String s) {
		affiche(2, "ALERTE : " + s);
	}

	public static void erreur(String s) {
		affiche(3, "ERREUR : " + s);
		System.exit(1);
	}

	public static <E> Sequence<E> nouvelleSequence() {
		switch (typeSequences) {
			case "Liste" :
				return new SequenceListe<>();
			case "Tableau" :
				return new SequenceTableau<>();
			default:
				erreur("Type de séquence invalide : " + typeSequences);
				return null;
		}
	}
}
