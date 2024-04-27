package Vue;
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
import Modele.Jeu;
import Modele.Niveau;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class NiveauGraphique extends JComponent implements Observateur {
	Image pousseur, mur, sol, caisse, but, caisseSurBut;
	Jeu j;
	int largeurCase;
	int hauteurCase;
	// Décalage des éléments (pour pouvoir les animer)
	Vecteur [][] decalages;
	// Images du pousseur (pour l'animation)
	Image [][] pousseurs;
	int direction, etape;

	NiveauGraphique(Jeu jeu) {
		j = jeu;
		j.ajouteObservateur(this);
		pousseur = lisImage("Pousseur");
		mur = lisImage("Mur");
		sol = lisImage("Sol");
		caisse = lisImage("Caisse");
		but = lisImage("But");
		caisseSurBut = lisImage("Caisse_sur_but");

		pousseurs = new Image[4][4];
		for (int d = 0; d < pousseurs.length; d++)
			for (int i = 0; i < pousseurs[d].length; i++)
				pousseurs[d][i] = lisImage("Pousseur_" + d + "_" + i);
		etape = 0;
		direction = 2;
		metAJourPousseur();
	}

	private Image lisImage(String nom) {
		InputStream in = Configuration.ouvre("Images/" + nom + ".png");
		Configuration.info("Chargement de l'image " + nom);
		try {
			// Chargement d'une image utilisable dans Swing
			return ImageIO.read(in);
		} catch (Exception e) {
			Configuration.erreur("Impossible de charger l'image " + nom);
		}
		return null;
	}

	private void tracer(Graphics2D g, Image i, int x, int y, int l, int h) {
		g.drawImage(i, x, y, l, h, null);
	}

	public void paintComponent(Graphics g) {
		Graphics2D drawable = (Graphics2D) g;
		Niveau n = j.niveau();

		int largeur = getSize().width;
		int hauteur = getSize().height;
		largeurCase = largeur / n.colonnes();
		hauteurCase = hauteur / n.lignes();
		// On prend des cases carrées
		largeurCase = Math.min(largeurCase, hauteurCase);
		hauteurCase = largeurCase;

		// Le vecteur de décalages doit être conforme au niveau
		if ((decalages == null)
				|| (decalages.length != n.lignes())
				|| (decalages[0].length != n.colonnes()))
			decalages = new Vecteur[n.lignes()][n.colonnes()];

		// Tracé du niveau
		// En deux étapes à cause des décalages possibles
		for (int ligne = 0; ligne < n.lignes(); ligne++)
			for (int colonne = 0; colonne < n.colonnes(); colonne++) {
				int x = colonne * largeurCase;
				int y = ligne * hauteurCase;
				int marque = n.marque(ligne, colonne);
				// Tracé du sol
				if (n.aBut(ligne, colonne))
					tracer(drawable, but, x, y, largeurCase, hauteurCase);
				else
					tracer(drawable, sol, x, y, largeurCase, hauteurCase);
				if (marque > 0)
					tracerCroix(drawable, marque, x, y, largeurCase, hauteurCase);
			}
		for (int ligne = 0; ligne < n.lignes(); ligne++)
			for (int colonne = 0; colonne < n.colonnes(); colonne++) {
				int x = colonne * largeurCase;
				int y = ligne * hauteurCase;
				// Décalage éventuel
				Vecteur decal = decalages[ligne][colonne];
				if (decal != null) {
					x += decal.x * largeurCase;
					y += decal.y * hauteurCase;
				}
				// Tracé des objets
				if (n.aMur(ligne, colonne))
					tracer(drawable, mur, x, y, largeurCase, hauteurCase);
				else if (n.aPousseur(ligne, colonne))
					tracer(drawable, pousseur, x, y, largeurCase, hauteurCase);
				else if (n.aCaisse(ligne, colonne)) {
					int marque = n.marque(ligne, colonne);
					if (n.aBut(ligne, colonne))
						tracer(drawable, caisseSurBut, x, y, largeurCase, hauteurCase);
					else
						tracer(drawable, caisse, x, y, largeurCase, hauteurCase);
					if (marque > 0)
						tracerCroix(drawable, marque, x, y, largeurCase, hauteurCase);
				}
			}
	}

	public void tracerCroix(Graphics2D drawable, int marque, int x, int y, int l, int h) {
		int s = l/10;
		drawable.setColor(new Color(marque));
		drawable.setStroke(new BasicStroke(s));
		drawable.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawable.drawLine(x+s, y+s, x+l-s, y+h-s);
		drawable.drawLine(x+h-s, y+s, x+s, y+h-s);
	}

	int hauteurCase() {
		return hauteurCase;
	}

	int largeurCase() {
		return largeurCase;
	}

	@Override
	public void miseAJour() {
		repaint();
	}

	public void decale(int l, int c, double dl, double dc) {
		if ((dl != 0) || (dc != 0)) {
			Vecteur v = decalages[l][c];
			if (v == null) {
				v = new Vecteur();
				decalages[l][c] = v;
			}
			v.x = dc;
			v.y = dl;
		} else {
			decalages[l][c] = null;
		}
		miseAJour();
	}

	// Animation du pousseur
	void metAJourPousseur() {
		pousseur = pousseurs[direction][etape];
	}

	public void metAJourDirection(int dL, int dC) {
		switch (dL + 2 * dC) {
			case -2:
				direction = 1;
				break;
			case -1:
				direction = 0;
				break;
			case 0:
				// Rien, pas de mouvement, direction inchangée
				break;
			case 1:
				direction = 2;
				break;
			case 2:
				direction = 3;
				break;
			default:
				Configuration.erreur("Bug interne, direction invalide");
		}
		metAJourPousseur();
	}

	public void changeEtape() {
		etape = (etape + 1) % pousseurs[direction].length;
		metAJourPousseur();
		miseAJour();
	}
}