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
public class Arbitre extends Personne {
    
    private int idArbitre = 0;
    private static int lastId;

    public Arbitre(String nom, String prenom, String nationalite) {
        super(nom, prenom, nationalite);
        this.idArbitre = ++lastId;
        lastId = idArbitre;
    }
    
    public int getIdArbitre() {return idArbitre;}
    public void setIdArbitre(int idArbitre) {this.idArbitre = idArbitre;}
    
}
