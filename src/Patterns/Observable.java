package Patterns;
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

/*
 * Pattern Observateur tel que présenté dans le livre de Gamma et Al.
 * Ce pattern existe déjà dans la bibliothèque standard de Java sous une forme
 * légèrement différente. Il est réimplémenté ici à des fins pédagogiques
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Observable {
	List<Observateur> observateurs;

	public Observable() {
		observateurs = new ArrayList<>();
	}

	public void ajouteObservateur(Observateur o) {
		observateurs.add(o);
	}

	public void metAJour() {
		Iterator<Observateur> it;

		it = observateurs.iterator();
		while (it.hasNext()) {
			Observateur o = it.next();
			o.miseAJour();
		}
	}
}
