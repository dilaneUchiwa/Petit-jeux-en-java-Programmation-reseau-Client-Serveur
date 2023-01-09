package com.company.Evenement;

import com.company.Interface.BlocPrincipale;
import com.company.Interface.PanelChoisirServeur;
import com.company.reseau.Client;
import com.company.reseau.Serveur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class GereChoixNature implements ActionListener {
    JFrame fenetre;
    JPanel choixinteface;

    Object moi;

    boolean isServeur;


    public GereChoixNature(JFrame fenetre, JPanel choixinteface,boolean isServeur) {
        this.fenetre = fenetre;
        this.choixinteface = choixinteface;
        this.isServeur=isServeur;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fenetre.remove(choixinteface);
        if(isServeur) this.moi=new Serveur(23000);
        else {
            String nomServeur=new PanelChoisirServeur().getNom();
            if(nomServeur!=null){
                this.moi=new Client(23000,nomServeur);
            }}

        fenetre.add(new BlocPrincipale(moi));

        SwingUtilities.updateComponentTreeUI(fenetre);
    }
}
