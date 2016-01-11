/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import java.awt.BorderLayout;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import metiers.Joueur;

/**
 *
 * @author Ben
 */
class ModifierJoueur extends JDialog {
    
    private final Connection connexion;
    private final Gestionnaire parent;
    
    private final Joueur j;
    
    private JTextField choixNom, choixPrenom;
    private JComboBox choixNat;

    public ModifierJoueur(Gestionnaire parent, String title, Joueur j, Connection conn) {
        //On appelle le construteur de JDialog correspondant
        super(parent, title);
        this.parent = parent;
        this.j = j;
        this.connexion = conn;
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        this.initComponent();
        this.setVisible(true); 
    }
    
    private void initComponent(){
        JPanel nomPrenom = new JPanel();
        choixNom = new JTextField(30);
        choixNom.setText(j.getNom());
        choixPrenom = new JTextField(25);
        choixPrenom.setText(j.getPrenom());
        nomPrenom.setBorder(BorderFactory.createTitledBorder("Nom et Prénom"));
        nomPrenom.add(new JLabel("Nom : "));
        nomPrenom.add(choixNom);
        nomPrenom.add(new JLabel("Prénom : "));
        nomPrenom.add(choixPrenom);
        
        JPanel nationalite = new JPanel();
        choixNat = new JComboBox(Joueur.getLISTENAT());
        choixNat.setSelectedItem(j.getNationalite());
        nationalite.setBorder(BorderFactory.createTitledBorder("Nationalité"));
        nationalite.add(new JLabel("Nationalié : "));
        nationalite.add(choixNat);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        content.add(nomPrenom);
        content.add(nationalite);

        JPanel control = new JPanel();
        JButton okBouton = new JButton("Valider");

        okBouton.addActionListener((ActionEvent arg0) -> {
            j.setNom(choixNom.getText());
            j.setPrenom(choixPrenom.getText());
            j.setNationalite((String)choixNat.getSelectedItem());
            
            try {
                String requete = "Update Joueur Set nom=?, prenom=?, nationalite=?Where idJoueur=?";
                PreparedStatement prepared = connexion.prepareStatement(requete);
                prepared.setString(1,j.getNom());
                prepared.setString(2,j.getPrenom());
                prepared.setString(3,j.getNationalite());
                prepared.setInt(4,j.getIdJoueur());
                int result = prepared.executeUpdate();
                System.out.println(result + " ligne(s) modifiée(s)");
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(parent,
                        "Problème de contrainte.",
                        "SQLIntegrityConstraintViolationException",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLSyntaxErrorException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);
            parent.updatePanelJoueur();
            
        });
        
        JButton deleteBouton = new JButton("Supprimer le joueur");
        deleteBouton.addActionListener((ActionEvent ae) -> {
            try {
                String requete = "Delete From Joueur Where idJoueur=?";
                PreparedStatement prepared = connexion.prepareStatement(requete);
                prepared.setInt(1,j.getIdJoueur());
                int result = prepared.executeUpdate();
                System.out.println(result + " ligne(s) modifiée(s)");
                Joueur remove = Joueur.listeJoueurs.remove(j.getIdJoueur());
                parent.updatePanelJoueur();
                JOptionPane.showMessageDialog(parent,
                    "Le joueur"+remove.prenomNom()+" a bien été supprimé.",
                    "Suppresion réussie",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLSyntaxErrorException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(parent,
                    "Impossible de supprimer "+j.prenomNom()+".\n\nVeuillez supprimer les matchs auxquels il participe.",
                    "Erreur de contrainte d'intégrité",
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);
        });
        
        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener((ActionEvent arg0) -> {
            setVisible(false);      
        });
        
        control.add(okBouton);
        control.add(deleteBouton);
        control.add(cancelBouton);

        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);
    }
    
}
