
import javax.swing.*;
import java.awt.event.*;
public class Main {
    public final static int HT =1073;
    public final static int LG =762;
    public static void main(String[] args) {
        JFrame F = new JFrame("Une fenetre en Swing");
        F.setSize(HT,LG);
        F.setVisible(true);
        F.addWindowListener(new gestionFenetre());
        ImageIcon icone = new ImageIcon("INSA_Maps2.png");
        JLabel image = new JLabel(icone);
        F.add(image);
        F.setVisible(true);
        F.setResizable(false);
    }
}
class gestionFenetre extends WindowAdapter{
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}

