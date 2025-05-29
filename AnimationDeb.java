import java.awt.*;
import static java.awt.Font.ITALIC;
import javax.swing.*;
public class AnimationDeb extends JPanel{
    //condition d'animation
    private boolean anim=true;
    //variables qui vont servir à animer des lettres
    int a=40; int b=0; int c=400; int d=300; int e=160; int f=50; int g=400; int h=350;
    int i=55; int j=100; int k=415; int l=400; int m=55; int n=150; int o=415; int p=450;

    //methode paint pour dessiner des lettres et arriereplan
    public void paint(Graphics gp)
    {
        super.paint(gp);
        Graphics2D g2d= (Graphics2D) gp;

        ImageIcon iconImgScaledIcon=new ImageIcon(new ImageIcon("pictures/wallpaper.png").getImage().getScaledInstance(1100,600, Image.SCALE_SMOOTH));

        int x = 0;
        int y = 0;
        iconImgScaledIcon.paintIcon(this,g2d,x,y);

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 75));

        g2d.drawString("I", a, 150);
        g2d.drawString("N", 200, b);
        g2d.drawString("S", c, 150);
        g2d.drawString("A", 285, d);

        g2d.setColor(Color.BLACK);
        g2d.drawString("M", i, 250);
        g2d.drawString("A", 220, j);
        g2d.drawString("P", k, 250);
        g2d.drawString("S", 305, l);

        g2d.setColor(Color.DARK_GRAY);
        g2d.setFont(new Font("San Francisco", ITALIC, 30));
        g2d.drawString("by grp98 asinsa2A", 170, 650);

        try {

            //animation chaque 0,1s
            Thread.sleep(100);

            if(a<170 && c>250 && e<310 && b<170 &&d>150 && f<200 && g>250 && h>200 && i<300 && j<250 && k>265 && l>250 )
            {
            a+=5;b+=5;c-=5;d-=5;e+=5;f+=5;g-=5;h-=5;
            i+=5;j+=5;k-=5;l-=5;m+=5;n+=5;o-=5;p-=5;
            }
            else
            {
            a=190; b=150; c=245; d=150; e=310; f=200; g=250;
            h=200;i=165; j=250;k=265;l=250;m=205;n=300;o=265;p=300;
            repaint();
            //Thread.sleep(5000);
            //mettre fin a anim
            anim=false;
            this.setVisible(false);
            }
        } catch (Exception e) {
        }
        repaint();
        }
        public boolean getAnim(){
            return anim;
        }
}

