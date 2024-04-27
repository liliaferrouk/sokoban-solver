package Modele;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.awt.Point;
import Global.Configuration;
import Structures.Sequence;

public class IASolution extends IA {

    HashSet<Point> walls;
    HashSet<Point> goals;
    HashSet<Point> boxes;
    Point player;
    Plateau plateau;
    Heuristic h;


    public void loadNiveau(Niveau n) { //initialiser avec niveau courant
		walls = new HashSet<Point>();
		goals = new HashSet<Point>();
		boxes = new HashSet<Point>();
		for (int i = 0; i < n.lignes(); i++) {
			int dernier = 0;
			for (int j = 0; j < n.colonnes(); j++)
				if (!n.estVide(i, j))
					dernier = j;
			for (int j = 0; j <= dernier; j++)
				if (n.aMur(i, j))
					walls.add(new Point(i, j));
				else if (n.aBut(i, j)){
					goals.add(new Point(i, j));
					if (n.aPousseur(i, j)){
						player = new Point(i, j);
					}
					else if (n.aCaisse(i, j)){
						boxes.add(new Point(i,j));
					}
				}
				else if (n.aPousseur(i, j))
					player = new Point(i, j);
				else if (n.aCaisse(i, j))
					boxes.add(new Point(i,j));
		}
		plateau = new Plateau(new Etat(boxes, player),walls, goals);
        h = new Heuristic(goals);
    }


    public String solve() throws NoSolutionException {
        long startTime = System.currentTimeMillis();
		int nbNoeud = 1;
		Noeud racine = new Noeud(plateau.etatInitiale, null, 0, "");
		Set<Etat> explored = new HashSet<Etat>();
		Queue<Noeud> toExplore = new PriorityQueue<Noeud>(11, Comparateur);
		toExplore.add(racine);
		while (!toExplore.isEmpty()) {
			Noeud n = toExplore.remove();
			if (plateau.etatFinal(n.etat)) //etat solution
			{
				String result = "";
				if (n != null)
					while (n.pere!=null) {
						result = n.direction + result;
						n = n.pere;
					}
					long temps = System.currentTimeMillis() - startTime;
					System.out.println("Solution : "+ result);
					System.out.println("(Avec " + result.length() + " etapes)");
					System.out.println("nb noeud genere: " + nbNoeud);
					System.out.println("nb seconds: " + temps + "ms");
				return result;
			}
			if (!plateau.etatBloquant(n.etat)) { //si pas etat bloquant
				explored.add(n.etat);
				ArrayList<String> directions = plateau.directionsPossible(n.etat);
				for (int i=0; i<directions.size(); i++) {
					Noeud fils = filsNoeud(n, directions.get(i));
					if((fils!=null) && (fils.etat!=null)) {
						nbNoeud++;
						if ((!explored.contains(fils.etat))&&(!toExplore.contains(fils)))
							toExplore.add(fils);//si pas deja explore et pas deja dans la list a explorer
						else {
							for (Noeud toexp : toExplore) {
								if (toexp == fils)
									if(fils.cout < toexp.cout) {
										toexp = fils; // le chemin pris par fils est plus optimale
									}
							}
						}
					}
				}
			}
		}
		throw new NoSolutionException();//sol pas trouve
    }

    public Comparator<Noeud> Comparateur = new Comparator<Noeud>() {
		@Override
		public int compare(Noeud n1, Noeud n2) {
            return (int) ((n1.cout + h.heuristicEtat(n1.etat)) - (n2.cout + h.heuristicEtat(n2.etat)));
        }
	};

    private Noeud filsNoeud( Noeud n, String action ) {
		@SuppressWarnings("unchecked")
		HashSet<Point> boxes = (HashSet<Point>) n.etat.boxes.clone(); //copier etat des boxes
		int px = n.etat.player.x;
		int py = n.etat.player.y;
		int newCout = n.cout+1;//un pas de plus est fait
		Point newPlayer = n.etat.player;
		char choice = action.charAt(0);
		switch(choice) {
			case 'u':
				newPlayer = new Point(px-1, py);
				if (boxes.contains(newPlayer)) { //si pousse une box
					Point newBox = new Point(px-2, py);
					boxes.remove(newPlayer); //retirer encienne pos de box
					boxes.add(newBox);
				}
				break;
			case 'd':
				newPlayer = new Point(px+1, py);
				if (boxes.contains(newPlayer)) {
					Point newBox = new Point(px+2, py);
					boxes.remove(newPlayer);
					boxes.add(newBox);
				}
				break;
			case 'l':
				newPlayer = new Point(px, py-1);
				if (boxes.contains(newPlayer)) {
					Point newBox = new Point(px, py-2);
					boxes.remove(newPlayer);
					boxes.add(newBox);
				}
				break;
			case 'r':
				newPlayer = new Point(px, py+1);
				if (boxes.contains(newPlayer)) {
					Point newBox = new Point(px, py+2);
					boxes.remove(newPlayer);
					boxes.add(newBox);
				}
				break;
		}
		return new Noeud(new Etat(boxes, newPlayer), n, newCout, Character.toString(choice));
	}


	final static Point UP = new Point(-1,0);
	final static Point DOWN = new Point(1, 0);
	final static Point RIGHT = new Point(0, 1);
	final static Point LEFT = new Point(0, -1);

    final static int VERT = 0x00CC00;

	Coup coupAvecMarque(Point direction) {
		// Un coup dans la direction donnée
		Coup coup = niveau.deplace(direction.x, direction.y);
		if (coup!=null) {
			int pL = niveau.lignePousseur();
			int pC = niveau.colonnePousseur();
			coup.ajouteMarque(pL, pC, VERT);
		}

		return coup;
	}

	@Override
	public Sequence<Coup> joue() {
		Sequence<Coup> resultat = Configuration.nouvelleSequence();
		try{
			loadNiveau(niveau);
			String reponse = solve();
			// System.out.println(answer);
			for (char c : reponse.toCharArray()) {
				switch (c) {
					case 'l':
						resultat.insereQueue(coupAvecMarque(LEFT));
						break;
					case 'u':
						resultat.insereQueue(coupAvecMarque(UP));
						break;
					case 'd':
						resultat.insereQueue(coupAvecMarque(DOWN));
						break;
					case 'r':
						resultat.insereQueue(coupAvecMarque(RIGHT));
						break;
				}
			}
		} catch (NoSolutionException e) {
			System.out.println("Solution non existante");
		}
		return resultat;
	}
}
