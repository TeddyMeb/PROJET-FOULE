/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulation;

import FenetreGraphique.FenetreGraphique;
import java.awt.Color;
import java.awt.Graphics2D;
import Foule.Foule;
import carte.Carte;
import Agent.Agent;
import point.Point;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author teddy.mebarki
 */
public class TestSimulation {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        int mode = 4;

        Point sortie = new Point(750, 500);
        Point sortie2 = new Point(750, 500);

        Foule foule1 = new Foule(new ArrayList<>(), sortie);
        Foule foule2 = new Foule(new ArrayList<>(), sortie2);

        if (mode == 1) {
            foule1.positionAgentAleatoire(100);
            foule2.positionAgentAleatoire(100);
        }

        if (mode == 2) {
            ArrayList<Agent> copieAgentFoule1 = new ArrayList<>(foule1.getAgents());
            Foule copieFoule1 = new Foule(copieAgentFoule1, foule1.getSortie());
            copieFoule1.positionAgentEnGrille(10, 10, 50, 50, 30);
            ArrayList<Agent> copieAgentFoule2 = new ArrayList<>(foule2.getAgents());
            Foule copieFoule2 = new Foule(copieAgentFoule2, foule2.getSortie());
            copieFoule2.positionAgentEnGrille(10, 10, 480, 50, 30);
            foule1.positionAgentEnGrille2(copieAgentFoule2, 10, 10, 50, 50, 30);
            foule1.positionAgentEnGrille2(copieAgentFoule1, 10, 10, 480, 50, 30);
        }

        if (mode == 3) {
            foule1.positionAgentEnCercle(100, new Point(400, 300), 250);
        }

        if (mode == 2) {
            FenetreGraphique fenetre = new FenetreGraphique("Simulation", Simulation.X_MAX, Simulation.Y_MAX);
            Graphics2D contexte = fenetre.getGraphics2D();
            ArrayList<Agent> copieAgentFoule1 = new ArrayList<>(foule1.getAgents());
            Foule copieFoule1 = new Foule(copieAgentFoule1, foule1.getSortie());
            copieFoule1.positionAgentEnGrille(18, 10, 30, 30, 30);
            ArrayList<Agent> copieAgentFoule2 = new ArrayList<>(foule2.getAgents());
            Foule copieFoule2 = new Foule(copieAgentFoule2, foule2.getSortie());
            copieFoule2.positionAgentEnGrille(18, 10, 460, 30, 30);
            foule1.positionAgentEnGrille2(copieAgentFoule2, 18, 10, 30, 45, 30);
            foule2.positionAgentEnGrille2(copieAgentFoule1, 18, 10, 460, 30, 30);

            for (Agent agent : foule1.getAgents()) {
                agent.dessinAgent(contexte, Color.MAGENTA);
            }
            for (Agent agent : foule2.getAgents()) {
                agent.dessinAgent(contexte, Color.CYAN);
            }
            fenetre.actualiser();
            Thread.sleep(1500);
            ArrayList<Agent> agentsArrives1 = new ArrayList<>();
            ArrayList<Agent> agentsArrives2 = new ArrayList<>();
            while (agentsArrives1.size() < foule1.getAgents().size() || agentsArrives2.size() < foule2.getAgents().size()) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                fenetre.effacer();
                for (Agent agent : foule1.getAgents()) {
                    agent.mettreAJourDirectionAvecCollision(foule1.getAgents(), 10, 5, 0.9);
                    agent.avancer();
                    if (!agentsArrives1.contains(agent) && agent.estArrive()) {
                        agentsArrives1.add(agent);
                    }
                    agent.dessinAgent(contexte, Color.MAGENTA);
                }
                for (Agent agent : foule2.getAgents()) {
                    agent.mettreAJourDirectionAvecCollision(foule1.getAgents(), 10, 5, 0.9);
                    agent.avancer();
                    if (!agentsArrives2.contains(agent) && agent.estArrive()) {
                        agentsArrives2.add(agent);
                    }
                    agent.dessinAgent(contexte, Color.CYAN);
                }

                fenetre.actualiser(0.005);
            }
        }

        if (mode == 1) {
            FenetreGraphique fenetre = new FenetreGraphique("Simulation", Simulation.X_MAX, Simulation.Y_MAX);
            Graphics2D contexte = fenetre.getGraphics2D();

            foule1.positionAgentAleatoire(100);
            foule2.positionAgentAleatoire(100);
            contexte.setColor(Color.MAGENTA);
            contexte.drawRect((int) foule1.getSortie().getX() - 50, (int) foule1.getSortie().getY() - 35, 100, 70);
            contexte.setColor(Color.CYAN);
            contexte.drawRect((int) foule2.getSortie().getX() - 50, (int) foule2.getSortie().getY() - 35, 100, 70);

            for (Agent agent : foule1.getAgents()) {
                agent.dessinAgent(contexte, Color.MAGENTA);
            }
            for (Agent agent : foule2.getAgents()) {
                agent.dessinAgent(contexte, Color.CYAN);
            }
            fenetre.actualiser();
            Thread.sleep(1500);
            while (!foule1.getAgents().isEmpty() || !foule2.getAgents().isEmpty()) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                fenetre.effacer();
                int i = 0;
                contexte.setColor(Color.MAGENTA);
                contexte.drawRect((int) foule1.getSortie().getX() - 50, (int) foule1.getSortie().getY() - 35, 100, 70);
                contexte.setColor(Color.CYAN);
                contexte.drawRect((int) foule2.getSortie().getX() - 50, (int) foule2.getSortie().getY() - 35, 100, 70);
                while (i < foule1.getAgents().size()) {
                    ArrayList<Agent> copieAgents = new ArrayList<>(foule1.getAgents());
                    copieAgents.get(i).avancer();
                    ArrayList<Agent> concatener = new ArrayList<>(foule1.getAgents());
                    concatener.addAll(foule2.getAgents());
                    copieAgents.get(i).mettreAJourDirectionAvecCollision(concatener, 10, 5, 0.9);
                    foule1.retirerAgents(copieAgents.get(i));
                    if (foule1.getAgents().contains(copieAgents.get(i))) {
                        foule1.getAgents().get(i).dessinAgent(contexte, Color.MAGENTA);
                    }
                    i++;
                }

                int i2 = 0;
                while (i2 < foule2.getAgents().size()) {
                    ArrayList<Agent> copieAgents2 = new ArrayList<>(foule2.getAgents());
                    copieAgents2.get(i2).avancer();
                    ArrayList<Agent> concatener2 = new ArrayList<>(foule2.getAgents());
                    concatener2.addAll(foule1.getAgents());
                    copieAgents2.get(i2).mettreAJourDirectionAvecCollision(concatener2, 10, 5, 0.9);
                    foule2.retirerAgents(copieAgents2.get(i2));
                    if (foule2.getAgents().contains(copieAgents2.get(i2))) {
                        foule2.getAgents().get(i2).dessinAgent(contexte, Color.CYAN);
                    }
                    i2++;
                }
                fenetre.actualiser(0.005);
            }
        }

        if (mode == 3) {
            FenetreGraphique fenetre = new FenetreGraphique("Simulation", Simulation.X_MAX, Simulation.Y_MAX);
            Graphics2D contexte = fenetre.getGraphics2D();
            for (Agent agent : foule1.getAgents()) {
                agent.dessinAgent(contexte, Color.orange);
            }
            fenetre.actualiser();
            Thread.sleep(1500);
            ArrayList<Agent> agents = new ArrayList<>();
            while (agents.size() < foule1.getAgents().size()) {
                fenetre.effacer();
                for (Agent agent : foule1.getAgents()) {
                    agent.mettreAJourDirectionAvecCollision(foule1.getAgents(), 20, 10, 0.9);
                    agent.avancer();
                    if (!agents.contains(agent) && agent.estArrive()) {
                        agents.add(agent);
                    }
                    agent.dessinAgent(contexte, Color.orange);
                }
                fenetre.actualiser(0.005);
            }
            Thread.sleep(3000);
            fenetre.fermer();
        }
        if (mode == 4) {
            // Initialisation de la carte et calcul de la carte des distances
            Carte carte = new Carte("train1600.png");
            carte.preparationDeLaCarteDesDistances();
            Point sortie3 = new Point(1150, 155);
            carte.parcoursEnLargeur(sortie3);

            // compartiment 1 : zone gauche
            Foule foule1_train = new Foule(new ArrayList<>(), sortie3);
            foule1_train.positionner_dans_le_train(38, 17, 14.2, Color.RED);

            // compartiment 2 : zone centrale
            Foule foule2_train = new Foule(new ArrayList<>(), sortie3);
            foule2_train.positionner_dans_le_train(570, 17, 14.2, Color.BLUE);

            // compartiment 3 : zone droite
            Foule foule3_train = new Foule(new ArrayList<>(), sortie3);
            foule3_train.positionner_dans_le_train(1100, 17, 14.2, Color.GREEN);
            FenetreGraphique fenetre = new FenetreGraphique("Simulation Train", carte.getLargeur(), carte.getHauteur());
            Graphics2D contexte = fenetre.getGraphics2D();
            contexte.setColor(Color.BLACK);
            carte.dessiner(contexte); // dessiner la carte une fois
            fenetre.actualiser();
            Thread.sleep(1000);

            // Boucle principale : tant qu'il reste des agents dans au moins une foule
            while (!foule1_train.getAgents().isEmpty() || !foule2_train.getAgents().isEmpty() || !foule3_train.getAgents().isEmpty()) {
                fenetre.effacer();
                carte.dessiner(contexte); // dessiner fond à chaque frame

                ArrayList<Agent> tousLesAgents = new ArrayList<>();
                tousLesAgents.addAll(foule1_train.getAgents());
                tousLesAgents.addAll(foule2_train.getAgents());
                tousLesAgents.addAll(foule3_train.getAgents());

                // Fonction lambda pour avancer une foule avec collisions et gradient
                Consumer<Foule> avancerFoule = (foule) -> {
                    ArrayList<Agent> agents = new ArrayList<>(foule.getAgents()); // Copie
                    int i = 0;
                    while (i < agents.size()) {
                        Agent agent = agents.get(i);
                        // 1. Calculer la direction selon la carte des distances
                        agent.mettreAJourCapDepuisCarte(carte);
                        // 2. Évitement collisions 
                        agent.mettreAJourDirectionAvecCollisionEtMur(carte, tousLesAgents, 10, 5, 0.9);
                        // 3. Avancer si possible (test mur dans avancer)
                        agent.avancer_avec_Carte(carte);
                        // 4. Retirer agent arrivé
                        if (agent.estDansZoneSortieTrain()) {
                            foule.retirerAgents(agent);
                        } else {
                            agent.dessinAgent(contexte, agent.getColor());
                        }
                        i++;
                    }
                };
                contexte.setColor(Color.RED);
                avancerFoule.accept(foule1_train);
                contexte.setColor(Color.BLUE);
                avancerFoule.accept(foule2_train);
                contexte.setColor(Color.GREEN);
                avancerFoule.accept(foule3_train);
                fenetre.actualiser(0.001);
            }
            Thread.sleep(2000);
            fenetre.fermer();
        }
    }
}
