package com.company;
import com.company.Evenement.GereClickButton;
import com.company.Interface.BlocGrilleImage;
import com.company.Interface.FenetrePrincipale;
import com.company.Interface.PanelChoisirNature;
import com.company.reseau.Client;
import com.company.reseau.Serveur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Scanner;
import java.net.InetAddress;


public class Main {
    public static void main(String[] args) throws UnknownHostException {
        new FenetrePrincipale();
    }
}