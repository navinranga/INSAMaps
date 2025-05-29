import javax.swing.*;
import java.util.ArrayList;

class Main{
	static int N = Graphe.csteParDefaut ;

	//matrice contenant la distance entre deux batiments entre lesquels il y a une route/arrete
	static int [][] matDistance={
			{N,    81,  280,	N,     	N,	    N,	    N,    N,	  N,	N},
			{81,    N,  200,	N,	    N,	    N,  	N,	   N,	 N,  	N},
			{280, 200,    N,	N,	    300,	300,	N,	  N,     N,  	N},
			{N,     N,     N,	  N,	160,	N,	    N,	    N,  	N,     N},
			{N,     N,	 300,    160,      N,	350,	N,	    N,	    N,	    N},
			{N,      N,	 300,	N,      350,	   N,	 280,	500,	550,	N},
			{N,      N,	  N,    N,  	N,      280,  	N,	    400,	220,	N},
			{N,     N,	 N,	    N,  	N,	    500,	400,	N,	    250,	140},
			{N,     N,	 N,	    N,	    N,	    550,	220,	250,	N,	    250},
			{N,     N,	 N,	    N,	    N,  	N,	    N,	    140,	250,	N},
	};
	//tableau contenant liste des noms de batiment
	static String[] nomDest={"Residence C", "Résidence D", "Le Galilée", "La Rotonde", "Humanités", "Bibliothèque Marie Curie", "INSA Direction ", "Louis Neel", "Jean D'Alembert", "Pierre de Fermat"};
	//graphe ayant comme parametres la matrice distance et noms des batiments
	static Graphe g0 = new Graphe(matDistance, nomDest);
	//fenetre du programme
	public static FenetreCarte maFenetreCarte;


	public static void main(String[] args) {

		//animation de debut du programme
		JFrame jf = new JFrame();
		jf.setSize(1100, 725);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AnimationDeb myAnim = new AnimationDeb();
		jf.add(myAnim);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		while (myAnim.getAnim()) {
			jf.setVisible(true);
		}
		jf.dispose();


		//tableau avec nom des bat, leurs coordonnées X et Y relatives à notre fenetre
		String[][] coordonneesDest={
			{"Résidence C",	"70",	"630"},
			{"Résidence D",	"158",	"633"},
			{"Le Galilée",	"145",	"538"},
			{"La Rotonde",	"262",	"294"},
			{"Humanités"	,"279",	"328"},
			{"Bibliothèque Marie Curie",	"374",	"451"},
			{"INSA Direction",	"488",	"428"},
			{"Louis Neel",	"750",	"475"},
			{"Jean d'Alembert"	,"746",	"383"},
			{"Pierre de Fermat",	"813",	"390"}
			};

		//creation d'arraylist des bat
		ArrayList<Batiment> maListeBatiment = new ArrayList<>();
		for(int i=0;i<coordonneesDest.length;i++){
			maListeBatiment.add(new Batiment(coordonneesDest[i][0],Integer.parseInt(coordonneesDest[i][1]),Integer.parseInt(coordonneesDest[i][2])));
		}

		int[] M =new int[0];

		//matrice coordonnées X des points permettant d'effectuer de "dessiner" une arrête/route
		int[][][] matCoordX={
				{M,	{158,70},	{145,145,110,110,83,83,70},	M,	M,	M,	M,	M,	M,	M},
				{{158,70},	M,	{145,152,180,180,158},	M,	M,	M,	M,	M,	M,	M},
				{{145,145,110,110,83,83,70},	{145,152,180,180,158},	M,	M,	{279,270,267,145,145},	{374,374,305,296,145,145},	M,	M,	M,	M},
				{M,	M,	M,	M,	{279,262},	M,	{488,488,420,414,305,296,269,269,262},	{750,750,553,537,420,414,305,296,269,269,262},	{746,746,715,708,697,672,653,553,537,420,414,305,296,269,269,262},	M},
				{M,	M,	{279,270,267,145,145},	{279,262},	M,	{374,374,305,296,269,269,262},	M,	M,	M,	M},
				{M,	M,	{374,374,305,296,145,145},	M,	{374,374,305,296,269,269,262},	M,	{488,488,420,414,374,374},	{750,750,553,537,420,414,374,374},	{746,746,715,708,697,672,653,553,537,420,414,374,374},	M},
				{M,	M,	M,	{488,488,420,414,305,296,269,269,262},	M,	{488,488,420,414,374,374},	M,	{750,750,553,537,488,488},	{746,746,715,708,697,672,653,553,537,488,488},	M},
				{M,	M,	M,	{750,750,553,537,420,414,305,296,269,269,262},	M,	{750,750,553,537,420,414,374,374},	{750,750,553,537,488,488},	M,	{746,746,715,708,697,672,653,750,750},	{813,813,787,775,715,708,697,672,653,750,750}},
				{M,	M,	M,	{746,746,715,708,697,672,653,553,537,420,414,305,296,269,269,262},	M,	{746,746,715,708,697,672,653,553,537,420,414,374,374},	{746,746,715,708,697,672,653,553,537,488,488},	{746,746,715,708,697,672,653,750,750},	M,	{813,813,787,775,746,746}},
				{M,	M,	M,	M,	M,	M,	M,	{813,813,787,775,715,708,697,672,653,750,750},	{813,813,787,775,746,746},	M}
		};

		//matrice coordonnées Y des points permettant d'effectuer de "dessiner" une arrête/route
		int[][][] matCoordY={
				{M,	{633,630},	{538,499,499,581,581,630,630},	M,	M,	M,	M,	M,	M,	M},
				{{633,630},	M,	{538,505,506,633,633},	M,	M,	M,	M,	M,	M,	M},
				{{538,499,499,581,581,630,630},	{538,505,506,633,633},	M,	M,	{328,368,470,471,538},	{451,469,469,474,471,538},	M,	M,	M,	M},
				{M,	M,	M,	M,	{328,294},	M,	{428,463,461,471,469,474,472,365,294},	{475,478,478,465,461,471,469,474,472,365,294},	{383,372,370,379,420,415,476,478,465,461,471,469,474,472,365,294},	M},
				{M,	M,	{328,368,470,471,538},	{328,294},	M,	{451,469,469,474,472,365,294},	M,	M,	M,	M},
				{M,	M,	{451,469,469,474,471,538},	M,	{451,469,469,474,472,365,294},	M,	{428,463,461,471,469,451},	{475,478,478,465,461,471,469,451},	{383,372,370,379,420,415,476,478,465,461,471,469,451},	M},
				{M,	M,	M,	{428,463,461,471,469,474,472,365,294},	M,	{428,463,461,471,469,451},	M,	{472,478,478,465,428,463},	{383,372,370,379,420,415,476,478,465,428,463},	M},
				{M,	M,	M,	{475,478,478,465,461,471,469,474,472,365,294},	M,	{475,478,478,465,461,471,469,451},	{472,478,478,465,428,463},	M,	{383,372,370,379,420,415,476,478,475},	{390,354,355,370,370,379,420,415,476,478,475}},
				{M,	M,	M,	{383,372,370,379,420,415,476,478,465,461,471,469,474,472,365,294},	M,	{383,372,370,379,420,415,476,478,465,461,471,469,451},	{383,372,370,379,420,415,476,478,465,428,463},	{383,372,370,379,420,415,476,478,475},	M,	{390,354,355,370,372,383}},
				{M,	M,	M,	M,	M,	M,	M,	{390,354,355,370,370,379,420,415,476,478,475},	{390,354,355,370,372,383},	M}

		};

		//creation fenetre programme
		maFenetreCarte = new FenetreCarte(maListeBatiment, g0, matCoordX,matCoordY);

		boolean faireTrajetExemple=false;
		if(faireTrajetExemple==true){
			// creation d'une instance de Dijsktra avec le graphe g0 ayant comme point de départ le 6e bat de notre liste
			Dijkstra trajetToDo= new Dijkstra(5,g0, matCoordX, matCoordY);

			// Calcul du plus court chemin avec l'algorithme de Dijkstra
			trajetToDo.calculePlusCourtChemin();

			// Pour afficher le chemin le plus rapide pour aller au bat ayant comme rang 1
			int dest=1;

			trajetToDo.afficheChemin(dest);

			//affichage des détails concernant ce trajet
			int distance2 = trajetToDo.getLongueurChemin(dest);
			System.out.print("La Distance minimale pour y aller : ");
			trajetToDo.longueurChemin(dest);
			System.out.println();
			System.out.print("La Durée minimale pour y aller : " );
			trajetToDo.dureeChemin(dest);
			System.out.println();
			System.out.print("Données calorifiques : ");
			trajetToDo.calculCalories(dest);


		/*
		Des tests utils...
		int duree = trajetToDo.getLongueurChemin(5);
		System.out.println("temps mini :"+duree);
		int duree2 = trajetToDo.getLongueurChemin(7);
		System.out.println("distance mini pour aller de beaulieu au Stade est :"+duree2);
		*/

		}
	}
}