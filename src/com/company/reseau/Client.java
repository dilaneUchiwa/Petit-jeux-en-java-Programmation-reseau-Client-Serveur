package com.company.reseau;

import com.company.Cellule;
import com.company.Interface.BlocGrilleImage;
import com.company.Interface.BlocSalon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    int port;
    String serveur;

    public static long idThreadInitialisation;

    public static long idThreadAttente;


    public static ArrayList<String> nomDesjoueurs=new ArrayList<>();
    BufferedReader entree;
    PrintStream sortie;
    Socket socket;

    public Client(int port, String serveur) {
        this.port = port;
        this.serveur = serveur;
        nomDesjoueurs.add(serveur);
        try {
            nomDesjoueurs.add(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.connexion();

    }
    public boolean connexion(){
        try {
            this.socket=new Socket(InetAddress.getByName(serveur),port);
            System.out.println("Connexion au serveur reussi");
            this.entree=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sortie=new PrintStream(socket.getOutputStream());

            while (true){
                String nom=recevoirNom();
                nomDesjoueurs.add(nom);
                BlocSalon.update(nom,this);
                if(nom.equals("EndbyDiablo"))break;
            }

            // Pour recuperer les noms des autres machines du reseau

            Client.nomDesjoueurs.remove("EndbyDiablo");
            Thread AttendreLesAutresJoueurs=new Thread(new Runnable() {
                @Override
                public void run() {
                    String nom=recevoirNom();
                    while (nom!=null){
                        if(nom=="arreted'enregistrerlesnomsdesjoueurs");{
                            for (Thread t:
                                    Thread.getAllStackTraces().keySet()) {
                                if(t.getId()==Client.idThreadAttente) t.stop();
                            }
                        }
                        BlocSalon.update(nom,this);
                        nom=recevoirNom();
                    }
                }
            });
            idThreadAttente=AttendreLesAutresJoueurs.getId();
            AttendreLesAutresJoueurs.start();

            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public void envoyerCellule(Cellule c){
        //if(this.socket.isClosed()) System.out.println("Vous n'etes pas dans un salon");

        this.sortie.println(c.ligne+"+"+c.colonne);
        //BlocGrilleImage.jeton=false;
    }
    public Cellule recevoirCellule() {
        Cellule c;
        String chaine;
        String[] mot;

        try {
            chaine = this.entree.readLine();
            mot = chaine.split("\\+");
            BlocGrilleImage.jeton=Boolean.parseBoolean(mot[2]);
            return new Cellule(Integer.parseInt(mot[0]), Integer.parseInt(mot[1]));

        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        }
//    }
        return null;
    }

    public Cellule recevoirCelluleInitiale() {
        Cellule c;
        String chaine;
        String[] mot;

        try {
            chaine = this.entree.readLine();
            if(chaine.equals("sagueuditqu'onpeutcommencer")) {

                chaine = this.entree.readLine();
                mot = chaine.split("\\+");
                BlocGrilleImage.jeton=Boolean.parseBoolean(mot[2]);
                return new Cellule(Integer.parseInt(mot[0]), Integer.parseInt(mot[1]));
            }
            return null;
        } catch (IOException e) {
            System.out.println("Erreur de lecture");
        }
//    }
        return null;
    }
    public String recevoirNom(){
        try {


            String nom=this.entree.readLine();
            nomDesjoueurs.add(nom);


            return nom;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deconnexion(){
        try {
            socket.close();
            sortie.close();
            entree.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
