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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TranslationPousseur implements ActionListener {
	AireDeDessinSolution aire;
	Point origine, destination;
	double progres, vitesse = 1;
	Timer timer;

	TranslationPousseur(AireDeDessinSolution a, int x, int y) {
		aire = a;
		origine = aire.position();
		destination = new Point(x, y);
		progres = 0;
		timer = new Timer(1, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		progres += vitesse;
		if (progres > 1) {
			progres = 1;
			timer.stop();
		}
		int x = (int) Math.round(progres*destination.x + (1-progres)*origine.x);
		int y = (int) Math.round(progres*destination.y + (1-progres)*origine.y);
		aire.fixePosition(x, y);
		aire.repaint();
	}
}
