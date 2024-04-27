package Modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Point;

public class Plateau {
    Etat etatInitiale;
	HashSet<Point> walls;
	HashSet<Point> goals;

	public Plateau( Etat etatInitiale,HashSet<Point> walls, HashSet<Point> goals) {
		this.etatInitiale = etatInitiale;
		this.walls = walls;
		this.goals = goals;
	}

	public boolean etatFinal(Etat etat) {
		for(Point b : etat.boxes)
			if (!goals.contains(b)) return false;
		return true; //si l'etat est une solution: tt les boxes sont sur les goals
	}

	public boolean etatBloquant(Etat etat) {
		//true si le pousseur ne poura plus bouger une box qui n'est pas encore sur le goal
		//box bloque
		for (Point box : etat.boxes) {
			int x = box.x;
			int y = box.y;
			if (!objetDansSet(goals, x, y)) {//tester que les boxes qui sont pas sur goal
				if (objetDansSet(walls, x-1, y)&&objetDansSet(walls, x, y-1))
					return true; //top & left
				if (objetDansSet(walls, x-1, y)&&objetDansSet(walls, x, y+1))
					return true; //top & right
				if (objetDansSet(walls, x+1, y)&&objetDansSet(walls, x, y-1))
					return true; //bottom & left
				if (objetDansSet(walls, x+1, y)&&objetDansSet(walls, x, y+1))
					return true; //bottom & right

				if (objetDansSet(walls, x-1, y-1)&&objetDansSet(walls, x-1, y)&&
						objetDansSet(walls, x-1, y+1)&&objetDansSet(walls, x, y-2)&&
						objetDansSet(walls, x, y+2)&&(!objetDansSet(goals, x, y-1))&&
								!objetDansSet(goals, x, y+1))
					return true; //top & sides
				if (objetDansSet(walls, x+1, y-1)&&objetDansSet(walls, x+1, y)&&
						objetDansSet(walls, x+1, y+1)&&objetDansSet(walls, x, y-2)&&
						objetDansSet(walls, x, y+2)&&(!objetDansSet(goals, x, y-1))&&
								(!objetDansSet(goals, x, y+1)))
					return true; //bottom & sides
				if (objetDansSet(walls, x-1, y-1)&&objetDansSet(walls, x, y-1)&&
						objetDansSet(walls, x+1, y-1)&&objetDansSet(walls, x-2, y)&&
						objetDansSet(walls, x+2, y)&&(!objetDansSet(goals, x-1, y))&&
								(!objetDansSet(goals, x+1, y)))
					return true; //left & vertical
				if (objetDansSet(walls, x-1, y+1)&&objetDansSet(walls, x, y+1)&&
						objetDansSet(walls, x+1, y+1)&&objetDansSet(walls, x-2, y)&&
						objetDansSet(walls, x+2, y)&&(!objetDansSet(goals, x-1, y))&&
								(!objetDansSet(goals, x+1, y)))
					return true; //right & top/bottom
			}
		}
		return false;
	}


	public ArrayList<String> directionsPossible(Etat etat) {
		//return udlr si tt u et d et l et r sont possible
		ArrayList<String> listdirections = new ArrayList<String>();
		int playerx = etat.player.x;
		int playery = etat.player.y;
		HashSet<Point> boxes = etat.boxes;

		//UP:
		Point newPlayer = new Point(playerx-1,playery);
		Point newBox = new Point(playerx-2, playery);
		possible(newPlayer, newBox, "u", listdirections, boxes);

		//Right:
		newPlayer = new Point(playerx,playery+1);
		newBox = new Point(playerx, playery+2);
		possible(newPlayer, newBox, "r", listdirections, boxes);

		//DOWN:
		newPlayer = new Point(playerx+1,playery);
		newBox = new Point(playerx+2, playery);
		possible(newPlayer, newBox, "d", listdirections, boxes);

		//LEFT:
		newPlayer = new Point(playerx,playery-1);
		newBox = new Point(playerx, playery-2);
		possible(newPlayer, newBox, "l", listdirections, boxes);

		return listdirections;
	}

	void possible(Point newPlayer,Point newBox, String s,ArrayList<String> list, HashSet<Point> boxes){
		//player peut pas marcher sur un mur:
		if (!walls.contains(newPlayer))
			//si player essaye de pousser plus d'une box
			//ou
			//si la box veut se mettre sur le mur
			if (boxes.contains(newPlayer)&&(boxes.contains(newBox)||walls.contains(newBox)))
				;
			else
				list.add(s);
	}

	private boolean objetDansSet(HashSet<Point> set, int x, int y) {
		if (set.contains(new Point(x, y)))
			return true;
		return false;
	}
}
