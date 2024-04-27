package Modele;

public class Noeud {
    
	public Noeud pere;
	public Etat etat;
	public int cout;
	public String direction; //direction prise du pere pour arriver a ce node
	
	public Noeud(Etat etat, Noeud pere, int cout, String direction) {
		this.etat = etat;
		this.pere = pere;
		this.cout = cout; //cout deplacement
		this.direction = direction;
	}

	@Override
	public boolean equals(Object n) {
		//etat == etat
		return (this.etat == ((Noeud) n).etat);
	}
}
