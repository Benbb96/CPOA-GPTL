/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import metiers.Joueur;
import metiers.MatchDouble;
import metiers.MatchSimple;

/**
 *
 * @author Ben
 */
public class ModifierMatchDouble extends JDialog{
    
    private final Connection connexion;
    private final JFrame parent;
    
    private final MatchDouble match;
    
    private JComboBox a1, a2, b1, b2, date, tour;
    private JRadioButton h1, h2, h3, h4, h5;
    private boolean sendData;
    
    public ModifierMatchDouble(JFrame parent, String title, MatchDouble m, Connection conn){
        //On appelle le construteur de JDialog correspondant
        super(parent, title);
        this.parent = parent;
        match = m;
        this.connexion = conn;
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }
    
    public MatchDouble afficheD(){
        this.sendData = false;
        this.setVisible(true);      
        return this.match;      
    }
    
    private void initComponent(){
        //Icône
        /*
        icon = new JLabel(new ImageIcon("images/icone.jpg"));
        JPanel panIcon = new JPanel();
        panIcon.setBackground(Color.white);
        panIcon.setLayout(new BorderLayout());
        panIcon.add(icon);*/

        //Choix des Joueurs de l'équipe 1
        JPanel choixJoueurs1 = new JPanel();
        a1 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            a1.addItem(j.prenomNom());
        });
        a1.setSelectedIndex(match.getA1().getIdJoueur()-1);
        a2 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            a2.addItem(j.prenomNom());
        });
        a2.setSelectedIndex(match.getA2().getIdJoueur()-1);
        choixJoueurs1.setBorder(BorderFactory.createTitledBorder("Choix des joueurs dans l'équipe 1"));
        choixJoueurs1.add(a1);
        choixJoueurs1.add(new JLabel(" et "));
        choixJoueurs1.add(a2);
        
        //Choix des Joueurs de l'équipe 2
        JPanel choixJoueurs2 = new JPanel();
        b1 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            b1.addItem(j.prenomNom());
        });
        b1.setSelectedIndex(match.getB1().getIdJoueur()-1);
        b2 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            b2.addItem(j.prenomNom());
        });
        b2.setSelectedIndex(match.getB2().getIdJoueur()-1);
        choixJoueurs2.setBorder(BorderFactory.createTitledBorder("Choix des joueurs dans l'équipe 2"));
        choixJoueurs2.add(b1);
        choixJoueurs2.add(new JLabel(" et "));
        choixJoueurs2.add(b2);
        
        //Choix du tour
        JPanel choixTour = new JPanel();
        choixTour.setBorder(BorderFactory.createTitledBorder("Choix du tour"));
        tour = new JComboBox();
        tour.addItem("Qualification");
        tour.addItem("1/32");
        tour.addItem("1/16");
        tour.addItem("1/8");
        tour.addItem("Quart de finale");
        tour.addItem("Demi-finale");
        tour.addItem("Finale");
        tour.setSelectedItem(match.getTour());
        choixTour.add(tour);

        //Date et heure
        JPanel dateHeure = new JPanel();
        dateHeure.setBorder(BorderFactory.createTitledBorder("Date et Heure"));
        date = new JComboBox();
        date.addItem("31/01/16");
        date.addItem("01/02/16");
        date.addItem("02/02/16");
        date.addItem("03/02/16");
        date.addItem("04/02/16");
        date.addItem("05/02/16");
        date.addItem("06/02/16");
        date.setSelectedItem(match.getDate());
        h1 = new JRadioButton("8h");
        if (match.getHeure() == 0) h1.setSelected(true);
        h2 = new JRadioButton("11h");
        if (match.getHeure() == 1) h2.setSelected(true);
        h3 = new JRadioButton("15h");
        if (match.getHeure() == 2) h3.setSelected(true);
        h4 = new JRadioButton("18h");
        if (match.getHeure() == 3) h4.setSelected(true);
        h5 = new JRadioButton("21h");
        if (match.getHeure() == 4) h5.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(h1);
        bg.add(h2);
        bg.add(h3);
        bg.add(h4);
        bg.add(h5);
        dateHeure.add(new JLabel("Date : "));
        dateHeure.add(date);
        dateHeure.add(new JLabel("Heure : "));
        dateHeure.add(h1);
        dateHeure.add(h2);
        dateHeure.add(h3);
        dateHeure.add(h4);
        dateHeure.add(h5);

        //Choix du Court
        /*
        JPanel panAge = new JPanel();
        panAge.setBackground(Color.white);
        panAge.setBorder(BorderFactory.createTitledBorder("Age du personnage"));
        panAge.setPreferredSize(new Dimension(440, 60));
        tranche1 = new JRadioButton("Court 1 ??");
        tranche1.setSelected(true);
        tranche2 = new JRadioButton("26 - 35 ans");
        tranche3 = new JRadioButton("36 - 50 ans");
        tranche4 = new JRadioButton("+ de 50 ans");
        ButtonGroup bg = new ButtonGroup();
        bg.add(tranche1);
        bg.add(tranche2);
        bg.add(tranche3);
        bg.add(tranche4);
        panAge.add(tranche1);
        panAge.add(tranche2);
        panAge.add(tranche3);
        panAge.add(tranche4);
        */

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.setBackground(Color.white);
        content.add(choixJoueurs1);
        content.add(choixJoueurs2);
        content.add(choixTour);
        content.add(dateHeure);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("Valider");

        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                match.setA1(Joueur.getListeJoueurs().get(a1.getSelectedIndex()+1));
                match.setA2(Joueur.getListeJoueurs().get(a2.getSelectedIndex()+1));
                match.setB1(Joueur.getListeJoueurs().get(b1.getSelectedIndex()+1));
                match.setB2(Joueur.getListeJoueurs().get(b2.getSelectedIndex()+1));
                match.setTour((String) tour.getSelectedItem());
                match.setDate((String) date.getSelectedItem());
                match.setHeure(getHeure());
                
                try {
                    String requete = "Update Match_Double Set idja1=?, idja2=?, idjb1=?, idjb2=?, date_match=?, heure_match=?, tour_match=? Where idMatch=?";
                    PreparedStatement prepared = connexion.prepareStatement(requete);
                    prepared.setInt(1,match.getA1().getIdJoueur());
                    prepared.setInt(2,match.getA2().getIdJoueur());
                    prepared.setInt(3,match.getB1().getIdJoueur());
                    prepared.setInt(4,match.getB2().getIdJoueur());
                    prepared.setString(5,match.getDate());
                    prepared.setInt(6,match.getHeure());
                    prepared.setString(7,match.getTour());
                    prepared.setInt(8,match.getIdMatch());
                    int result = prepared.executeUpdate();
                    System.out.println(result + " ligne(s) modifiée(s)");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(parent,
                        "Il existe déjà un match au même moment",
                        "SQLIntegrityConstraintViolationException",
                        JOptionPane.ERROR_MESSAGE);
                } catch (SQLSyntaxErrorException ex) {
                    Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(MatchSimple.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                setVisible(false);
            }
            
            public int getHeure() {
                return (h1.isSelected()) ? 0 :
                        (h2.isSelected()) ? 1 :
                        (h3.isSelected()) ? 2 :
                        (h4.isSelected()) ? 3 :
                        (h5.isSelected()) ? 4 :
                        0;
            }
        });
        
        JButton deleteBouton = new JButton("Supprimer le match");
        deleteBouton.addActionListener((ActionEvent ae) -> {
            try {
                String requete = "Delete From Match_Double Where idMatch=?";
                PreparedStatement prepared = connexion.prepareStatement(requete);
                prepared.setInt(1,match.getIdMatch());
                int result = prepared.executeUpdate();
                System.out.println(result + " ligne(s) modifiée(s)");
            } catch (SQLSyntaxErrorException ex) {
                Logger.getLogger(ModifierMatchDouble.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ModifierMatchDouble.class.getName()).log(Level.SEVERE, null, ex);
            }
            MatchDouble remove = MatchDouble.listeMatchDouble.remove(match.getIdMatch());
            setVisible(false);
            JOptionPane.showMessageDialog(parent,
                "Le match double de "+remove.getRealTime()+" a bien été supprimé.",
                "Suppresion réussie",
                JOptionPane.INFORMATION_MESSAGE);
        });

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener((ActionEvent arg0) -> {
            setVisible(false);      
        });

        control.add(okBouton);
        control.add(deleteBouton);
        control.add(cancelBouton);

        //this.getContentPane().add(panIcon, BorderLayout.WEST);
        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }
}
