import javax.swing.*;
import java.awt.*;

class FenetreGraphique extends JComponent {

    int[] cheminX=null;
    int[] cheminY=null;
    boolean jeDessine=false;

    //dessiner le chemin en parametres composé des coordX et coordY
    public void cheminDessiner(int[] coordX,int[] coordY){
        this.cheminX=coordX;
        this.cheminY=coordY;
        jeDessine=true;
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        //arriere plan de carte
        ImageIcon icon = new ImageIcon("pictures/INSA_Maps.png");
        int x = 0;
        int y = 0;
        icon.paintIcon(this, g, x, y);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(7));
        g2.setColor(Color.red);
        //dessiner le chemin voulu
        if(jeDessine)  {
            g2.drawPolyline(cheminX, cheminY, cheminX.length);
            //System.out.println("jeDessine vrai");
        }
    }

}