package com.company.Interface;
import com.company.Cellule;
import com.company.Evenement.GereClickButton;
import com.company.reseau.Client;
import com.company.reseau.Serveur;

import javax.swing.*;
import java.awt.*;

public class BlocGrilleImage extends JPanel{
    public static Cellule cellule;

    // le jeton donne la position de celui qui vaa transmettre le message
    public static boolean jeton=false;
    public static ImageIcon image=new ImageIcon("src\\com\\company\\image\\1.jpg");
    public static JButton[][] buttons;

    public BlocGrilleImage(int n_ligne, int n_colonne, Object moi){
        super();
        this.setLayout(new GridLayout(n_ligne,n_colonne));
        buttons = new JButton[n_ligne][n_colonne];

        for(int i=0;i<n_ligne;i++)
            for(int j=0;j<n_colonne;j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new GereClickButton(new Cellule(i,j),moi));
            }

        // je fixe l'image dans la cellule initiale connu de tous

        if(moi instanceof Serveur) {

            Thread recevoirServeur = new Thread(new Runnable() {
                @Override
                public void run() {
                    Cellule c = ((Serveur) moi).recevoirCellule();
                    while (c!=null) {
                        ((Serveur)moi).envoyerCelluletoEveryone(c);
                        c = ((Serveur) moi).recevoirCellule();
                    }
                }
            });
            recevoirServeur.start();
            Thread recevoirClient = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("valeur jeton :"+BlocGrilleImage.jeton);
                    Cellule c = ( Serveur.client).recevoirCellule();
                    while (c!=null) {
                        if(BlocGrilleImage.jeton){
                            BlocPrincipale.labelJeton.setText("C'est votre tour");
                            BlocPrincipale.labelJeton.setForeground(Color.blue);
                            SwingUtilities.updateComponentTreeUI(BlocPrincipale.traceDeMoi);
                        }
                        else{
                            BlocPrincipale.labelJeton.setText("Ce n'est votre pas tour");
                            BlocPrincipale.labelJeton.setForeground(Color.red);
                            SwingUtilities.updateComponentTreeUI(BlocPrincipale.traceDeMoi);
                        }
                        if (!c.equals(BlocGrilleImage.cellule)) {
                            BlocGrilleImage.buttons[BlocGrilleImage.cellule.ligne][BlocGrilleImage.cellule.colonne].setIcon(null);
                            BlocGrilleImage.buttons[c.ligne][c.colonne].setIcon(BlocGrilleImage.image);
                            BlocGrilleImage.cellule = c;
                        }
                        c = ( Serveur.client).recevoirCellule();
                    }
                }
            });
            recevoirClient.start();

        }
        else {

            // je lance le thread qui va constament verifier si une case a ete active chez les autres et mettre à jour sur l'ecran

            Thread recevoir = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("valeur jeton :"+BlocGrilleImage.jeton);
                    Cellule c = ((Client) moi).recevoirCellule();
                    while (c!=null) {
                        if(BlocGrilleImage.jeton){
                            BlocPrincipale.labelJeton.setText("C'est votre tour");
                            BlocPrincipale.labelJeton.setForeground(Color.blue);
                            SwingUtilities.updateComponentTreeUI(BlocPrincipale.traceDeMoi);
                        }
                        else{
                            BlocPrincipale.labelJeton.setText("Ce n'est pas votre tour");
                            BlocPrincipale.labelJeton.setForeground(Color.red);
                            SwingUtilities.updateComponentTreeUI(BlocPrincipale.traceDeMoi);
                        }
                        if (!c.equals(BlocGrilleImage.cellule)) {
                            BlocGrilleImage.buttons[BlocGrilleImage.cellule.ligne][BlocGrilleImage.cellule.colonne].setIcon(null);
                            BlocGrilleImage.buttons[c.ligne][c.colonne].setIcon(BlocGrilleImage.image);
                            BlocGrilleImage.cellule = c;
                        }
                        c = ((Client) moi).recevoirCellule();
                    }
                }
            });
            recevoir.start();
        }


        buttons[BlocGrilleImage.cellule.ligne][BlocGrilleImage.cellule.colonne].setIcon(image);

        for (int i=0;i<buttons.length;i++)
            for (int j=0;j<buttons[i].length;j++)
                this.add(buttons[i][j]);

        this.setPreferredSize(new Dimension(450,450));
        this.setBackground(Color.cyan);


    }
}
