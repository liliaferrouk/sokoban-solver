package Modele;

import java.util.HashSet;
import java.awt.Point;

public class Heuristic {
    
	private HashSet<Point> goals;

	public Heuristic(HashSet<Point> goals) {
		this.goals = goals;
	}

	private int distance(Point p1, Point p2) {
		return Math.abs(p1.x-p2.x) + Math.abs(p1.y-p2.y);
	}

	//calcule ditance minimale entre un obj et chaque objet d'un HashSet
	private int distancePlusieurObjets(Point obj, HashSet<Point> set) {
		int min = 1000000;
		for (Point p : set) {
			int dist = distance(obj, p);
			if (dist < min)
			min = dist;
		}
		return min;
	}

	public int heuristicEtat(Etat etat) {
		HashSet<Point> boxes = etat.boxes;
		Point player = etat.player;
		int sum = 0;
		sum+= distancePlusieurObjets(player, boxes);
		for (Point b : boxes) {
			sum += distancePlusieurObjets(b, goals);
		}
		return sum; //distance du player a la plus proche box + dist de chaque box au plus proch goal
	}
}
