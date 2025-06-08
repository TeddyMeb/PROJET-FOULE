/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package carte;
import java.awt.Color;

import FenetreGraphique.FenetreGraphique;
import point.Point;


/**
 *
 * @author guillaume.laurent
 */
public class TestCarte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Carte carte = new Carte("C:/Users/teddy/OneDrive/Documents/PROJET INFO BLEU/PROJET-FOULE-main/ProjetModelisationFoule/ProjetModelisationFoule/train1600.png");
        
        FenetreGraphique fenetre = new FenetreGraphique("Arrivee d'un train", carte.getLargeur(), carte.getHauteur());
        System.out.println(carte.getHauteur());
        System.out.println(carte.getLargeur());
          
        fenetre.getGraphics2D().setColor(Color.RED);
        fenetre.getGraphics2D().fillOval(1150,155,5,5);
        System.out.println("Valeur du pixel en (0;0) = " + carte.getImage().getRGB(0, 0));
        
        System.out.println("Valeur du pixel en (300;200) = " + carte.getImage().getRGB(300,200));
        
        fenetre.actualiser();
        
        carte.preparationDeLaCarteDesDistances();
        carte.parcoursEnLargeur(new Point(1150, 155)); 
        carte.dessiner(fenetre.getGraphics2D());
        fenetre.getGraphics2D().setColor(Color.RED);
        fenetre.getGraphics2D().fillRect(1150,155,12,14); 
        fenetre.actualiser();
    }
    
}
