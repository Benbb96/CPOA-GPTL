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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import metiers.Joueur;
import metiers.MatchSimple;

/**
 *
 * @author Ben
 */
public class ModifierMatchSimple extends JDialog{
    
    private final Connection connexion;
    private final JFrame parent;
    
    private final MatchSimple match;
    
    private JComboBox j1, j2, date, tour;
    private JRadioButton h1, h2, h3, h4, h5;
    
    public ModifierMatchSimple(JFrame parent, String title, MatchSimple m, Connection conn){
        //On appelle le construteur de JDialog correspondant
        super(parent, title);
        this.parent = parent;
        match = m;
        this.connexion = conn;
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }
    
    
    public MatchSimple afficheS(){
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

        //Choix des Joueurs
        JPanel choixJoueurs = new JPanel();
        j1 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            j1.addItem(j.prenomNom());
        });
        j1.setSelectedIndex(match.getJ1().getIdJoueur()-1);
        j2 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            j2.addItem(j.prenomNom());
        });
        j2.setSelectedIndex(match.getJ2().getIdJoueur()-1);
        choixJoueurs.setBorder(BorderFactory.createTitledBorder("Choix des joueurs"));
        choixJoueurs.add(j1);
        choixJoueurs.add(new JLabel(" VS "));
        choixJoueurs.add(j2);
        
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
        content.add(choixJoueurs);
        content.add(choixTour);
        content.add(dateHeure);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("Valider");

        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                match.setJ1(Joueur.getListeJoueurs().get(j1.getSelectedIndex()+1));
                match.setJ2(Joueur.getListeJoueurs().get(j2.getSelectedIndex()+1));
                match.setTour((String) tour.getSelectedItem());
                match.setDate((String) date.getSelectedItem());
                match.setHeure(getHeure());
                
                try {
                    String requete = "Update Match_Simple Set idj1=?, idj2=?, date_match=?, heure_match=?, tour_match=? Where idMatch=?";
                    PreparedStatement prepared = connexion.prepareStatement(requete);
                    prepared.setInt(1,match.getJ1().getIdJoueur());
                    prepared.setInt(2,match.getJ2().getIdJoueur());
                    prepared.setString(3,match.getDate());
                    prepared.setInt(4,match.getHeure());
                    prepared.setString(5,match.getTour());
                    prepared.setInt(6,match.getIdMatch());
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

        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener((ActionEvent arg0) -> {
            setVisible(false);      
        });

        control.add(okBouton);
        control.add(cancelBouton);

        //this.getContentPane().add(panIcon, BorderLayout.WEST);
        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }
}
