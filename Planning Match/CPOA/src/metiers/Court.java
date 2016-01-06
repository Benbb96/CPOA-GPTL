/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metiers;

/**
 *
 * @author Ben
 */
public class Court {
    
    private int idCourt = 0;
    private String type; //tournoi ou entrainement
    
    public Court(String type) {
        idCourt = idCourt++;
        this.type = type;
    }
    
    public int getIdCourt() {return idCourt;}
    public String getType() {return type;}
    public void setIdCourt(int idCourt) {this.idCourt = idCourt;}
    public void setType(String type) {this.type = type;}
}
