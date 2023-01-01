package com.company;
public class Cellule {

    public int ligne;
    public int colonne;

    public Cellule(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }
    public boolean equals(Cellule c){
        if(this.ligne==c.ligne&&this.colonne==c.colonne)return true;
        return false;
    }
    public String toString(){
        return "("+this.ligne+","+this.colonne+")";
    }
}
