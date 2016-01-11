/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import metiers.Joueur;

/**
 *
 * @author Ben
 */
public class JJoueur extends JButton {
    
    private final Joueur joueur;
    
    public JJoueur(Gestionnaire parent, Joueur j, Connection conn) {
        joueur = j;
        this.setText(joueur.prenomNom());
        this.addActionListener((ActionEvent ae) -> {
            int option = JOptionPane.showConfirmDialog(parent, j, "Info Joueur", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                ModifierJoueur mj = new ModifierJoueur(parent,j.toString(),j,conn);
            }
        });
    }
    
}
