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

public class SimulationAvecFenetreGraphique {

    private Thread simulationThread;
    private FenetreGraphique fenetre;

    public void lancerSimulation(int mode, double longueurRayon, int nombreRayons, double lambda, int nbAgents) {
        simulationThread = new Thread(() -> {
            try {
                switch (mode) {
                    case 1:
                        simulationMode1(lambda, nombreRayons, longueurRayon, nbAgents);
                        return;
                    case 2:
                        simulationMode2(lambda, nombreRayons, longueurRayon);
                        return;
                    case 3:
                        simulationMode3(lambda, nombreRayons, longueurRayon, nbAgents);
                        return;
                    case 4:
                        simulationMode4(lambda, nombreRayons, longueurRayon);
                        return;
                }
            } catch (InterruptedException e) {
                System.out.println("Simulation interrompue");
            }
        });
        simulationThread.start();
    }

    public void stopSimulation() {
        if (simulationThread != null && simulationThread.isAlive()) {
            simulationThread.interrupt();
        }
        if (fenetre != null) {
            fenetre.fermer();
            fenetre = null;
        }
    }

    public void carteDesDistances() {
        Carte carte = new Carte("train1600.png");
        fenetre = new FenetreGraphique("Arrivee d'un train", carte.getLargeur(), carte.getHauteur());
        carte.preparationDeLaCarteDesDistances();
        carte.parcoursEnLargeur(new Point(1150, 155));
        carte.dessiner(fenetre.getGraphics2D());
        fenetre.actualiser();
    }

    private void simulationMode1(double longueurRayon, int nombreRayons, double lambda, int nbAgents) throws InterruptedException {
        Point sortie = new Point(400, 300);
        Point sortie2 = new Point(400, 300);

        Foule foule1 = new Foule(new ArrayList<>(), sortie);
        Foule foule2 = new Foule(new ArrayList<>(), sortie2);

        fenetre = new FenetreGraphique("Simulation", Simulation.X_MAX, Simulation.Y_MAX);
        Graphics2D contexte = fenetre.getGraphics2D();

        foule1.positionAgentAleatoire(nbAgents);
        foule2.positionAgentAleatoire(nbAgents);
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
                copieAgents.get(i).mettreAJourDirectionAvecCollision(concatener, longueurRayon, nombreRayons, lambda);
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
                copieAgents2.get(i2).mettreAJourDirectionAvecCollision(concatener2, longueurRayon, nombreRayons, lambda);
                foule2.retirerAgents(copieAgents2.get(i2));
                if (foule2.getAgents().contains(copieAgents2.get(i2))) {
                    foule2.getAgents().get(i2).dessinAgent(contexte, Color.CYAN);
                }
                i2++;
            }
            fenetre.actualiser(0.005);
        }
    }

    private void simulationMode2(double longueurRayon, int nombreRayons, double lambda) throws InterruptedException {
        Point sortie = new Point(400, 300);
        Point sortie2 = new Point(400, 300);

        Foule foule1 = new Foule(new ArrayList<>(), sortie);
        Foule foule2 = new Foule(new ArrayList<>(), sortie2);

        fenetre = new FenetreGraphique("Simulation", Simulation.X_MAX, Simulation.Y_MAX);
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
                agent.mettreAJourDirectionAvecCollision(foule1.getAgents(), longueurRayon, nombreRayons, lambda);
                agent.avancer();
                if (!agentsArrives1.contains(agent) && agent.estArrive()) {
                    agentsArrives1.add(agent);
                }
                agent.dessinAgent(contexte, Color.MAGENTA);
            }
            for (Agent agent : foule2.getAgents()) {
                agent.mettreAJourDirectionAvecCollision(foule1.getAgents(), longueurRayon, nombreRayons, lambda);
                agent.avancer();
                if (!agentsArrives2.contains(agent) && agent.estArrive()) {
                    agentsArrives2.add(agent);
                }
                agent.dessinAgent(contexte, Color.CYAN);
            }

            fenetre.actualiser(0.005);
        }
    }

    private void simulationMode3(double longueurRayon, int nombreRayons, double lambda, int nbAgents) throws InterruptedException {
        Point sortie = new Point(400, 300);
        Foule foule1 = new Foule(new ArrayList<>(), sortie);
        foule1.positionAgentEnCercle(nbAgents, sortie, 250);

        fenetre = new FenetreGraphique("Simulation", Simulation.X_MAX, Simulation.Y_MAX);
        Graphics2D contexte = fenetre.getGraphics2D();
        for (Agent agent : foule1.getAgents()) {
            agent.dessinAgent(contexte, Color.orange);
        }
        fenetre.actualiser();
        Thread.sleep(1500);

        ArrayList<Agent> agentsArrives = new ArrayList<>();
        while (agentsArrives.size() < foule1.getAgents().size()) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            fenetre.effacer();
            for (Agent agent : foule1.getAgents()) {
                agent.mettreAJourDirectionAvecCollision(foule1.getAgents(), longueurRayon, nombreRayons, lambda);
                agent.avancer();
                if (!agentsArrives.contains(agent) && agent.estArrive()) {
                    agentsArrives.add(agent);
                }
                agent.dessinAgent(contexte, Color.orange);
            }
            fenetre.actualiser(0.005);
        }
    }

    private void simulationMode4(double longueurRayon, int nombreRayons, double lambda) throws InterruptedException {
        Carte carte = new Carte("train1600.png");
        carte.preparationDeLaCarteDesDistances();
        Point sortie = new Point(1150, 155);
        carte.parcoursEnLargeur(sortie);

        Foule foule1 = new Foule(new ArrayList<>(), sortie);
        foule1.positionner_dans_le_train(38, 17, 14.2, Color.RED);
        Foule foule2 = new Foule(new ArrayList<>(), sortie);
        foule2.positionner_dans_le_train(570, 17, 14.2, Color.BLUE);
        Foule foule3 = new Foule(new ArrayList<>(), sortie);
        foule3.positionner_dans_le_train(1100, 17, 14.2, Color.GREEN);

        fenetre = new FenetreGraphique("Simulation Train", carte.getLargeur(), carte.getHauteur());
        Graphics2D contexte = fenetre.getGraphics2D();
        contexte.setColor(Color.BLACK);
        carte.dessiner(contexte);
        fenetre.actualiser();
        Thread.sleep(1000);

        while (!foule1.getAgents().isEmpty() || !foule2.getAgents().isEmpty() || !foule3.getAgents().isEmpty()) {
            if (Thread.currentThread().isInterrupted()) {
                return;
            }

            fenetre.effacer();
            carte.dessiner(contexte);

            ArrayList<Agent> tousLesAgents = new ArrayList<>();
            tousLesAgents.addAll(foule1.getAgents());
            tousLesAgents.addAll(foule2.getAgents());
            tousLesAgents.addAll(foule3.getAgents());

            Consumer<Foule> avancerFoule = (foule) -> {
                ArrayList<Agent> agents = new ArrayList<>(foule.getAgents());
                for (Agent agent : agents) {
                    agent.mettreAJourCapDepuisCarte(carte);
                    agent.mettreAJourDirectionAvecCollisionEtMur(carte, tousLesAgents, longueurRayon, nombreRayons, lambda);
                    agent.avancer_avec_Carte(carte);
                    if (agent.estDansZoneSortieTrain()) {
                        foule.retirerAgents(agent);
                    } else {
                        agent.dessinAgent(contexte, agent.getColor());
                    }
                }
            };

            contexte.setColor(Color.RED);
            avancerFoule.accept(foule1);
            contexte.setColor(Color.BLUE);
            avancerFoule.accept(foule2);
            contexte.setColor(Color.GREEN);
            avancerFoule.accept(foule3);

            fenetre.actualiser(0.001);
        }
    }
}
