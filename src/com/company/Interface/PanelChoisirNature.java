package com.company.Interface;

import com.company.Constante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelChoisirNature extends JPanel{
    private final JButton btn_serveur;
    private final JButton btn_client;

    PanelChoisirNature() {
        super();
        JPanel panelIntermediaire1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 60));
        JPanel panelIntermediaire2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JPanel panelIntermediaire3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        JPanel panelIntermediaire4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        JPanel panelIntermediaire5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        JPanel panelIntermediaire6 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));

        JLabel label0 = new JLabel("Programmation Java Client-Serveur");
        JLabel label1 = new JLabel("voulez-vous ?");
        JLabel label2 = new JLabel("ou");
        JLabel label3 = new JLabel("?");


        this.btn_client = new JButton("Rejoindre une partie");
        this.btn_serveur = new JButton("Créer une partie");

        // on tue le css sur les textes

        Color couleur = Color.gray;

        label0.setFont(Constante.styleGeneral);
        label0.setForeground(Color.blue);
        label1.setFont(Constante.styleGeneral);
        label1.setForeground(couleur);
        label2.setFont(Constante.styleGeneral);
        label2.setForeground(couleur);
        label3.setFont(Constante.styleGeneral);
        label3.setForeground(couleur);

        // on tue le css sur les boutons

        btn_client.setBorder(BorderFactory.createLineBorder(Color.green, 3));
        btn_serveur.setBorder(BorderFactory.createLineBorder(Color.red, 3));

        // on dimension nos buttons

        btn_client.setPreferredSize(new Dimension(180, 60));
        btn_serveur.setPreferredSize(new Dimension(180, 60));


        btn_client.setToolTipText("cliquez ici pour être client");
        btn_serveur.setToolTipText("cliquez ici pour être serveur");


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(BorderFactory.createEmptyBorder(10,0,80,0));

        add(panelIntermediaire1);
        //add(panelIntermediaire2);
        add(panelIntermediaire3);
        add(panelIntermediaire4);
        add(panelIntermediaire5);
        add(panelIntermediaire6);

        panelIntermediaire1.add(label0);
        panelIntermediaire2.add(label1);

        panelIntermediaire3.add(this.btn_client);
        panelIntermediaire4.add(label2);
        panelIntermediaire5.add(this.btn_serveur);

        panelIntermediaire6.add(label3);

    }
    public JButton getBtn_serveur() {
        return btn_serveur;
    }

    public JButton getBtn_client() {
        return btn_client;
    }
}
