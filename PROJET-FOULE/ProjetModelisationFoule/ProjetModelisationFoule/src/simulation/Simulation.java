package simulation;

import java.util.Random;

public class Simulation {

    // Parametres de la fenêtre800 600
    final static public int X_MIN = 0;
    final static public int X_MAX = 800;
    final static public int Y_MIN = 0;
    final static public int Y_MAX = 600;

    // Parametres des agents
    final static public double RAYON_MIN = 4.0;
    final static public double RAYON_MAX = 5.0;
    final static public double VITESSE_MIN = 0.5;
    final static public double VITESSE_MAX = 1.0;
    
    // Générateur de nombre aléatoire avec une graine fixe pour la reproductibilite
    final static public long GRAINE = 2025;
    final static public Random generateur = new Random(GRAINE);

}