
public class Graphe {

	private int [][] U;// Declaration de variable
	public String[] nomDest; //tab nom des bat
	private boolean [] existence; // existence de sommet
	public final static int csteParDefaut = -999 ; // constante
	
	
	 // Contructeur de graphe	
	public Graphe () {
		int NMax = 1000;
		U = new int [NMax][NMax];
		existence = new boolean [NMax];
		for (int i=0 ; i<U.length ; i++){
			existence[i] = false;
			for (int j=0 ; j<U[i].length ; j++){
				U[i][j] = csteParDefaut;
			}
		}
	}
	// constructeur avec une matrice adjacence:
	public Graphe (int [][] mat, String[] nomDestAffect){
		this.nomDest=nomDestAffect;
		int NMax = mat.length;
		//int NMax = 1000 + mat.length;
		U = new int [NMax][NMax];
		existence= new boolean [NMax];
		for (int i = 0 ; i<U.length ; i++){
			if (i < mat.length){
				existence [i] = true;
			}
			else {
				existence[i] = false;
			}
			for (int j = 0 ; j<U.length ; j++){
				if ((j<mat.length) && (i<mat.length)){
					U[i][j] = mat [i][j];
				}
				else {
					U[i][j] = csteParDefaut;
				}
			}
		}
	}
	
	//existence de sommet?
	public boolean existeSommet(int i){
		boolean res = false;
		if (!((i > existence.length) || (i<0))){
			res = existence[i];
		}
		return res;
	}
	
	//combien de sommet?
	public int nbSommet(){
		int nb = 0;
		for (int i = 0 ; i<U.length ; i++){
			if (existence[i]){ nb ++;}
		}
		return nb;
	}
	
	public boolean existeLien(int i, int j){
		return ((existeSommet(i)) && (existeSommet(j)) && (U[i][j] != csteParDefaut));
	}
	
	//recup dist permettant de ponderer notre graphe
	public int getValArrete(int i, int j){
		if ((i<0) || (i >= U.length) || (j<0) || (j >= U.length)){
			System.out.println("Graphe::getValArrete : Erreur: hors de la matrice.");
			System.exit(-1);
		}
		if (!existeLien( i, j)){
			System.out.println("Graphe::getValArrete : Erreur: aucun arc.");
			System.exit(-1);
		}
		return U[i][j];
	}
	
	
	
	//ajouter un sommet/"bat"
	public boolean ajoutSommet(int i){
		if ((i<0) || (i >= U.length)){
			System.out.println("Graphe::degre : Erreur: hors de la matrice.");
			System.exit(-1);
		}
		if (existeSommet(i)){
			return false;
		}
		existence[i]=true;
		for (int k=0 ; k<U.length ; k++){
			U[i][k] = csteParDefaut;
			U[k][i] = csteParDefaut;
		}
		return true;
	}
	
	//supprimer un sommet/"bat"
	 public boolean supprimeSommet(int i){
         if((i<0)||(i>=U.length))
         {
         System.out.println(" i n'appartient pas à [0;U.length]");
         System.exit(-1);
         }
                 if(!existeSommet(i))
                 {
                    return false;
                 }
                 else {
					 if (degre(i) != 0) {
						 System.out.println("le sommet " + i + " est encore connecté");
						 System.exit(-1);
					 }

					 existence[i] = false;
				 }
          return true;
	 }
	
	//ajouter arrete
	public boolean ajoutArrete(int i, int j, int val){
		if (!(existeSommet(i) && (existeSommet(j)))){
			return false;
		}
		if (existeLien(i,j)){
			return false;
		}
		U[i][j]=val;
		return true;
	}
	
	//supprimer arrete
	public boolean supprimerArrete(int i,int j){
		if (!(existeSommet(i) && (existeSommet(j)))){
			return false;
		}
		U[i][j]=csteParDefaut;
		return true;
	}
	
	public int degreEntrant(int i){
		int x = 0;
		if ((i<0) || (i >= U.length)){
			System.out.println("Graphe::degreEntrant : Erreur: hors de la matrice.");
			System.exit(-1);
		}
		if (!existeSommet(i)){
			System.out.println("Graphe::degreEntrant : Erreur: ce sommet n'existe pas.");
			System.exit(-1);
		}
		for (int k=0 ; k<U.length ; k++){
			if (U[i][k] != csteParDefaut){
				x++;
			}
		}
		return x;
	}
	
	public int degreSortant(int i){
		int x = 0;
		if ((i<0) || (i >= U.length)){
			System.out.println("Graphe::degreSortant : Erreur: hors de la matrice.");
			System.exit(-1);
		}
		if (!existeSommet(i)){
			System.out.println("Graphe::degreSortant : Erreur: ce sommet n'existe pas.");
			System.exit(-1);
		}
		for (int k=0 ; k<U.length ; k++){
			if (U[k][i] != csteParDefaut){
				x++;
			}
		}
		return x;
	}
	
	public int degre(int i){
		if ((i<0) || (i >= U.length)){
			System.out.println("Graphe::degre : Erreur: hors de la matrice.");
			System.exit(-1);
		}
		if (!existeSommet(i)){
			System.out.println("Graphe::degre : Erreur: ce sommet n'existe pas.");
			System.exit(-1);
		}
		return (degreEntrant(i)+degreSortant(i));
	}

	//liste des successeurs
	public int [] lst_succ(int i){
		if ((i<0) || (i >= U.length)){
			System.out.println("Graphe::lst_succ : Erreur: hors de la matrice.");
			System.exit(-1);
		}
		if (!existeSommet(i)){
			System.out.println("Graphe::lst_succ : Erreur: ce sommet n'existe pas.");
			System.exit(-1);
		}
		int[] tab = new int[nbSommet()];
		int y=0;
		while (y < nbSommet()){
			for (int j=0; j<U[i].length;j++)//oublié dans le code
				if (U[i][j]!=csteParDefaut){
					tab[y]=U[i][j];
					y++;
				}
		}
		return tab;	//oublié dans le code		
	}

	//afficher graphe
	public String toString(){
		int x;
		int i=0;
		int j=0;
		x = nbSommet();
		String chaine="";
		while(j<x){
			chaine += "[";
			if (existence[j]){
				while (i<x){
					if (existence[i]){
						chaine += U[i][j];
					}
					i++;	
				}
				System.out.print("]");
			}
			j++;
			System.out.println();
		}
		return chaine;
	}

	//getter et setter de U
	public int[][] getU() {
		return U;
	}

	public void setU(int[][] u) {
		U = u;
	}

	//getter et setter existence
	public boolean[] getexistence() {
		return existence;
	}

	public void setexistence(boolean[] existence) {
		this.existence = existence;
	}
}
