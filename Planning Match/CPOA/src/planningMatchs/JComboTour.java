/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planningMatchs;

import javax.swing.JComboBox;

/**
 *
 * @author Ben
 */
public class JComboTour extends JComboBox {
    public JComboTour() {
        super();
        this.addItem("Qualification");
        this.addItem("1/16");
        this.addItem("1/8");
        this.addItem("Quart de finale");
        this.addItem("Demi-finale");
        this.addItem("Finale");
    }
}
