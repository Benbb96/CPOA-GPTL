/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import metiers.MatchDouble;

/**
 *
 * @author Ben
 */
public class JMatchDouble extends JButton {
    
    private final MatchDouble match;
    
    public JMatchDouble(Gestionnaire parent, MatchDouble m, Connection conn) {
        match = m;
        this.setText(match.getRealTime());
        this.addActionListener((ActionEvent ae) -> {
            int option = JOptionPane.showConfirmDialog(parent, m.affiche(), "Info Match", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                ModifierMatchDouble mms = new ModifierMatchDouble(parent,m.toString(),m,conn);
            }
        });
    }
}
