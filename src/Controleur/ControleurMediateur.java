package Controleur;
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
import Modele.Coup;
import Modele.IA;
import Modele.Jeu;
import Structures.Iterateur;
import Structures.Sequence;
import Vue.CollecteurEvenements;
import Vue.InterfaceUtilisateur;

public class ControleurMediateur implements CollecteurEvenements {
	Jeu jeu;
	InterfaceUtilisateur vue;
	Sequence<Animation> animations;
	double vitesseAnimations;
	int lenteurPas;
	Animation mouvement;
	boolean animationsSupportees, animationsActives;
	int lenteurJeuAutomatique;
	IA joueurAutomatique;
	boolean IAActive;
	AnimationJeuAutomatique animationIA;

	public ControleurMediateur(Jeu j) {
		jeu = j;

		animations = Configuration.nouvelleSequence();
		vitesseAnimations = Configuration.vitesseAnimations;
		lenteurPas = Configuration.lenteurPas;
		animations.insereTete(new AnimationPousseur(lenteurPas, this));
		mouvement = null;
		// Tant qu'on ne reçoit pas d'évènement temporel, on n'est pas sur que les
		// animations soient supportées (ex. interface textuelle)
		animationsSupportees = false;
		animationsActives = false;
	}

	@Override
	public void clicSouris(int l, int c) {
		int dL = l - jeu.lignePousseur();
		int dC = c - jeu.colonnePousseur();
		int sum = dC + dL;
		sum = sum * sum;
		if ((dC * dL == 0) && (sum == 1))
			deplace(dL, dC);
	}

	void joue(Coup cp) {
		if (cp != null) {
			jeu.joue(cp);
			vue.metAJourDirection(cp.dirPousseurL(), cp.dirPousseurC());
			if (animationsActives) {
				mouvement = new AnimationCoup(cp, vitesseAnimations, this);
				animations.insereQueue(mouvement);
			} else
				testFin();
		} else {
			Configuration.alerte("Coup null fourni, probablement un bug dans l'IA");
		}
	}

	void deplace(int dL, int dC) {
		if (mouvement == null) {
			Coup cp = jeu.elaboreCoup(dL, dC);
			if (cp != null)
				joue(cp);
		}
	}

	private void testFin() {
		if (jeu.niveauTermine()) {
			//arreter AI si active
			if(IAActive) IAActive=false; //si active le stoper
			jeu.prochainNiveau();
			if (jeu.jeuTermine())
				System.exit(0);
		}
	}


	@Override
	public void toucheClavier(String touche) {
		switch (touche) {
			case "Left":
				deplace(0, -1);
				break;
			case "Right":
				deplace(0, 1);
				break;
			case "Up":
				deplace(-1, 0);
				break;
			case "Down":
				deplace(1, 0);
				break;
			case "Quit":
				System.exit(0);
				break;
			case "Pause":
				basculeAnimations();
				break;
			case "IA":
				basculeIA();
				break;
			case "Full":
				vue.toggleFullscreen();
				break;
			default:
				System.out.println("Touche inconnue : " + touche);
		}
	}

	public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
		vue = v;
	}

	@Override
	public void tictac() {
		// On sait qu'on supporte les animations si on reçoit des évènements temporels
		if (!animationsSupportees) {
			animationsSupportees = true;
			animationsActives = Configuration.animations;
		}
		// On traite l'IA séparément pour pouvoir l'activer même si les animations
		// "esthétiques" sont désactivées
		if (IAActive && (mouvement == null)) {
			animationIA.tictac();
		}
		if (animationsActives) {
			Iterateur<Animation> it = animations.iterateur();
			while (it.aProchain()) {
				Animation a = it.prochain();
				a.tictac();
				if (a.estTerminee()) {
					if (a == mouvement) {
						testFin();
						mouvement = null;
					}
					it.supprime();
				}
			}
		}
	}

	public void changeEtape() {
		vue.changeEtape();
	}

	public void decale(int versL, int versC, double dL, double dC) {
		vue.decale(versL, versC, dL, dC);
	}

	public void basculeAnimations() {
		if (animationsSupportees && (mouvement == null))
			animationsActives = !animationsActives;
	}

	public void basculeIA() {
		if (animationsSupportees) {
			if (joueurAutomatique == null) {
				joueurAutomatique = IA.nouvelle(jeu);
				if (joueurAutomatique != null) {
					lenteurJeuAutomatique = Configuration.lenteurJeuAutomatique;
					animationIA = new AnimationJeuAutomatique(lenteurJeuAutomatique, joueurAutomatique, this);
				}
			}
			if (joueurAutomatique != null)
				if(IAActive) IAActive=false; //si active le stoper
				else{//si pas active mais deja active
					joueurAutomatique = IA.nouvelle(jeu);
					if (joueurAutomatique != null) {
						lenteurJeuAutomatique = Configuration.lenteurJeuAutomatique;
						animationIA = new AnimationJeuAutomatique(lenteurJeuAutomatique, joueurAutomatique, this);
					}
					IAActive=true;
				}
		}
	}
}
