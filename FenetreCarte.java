import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.Desktop;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import static java.awt.Font.ITALIC;


public class FenetreCarte extends JFrame implements ActionListener {

  boolean animationFrame=true; //condition d'animation
  ArrayList<Batiment> mesBatiments; //liste des bat
  JButton buttonDB; //cliquer ce bouton permet d'ouvrir la BD de toutes les salles
  JButton labelResultat; //cliquer pour afficher resultat du trajet
  int[][][] coordX; //coord X des points de votre chemin
  int[][][] coordY; //coord Y des points de votre chemin
  Graphe g; //graphe
  Dijkstra initDijkstra; //instance dijsktra

  FenetreGraphique maFenetreGraphique;
  JButton labelResultatCalories; //resultat calories
  JButton labelResultatDuration; //resultat duree
  JButton labelResultatDistance; //resultat distance
  int comboNumBoxArrivee; //indice de destination

  FenetreCarte( ArrayList<Batiment> maListeBatiment, Graphe gAffect, int[][][] coordXAffect, int[][][] coordYAffect) {
    JFrame frame = new JFrame();

    this.mesBatiments=maListeBatiment;
    this.coordX=coordXAffect;
    this.coordY=coordYAffect;
    this.g=gAffect;

    frame.setSize(1100, 725);
    JPanel choixParcours=new JPanel();
    choixParcours.setLayout(null);
    choixParcours.setBounds(500,510,550,170);
    choixParcours.setBackground(Color.BLACK);
    frame.add(choixParcours);

    JLabel labelDepart = new JLabel("Départ");
    labelDepart.setFont(new Font("San Francisco", Font.BOLD, 14));
    labelDepart.setBounds(50,10,75,30);
    labelDepart.setForeground(Color.white);
    choixParcours.add(labelDepart);

    String[] name = new String[mesBatiments.size()+1];
    name[0]="Choisissez!";
    for(int i=1; i<name.length;i++){
      name[i]=mesBatiments.get(i-1).getNomBat();
    }

    JComboBox comboBoxDepart=new JComboBox(name);
    comboBoxDepart.setFont(new Font("San Francisco",Font.ITALIC, 14));
    comboBoxDepart.setBounds(120,10,150,30);
    choixParcours.add(comboBoxDepart,BorderLayout.NORTH);

    //comboBox de notre départ
    comboBoxDepart.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent arg0) {
        if(arg0.getStateChange()==ItemEvent.SELECTED){
          if(!comboBoxDepart.getSelectedItem().toString().equals("Choose")){
            JOptionPane.showMessageDialog(null,comboBoxDepart.getSelectedItem().toString()+" est votre point de Depart !");
            initDijkstra = new Dijkstra(comboBoxDepart.getSelectedIndex()-1,g,coordX,coordY);
            initDijkstra.calculePlusCourtChemin();
          }
        }
      }
    });
    //comboBox de notre arrivée
    JComboBox comboBoxArrivee=new JComboBox(name);
    comboBoxArrivee.setFont(new Font("San Francisco",Font.ITALIC, 14));
    comboBoxArrivee.setBounds(285,10,150,30);
    choixParcours.add(comboBoxArrivee);
    //BorderLayout.SOUTH
    comboBoxArrivee.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent arg0) {
        if(arg0.getStateChange()==ItemEvent.SELECTED){
          if(!comboBoxArrivee.getSelectedItem().toString().equals("Choose")){
            JOptionPane.showMessageDialog(null,comboBoxArrivee.getSelectedItem().toString()+" sera votre Destination !");
            comboNumBoxArrivee=comboBoxArrivee.getSelectedIndex()-1;
          }
        }
      }
    });

    JLabel labelArrivee = new JLabel("Arrivée");
    labelArrivee.setFont(new Font("San Francisco", Font.BOLD, 14));
    labelArrivee.setBounds(465,10,75,30);
    labelArrivee.setForeground(Color.white);
    choixParcours.add(labelArrivee);

    labelResultat = new JButton("LET'S GO...");
    labelResultat.addActionListener(this);
    labelResultat.setFont(new Font("San Francisco", Font.BOLD, 14));
    labelResultat.setBounds(32,50,500,40);
    labelResultat.setForeground(Color.green);
    labelResultat.setBackground(Color.darkGray);
    choixParcours.add(labelResultat);

    JLabel labelDistance=new JLabel();
    labelDistance.setBackground(Color.gray);
    labelDistance.setBounds(32,95,50,50);
    ImageIcon iconDistance = new ImageIcon("pictures/distance.png");

    Image imgDistance=iconDistance.getImage();
    Image imgDistanceScale = imgDistance.getScaledInstance(labelDistance.getWidth(),labelDistance.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon imgDistanceScaledIcon =new ImageIcon(imgDistanceScale);
    labelDistance.setIcon(imgDistanceScaledIcon);
    choixParcours.add(labelDistance);

    JLabel labelDuration=new JLabel();
    labelDuration.setBackground(Color.gray);
    labelDuration.setBounds(180,95,50,50);
    ImageIcon iconDuration = new ImageIcon("pictures/chronometer.png");

    Image imgDuration=iconDuration.getImage();
    Image imgDurationScale = imgDuration.getScaledInstance(labelDuration.getWidth(),labelDuration.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon imgDurationScaledIcon =new ImageIcon(imgDurationScale);
    labelDuration.setIcon(imgDurationScaledIcon);
    choixParcours.add(labelDuration);


    JLabel labelCalories=new JLabel();
    labelCalories.setBackground(Color.gray);
    labelCalories.setBounds(330,95,50,50);
    ImageIcon iconCalories = new ImageIcon("pictures/calories.png");

    Image imgCalories=iconCalories.getImage();
    Image imgCaloriesScale = imgCalories.getScaledInstance(labelCalories.getWidth(),labelCalories.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon imgCaloriesScaledIcon =new ImageIcon(imgCaloriesScale);
    labelCalories.setIcon(imgCaloriesScaledIcon);
    choixParcours.add(labelCalories);

    buttonDB=new JButton();
    buttonDB.setBackground(Color.black);
    buttonDB.setBounds(480,95,50,50);
    ImageIcon iconDB = new ImageIcon("pictures/database.png");


    Image imgDB=iconDB.getImage();
    Image imgDBScale = imgDB.getScaledInstance(buttonDB.getWidth(),buttonDB.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon imgDBScaledIcon =new ImageIcon(imgDBScale);
    buttonDB.setIcon(imgDBScaledIcon);
    choixParcours.add(buttonDB);
    buttonDB.addActionListener(this);

    labelResultatDistance = new JButton("Distance");
    labelResultatDistance.setFont(new Font("San Francisco", Font.BOLD, 12));
    labelResultatDistance.setBounds(85,95,90,50);
    labelResultatDistance.setForeground(Color.CYAN);
    labelResultatDistance.setBackground(Color.darkGray);
    labelResultatDistance.setHorizontalTextPosition(SwingConstants.LEFT);
    choixParcours.add(labelResultatDistance);

    labelResultatDuration = new JButton("Durée");
    labelResultatDuration.setFont(new Font("San Francisco", Font.BOLD, 12));
    labelResultatDuration.setBounds(235,95,95,50);
    labelResultatDuration.setForeground(Color.CYAN);
    labelResultatDuration.setBackground(Color.darkGray);
    choixParcours.add(labelResultatDuration);

    labelResultatCalories = new JButton("Calories");
    labelResultatCalories.setFont(new Font("San Francisco", Font.BOLD, 12));
    labelResultatCalories.setBounds(385,95,90,50);
    labelResultatCalories.setForeground(Color.CYAN);
    labelResultatCalories.setBackground(Color.darkGray);
    choixParcours.add(labelResultatCalories);

    maFenetreGraphique=new FenetreGraphique();
    frame.add(maFenetreGraphique);
    //maFenetreGraphique.dessinerChemin();
    ImageIcon img = new ImageIcon("pictures/insa_logo.png");
    frame.setIconImage(img.getImage());
    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource()==buttonDB){
      try {
        Desktop.getDesktop().open(new File("autres/catalogue.xlsm"));
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }
    if(e.getSource()==labelResultat){
      labelResultatDistance.setText(initDijkstra.longueurCheminString(comboNumBoxArrivee));
      labelResultatDuration.setText(initDijkstra.dureeCheminString(comboNumBoxArrivee));
      labelResultatCalories.setText(initDijkstra.calculCaloriesString(comboNumBoxArrivee));
      initDijkstra.afficheChemin(comboNumBoxArrivee);
      maFenetreGraphique.cheminDessiner(initDijkstra.coordXaRenvoyer,initDijkstra.coordYaRenvoyer);

    }

  }


}

