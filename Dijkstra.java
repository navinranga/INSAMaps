import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Vector;

public class Dijkstra {
	// creation du graphe
	public static final int csteMaxVal = 1000;// constante max
	public final static int csteParDefaut = -999;// constante par défaut
	private int x0;//debut du trajet
	private int[] S;//ensemble de sommets dont les distances les plus courtes à la source sont connues
	private int[] R;//ensemble des prédécesseur des sommets de 0 à N-1
	private Graphe g0; //graphe
	private int[] D;//tableau des valeurs du meilleur raccourci pour se rendre à chaque sommet
	private boolean[] noeudsMarqués;//ensemble des boolean des noeuds masqués
	private static int dimensionDeLaMatrice;//dim de matrice
	String[] nomDest; //nom des bat
	int[][][] matCoordX; //coordX des bat dans un tableau 3D
	int[][][] matCoordY; //coordY des bat dans un tableau 3D

	int[] coordXaRenvoyer;//coordX à Dessiner
	int[] coordYaRenvoyer;//coordY à Dessiner

	//constructeur
	public Dijkstra(int x, Graphe g, int[][][] matCoordXAffect, int[][][] matCoordYAffect) {
		x0 = x;
		g0 = g;
		dimensionDeLaMatrice = g0.nbSommet();
		S = new int[dimensionDeLaMatrice];//sommets atteints
		D = new int[dimensionDeLaMatrice];//distances
		noeudsMarqués = new boolean[dimensionDeLaMatrice];
		R = new int[dimensionDeLaMatrice];
		calculePlusCourtChemin();
		this.nomDest = g0.nomDest;
		this.matCoordX = matCoordXAffect;
		this.matCoordY = matCoordYAffect;
	}

	//calcul du plus court chemin à partir de la source
	public void calculePlusCourtChemin() {
		int n = 0;
		for (int a = 0; a < dimensionDeLaMatrice; a++) {
			noeudsMarqués[a] = false;
			S[a] = -1; //en supposant qu'on numérote les sommets de 0 ou de 1 à n.
			R[a] = -1; // pour les noeuds qui n'ont pas de prédecésseur
		}
		S[n] = x0;
		D[x0] = 0;
		R[x0] = x0;
		initDistMin();
		for (int i = 0; i < dimensionDeLaMatrice; i++) {
			if (!contains(S, i)) {// à revoir
				int t = choixSommet();
				noeudsMarqués[t] = true;
				n++;
				S[n] = t;
				majDistMin(t);
			}
		}
	}

	//implémentation de initDistMin
	private void initDistMin() {
		noeudsMarqués[x0] = true;
		for (int i = 0; i < dimensionDeLaMatrice; i++) {
			if (g0.existeLien(x0, i)) {
				D[i] = g0.getU()[x0][i];
				R[i] = x0;
			} else {
				if (x0 != i)
					D[i] = -g0.csteParDefaut + 1;
			}
		}
	}

	private void majDistMin(int n) {
		for (int i = 0; i < dimensionDeLaMatrice; i++) {
			if (!contains(S, i)) {
				//D[i] = min(D[i], D[n] + distanceDsGraphe(n,i));
				if (D[n] + distanceDsGraphe(n, i) < D[i]) {
					D[i] = D[n] + distanceDsGraphe(n, i);
					R[i] = n;
				}
			}
		}
	}

	private int distanceDsGraphe(int t, int s) {
		if (g0.existeLien(t, s)) {
			return g0.getU()[t][s];
		} else {
			return csteMaxVal;
		}
	}

	public int choixSommet() {
		int valeurMin = csteMaxVal;
		int min = x0;
		for (int i = 0; i < dimensionDeLaMatrice; i++) {
			if (!noeudsMarqués[i])
				if (D[i] <= valeurMin) {
					min = i;
					valeurMin = D[i];
				}
		}
		return min;
	}

	private boolean contains(int[] S, int i) {
		return noeudsMarqués[i];
	}

	//obtenir longueur à partir de la source jusqu'au bat ayant pour rang i
	public int getLongueurChemin(int i) {
		return D[i];
	}

	//afficher longueur à partir de la source jusqu'au bat ayant pour rang i
	public void longueurChemin(int i) {
		int d = D[i];
		int metre = d % 1000;
		int km = (d / 1000) % 1000;
		String metreString = Integer.toString(metre);
		String kmString = Integer.toString(km);
		System.out.print(kmString + " km " + metreString + " m ");
	}

	//obtenir longueur à partir de la source jusqu'au bat ayant pour rang i
	public String longueurCheminString(int i) {
		String res;

		int d = D[i];

		int metre = d % 1000;
		int km = (d / 1000) % 1000;

		String metreString = Integer.toString(metre);
		String kmString = Integer.toString(km);
		res = "<html>" + kmString + " km " + "<br>" + metreString + " m" + "</html>";
		return (res);
	}

	//obtenir durée pour faire le trajet de la source jusqu'au bat ayant pour rang i
	public int getDureeChemin(int i) {
		return D[i];
	}

	//afficher durée pour faire le trajet de la source jusqu'au bat ayant pour rang i
	public void dureeChemin(int i) {
		double s = D[i] / (double) 1.77778;

		int sec = (int) s % 60;
		int min = ((int) s / 60) % 60;
		int hours = ((int) s / 60) / 60;

		String strSec = (sec < 10) ? "0" + Integer.toString(sec) : Integer.toString(sec);
		String strmin = (min < 10) ? "0" + Integer.toString(min) : Integer.toString(min);
		String strHours = (hours < 10) ? "0" + Integer.toString(hours) : Integer.toString(hours);

		System.out.print(strHours + " h " + strmin + " min " + strSec + " sec");

	}

	//obtenir durée pour faire le trajet de la source jusqu'au bat ayant pour rang i
	public String dureeCheminString(int i) {
		String res;
		double s = D[i] / (double) 1.77778;
		int sec = (int) s % 60;
		int min = ((int) s / 60) % 60;
		int hours = ((int) s / 60) / 60;
		String strSec = (sec < 10) ? "0" + Integer.toString(sec) : Integer.toString(sec);
		String strmin = (min < 10) ? "0" + Integer.toString(min) : Integer.toString(min);
		String strHours = (hours < 10) ? "0" + Integer.toString(hours) : Integer.toString(hours);
		res = "<html>" + strmin + " min " + strSec + "</html>";
		//res="<html>" + strHours + " h " +"<br>" + strmin + " min " + strSec  + "</html>";
		return res;
	}

	//obtenir calories que brûle une personne de 70kg pour une marche modérée de 5km/h ou 1,77778m/s
	public double getcalculCalories(int i) {
		return (double) D[i] / 1000 * 52.5;
	}

	//afficher calories que brûle une personne de 70kg pour une marche modérée de 5km/h ou 1,77778m/s
	public void calculCalories(int i) {
		System.out.print(String.format("%.2f", ((double) D[i] / 1000 * 52.5)) + " cal");
	}

	//obtenir calories que brûle une personne de 70kg pour une marche modérée de 5km/h ou 1,77778m/s
	public String calculCaloriesString(int i) {
		String res;
		res = "<html>" + String.format("%.2f", ((double) D[i] / 1000 * 52.5)) + " cal" + "<html>";
		return res;
	}

	//afficher le Chemin à partir de la source choisie jusqu'à la destination choisie
	public void afficheChemin(int i) {
		ArrayList<Integer> coordXaDessiner = new ArrayList<Integer>();
		ArrayList<Integer> coordYaDessiner = new ArrayList<Integer>();
		ArrayList<Integer> cheminInt = new ArrayList<Integer>();

		int source = x0;
		int antécédent = i;
		Vector<Integer> lesNoeudsIntermediaires = new Vector<Integer>();
		;
		System.out.println("Chemin de " + this.nomDest[source] + " à " + this.nomDest[antécédent] + ":");

		while (antécédent != source) {
			lesNoeudsIntermediaires.add(antécédent);
			//System.out.println(Math.abs(R[antécédent]));
			antécédent = R[antécédent];
		}
		lesNoeudsIntermediaires.add(source);
		System.out.print(this.nomDest[lesNoeudsIntermediaires.get(lesNoeudsIntermediaires.size() - 1)]);
		cheminInt.add(lesNoeudsIntermediaires.get(lesNoeudsIntermediaires.size() - 1));

		for (int j = lesNoeudsIntermediaires.size() - 2; j > 0; j--) {
			System.out.print(" --> " + this.nomDest[lesNoeudsIntermediaires.get(j)]);
			cheminInt.add(lesNoeudsIntermediaires.get(j));
		}
		System.out.print(" --> " + nomDest[i]);
		cheminInt.add(i);

		System.out.println();

		for(int inc=0;inc<cheminInt.size()-1;inc++){
			for(int inc2=0;inc2<matCoordY[cheminInt.get(inc)][cheminInt.get(inc+1)].length;inc2++) {
				coordXaDessiner.add(matCoordX[cheminInt.get(inc)][cheminInt.get(inc + 1)][inc2]);
				coordYaDessiner.add(matCoordY[cheminInt.get(inc)][cheminInt.get(inc + 1)][inc2]);
			}
		}

		int[] coordXaRenvoyerTemp=new int[coordXaDessiner.size()];
		int[] coordYaRenvoyerTemp=new int[coordXaDessiner.size()];
		for(int inc3=0;inc3<coordXaDessiner.size();inc3++){
			coordXaRenvoyerTemp[inc3]=coordXaDessiner.get(inc3);
			coordYaRenvoyerTemp[inc3]=coordYaDessiner.get(inc3);
		}
		coordXaRenvoyer=coordXaRenvoyerTemp;
		coordYaRenvoyer=coordYaRenvoyerTemp;
	}

}