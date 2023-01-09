package com.company.Evenement;
import com.company.Cellule;
import com.company.Interface.BlocGrilleImage;
import com.company.reseau.Client;
import com.company.reseau.Serveur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GereClickButton  implements ActionListener {

    // la cellule sur laquelle on vient de cliquer
   Cellule moi;

   // sagueu dilane mets le type ancestrale parceque ca peut etre serveur ou client
   Object nature;

    public GereClickButton(Cellule moi,Object nature ) {
        this.moi = moi;
        this.nature=nature;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(BlocGrilleImage.jeton){
            if(!this.moi.equals(BlocGrilleImage.cellule)){
                BlocGrilleImage.buttons[BlocGrilleImage.cellule.ligne][BlocGrilleImage.cellule.colonne].setIcon(null);
                BlocGrilleImage.buttons[moi.ligne][moi.colonne].setIcon(BlocGrilleImage.image);
                BlocGrilleImage.cellule=moi;

            }
            if(nature instanceof Serveur){
                (Serveur.client).envoyerCellule(moi);
            }
            else{
                ((Client)nature).envoyerCellule(moi);
            }
        }


    }
}
