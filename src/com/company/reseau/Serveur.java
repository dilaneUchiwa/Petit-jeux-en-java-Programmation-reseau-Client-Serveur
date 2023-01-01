package com.company.reseau;

import com.company.Cellule;
import com.company.Interface.BlocGrilleImage;
import com.company.Interface.BlocSalon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {
    int portEcoute;


    public static Partie salon;

    public static int jetons=0;

    public static boolean salonFermer=false;
    ServerSocket standardiste;
    Socket socket;
    BufferedReader entree;
    PrintStream sortie;

    public static long idThreadAttente;

    public Serveur(int portEcoute) {
        this.portEcoute = portEcoute;
        try {
            this.standardiste = new ServerSocket(portEcoute);
            salon=new Partie(this.standardiste);

            System.out.println("En attente d'un client....");
            AttendreLesClient(this);
         // System.out.println("Le show peut commencer...");

        } catch (IOException e) {
            System.out.println("probleme de connexion");
        }
    }
    private static void AttendreLesClient(Serveur serveur){

        Thread AttendreClient=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (true){
                        serveur.socket = serveur.standardiste.accept();
                        serveur.entree = new BufferedReader(new InputStreamReader(serveur.socket.getInputStream()));
                        serveur.sortie = new PrintStream(serveur.socket.getOutputStream());

                        BlocSalon.update(serveur.socket.getInetAddress().getCanonicalHostName(),this);


                        serveur.salon.getJoueurs().forEach((joueur)->{
                            serveur.envoyerNom(joueur);
                        });
                        serveur.sortie.println("EndbyDiablo");
                        serveur.envoyerNomToEveryone(serveur.socket);

                        salon.addClient(serveur.socket,serveur.entree,serveur.sortie);

                    }
                }catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
      AttendreClient.start();
      Serveur.idThreadAttente=AttendreClient.getId();

    }

    public static void fermerlesalon(){

    }
    public void envoyerNom(Socket socket){
           this.sortie.println(socket.getInetAddress().getHostName());
    }
    public void envoyerNomToEveryone(Socket socket){
        salon.sorties.forEach((sortie)->{
            sortie.println(socket.getInetAddress().getHostName());
        });
    }

    public void envoyerCellule(Cellule c) {

        //this.sortie.println(c.ligne + "+" + c.colonne+"+"+GrilleImage.jeton);
        //GrilleImage.jeton=false;
        this.sortie.println(c.ligne + "+" + c.colonne);
    }
    public void envoyerCelluletoEveryone(Cellule c) {

        if (BlocGrilleImage.jeton || Serveur.jetons==salon.getJoueurs().toArray().length) {
            BlocGrilleImage.jeton=false;
            Serveur.jetons=0;
        };
        salon.sorties.forEach((element)->{
            if(element.equals(salon.sorties.get(Serveur.jetons)))element.println(c.ligne + "+" + c.colonne+"+"+true);
            else element.println(c.ligne + "+" + c.colonne+"+"+false);

        });
    }
    public void envoyerCelluletoEveryoneInitiale(Cellule c) {

        salon.sorties.forEach((element)->{
            //element.println("arreted'enregistrerlesnomsdesjoueurs");
            element.println("sagueuditqu'onpeutcommencer");
            element.println(c.ligne + "+" + c.colonne+"+"+false);
        });
        BlocGrilleImage.jeton=true;
        //Serveur.jetons=salon.getJoueurs().toArray().length;
    }

    public Cellule recevoirCellule() {
        Cellule c;
        String chaine;
        String[] mot;

        try {

            System.out.println("valeur de jeton :"+Serveur.jetons);
            if (Serveur.jetons==Serveur.salon.getJoueurs().toArray().length){
                BlocGrilleImage.jeton=true;
            }else {
                this.entree=salon.entrees.get(Serveur.jetons);
                Serveur.jetons++;
            }

            chaine = this.entree.readLine();
            mot = chaine.split("\\+");
            return new Cellule(Integer.parseInt(mot[0]), Integer.parseInt(mot[1]));

        } catch (IOException e) {
            System.out.println("Erreur de lecture");
            return null;
        }
//    }
    }

}
