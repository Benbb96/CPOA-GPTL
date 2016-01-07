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
import metiers.MatchSimple;

/**
 *
 * @author Ben
 */
public class JMatchSimple extends JButton {
    
    private final MatchSimple match;
    
    public JMatchSimple(JFrame parent, MatchSimple m, Connection conn) {
        match = m;
        this.setText("Simple "+match.getRealTime());
        this.addActionListener((ActionEvent ae) -> {
            int option = JOptionPane.showConfirmDialog(JMatchSimple.this, m.affiche(), "Info Match", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                ModifierMatchSimple mms = new ModifierMatchSimple(parent,m.toString(),m,conn);
                MatchSimple m1 = mms.afficheS();
            }
        });
    }
    
}
