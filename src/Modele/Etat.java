package Modele;

import java.util.HashSet;
import java.awt.Point;

public class Etat { //possition de chaque box et du player
    HashSet<Point> boxes;
	Point player;
	

	public Etat(HashSet<Point> boxes, Point player) {
		this.boxes = boxes;
		this.player = player;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (Point b : boxes) {
			result = 37 * result + b.hashCode();
		}
		result = 37 * result + player.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object object){
	    if (object == null) return false;
	    if (object == this) return true;
	    if (this.getClass() != object.getClass()) return false;
	    Etat s = (Etat)object;
	    if(this.hashCode()== s.hashCode()) return true;
	    if((this.boxes == s.boxes) && (this.player == s.player)) return true;
	    return false;
	}
}
