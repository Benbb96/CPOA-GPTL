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
public class RamasseurBalle extends Personne {
    
    private int idRamasseur =0;

    public RamasseurBalle(String nom, String prenom, String nationalite) {
        super(nom, prenom, nationalite);
        this.idRamasseur = idRamasseur++;
    }
    
    public int getIdRamasseur() {return idRamasseur;}
    public void setIdRamasseur(int idRamasseur) {this.idRamasseur = idRamasseur;}
    
}
