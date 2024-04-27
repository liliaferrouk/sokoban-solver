package Structures;
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

import java.util.Random;

public class TestFAP {
	public static void main(String[] args) {
		int min = 0;
		int[] count = new int[100];
		Random r = new Random();
		FAP<Integer> f = new FAPListe<>();
		FAP<Integer> g = new FAPTableau<>();

		assert (f.estVide());
		assert (g.estVide());
		for (int i = 0; i < 10000; i++) {
			if (r.nextBoolean()) {
				int val = r.nextInt(count.length);
				System.out.println("Insertion de " + val + " (Tableau et Liste)");
				f.insere(val);
				g.insere(val);
				assert (!f.estVide());
				assert (!g.estVide());
				if (val < min)
					min = val;
				count[val]++;
			} else {
				if (!f.estVide()) {
					assert (!g.estVide());
					int val = f.extrait();
					int val2 = g.extrait();
					assert (val == val2);
					count[val]--;
					assert (count[val] >= 0);
					assert (val >= min);
					if (val > min)
						min = val;
					System.out.println("Extraction de " + val + " (Tableau et Liste)");
				}
			}
		}
	}
}
