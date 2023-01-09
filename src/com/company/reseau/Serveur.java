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

    public static Client client;
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
            System.out.println("En attente des client....");
            Serveur.client=new Client(23000,"localhost");
            AttendreLesClient(this);

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

//                        BlocSalon.update(serveur.socket.getInetAddress().getHostName(),this);
//
//                        serveur.salon.getJoueurs().forEach((joueur)->{
//                            serveur.envoyerNom(joueur);
//                        });
//                        serveur.sortie.println("EndbyDiablo");
//                        serveur.envoyerNomToEveryone(serveur.socket);

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


        salon.sorties.forEach((element)->{
            if(element.equals(salon.sorties.get(Serveur.jetons)))element.println(c.ligne + "+" + c.colonne+"+"+true);
            else element.println(c.ligne + "+" + c.colonne+"+"+false);

        });
    }
    public void envoyerCelluletoEveryoneInitiale(Cellule c) {


        salon.sorties.forEach((element)->{
            //element.println("arreted'enregistrerlesnomsdesjoueurs");
            if(element!=salon.sorties.get(0)) {
                element.println("sagueuditqu'onpeutcommencer");
                element.println(c.ligne + "+" + c.colonne + "+" + false);
            }
        });
    }

    public Cellule recevoirCellule() {
        Cellule c;
        String chaine;
        String[] mot;

        try {

            System.out.println("valeur de jeton :"+Serveur.jetons);

            this.entree=salon.entrees.get(Serveur.jetons);
            Serveur.jetons++;
            if(jetons==salon.getJoueurs().toArray().length){
                Serveur.jetons=0;
            }

            chaine = this.entree.readLine();
            mot = chaine.split("\\+");
            return new Cellule(Integer.parseInt(mot[0]), Integer.parseInt(mot[1]));

        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        }
//    }
        return null;
    }

}
