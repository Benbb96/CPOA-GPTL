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
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
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
import metiers.Match;
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
    private final ArrayList<JJoueur> joueurs = new ArrayList();
    ArrayList<JComboBox> comboJ;
    
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
        this.setSize(900, 550);
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
        //Bouton pour générer un planning prévisionnel automatiquement en fonction du nombre de joueur
        JButton generate = new JButton("Générer");
        generate.addActionListener((ActionEvent ae) -> {
            generatePlanning();
        });
        enTete.add(generate);
        enTete.add(Box.createHorizontalGlue());
        //Bouton pour supprimer tous les matchs
        JButton deleteAll = new JButton("Tout supprimer");
        deleteAll.addActionListener((ActionEvent ae) -> {
            int option = JOptionPane.showConfirmDialog(planning, "Êtes-vous sûr de vouloir supprimer tous les matchs ?", "Suppression des matchs", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String requete = "Delete From Match_Simple";
                ConfigConnexion.executeRequete(CONNEXION, requete);
                requete = "Delete From Match_Double";
                ConfigConnexion.executeRequete(CONNEXION, requete);
                updateApp();
            }
        });
        enTete.add(deleteAll);
        enTete.add(Box.createHorizontalGlue());
        //Bouton Refresh au cas où les matchs sont modifiés ailleurs que sur l'application
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
        
        // ---------------------- Onglet Ajout Match -------------------------------
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
        //Quand on appuie sur le bouton simple, on affiche que deux champs de sélection de joueur
        matchSimple.addActionListener((ActionEvent ae) -> {
            simple.setVisible(true);
            doubl.setVisible(false);
        });
        matchSimple.setSelected(true);
        JRadioButton matchDouble = new JRadioButton("Double");
        matchDouble.setActionCommand("Double");
        //Quand on appuie sur le bouton double, on affiche 4 champs de sélection de joueur
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
        j1.setMaximumSize(new Dimension(200,25));
        j1Pane.add(j1);
        j1Pane.add(Box.createHorizontalGlue());
        simple.add(j1Pane);
        
        JPanel versus = new JPanel();
        versus.setLayout(new BoxLayout(versus, BoxLayout.LINE_AXIS));
        versus.add(Box.createHorizontalStrut(175));
        versus.add(new JLabel("Versus"));
        versus.add(Box.createHorizontalGlue());
        simple.add(versus);
        
        //Sélection Joueur 2
        JPanel j2Pane = new JPanel();
        j2Pane.setLayout(new BoxLayout(j2Pane, BoxLayout.LINE_AXIS));
        j2Pane.add(Box.createHorizontalGlue());
        j2Pane.add(new JLabel("Joueur 2 :"));
        j2Pane.add(Box.createHorizontalStrut(20));
        JComboBox j2 = new JComboBox();
        j2.setMaximumSize(new Dimension(200,25));
        j2Pane.add(j2);
        simple.add(j2Pane);
        
        simple.setBorder(BorderFactory.createTitledBorder("Choix des joueurs en simple"));
        ajoutMatch.add(simple);
        
        //DOUBLE
        doubl.setLayout(new BoxLayout(doubl, BoxLayout.PAGE_AXIS));
        //Sélection A1
        JPanel a1Pane = new JPanel();
        a1Pane.setLayout(new BoxLayout(a1Pane, BoxLayout.LINE_AXIS));
        a1Pane.add(new JLabel("Joueur A1 :"));
        a1Pane.add(Box.createHorizontalStrut(20));
        JComboBox a1 = new JComboBox();
        a1.setMaximumSize(new Dimension(200,25));
        a1Pane.add(a1);
        a1Pane.add(Box.createHorizontalGlue());
        doubl.add(a1Pane);
        
        JPanel avec1 = new JPanel();
        avec1.setLayout(new BoxLayout(avec1, BoxLayout.LINE_AXIS));
        avec1.add(Box.createHorizontalStrut(100));
        avec1.add(new JLabel("Avec"));
        avec1.add(Box.createHorizontalGlue());
        doubl.add(avec1);
        
        //Sélection A2
        JPanel a2Pane = new JPanel();
        a2Pane.setLayout(new BoxLayout(a2Pane, BoxLayout.LINE_AXIS));
        a2Pane.add(new JLabel("Joueur A2 :"));
        a2Pane.add(Box.createHorizontalStrut(20));
        JComboBox a2 = new JComboBox();
        a2.setMaximumSize(new Dimension(200,25));
        a2Pane.add(a2);
        a2Pane.add(Box.createHorizontalGlue());
        doubl.add(a2Pane);
        
        doubl.add(Box.createVerticalGlue());
        JPanel versus2 = new JPanel();
        versus2.setLayout(new BoxLayout(versus2, BoxLayout.LINE_AXIS));
        versus2.add(Box.createHorizontalStrut(175));
        versus2.add(new JLabel("Versus"));
        versus2.add(Box.createHorizontalGlue());
        doubl.add(versus2);
        doubl.add(Box.createVerticalGlue());
        
        //Sélection B1
        JPanel b1Pane = new JPanel();
        b1Pane.setLayout(new BoxLayout(b1Pane, BoxLayout.LINE_AXIS));
        b1Pane.add(Box.createHorizontalGlue());
        b1Pane.add(new JLabel("Joueur B1 :"));
        b1Pane.add(Box.createHorizontalStrut(20));
        JComboBox b1 = new JComboBox();
        b1.setMaximumSize(new Dimension(200,25));
        b1Pane.add(b1);
        doubl.add(b1Pane);
        
        JPanel avec2 = new JPanel();
        avec2.setLayout(new BoxLayout(avec2, BoxLayout.LINE_AXIS));
        avec2.add(Box.createHorizontalGlue());
        avec2.add(new JLabel("Avec"));
        avec2.add(Box.createHorizontalStrut(100));
        doubl.add(avec2);
        
        //Sélection B2
        JPanel b2Pane = new JPanel();
        b2Pane.setLayout(new BoxLayout(b2Pane, BoxLayout.LINE_AXIS));
        b2Pane.add(Box.createHorizontalGlue());
        b2Pane.add(new JLabel("Joueur B2 :"));
        b2Pane.add(Box.createHorizontalStrut(20));
        JComboBox b2 = new JComboBox();
        b2.setMaximumSize(new Dimension(200,25));
        b2Pane.add(b2);
        doubl.add(b2Pane);
        
        doubl.setVisible(false);
        doubl.setBorder(BorderFactory.createTitledBorder("Choix des joueurs en double"));
        ajoutMatch.add(doubl);
        
        //Regroupement de tous les combobox dans un array pour pouvoir les remettre à jour plus facilement
        comboJ = new ArrayList();
        comboJ.add(j1);
        comboJ.add(j2);
        comboJ.add(a1);
        comboJ.add(a2);
        comboJ.add(b1);
        comboJ.add(b2);
        
        ajoutMatch.add(Box.createVerticalGlue());
        
        //Choix du tour du match
        JPanel tour = new JPanel();
        tour.setLayout(new BoxLayout(tour,BoxLayout.LINE_AXIS));
        tour.add(Box.createHorizontalStrut(50));
        tour.add(new JLabel("Tour"));
        tour.add(Box.createHorizontalStrut(20));
        JComboBox choixTour = new JComboBox();
        choixTour.addItem("Qualification");
        choixTour.addItem("1/32");
        choixTour.addItem("1/16");
        choixTour.addItem("1/8");
        choixTour.addItem("Quart de finale");
        choixTour.addItem("Demi-finale");
        choixTour.addItem("Finale");
        choixTour.setMaximumSize(new Dimension(200,25));
        tour.add(choixTour);
        tour.add(Box.createHorizontalGlue());
        ajoutMatch.add(tour);
        
        ajoutMatch.add(Box.createVerticalGlue());
        
        JPanel date = new JPanel();
        date.setLayout(new BoxLayout(date,BoxLayout.LINE_AXIS));
        date.add(Box.createHorizontalStrut(50));
        date.add(new JLabel("Date"));
        date.add(Box.createHorizontalStrut(20));
        JComboBox jour = new JComboBox();
        jour.addItem("31/01/16");
        jour.addItem("01/02/16");
        jour.addItem("02/02/16");
        jour.addItem("03/02/16");
        jour.addItem("04/02/16");
        jour.addItem("05/02/16");
        jour.addItem("06/02/16");
        jour.setMaximumSize(new Dimension(200,25));
        date.add(jour);
        date.add(Box.createHorizontalGlue());
        ajoutMatch.add(date);
        
        ajoutMatch.add(Box.createVerticalGlue());
        
        JPanel crenau = new JPanel();
        crenau.setLayout(new BoxLayout(crenau,BoxLayout.LINE_AXIS));
        crenau.add(Box.createHorizontalStrut(50));
        crenau.add(new JLabel("Heure (crénau)"));
        crenau.add(Box.createHorizontalStrut(20));
        JRadioButton h1 = new JRadioButton("8h");
        h1.setSelected(true);
        JRadioButton h2 = new JRadioButton("11h");
        JRadioButton h3 = new JRadioButton("15h");
        JRadioButton h4 = new JRadioButton("18h");
        JRadioButton h5 = new JRadioButton("21h");
        ButtonGroup bg = new ButtonGroup();
        bg.add(h1);
        bg.add(h2);
        bg.add(h3);
        bg.add(h4);
        bg.add(h5);
        crenau.add(h1);
        crenau.add(h2);
        crenau.add(h3);
        crenau.add(h4);
        crenau.add(h5);
        crenau.add(Box.createHorizontalGlue());
        ajoutMatch.add(crenau);
        ajoutMatch.add(Box.createVerticalGlue());
        
        JButton valider = new JButton("Ajouter");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (group.getSelection().getActionCommand().equals("Simple")) {
                    Joueur joueur1 = Joueur.getListeJoueurs().get(j1.getSelectedIndex()+1);
                    Joueur joueur2 = Joueur.getListeJoueurs().get(j2.getSelectedIndex()+1);
                    try {
                        // Vérification qu'un joueur ne joue pas contre lui même
                        if (joueur1.equals(joueur2)) {
                            throw new DoublonJoueurException("Erreur");
                        }
                        MatchSimple m = new MatchSimple(jour.getSelectedItem().toString(),getHeure(),choixTour.getSelectedItem().toString(),
                                joueur1,
                                joueur2);
                        m.ajouterMatchSimple(CONNEXION);
                        updateApp();
                    } catch (SQLIntegrityConstraintViolationException ex) {
                        JOptionPane.showMessageDialog(tabGestion,
                                "Il existe déjà un match au même moment",
                                "SQLIntegrityConstraintViolationException",
                                JOptionPane.ERROR_MESSAGE);
                    }catch (DoublonJoueurException ex) {
                        JOptionPane.showMessageDialog(tabGestion,
                                Joueur.getListeJoueurs().get(j1.getSelectedIndex()+1).prenomNom()+" ne peut pas jouer contre lui-même !",
                                "Erreur Sélection des joueurs",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(group.getSelection().getActionCommand().equals("Double")) {
                    Joueur joueur1 = Joueur.getListeJoueurs().get(a1.getSelectedIndex()+1);
                    Joueur joueur2 = Joueur.getListeJoueurs().get(a2.getSelectedIndex()+1);
                    Joueur joueur3 = Joueur.getListeJoueurs().get(b1.getSelectedIndex()+1);
                    Joueur joueur4 = Joueur.getListeJoueurs().get(b2.getSelectedIndex()+1);
                    try {
                        //Vérification des contraintes
                        if (joueur1.equals(joueur2) || joueur3.equals(joueur4)) {
                            throw new DoublonJoueurException(joueur1.prenomNom()+" ne peut pas jouer deux fois dans la même équipe !");
                        }
                        if (joueur1.equals(joueur3) || joueur1.equals(joueur4)) {
                            throw new DoublonJoueurException(joueur1.prenomNom()+" ne peut pas jouer dans deux équipes à la fois !");
                        }
                        if (joueur2.equals(joueur3) || joueur1.equals(joueur4)) {
                            throw new DoublonJoueurException(joueur2.prenomNom()+" ne peut pas jouer dans deux équipes à la fois !");
                        }
                        
                        MatchDouble m = new MatchDouble(jour.getSelectedItem().toString(),getHeure(),choixTour.getSelectedItem().toString(),
                            joueur1,
                            joueur2,
                            joueur3,
                            joueur4);
                        m.ajouterMatchDouble(CONNEXION);
                        updateApp();
                    } catch (SQLIntegrityConstraintViolationException ex) {
                        JOptionPane.showMessageDialog(tabGestion,
                                "Il existe déjà un match au même moment",
                                "SQLIntegrityConstraintViolationException",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (DoublonJoueurException ex) {
                        JOptionPane.showMessageDialog(tabGestion,
                                ex.getMessage(),
                                "Erreur Sélection des joueurs",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
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
        ajoutMatch.add(valider);
        //Fin onglet Ajout Match
        
        // ----------------------------------- Onglet Ajout Joueur ----------------------------------------
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
        JComboBox comboNationalite = new JComboBox(Joueur.getLISTENAT());
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
            
            textNom.setText("");
            textPrenom.setText("");
        });
        ajoutJoueur.add(nom);
        ajoutJoueur.add(prenom);
        ajoutJoueur.add(nationalite);
        ajoutJoueur.add(submit);
        //Fin onglet Ajout Joueur
        
        // ------------------------------------------ Onglet Liste Joueur ----------------------------------------
        listeJoueur.setLayout(new BoxLayout(listeJoueur, BoxLayout.PAGE_AXIS));
        listeJoueur.setAlignmentY(CENTER_ALIGNMENT);
        updatePanelJoueur();
        
        //Ajout des onglets au Panel tabGestion
        tabGestion.addTab(AJOUTERMATCH, ajoutMatch);
        tabGestion.addTab(AJOUTERJOUEUR, ajoutJoueur);
        tabGestion.addTab(LISTEJOUEUR, listeJoueur);
    }
    
    /**
     * Remise à jour de l'application, en particulier du planning des matchs
     */
    public void updateApp() {
        System.out.println("\tUpdate Planning");
        MatchSimple.updateListMatchSimple(CONNEXION);
        MatchDouble.updateListMatchDouble(CONNEXION);
        jours.stream().forEach((j) -> {
            j.updateJour();
        });
        this.repaint();// Redessine les boutons avec leurs nouveaux intitulés
        this.revalidate();// Remet à jour les boutons des matchs en les affichant
    }
    
    /**
     * Remise à jour de la liste des joueurs ainsi que tous les composants
     * les utilisant.
     */
    public void updatePanelJoueur() {
        System.out.println("\tUpdate liste Joueurs");
        listeJoueur.removeAll();
        Joueur.getListeJoueurs().values().stream().forEach((j) -> {
            listeJoueur.add(new JJoueur(this,j,CONNEXION));
            comboJ.stream().forEach((jc) -> {
                jc.addItem(j.getPrenom()+" "+j.getNom());//On ajoute le joueur sur chaque combo box
            });
        });
        this.repaint();
        this.revalidate();
    }
    
    /**
     * Génération d'un planning prévisionnel à partir des joueurs présents sur la base de donnée
     */
    private void generatePlanning() {
        int i = 0;
        MatchSimple m;
        while (i<Joueur.listeJoueurs.size()) {
            if (i <= 10) {
                m = new MatchSimple("31/01/16",(i/2)%5,"Qualification",Joueur.listeJoueurs.get(i+1),Joueur.listeJoueurs.get(i+2));
            } else {
                m = new MatchSimple("01/02/16",(i/2)%5,"Qualification",Joueur.listeJoueurs.get(i+1),Joueur.listeJoueurs.get(i+2));
            }
                try {
                m.ajouterMatchSimple(CONNEXION);
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(planning,
                m+"\n\nCe match existe déjà.",
                "Erreur de contrainte d'intégrité",
                JOptionPane.ERROR_MESSAGE);
            }
            i+=2;
        }
        updateApp();
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
