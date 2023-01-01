package com.company.Interface;

import com.company.Constante;
import com.company.reseau.Client;
import com.company.reseau.Serveur;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BlocSalon extends JPanel {
    private Object moi;

    public static JPanel panelDeBase;
    public static JPanel panelListeJoueur;

    public static JLabel labelNbreParticipant;

    BlocSalon(Object moi){
        super();
        this.moi=moi;
        JPanel panelIntermediaire=new JPanel();
        int nbreParticipant=0;

        panelIntermediaire.setLayout(new BoxLayout(panelIntermediaire,BoxLayout.Y_AXIS));
        panelIntermediaire.setAutoscrolls(true);
        panelIntermediaire.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titreBloc=new JLabel(" participants ".toUpperCase());
        titreBloc.setFont(Constante.styleTitreParticipant);
        titreBloc.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        titreBloc.setOpaque(true);
        titreBloc.setToolTipText("Liste de joueurs participants à la partie");

        this.setLayout(new BorderLayout());
        this.add(panelIntermediaire,BorderLayout.NORTH);


        panelIntermediaire.add(titreBloc);

        // variable pour lancement dans les threads

        BlocSalon.panelListeJoueur=panelIntermediaire;
        BlocSalon.panelDeBase=this;


        if(moi instanceof Serveur){

            JLabel nomServeur= null;
            try {
                nomServeur = new JLabel("  ---"+InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            nomServeur.setFont(Constante.styleParticipant);
           nomServeur.setForeground(Color.red);
            panelIntermediaire.add(nomServeur);
           Serveur.salon.getJoueurs().forEach((joueur)->{
               JLabel nomJoueur=new JLabel("  "+joueur.getInetAddress().getHostName());
               nomJoueur.setFont(Constante.styleParticipant);
               nomJoueur.setForeground(Color.green);
               panelIntermediaire.add(nomJoueur);
           });

           nbreParticipant=Serveur.salon.getJoueurs().toArray().length+1;


        }else{
            JLabel nomServeur=new JLabel("  "+Client.nomDesjoueurs.get(0));
            JLabel nomJoueur;

            nomServeur.setFont(Constante.styleParticipant);
            nomServeur.setForeground(Color.red);
            try {
                nomJoueur=new JLabel("  ---"+InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            nomJoueur.setFont(Constante.styleParticipant);
            nomJoueur.setForeground(Color.green);
            panelIntermediaire.add(nomJoueur);
            panelIntermediaire.add(nomServeur);

            nbreParticipant=Client.nomDesjoueurs.toArray().length;

        }
        BlocSalon.labelNbreParticipant=new JLabel("Nombre de participant :"+nbreParticipant);
        this.add(BlocSalon.labelNbreParticipant,BorderLayout.SOUTH);
        }
    public static void update(String nom,Object moi){
        int nbreParticipant;

        JLabel nomJoueur=new JLabel("  "+nom);
        nomJoueur.setFont(Constante.styleParticipant);
        nomJoueur.setForeground(Color.green);
        BlocSalon.panelListeJoueur.add(nomJoueur);

        if(moi instanceof Serveur){
            nbreParticipant=Serveur.salon.getJoueurs().toArray().length+1;
        }
        else{
            nbreParticipant=Client.nomDesjoueurs.toArray().length;
        }
        BlocSalon.labelNbreParticipant=null;
        BlocSalon.labelNbreParticipant=new JLabel("Nombre de participant :"+nbreParticipant);
        // Pour mettre a jour donc actualiser l'ecran
        SwingUtilities.updateComponentTreeUI(BlocPrincipale.traceDeMoi);
    }
}
