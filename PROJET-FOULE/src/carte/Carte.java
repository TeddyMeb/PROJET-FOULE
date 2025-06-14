package carte;

import colormap.Colormap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;


import javax.imageio.ImageIO;
import pile.Noeud;
import point.Point;

public class Carte {

    private BufferedImage image;
    private double [][] distance;

    public Carte(String nomDuFichier) {
        try {
            image = ImageIO.read(new File(nomDuFichier));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void dessiner(Graphics2D contexte) {
        contexte.drawImage(image, 0, 0, null);
    }

    public int getLargeur() {
        return image.getWidth();
    }

    public int getHauteur() {
        return image.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }
    
    public int getDistance(int x, int y){
        return (int) distance[x][y];
    }

    public void preparationDeLaCarteDesDistances() {
        distance = new double[image.getWidth()][image.getHeight()];
        for (int col = 0; col < image.getWidth(); col++) {
            for (int ligne = 0; ligne < image.getHeight(); ligne++) {
                if (image.getRGB(col, ligne) == image.getRGB(0, 0)) {
                    distance[col][ligne] = Double.POSITIVE_INFINITY;
                } else {
                    distance[col][ligne] = -1.0;
                }
            }
        }
    }
    /*
    public void afficherCarteDesDistances() {
        for (int ligne = 0; ligne < distance[0].length; ligne++) {
            for (int col = 0; col < distance.length; col++) {
                System.out.printf("%.2f ", distance[col][ligne]); // Affiche avec 2 décimales
            }
        System.out.println(); // Retour à la ligne après chaque ligne
        }
    }
    */

    public void parcoursEnLargeur(Point sortie) {
        ArrayDeque<Noeud> pile = new ArrayDeque<>();
        boolean[][] visite = new boolean[distance.length][distance[0].length]; //Liste des pixels, visité = true, pas visité = false
        int x0 = (int) sortie.getX();
        int y0 = (int) sortie.getY();
        pile.addLast(new Noeud(x0, y0, 0.0));
        visite[x0][y0] = true;
        while (!pile.isEmpty()) {
            Noeud noeud = pile.pollFirst();
            int x = noeud.x;
            int y = noeud.y;
            double d = noeud.distance;
            if (x >= 0 && x < distance.length && y >= 0 && y < distance[0].length && distance[x][y]<0) {
                distance[x][y] = d;
                // Ajouter voisin de gauche
                if (x > 0 && !visite[x - 1][y]) {
                    pile.addLast(new Noeud(x - 1, y, d + 1));
                    visite[x - 1][y] = true;
                }
                // Ajouter voisin de droite            
                if (x < distance.length - 1 && !visite[x + 1][y]) {
                    pile.addLast(new Noeud(x + 1, y, d + 1));
                    visite[x + 1][y] = true;
                }
                // Ajouter voisin du haut 
                if (y > 0 && !visite[x][y - 1]) {
                    pile.addLast(new Noeud(x, y - 1, d + 1));
                    visite[x][y - 1] = true;
                }
                // Ajouter voisin du bas
                if (y < distance[0].length - 1 && !visite[x][y + 1]) {
                    pile.addLast(new Noeud(x, y + 1, d + 1));
                    visite[x][y + 1] = true;
                }
            }
        }
        // Trouver la distance maximale pour normaliser les couleurs
        double max = 0.0;
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < distance[0].length; j++) {
                if (distance[i][j] < Double.POSITIVE_INFINITY && distance[i][j] > max) {
                    max = distance[i][j];
                }
            }
        }
        // Colorier en fonction de la distance
        Colormap colormap = new Colormap(Colormap.GRAYSCALE);
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < distance[0].length; j++) {
                if (distance[i][j] < Double.POSITIVE_INFINITY) {
                    Color couleur = colormap.getColor(distance[i][j] / max);
                    image.setRGB(i, j, couleur.getRGB());
                }
            }
        }
    }

    public boolean estAccessible(Point p) {
        Color couleur = new Color(image.getRGB((int)p.getX(), (int)p.getY()));
        return !(couleur.equals(Color.BLACK)); // accessible si pas noir
    }   
}
