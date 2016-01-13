/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservation;

import connexion.ConfigConnexion;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import metiers.Joueur;
import metiers.Match;
import metiers.MatchDouble;
import metiers.MatchSimple;
import planningMatchs.Gestionnaire;
import planningMatchs.JComboDate;

/**
 * Interface à destination des joueurs pour qu'il puisse réserver un court d'entraînement
 * @author Ben
 */
public class ReservationCourt extends JFrame{
    
    private final Connection CONNEXION;
    
    private JPanel authentification;
    private JPanel reservation;
    private JPanel disponibilite;
    
    JPanel deconnection;
    private Joueur j;
    private JTextField prenom, nom;
    private JLabel bonjour;

    public ReservationCourt(Connection conn)  {
        super();
        this.CONNEXION = conn;
        //Mise à jour des joueurs et des matchs par rapport à la base de donnée
        Joueur.updateListJoueurs(CONNEXION);
        MatchSimple.updateListMatchSimple(conn);
        MatchDouble.updateListMatchDouble(conn);
        build(); //On construit les fenêtres
    }
    
    private void build() {
        this.setTitle("Réservation Court");
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel application = new JPanel(new BorderLayout());
        JLabel titre = new JLabel(new ImageIcon("images/titre.png"));
        authentification();
        reservation();
        disponibilite();
        application.add(titre, BorderLayout.NORTH);
        JPanel screens = new JPanel();
        screens.add(authentification);
        screens.add(reservation);
        screens.add(disponibilite);
        application.add(screens,BorderLayout.CENTER);
        
        deconnection = new JPanel();
        JButton deconnect = new JButton("Déconnection");
        deconnect.addActionListener((ActionEvent ae) -> {
            prenom.setText("");
            nom.setText("");
            reservation.setVisible(false);
            deconnection.setVisible(false);
            authentification.setVisible(true);
        });
        deconnection.add(Box.createHorizontalGlue());
        deconnection.add(deconnect);
        
        application.add(deconnection,BorderLayout.SOUTH);
        deconnection.setVisible(false);
        reservation.setVisible(false);
        disponibilite.setVisible(false);
        this.setContentPane(application);
    }
        
    private void authentification() {
        authentification = new JPanel();
        authentification.setLayout(new BoxLayout(authentification,BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Veuillez vous identifier :");
        JPanel head = new JPanel();
        head.add(label);
        head.add(Box.createHorizontalGlue());
        authentification.add(head);
        authentification.add(Box.createVerticalStrut(30));
        
        //Champ Prénom
        JPanel champPrenom = new JPanel();
        champPrenom.add(new JLabel("Prénom"));
        champPrenom.add(Box.createHorizontalGlue());
        prenom = new JTextField(30);
        champPrenom.add(prenom);
        authentification.add(champPrenom);
        authentification.add(Box.createVerticalStrut(10));
        
        //Champ Nom
        JPanel champNom = new JPanel();
        champNom.add(new JLabel("Nom"));
        champNom.add(Box.createHorizontalGlue());
        nom = new JTextField(30);
        champNom.add(nom);
        authentification.add(champNom);
        authentification.add(Box.createVerticalStrut(10));
        
        //Champ Erreur
        JPanel champErreur = new JPanel();
        JLabel erreur = new JLabel();
        champErreur.add(erreur);
        JButton clear = new JButton("Ok");
        clear.addActionListener((ActionEvent ae) -> {
            champErreur.setVisible(false);//On efface le champs
        });
        champErreur.add(clear);
        champErreur.setVisible(false);
        authentification.add(champErreur);
        authentification.add(Box.createVerticalStrut(20));
        
        //Bouton de validation
        JPanel validation = new JPanel();
        JButton valider = new JButton("Valider");
        this.getRootPane().setDefaultButton(valider); //On attribue la touche entrée à ce bouton
        valider.addActionListener((ActionEvent ae) -> {
            String p = prenom.getText();
            String n = nom.getText();
            if (p.equals("") || n.equals("")){
                erreur.setText("Veuillez remplir les champs.");
                champErreur.setVisible(true);
            } else if (Joueur.contains(p, n)) { //Si le joueur appartient bien à la base de donnée
                j = Joueur.getJoueur(p, n);//On le récupère
                authentification.setVisible(false);
                bonjour.setText("Bonjour "+p+", choisissez un crénau horaire :");
                reservation.setVisible(true);
                deconnection.setVisible(true);
            } else {
                erreur.setText("Vous n'êtes pas enregistré dans la base.");
                prenom.setText("");
                nom.setText("");
                champErreur.setVisible(true);
            }
        });
        validation.add(Box.createHorizontalGlue());
        validation.add(valider);
        authentification.add(validation);
        authentification.setBorder(BorderFactory.createTitledBorder("Authentification"));
    }
    
    private void reservation() {
        reservation = new JPanel();
        reservation.setLayout(new BoxLayout(reservation,BoxLayout.PAGE_AXIS));
        
        bonjour = new JLabel();
        JPanel head = new JPanel();
        head.add(bonjour);
        head.add(Box.createHorizontalGlue());
        reservation.add(head);
        reservation.add(Box.createVerticalStrut(30));
        
        JPanel date = new JPanel();
        date.add(new JLabel("Date"));
        date.add(Box.createHorizontalGlue());
        JComboDate jDate = new JComboDate();
        date.add(jDate);
        reservation.add(date);
        reservation.add(Box.createVerticalStrut(30));
        
        JPanel heure = new JPanel();
        heure.add(new JLabel("Heure"));
        heure.add(Box.createGlue());
        JRadioButton h1,h2,h3,h4,h5;
        h1 = new JRadioButton("8h");
        h1.setSelected(true);
        h2 = new JRadioButton("11h");
        h3 = new JRadioButton("15h");
        h4 = new JRadioButton("18h");
        h5 = new JRadioButton("21h");
        ButtonGroup bg = new ButtonGroup();
        bg.add(h1);
        bg.add(h2);
        bg.add(h3);
        bg.add(h4);
        bg.add(h5);
        heure.add(h1);
        heure.add(h2);
        heure.add(h3);
        heure.add(h4);
        heure.add(h5);
        reservation.add(heure);
        reservation.add(Box.createVerticalStrut(30));
        
        JPanel validation = new JPanel();
        JButton valider = new JButton("Valider");
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String date = jDate.getSelectedItem().toString();
                int heure = getHeure();
                try (ResultSet rset = ConfigConnexion.executeRequete(CONNEXION, "Select idjoueur from reservation where date_reservation='"+date+"' and heure="+heure)) {
                    int i = 0;
                    while (rset.next()) {
                        System.out.println("Le joueur qui a réservé le court "+(i+3)+" est :"+Joueur.listeJoueurs.get(rset.getInt(1)));
                        i++;
                    }
                    switch (i) {
                        case 0 : ajouterResa(date,heure,3);break;
                        case 1 : ajouterResa(date,heure,4);break;
                        case 2 : ajouterResa(date,heure,5);break;
                        case 3 : ajouterResa(date,heure,6);break;
                        case 4 : JOptionPane.showMessageDialog(null,
                                    "Nous sommes navrés mais il n'y a plus aucun courts de disponible ce jour-ci à cette horaire.",
                                    "Plus de courts disponible",
                                    JOptionPane.ERROR_MESSAGE);
                        default : System.err.println("Erreur dans le switch");
                    }
                } catch (SQLException e) {
                    System.err.println("Erreur SQL");
                }
            }
            
            public void ajouterResa(String date, int heure, int court) {
                try (ResultSet nb = ConfigConnexion.executeRequete(CONNEXION,
                    "Insert Into Reservation Values ("+court+","+j.getIdJoueur()+",'"+date+"',"+heure+")")) {
                    JOptionPane.showMessageDialog(null,
                        "Vous avez réservé le court "+court+" le "+date+" à "+Match.getRealTime(heure)+"h.\n\nVous allez maintenant être déconnecté.",
                        "Réservation réussie",
                        JOptionPane.INFORMATION_MESSAGE);
                    reservation.setVisible(false);
                    authentification.setVisible(true);
                } catch (SQLIntegrityConstraintViolationException e) {
                    JOptionPane.showMessageDialog(null,
                        "Vous avez déjà réservé un court à cette même horaire !",
                        "Impossible de réserver",
                        JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    System.err.println("Erreur SQL");
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
        validation.add(Box.createHorizontalGlue());
        validation.add(valider);
        reservation.add(validation);
        
        reservation.setBorder(BorderFactory.createTitledBorder("Réservation"));
    }
    
    private void disponibilite() {
        disponibilite = new JPanel();
        disponibilite.setLayout(new BoxLayout(disponibilite,BoxLayout.PAGE_AXIS));
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connexion = ConfigConnexion.getConnection("connexion.properties");
                ReservationCourt appli = new ReservationCourt(connexion); // On crée l'objet Gestionnaire qui instancie la fenêtre
                appli.setVisible(true);// Et on la rend visible quand l'ordinateur est prêt
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Gestionnaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
