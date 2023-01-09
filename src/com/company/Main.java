package com.company;
import com.company.Evenement.GereClickButton;
import com.company.Interface.BlocGrilleImage;
import com.company.Interface.FenetrePrincipale;
import com.company.Interface.PanelChoisirNature;
import com.company.reseau.Client;
import com.company.reseau.Serveur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Scanner;
import java.net.InetAddress;


public class Main {
    public static void main(String[] args) throws UnknownHostException {
//        Scanner sc=new Scanner(System.in);
//        JPanel panelprincipale=new JPanel();
//        JPanel panel_gridlayout=new JPanel();
//        JPanel panel_accesoire=new JPanel();
//
//        JLabel label=new JLabel("I am god of war");
//        Object moi = null;
//
//        JFrame fenetre=new JFrame();
//        Dimension dimEcran=fenetre.getToolkit().getScreenSize();
//
//        int cpt=0;
//
//        System.out.println("Nom  :"+InetAddress.getLocalHost().getHostName());
//        System.out.println("Client ou serveur \n1. serveur\n2. client");
//        int choix=sc.nextInt();
//
//        if(choix==1){
//            fenetre.setTitle("Petit Jeux JAVA: SERVEUR");
//            JButton btn_lancer= new JButton("lancer la partie apres le dernier client");
//            btn_lancer.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Serveur.salonFermer=true;
//                    fenetre.remove(btn_lancer);
//                }
//            });
//            //fenetre.setVisible(true);
//            fenetre.setSize(300,300);
//            fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            fenetre.show();
//            fenetre.add(btn_lancer,BorderLayout.CENTER);
//            moi=new Serveur(23000);
//        }
//        else{
//            fenetre.setTitle("Petit Jeux JAVA: CLIENT");
//            moi=new Client(23000,"SAGUEU");
//        }
//        BlocGrilleImage blocGrilleImage =new BlocGrilleImage(4,4,moi);
//
//        //fenetre.getContentPane().add(panelprincipale);
//        //panelprincipale.add(panel_accesoire);
//        //panelprincipale.add(panel_gridlayout);
//        //panel_gridlayout.setLayout(grilleImage.grille);
//        //panel_accesoire.add(label);
//        fenetre.setLayout(blocGrilleImage.grille);
//        for(int i = 0; i< blocGrilleImage.grille.getRows(); i++)
//            for(int j = 0; j< blocGrilleImage.grille.getColumns(); j++)
//                fenetre.add(BlocGrilleImage.buttons[i][j]);
//        fenetre.setVisible(true);
//        //fenetre.setSize(dimEcran.width,dimEcran.height);
//        fenetre.setSize(300,300);
//        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        //fenetre.pack();
//        fenetre.show();

         new FenetrePrincipale();

//        JFrame fenetre=new JFrame();
//        JPanel panel=new JPanel();
//
//        JButton[][] buttons;
//
//        buttons = new JButton[4][4];
//        for(int i=0;i<4;i++)
//            for(int j=0;j<4;j++) {
//                buttons[i][j] = new JButton();
//            }
//        for (int i=0;i<buttons.length;i++)
//            for (int j=0;j<buttons[i].length;j++)
//                panel.add(buttons[i][j]);
//        panel.setLayout(new GridLayout(2,2));
//
//        fenetre.add(panel);
//        fenetre.setSize(800,400);
//
//        //fenetre.pack();
//        fenetre.setVisible(true);
//

    }
}