package com.company.Interface;

import com.company.Cellule;
import com.company.Constante;
import com.company.reseau.Client;
import com.company.reseau.Partie;
import com.company.reseau.Serveur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlocPrincipale extends JPanel {
    Object moi;
    int n_ligne=8;
    int n_colonne=8;

    public static JPanel traceDeMoi;
    public static JLabel labelJeton;
    JPanel panelintermediaire1;


    public BlocPrincipale(Object moi){
        super();
        this.moi=moi;

        panelintermediaire1=new JPanel();
        JPanel panelintermediaire2=new JPanel(new FlowLayout(FlowLayout.CENTER,100,20));
        labelJeton=new JLabel();
        JButton btn_lancer=new JButton("Lancer la partie");
        JLabel labelWaiting=new JLabel("Attendez que le serveur lance la partie");

        labelJeton.setFont(Constante.styleJeton);


        panelintermediaire1.setLayout(new BoxLayout(panelintermediaire1,BoxLayout.X_AXIS));


        if(moi instanceof Serveur && !Serveur.salonFermer){
            //JLabel labelNbre


            labelJeton.setText("En attente des joueurs.....");
            labelJeton.setForeground(Color.red);


            btn_lancer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Thread t:
                         Thread.getAllStackTraces().keySet()) {
                        if(t.getId()==Serveur.idThreadAttente) t.stop();
                    }
                    Serveur.salonFermer=true;
                    lancerPartie(moi);
                }
            });

            btn_lancer.setPreferredSize(new Dimension(200,60));
            panelintermediaire1.setLayout(new FlowLayout(FlowLayout.CENTER,200,200));
            panelintermediaire1.setPreferredSize(new Dimension(450,450));
            panelintermediaire1.setBackground(Color.cyan);
            panelintermediaire1.add(btn_lancer,BorderLayout.CENTER);


        }else{
            labelJeton.setText("En attente des joueurs.....");
            labelJeton.setForeground(Color.red);

            panelintermediaire1.setLayout(new FlowLayout(FlowLayout.CENTER,200,200));
            panelintermediaire1.setPreferredSize(new Dimension(450,450));
            panelintermediaire1.setBackground(Color.cyan);
            panelintermediaire1.add(labelWaiting,BorderLayout.CENTER);

            //on recoit la cellule initiale
            initialisationCellule();

        }

        this.setLayout(new BorderLayout());


        panelintermediaire2.add(labelJeton);

        this.add(panelintermediaire2,BorderLayout.NORTH);
        this.add(new BlocSalon(moi),BorderLayout.WEST);
        this.add(panelintermediaire1,BorderLayout.EAST);

        // je conserve une trace de moi pour les mises à jour
        traceDeMoi=this;
    }
    private void lancerPartie(Object moi){
        if(moi instanceof Serveur) {
            //on genere la cellule initiale et on envoie aux autres joueurs du reseau puis on donne le jeton au client du serveur
            BlocGrilleImage.cellule = new Cellule((int) (Math.random() * n_ligne), (int) (Math.random() * n_colonne));
            BlocGrilleImage.jeton=true;
            labelJeton.setText("C'est votre tour");
            labelJeton.setForeground(Color.blue);


            ((Serveur) moi).envoyerCelluletoEveryoneInitiale(BlocGrilleImage.cellule);
        }else {
            labelJeton.setText("C'est n'est pas votre tour");
        }

        // on enleve les elements d'indication d'attente du lancement de la partie et on mets les bouttons
        remove(this.panelintermediaire1);
        this.panelintermediaire1=new JPanel();
        panelintermediaire1.add(new BlocGrilleImage(n_ligne,n_colonne,moi));

        this.add(this.panelintermediaire1);

        // on met à jour l'interface
        SwingUtilities.updateComponentTreeUI(this);
    }
    private void initialisationCellule(){

        //ceci est destiné au client qui va attendre que le serveur envoi la cellule initiale genere  et arrete d'attendre une fois que c'est fait
        
        Thread initialisation=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    BlocGrilleImage.cellule=((Client)moi).recevoirCelluleInitiale();
                    if(BlocGrilleImage.cellule!=null){
                        lancerPartie(moi);
                        for (Thread t:
                                Thread.getAllStackTraces().keySet()) {
                            if(t.getId()==Client.idThreadInitialisation) t.stop();
                        }
                    }
                }
            }
        });
        Client.idThreadInitialisation=initialisation.getId();
        initialisation.start();
    }
}
