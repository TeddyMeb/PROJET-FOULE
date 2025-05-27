/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package carte;

import FenetreGraphique.FenetreGraphique;
import simulation.Simulation;

/**
 *
 * @author guillaume.laurent
 */
public class TestCarte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Carte carte = new Carte("train1600.png");
        
        FenetreGraphique fenetre = new FenetreGraphique("Arrivee d'un train", carte.getLargeur(), carte.getHauteur());
        
        carte.dessiner(fenetre.getGraphics2D());  
        
        System.out.println("Valeur du pixel en (0;0) = " + carte.getImage().getRGB(0, 0));
        
        System.out.println("Valeur du pixel en (1160;160) = " + carte.getImage().getRGB(1160,160));
        
        fenetre.actualiser();
        
    }
    
}
