package com.company.Interface;

import javax.swing.*;

public class PanelChoisirServeur extends JOptionPane {
    String nom;

    public PanelChoisirServeur(){
        super();
        this.nom=showInputDialog(null,"Entrez le nom ou l'adressse ip du serveur","ADRESSE SERVEUR",JOptionPane.OK_CANCEL_OPTION);
    }
    public String getNom(){
        return this.nom;
    }
}
