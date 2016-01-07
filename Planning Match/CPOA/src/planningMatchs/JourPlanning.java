/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import java.awt.GridLayout;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import metiers.MatchDouble;
import metiers.MatchSimple;

/**
 *
 * @author Ben
 */
public class JourPlanning extends JPanel {
    
    private final JFrame parent;
    private final String date;
    private final Connection connexion;
    private final JPanel matchSimple;
    private final JPanel matchDouble;
    
    public JourPlanning(JFrame parent, String date, Connection conn) {
        this.parent = parent;
        this.date = date;
        this.connexion = conn;
        matchSimple = new JPanel(new GridLayout(5,1));
        matchDouble = new JPanel(new GridLayout(5,1));
        this.add(matchSimple);
        this.add(matchDouble);
    }
    
    public void updateJour() {
        System.out.println("Remise à jour des matchs du "+date);
        //Simple
        matchSimple.removeAll();
        MatchSimple.listeMatchSimple.values().stream().filter((m) -> (m.getDate().equals(date))).forEach((m) -> {
            matchSimple.add(new JMatchSimple(parent, m, connexion));
        });
        //Double
        matchDouble.removeAll();
         MatchDouble.listeMatchDouble.values().stream().filter((m) -> (m.getDate().equals(date))).forEach((m) -> {
            matchDouble.add(new JMatchDouble(parent, m, connexion));
        });
    }
    
}
