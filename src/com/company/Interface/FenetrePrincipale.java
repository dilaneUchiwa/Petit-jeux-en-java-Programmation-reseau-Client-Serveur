package com.company.Interface;

import com.company.Evenement.GereChoixNature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

public class FenetrePrincipale extends JFrame {
    public FenetrePrincipale(){
        super("Pétit jeu java en réseau".toUpperCase(Locale.ROOT));
        this.setIconImage(new ImageIcon("src\\com\\company\\image\\2.jpg").getImage());
        PanelChoisirNature choisirNature=new PanelChoisirNature();
        this.setSize(615,600);
        this.add(choisirNature, BorderLayout.CENTER);


        choisirNature.getBtn_client().addActionListener(new GereChoixNature(this,choisirNature,false));
        choisirNature.getBtn_serveur().addActionListener(new GereChoixNature(this,choisirNature,true));


        //this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

    }
}
