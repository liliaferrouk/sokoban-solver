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

public class FAPTableau<E extends Comparable<E>> extends FAP<E> {
	SequenceTableau<E> s;

	public FAPTableau() {
		s = new SequenceTableau<>();
		super.s = s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insere(E element) {
		s.redimensionne();
		int courant = (s.debut + s.taille) % s.elements.length;
		int precedent = courant - 1;
		if (precedent < 0)
			precedent = s.elements.length - 1;
		while ((courant != s.debut) && (element.compareTo((E) s.elements[precedent]) < 0)) {
			s.elements[courant] = s.elements[precedent];
			courant = precedent;
			precedent = courant - 1;
			if (precedent < 0)
				precedent = s.elements.length - 1;
		}
		s.elements[courant] = element;
		s.taille++;
	}
}
