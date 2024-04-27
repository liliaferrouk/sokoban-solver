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

public class TestSequence {
	static int min, max, count;

	static int operation(Sequence<Integer> seq, int code, int pos) {
		int s = Integer.MIN_VALUE;
		System.out.println(seq);
		System.out.print("Affichage avec itérateur :");
		Iterateur<Integer> it = seq.iterateur();
		while (it.aProchain()) {
			System.out.print(" " + it.prochain());
		}
		System.out.println();
		switch (code) {
		case 0:
			s = min;
			System.out.println("Insertion en Tete de " + s);
			seq.insereTete(s);
			break;
		case 1:
			s = max;
			System.out.println("Insertion en Queue de " + s);
			seq.insereQueue(s);
			break;
		case 2:
			if (count > 0) {
				it = seq.iterateur();
				System.out.println("Extraction de l'élément de position " + pos);
				while (pos > 0) {
					assert (it.aProchain());
					it.prochain();
					pos--;
				}
				assert (it.aProchain());
				s = it.prochain();
				it.supprime();
			}
			break;
		case 3:
			if (count > 0) {
				s = seq.extraitTete();
				System.out.println("Extraction en Tete de " + s);
				assert ((count == 1) == (seq.estVide()));
			}
			break;
		}
		if (code < 2) {
			assert (!seq.estVide());
		} else {
			if (count > 0) {
				assert ((s > min) && (s < max));
				assert ((count == 1) == (seq.estVide()));
			}
		}
		return s;
	}

	public static void main(String[] args) {
		Random r = new Random();
		Sequence<Integer> s1, s2;
		s1 = new SequenceTableau<>();
		s2 = new SequenceListe<>();

		assert (s1.estVide());
		assert (s2.estVide());
		min = -1;
		max = 0;
		count = 0;
		for (int i = 0; i < 1000; i++) {
			int code = r.nextInt(4);
			int pos = (count > 0) ? r.nextInt(count) : -1;
			int r1 = operation(s1, code, pos);
			int r2 = operation(s2, code, pos);
			if (code < 2) {
				count++;
				if (code < 1)
					min--;
				else
					max++;
			} else {
				if (count > 0) {
					if ((code == 3) || (pos == 0))
						min = r1;
					if ((code == 2) && (pos == count - 1))
						max = r1;
					if (min == max)
						max++;
					count--;
				}
			}

			assert (r1 == r2);
		}
	}
}
