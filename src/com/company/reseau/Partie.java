package com.company.reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Partie{

    ServerSocket serveur;
    ArrayList<Socket> joueurs=new ArrayList<>();
    ArrayList<BufferedReader> entrees=new ArrayList<>();
    ArrayList<PrintStream> sorties=new ArrayList<>();

    public Partie(ServerSocket serveur) {
        this.serveur = serveur;
    }

    public Partie(ServerSocket serveur, ArrayList<Socket> joueurs, ArrayList<BufferedReader> entrees, ArrayList<PrintStream> sorties) {
        this.serveur = serveur;
        this.joueurs = joueurs;
        this.entrees = entrees;
        this.sorties = sorties;
    }

    public boolean addClient(Socket socket,BufferedReader entree,PrintStream sortie){
        return this.joueurs.add(socket)&&this.entrees.add(entree)&&this.sorties.add(sortie);
    }

    public ServerSocket getServeur() {
        return serveur;
    }

    public ArrayList<Socket> getJoueurs() {
        return joueurs;
    }
}
