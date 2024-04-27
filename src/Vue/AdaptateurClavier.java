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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdaptateurClavier extends KeyAdapter {
	CollecteurEvenements control;

	AdaptateurClavier(CollecteurEvenements c) {
		control = c;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				control.toucheClavier("Left");
				break;
			case KeyEvent.VK_RIGHT:
				control.toucheClavier("Right");
				break;
			case KeyEvent.VK_UP:
				control.toucheClavier("Up");
				break;
			case KeyEvent.VK_DOWN:
				control.toucheClavier("Down");
				break;
			case KeyEvent.VK_Q:
			case KeyEvent.VK_A:
				control.toucheClavier("Quit");
				break;
			case KeyEvent.VK_P:
				control.toucheClavier("Pause");
				break;
			case KeyEvent.VK_I:
				control.toucheClavier("IA");
				break;
			case KeyEvent.VK_ESCAPE:
				control.toucheClavier("Full");
				break;
		}
	}
}
