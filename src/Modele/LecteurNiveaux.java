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

import java.io.InputStream;
import java.util.Scanner;

public class LecteurNiveaux {
	Scanner s;
	Niveau n;

	public LecteurNiveaux(InputStream in) {
		s = new Scanner(in);
	}

	String lisLigne() {
		if (s.hasNextLine()) {
			String ligne;
			ligne = s.nextLine();
			// Nettoyage des séparateurs de fin et commentaires
			int i;
			char c = ' ';
			int dernier = -1;
			boolean commentaire = false;
			boolean niveau = false;
			for (i = 0; (i < ligne.length()) && !commentaire; i++) {
				c = ligne.charAt(i);
				if (c == '#') {
					niveau = true;
					dernier = i;
				}
				if (!niveau && !Character.isWhitespace(c)) {
					commentaire = true;
				}
			}
			// Un commentaire non vide sera pris comme nom de niveau
			// -> le dernier commentaire non vide sera le nom final
			if (commentaire) {
				// Si le premier caractère est un ; on le saute ainsi que les espaces après
				if (c == ';') {
					c = ' ';
					while (Character.isWhitespace(c) && (i<ligne.length())) {
						c = ligne.charAt(i);
						i++;
					}
				}
				if (!Character.isWhitespace(c))
					n.fixeNom(ligne.substring(i-1));
			}
			return ligne.substring(0, dernier + 1);
		} else {
			return null;
		}
	}

	Niveau lisProchainNiveau() {
		n = new Niveau();
		String ligne = "";
		while (ligne.length() == 0) {
			ligne = lisLigne();
			if (ligne == null)
				return null;
		}
		int i = 0;
		while ((ligne != null) && (ligne.length() > 0)) {
			for (int j = 0; j < ligne.length(); j++) {
				char c = ligne.charAt(j);
				n.videCase(i, j);
				switch (c) {
				case ' ':
					break;
				case '#':
					n.ajouteMur(i, j);
					break;
				case '@':
					n.ajoutePousseur(i, j);
					break;
				case '+':
					n.ajoutePousseur(i, j);
					n.ajouteBut(i, j);
					break;
				case '$':
					n.ajouteCaisse(i, j);
					break;
				case '*':
					n.ajouteCaisse(i, j);
					n.ajouteBut(i, j);
					break;
				case '.':
					n.ajouteBut(i, j);
					break;
				default:
					System.err.println("Caractère inconnu : " + c);
				}
			}
			ligne = lisLigne();
			i++;
		}
		if (i > 0)
			return n;
		else
			return null;
	}
}
