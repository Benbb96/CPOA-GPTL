/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import connexion.ConfigConnexion;
import metiers.Joueur;
import metiers.MatchSimple;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import metiers.MatchDouble;

/**
 * Je pourrai peut-être tout recommencer correctement en créant plusieurs classes avec des fenêtres distinctes
 * Ou suivre le tuto pour faire un truc pète sa mère...
 * @author Ben
 */
public class Gestionnaire extends JFrame {
    
    private final Connection CONNEXION;
    
    private final JPanel application = new JPanel(new GridLayout(1,2));
    private final JPanel planning = new JPanel(new BorderLayout());
    private final JTabbedPane tabGestion = new JTabbedPane();
    
    private final JPanel listeJoueur = new JPanel();
    
    private final JourPlanning dimanche;
    private final JourPlanning lundi;
    private final JourPlanning mardi;
    private final JourPlanning mercredi;
    private final JourPlanning jeudi;
    private final JourPlanning vendredi;
    private final JourPlanning samedi;
    
    private final ArrayList<JourPlanning> jours = new ArrayList();
    
    final static String AJOUTERMATCH = "Ajouter un Match";
    final static String AJOUTERJOUEUR = "Ajouter un Joueur";
    final static String LISTEJOUEUR = "Liste Joueur";
    
    public Gestionnaire(Connection connexion) {
        super();
        this.CONNEXION = connexion;
        //Mise à jour de la liste des joueurs
        Joueur.updateListJoueurs(CONNEXION);
        
        dimanche = new JourPlanning(this,"31/01/16",CONNEXION);
        lundi = new JourPlanning(this,"01/02/16",CONNEXION);
        mardi = new JourPlanning(this,"02/02/16",CONNEXION);
        mercredi = new JourPlanning(this,"03/02/16",CONNEXION);
        jeudi = new JourPlanning(this,"04/02/16",CONNEXION);
        vendredi = new JourPlanning(this,"05/02/16",CONNEXION);
        samedi = new JourPlanning(this,"06/02/16",CONNEXION);
        
        //Regroupement de tous les JourPlanning dans l'array list
        jours.add(dimanche);
        jours.add(lundi);
        jours.add(mardi);
        jours.add(mercredi);
        jours.add(jeudi);
        jours.add(vendredi);
        jours.add(samedi);
        
        //Construction de tous panels de l'application
        build();
    }
    
    private void build() {
        this.setTitle("Gestionnaire Match");
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Construire la fenêtre Planning
        planning();
        updateApp();
        
        //Construire la fenêtre avec les différents onglets de gestion
        tabGestion();
        
        application.add(planning);
        application.add(tabGestion);
        this.setContentPane(application);
    }
    
    public void planning() {
        //Se connecter à la base de donnée pour récupérer les matchs existants
        //Puis les afficher un à un dans le planning (CardLayout)
        JPanel matchs = new JPanel(new GridLayout(7,1));
        ArrayList<JLabel> nomsJour = new ArrayList();
        nomsJour.add(new JLabel("Dimanche 31"));
        nomsJour.add(new JLabel("Lundi 1"));
        nomsJour.add(new JLabel("Mardi 2"));
        nomsJour.add(new JLabel("Mercredi 3"));
        nomsJour.add(new JLabel("Jeudi 4"));
        nomsJour.add(new JLabel("Vendredi 5"));
        nomsJour.add(new JLabel("Samedi 6"));
        int i = 0;
        for (JourPlanning j : jours) {
            JPanel pan = new JPanel();
            pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
            pan.add(nomsJour.get(i));
            pan.add(j);
            matchs.add(pan);
            i++;
        }
        JScrollPane scroll = new JScrollPane(matchs);
        JPanel enTete = new JPanel();
        enTete.setLayout(new BoxLayout(enTete, BoxLayout.LINE_AXIS));
        JLabel titre = new JLabel("Planning prévisionnel");
        enTete.add(titre);
        enTete.add(Box.createHorizontalGlue());
        JButton update = new JButton("Refresh");
        update.addActionListener((ActionEvent ae) -> {
            updateApp();
        });
        enTete.add(update);
        planning.add(enTete, BorderLayout.NORTH);
        planning.add(scroll, BorderLayout.CENTER);
        planning.setSize(new Dimension(600,500));
    }
    
    public void tabGestion() {
        
        JPanel simple = new JPanel();
        JPanel doubl = new JPanel();
        
        //Onglet Ajout Match
        JPanel ajoutMatch = new JPanel();
        ajoutMatch.setLayout(new BoxLayout(ajoutMatch, BoxLayout.PAGE_AXIS));
        ajoutMatch.add(Box.createVerticalGlue());
        
        //Création champs1 : Choix du match simple ou double
        JPanel choixMatch = new JPanel();
        choixMatch.add(new JLabel("Match"));
        JPanel radioButtons = new JPanel(new FlowLayout());
        radioButtons.setLayout(new BoxLayout(radioButtons, BoxLayout.PAGE_AXIS));
        JRadioButton matchSimple = new JRadioButton("Simple");
        matchSimple.setActionCommand("Simple");
        matchSimple.addActionListener((ActionEvent ae) -> {
            simple.setVisible(true);
            doubl.setVisible(false);
        });
        matchSimple.setSelected(true);
        JRadioButton matchDouble = new JRadioButton("Double");
        matchDouble.setActionCommand("Double");
        matchDouble.addActionListener((ActionEvent ae) -> {
            doubl.setVisible(true);
            simple.setVisible(false);
        });
        ButtonGroup group = new ButtonGroup();
        group.add(matchSimple);
        radioButtons.add(matchSimple);
        group.add(matchDouble);
        radioButtons.add(matchDouble);
        choixMatch.add(Box.createRigidArea(new Dimension(80,0)));
        choixMatch.add(radioButtons);
        ajoutMatch.add(choixMatch);
        
        //SIMPLE
        simple.setLayout(new BoxLayout(simple, BoxLayout.PAGE_AXIS));
        //Sélection Joueur 1
        JPanel j1Pane = new JPanel();
        j1Pane.setLayout(new BoxLayout(j1Pane, BoxLayout.LINE_AXIS));
        j1Pane.add(new JLabel("Joueur 1 :"));
        j1Pane.add(Box.createHorizontalStrut(20));
        JComboBox j1 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            j1.addItem(j.prenomNom());
        });
        j1.setMaximumSize(new Dimension(140,25));
        j1Pane.add(j1);
        simple.add(j1Pane);
        
        simple.add(new JLabel("Versus"));
        
        //Sélection Joueur 2
        JPanel j2Pane = new JPanel();
        j2Pane.setLayout(new BoxLayout(j2Pane, BoxLayout.LINE_AXIS));
        j2Pane.add(new JLabel("Joueur 2 :"));
        j2Pane.add(Box.createHorizontalStrut(20));
        JComboBox j2 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            j2.addItem(j.prenomNom());
        });
        j2.setMaximumSize(new Dimension(140,25));
        j2Pane.add(j2);
        simple.add(j2Pane);
        
        ajoutMatch.add(simple);
        
        //DOUBLE
        doubl.setLayout(new BoxLayout(doubl, BoxLayout.PAGE_AXIS));
        //Sélection A1
        JPanel a1Pane = new JPanel();
        a1Pane.setLayout(new BoxLayout(a1Pane, BoxLayout.LINE_AXIS));
        a1Pane.add(new JLabel("Joueur A1 :"));
        a1Pane.add(Box.createHorizontalStrut(20));
        JComboBox a1 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            a1.addItem(j.prenomNom());
        });
        a1.setMaximumSize(new Dimension(140,25));
        a1Pane.add(a1);
        doubl.add(a1Pane);
        
        doubl.add(new JLabel("Avec"));
        
        //Sélection A2
        JPanel a2Pane = new JPanel();
        a2Pane.setLayout(new BoxLayout(a2Pane, BoxLayout.LINE_AXIS));
        a2Pane.add(new JLabel("Joueur A2 :"));
        a2Pane.add(Box.createHorizontalStrut(20));
        JComboBox a2 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            a2.addItem(j.prenomNom());
        });
        a2.setMaximumSize(new Dimension(140,25));
        a2Pane.add(a2);
        doubl.add(a2Pane);
        
        doubl.add(Box.createVerticalGlue());
        doubl.add(new JLabel("Versus"));
        doubl.add(Box.createVerticalGlue());
        
        //Sélection B1
        JPanel b1Pane = new JPanel();
        b1Pane.setLayout(new BoxLayout(b1Pane, BoxLayout.LINE_AXIS));
        b1Pane.add(new JLabel("Joueur B1 :"));
        b1Pane.add(Box.createHorizontalStrut(20));
        JComboBox b1 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            b1.addItem(j.prenomNom());
        });
        b1.setMaximumSize(new Dimension(140,25));
        b1Pane.add(b1);
        doubl.add(b1Pane);
        
        doubl.add(new JLabel("Avec"));
        
        //Sélection B2
        JPanel b2Pane = new JPanel();
        b2Pane.setLayout(new BoxLayout(b2Pane, BoxLayout.LINE_AXIS));
        b2Pane.add(new JLabel("Joueur B2 :"));
        b2Pane.add(Box.createHorizontalStrut(20));
        JComboBox b2 = new JComboBox();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            b2.addItem(j.prenomNom());
        });
        b2.setMaximumSize(new Dimension(140,25));
        b2Pane.add(b2);
        doubl.add(b2Pane);
        
        doubl.setVisible(false);
        
        //Regroupement de tous les combobox dans un array
        ArrayList<JComboBox> comboJ = new ArrayList();
        comboJ.add(j1);
        comboJ.add(j2);
        comboJ.add(a1);
        comboJ.add(a2);
        comboJ.add(b1);
        comboJ.add(b2);
        
        ajoutMatch.add(doubl);
        
        ajoutMatch.add(Box.createVerticalGlue());
        
        //Choix du tour du match
        JPanel tour = new JPanel(new FlowLayout());
        tour.add(new JLabel("Tour"));
        tour.add(Box.createHorizontalGlue());
        JComboBox choixTour = new JComboBox();
        choixTour.addItem("Qualification");
        choixTour.addItem("1/32");
        choixTour.addItem("1/16");
        choixTour.addItem("1/8");
        choixTour.addItem("Quart de finale");
        choixTour.addItem("Demi-finale");
        choixTour.addItem("Finale");
        tour.add(choixTour);
        ajoutMatch.add(tour);
        
        JPanel date = new JPanel(new FlowLayout());
        date.add(new JLabel("Date (jj/mm/aa)"));
        JTextField jour = new JTextField(8);
        date.add(jour);
        ajoutMatch.add(date);
        
        JPanel crenau = new JPanel(new FlowLayout());
        crenau.add(new JLabel("Heure (crénau)"));
        crenau.add(Box.createHorizontalGlue());
        JComboBox heure = new JComboBox();
        heure.addItem("matin (8h)");
        heure.addItem("matinée (11h)");
        heure.addItem("midi (15h)");
        heure.addItem("après-midi (18h)");
        heure.addItem("soirée (21h)");
        crenau.add(heure);
        ajoutMatch.add(crenau);
        ajoutMatch.add(Box.createVerticalGlue());
        
        JButton valider = new JButton("Ajouter");
        valider.addActionListener((ActionEvent ae) -> {
            System.out.print("Ajout Match ");
            if (group.getSelection().getActionCommand().equals("Simple")) {
                System.out.println("Simple");
                MatchSimple m = new MatchSimple(jour.getText(),heure.getSelectedIndex(),choixTour.getSelectedItem().toString(),
                        Joueur.getListeJoueurs().get(j1.getSelectedIndex()+1),
                        Joueur.getListeJoueurs().get(j2.getSelectedIndex()+1));
                try {
                    m.ajouterMatchSimple(CONNEXION);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(tabGestion,
                    "Il existe déjà un match au même moment",
                    "SQLIntegrityConstraintViolationException",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(group.getSelection().getActionCommand().equals("Double")) {
                System.out.println("Double");
                MatchDouble m = new MatchDouble(jour.getText(),heure.getSelectedIndex(),choixTour.getSelectedItem().toString(),
                        Joueur.getListeJoueurs().get(a1.getSelectedIndex()+1),
                        Joueur.getListeJoueurs().get(a2.getSelectedIndex()+1),
                        Joueur.getListeJoueurs().get(b1.getSelectedIndex()+1),
                        Joueur.getListeJoueurs().get(b2.getSelectedIndex()+1));
                try {
                    m.ajouterMatchDouble(CONNEXION);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(tabGestion,
                    "Il existe déjà un match au même moment",
                    "SQLIntegrityConstraintViolationException",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
            updateApp();
        });
        ajoutMatch.add(valider);
        //Fin onglet Ajout Match
        
        //Onglet Ajout Joueur
        JPanel ajoutJoueur = new JPanel();
        ajoutJoueur.setLayout(new BoxLayout(ajoutJoueur, BoxLayout.PAGE_AXIS));
        JPanel nom = new JPanel(new FlowLayout());
        JLabel labelNom = new JLabel("Nom ");
        JTextField textNom = new JTextField(30);
        nom.add(labelNom);
        nom.add(textNom);
        JPanel prenom = new JPanel(new FlowLayout());
        JLabel labelPrenom = new JLabel("Pénom ");
        JTextField textPrenom = new JTextField(30);
        prenom.add(labelPrenom);
        prenom.add(textPrenom);
        JPanel nationalite = new JPanel(new FlowLayout());
        JLabel labelNationalite = new JLabel("Nationalité ");
        JComboBox comboNationalite = new JComboBox();
        comboNationalite.addItem("FRA");
        comboNationalite.addItem("ESP");
        comboNationalite.addItem("GBR");
        comboNationalite.addItem("SRB");
        comboNationalite.addItem("CAN");
        comboNationalite.addItem("CZE");
        comboNationalite.addItem("LAT");
        nationalite.add(labelNationalite);
        nationalite.add(comboNationalite);
        JButton submit = new JButton("Valider");
        submit.addActionListener((ActionEvent ae) -> {
            Joueur nouvJ = new Joueur(textNom.getText(), textPrenom.getText(), comboNationalite.getSelectedItem().toString());
            try {
                nouvJ.ajouterJoueur(CONNEXION);
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(tabGestion,
                "Erreur de contrainte d'intégrité",
                "SQLIntegrityConstraintViolationException",
                JOptionPane.ERROR_MESSAGE);
            }
            Joueur.getListeJoueurs().put(nouvJ.getIdJoueur(),nouvJ);//On ajoute le joueur à la liste de tous les joueurs
            updatePanelJoueur();//On remet à jour le conteneur de la liste des joueurs
            comboJ.stream().forEach((jc) -> {
                jc.addItem(nouvJ.getPrenom()+" "+nouvJ.getNom());//On ajoute le nouveau joueurs sur chaque combo box
            });
            textNom.setText("");
            textPrenom.setText("");
        });
        ajoutJoueur.add(nom);
        ajoutJoueur.add(prenom);
        ajoutJoueur.add(nationalite);
        ajoutJoueur.add(submit);
        //Fin onglet Ajout Joueur
        
        //Onglet Liste Joueur
        listeJoueur.setLayout(new BoxLayout(listeJoueur, BoxLayout.PAGE_AXIS));
        updatePanelJoueur();
        
        //Ajout des onglets au Panel tabGestion
        tabGestion.addTab(AJOUTERMATCH, ajoutMatch);
        tabGestion.addTab(AJOUTERJOUEUR, ajoutJoueur);
        tabGestion.addTab(LISTEJOUEUR, listeJoueur);
    }
    
    public void updateApp() {
        System.out.println("\tUpdate Planning");
        MatchSimple.updateListMatchSimple(CONNEXION);
        MatchDouble.updateListMatchDouble(CONNEXION);
        jours.stream().forEach((j) -> {
            j.updateJour();
        });
        this.revalidate();// Remet à jour les boutons des matchs
    }
    
    public void updatePanelJoueur() {
        listeJoueur.removeAll();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            listeJoueur.add(new JLabel(j.toString()));
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connexion = ConfigConnexion.getConnection("connexion.properties");
                Gestionnaire appli = new Gestionnaire(connexion); // On crée l'objet Gestionnaire qui instancie la fenêtre
                appli.setVisible(true);// Et on la rend visible quand l'ordinateur est prêt
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
