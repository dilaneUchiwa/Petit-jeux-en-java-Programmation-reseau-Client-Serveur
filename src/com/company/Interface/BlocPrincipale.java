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
    int n_ligne=10;
    int n_colonne=10;

    public static JPanel traceDeMoi;
    public static JLabel labelJeton;
    JPanel panelintermediaire1;


    public BlocPrincipale(Object moi){
        super();
        this.moi=moi;
//        JMenuBar menuBar=new JMenuBar();
//        this.add(menuBar);
//
//        JMenuItem menuPartie=new JMenuItem("Partie");
//        menuBar.add(menuPartie);
//        JMenuItem menuApropos=new JMenuItem("A propos ?");
//        menuBar.add(menuApropos);


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
            //on genere la cellule initiale et on envoie à tous le monde
            BlocGrilleImage.cellule = new Cellule((int) (Math.random() * n_ligne), (int) (Math.random() * n_colonne));
            ((Serveur) moi).envoyerCelluletoEveryoneInitiale(BlocGrilleImage.cellule);
        }

        labelJeton.setText("C'est n'est pas encore votre tour");
        remove(this.panelintermediaire1);
        this.panelintermediaire1=new JPanel();
        panelintermediaire1.add(new BlocGrilleImage(n_ligne,n_colonne,moi));
        this.add(this.panelintermediaire1);
        SwingUtilities.updateComponentTreeUI(this);
    }
    private void initialisationCellule(){
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
