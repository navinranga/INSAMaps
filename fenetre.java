
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class fenetre extends JFrame implements MouseListener {
    JFrame F;
    public final static int HT =1050;
    public final static int LG= 750;

    public fenetre() {
        JFrame F = new JFrame("INSA Maps");
        F.setSize(HT,LG);
        F.setVisible(true);
        F.addWindowListener(new gestionFenetre());
        ImageIcon icone = new ImageIcon("INSA_Maps2.png");
        JLabel image = new JLabel(icone);
        F.add(image);
        F.setVisible(true);
        F.setResizable(false);
        F.addMouseListener(this);

        Image icon = Toolkit.getDefaultToolkit().getImage("logo.png");    
        F.setIconImage(icon);  
        
    }

    public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}

	public void mouseClicked(MouseEvent e){
		System.out.println("Vous avez clique avec la souris");
		String s = "("+e.getX()+","+e.getY()+")";
		System.out.println(s);
	}
}

class gestionFenetre extends WindowAdapter{
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}




