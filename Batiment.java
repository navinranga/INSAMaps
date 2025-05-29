public class Batiment{
    private String nomBat;
    //coordonnee X et Y du bat
    private int coordX;
    private int coordY;

    //constructeur du bat
    public Batiment(String nomBatAffect, int coordXAffect, int coordYAffect ){
        this.nomBat=nomBatAffect;
        this.coordX=coordXAffect;
        this.coordY=coordYAffect;
    }

    public Batiment() {
    }

    //getter batiment nom
    public String getNomBat(){
        return this.nomBat;
    }

    //afficher details du batiment
    public String toString(){
        String res ="Bâtiment "+this.nomBat+" situé au ("+this.coordX+";"+this.coordY+")";
        return res;
    }

}