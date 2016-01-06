/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import metiers.Joueur;
import metiers.MatchSimple;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    
    private final JPanel application = new JPanel(new GridLayout(1,2));
    private final JPanel planning = new JPanel(new BorderLayout());
    private final JTabbedPane tabGestion = new JTabbedPane();
    
    private final Map<Integer, Joueur> liste;
    JPanel listeJoueur = new JPanel();
    
    JPanel dimanche = new JPanel(new BorderLayout());
    JPanel lundi = new JPanel(new BorderLayout());
    JPanel mardi = new JPanel(new BorderLayout());
    JPanel mercredi = new JPanel(new BorderLayout());
    JPanel jeudi = new JPanel(new BorderLayout());
    JPanel vendredi = new JPanel(new BorderLayout());
    JPanel samedi = new JPanel(new BorderLayout());
    
    final static String AJOUTERMATCH = "Ajouter un Match";
    final static String AJOUTERJOUEUR = "Ajouter un Joueur";
    final static String LISTEJOUEUR = "Liste Joueur";
    
    public Gestionnaire() {
        super();
        
        //Mise à jour de la liste des joueurs
        Joueur.updateListJoueurs();
        this.liste = Joueur.getListeJoueurs();
        
        //Construction de tous panels de l'application
        build();
    }
    
    private void build() {
        this.setTitle("Gestionnaire Match");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Construire la fenêtre Planning
        planning();
        
        //Construire la fenêtre avec les différents onglets de gestion
        tabGestion();
        
        application.add(planning);
        application.add(tabGestion);
        this.setContentPane(application);
    }
    
    public void planning() {
        //Se connecter à la base de donnée pour récupérer les matchs existants
        //Puis les afficher un à un dans le planning (CardLayout)
        JPanel matchs = new JPanel(new GridLayout(6,1));
        JLabel dim = new JLabel("Dimanche");
        JPanel day1 = new JPanel();
        day1.setLayout(new BoxLayout(day1, BoxLayout.PAGE_AXIS));
        day1.add(dim);
        JLabel lun = new JLabel("Lundi");
        JLabel mar = new JLabel("Mardi");
        JLabel mer = new JLabel("Mercredi");
        JLabel jeu = new JLabel("Jeudi");
        JLabel ven = new JLabel("Vendredi");
        JLabel sam = new JLabel("Samedi");
        dimanche.setLayout(new GridLayout(5,2));//Une grille avec les 5 matchs simples d'un côté et les 5 matchs doubles de l'autre
        MatchSimple.updateListMatchSimple();
        for (MatchSimple m : MatchSimple.listeMatchSimple.values()) {
            if (m.getDate().equals("31/01/16")) {
                dimanche.add(new JLabel("S - "+m.getHeure()+" : "+m.getJ1().s()+ " VS "+m.getJ2().s()));
            }
        }
        MatchDouble.updateListMatchDouble();
        for (MatchDouble m : MatchDouble.listeMatchDouble.values()) {
            if (m.getDate().equals("31/01/16")) {
                dimanche.add(new JLabel("D - "+m.getHeure()+" : "+m.getA1().s()+" et "+m.getA2().s()+" VS "+m.getB1().s()+" et "+m.getB2().s()));
            }
        }
        //dimanche.setMaximumSize(new Dimension(420, 200));
        day1.add(dimanche);
        matchs.add(day1);
        matchs.add(lun);
        matchs.add(mar);
        matchs.add(mer);
        matchs.add(jeu);
        matchs.add(ven);
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
        liste.values().stream().forEach((j) -> {
            j1.addItem(j.getPrenom()+" "+j.getNom());
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
        liste.values().stream().forEach((j) -> {
            j2.addItem(j.getPrenom()+" "+j.getNom());
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
        liste.values().stream().forEach((j) -> {
            a1.addItem(j.getPrenom()+" "+j.getNom());
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
        liste.values().stream().forEach((j) -> {
            a2.addItem(j.getPrenom()+" "+j.getNom());
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
        liste.values().stream().forEach((j) -> {
            b1.addItem(j.getPrenom()+" "+j.getNom());
        });
        b1.setMaximumSize(new Dimension(140,25));
        b1Pane.add(b1);
        doubl.add(b1Pane);
        
        doubl.add(new JLabel("Avec"));
        
        //Sélection A2
        JPanel b2Pane = new JPanel();
        b2Pane.setLayout(new BoxLayout(b2Pane, BoxLayout.LINE_AXIS));
        b2Pane.add(new JLabel("Joueur B2 :"));
        b2Pane.add(Box.createHorizontalStrut(20));
        JComboBox b2 = new JComboBox();
        liste.values().stream().forEach((j) -> {
            b2.addItem(j.getPrenom()+" "+j.getNom());
        });
        b2.setMaximumSize(new Dimension(140,25));
        b2Pane.add(b2);
        doubl.add(b2Pane);
        
        doubl.setVisible(false);
        ajoutMatch.add(doubl);
        
        ajoutMatch.add(Box.createVerticalGlue());
        
        /* On ne fera que les matchs de qualification
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
        */
        
        /*
        JPanel date = new JPanel(new FlowLayout());
        date.add(new JLabel("Date"));
        JTextField jour = new JTextField(2);
        */
        
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
                MatchSimple m = new MatchSimple("31/01/16",heure.getSelectedIndex(),liste.get(j1.getSelectedIndex()+1),liste.get(j2.getSelectedIndex()+1));
                m.ajouterMatchSimple();
            }
            else if(group.getSelection().getActionCommand().equals("Double")) {
                System.out.println("Double");
                MatchDouble m = new MatchDouble("31/01/16",heure.getSelectedIndex(),liste.get(a1.getSelectedIndex()+1),liste.get(a2.getSelectedIndex()+1),liste.get(b1.getSelectedIndex()+1),liste.get(b2.getSelectedIndex()+1));
                m.ajouterMatchDouble();
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
            nouvJ.ajouterJoueur();
            listeJoueur.add(new JLabel(nouvJ.toString()));
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
        liste.values().stream().forEach((j) -> {
            listeJoueur.add(new JLabel(j.toString()));
        }); //Fin Liste Joueur
        
        //Ajout des onglets au Panel tabGestion
        tabGestion.addTab(AJOUTERMATCH, ajoutMatch);
        tabGestion.addTab(AJOUTERJOUEUR, ajoutJoueur);
        tabGestion.addTab(LISTEJOUEUR, listeJoueur);
    }
    
    public void updateApp() {
        System.out.println("Update");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gestionnaire appli = new Gestionnaire();// On crée l'objet Gestionnaire qui instancie la fenêtre
            appli.setVisible(true);// Et on la rend visible quand l'ordinateur est prêt
        });
    }
}
