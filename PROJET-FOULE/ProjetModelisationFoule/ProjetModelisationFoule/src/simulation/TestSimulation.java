/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulation;

import FenetreGraphique.FenetreGraphique;
import java.awt.Color;
import java.awt.Graphics2D;
import Foule.Foule;
import Agent.Agent;
import point.Point;
import java.util.ArrayList;
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
        
        int mode = 3;
        FenetreGraphique fenetre = new FenetreGraphique("Simulation", Simulation.X_MAX, Simulation.Y_MAX);
        Graphics2D contexte = fenetre.getGraphics2D();
        
        Point sortie = new Point(400,300);
        Point sortie2 = new Point(400,300);
        
        Foule foule1 = new Foule(new ArrayList<>(),sortie);
        Foule foule2 = new Foule(new ArrayList<>(),sortie2);
        
        if (mode==1){
            foule1.positionAgentAleatoire(100);
            foule2.positionAgentAleatoire(100);
        }
        
        if (mode==2){
            foule1.positionAgentEnGrille(10,10,70,50,30);
            foule2.positionAgentEnGrille(10,10,400,50,30);
        }
        
        if (mode==3){
            foule1.positionAgentEnCercle(70, new Point(400,300),250);
        }
        
        if (mode==1 || mode == 2){
            contexte.setColor(Color.MAGENTA);
            contexte.drawRect((int)sortie.getX()-50,(int) sortie.getY()-25, 100, 50 );
            for(Agent agent : foule1.getAgents()){
                agent.dessinAgent(contexte);
            }
            contexte.setColor(Color.CYAN);
            contexte.drawRect((int)sortie2.getX()-50,(int) sortie2.getY()-25, 100, 50 );
            for(Agent agent : foule2.getAgents()){
                agent.dessinAgent(contexte);
            }
            fenetre.actualiser();
            Thread.sleep(1500);
            while (!foule1.getAgents().isEmpty() || !foule2.getAgents().isEmpty()) {
                fenetre.effacer();
                contexte.setColor(Color.CYAN);
                contexte.drawRect((int)sortie2.getX()-50,(int) sortie2.getY()-25, 100, 50 );
                contexte.setColor(Color.MAGENTA);
                contexte.drawRect((int)sortie.getX()-50,(int) sortie.getY()-25, 100, 50 );
                
                int i=0;
                
                while (i<foule1.getAgents().size()){
                    ArrayList<Agent> copieAgents = new ArrayList<>(foule1.getAgents());
                    copieAgents.get(i).avancer();
                    ArrayList<Agent> concatener = new ArrayList<>(foule1.getAgents());
                    concatener.addAll(foule2.getAgents());
                    copieAgents.get(i).mettreAJourDirection(concatener, 10, 5, 0.9);
                    foule1.retirerAgents(copieAgents.get(i));
                    if (foule1.getAgents().contains(copieAgents.get(i))) {
                        foule1.getAgents().get(i).dessinAgent(contexte);
                    }       
                    i++; 
                }

                int i2=0;
           
                while (i2<foule2.getAgents().size()){ 
                    ArrayList<Agent> copieAgents2 = new ArrayList<>(foule2.getAgents());
                    copieAgents2.get(i2).avancer();
                    ArrayList<Agent> concatener2 = new ArrayList<>(foule2.getAgents());
                    concatener2.addAll(foule1.getAgents());
                    copieAgents2.get(i2).mettreAJourDirection(concatener2, 10, 5, 0.9);
                    foule2.retirerAgents(copieAgents2.get(i2));
                    if (foule2.getAgents().contains(copieAgents2.get(i2))) {
                        contexte.setColor(Color.CYAN);
                        foule2.getAgents().get(i2).dessinAgent(contexte);
                    } 
                    i2++;  
                }  
                fenetre.actualiser(0.005);
            }
        }
        
        if (mode==3){
            contexte.setColor(Color.orange);
            for(Agent agent : foule1.getAgents()){
                agent.dessinAgent(contexte);
            }
            fenetre.actualiser();
            Thread.sleep(1500);
            ArrayList<Agent> agents = new ArrayList<>();
            while (agents.size()<foule1.getAgents().size()){    
                fenetre.effacer();
                for (Agent agent : foule1.getAgents()) {
                    agent.mettreAJourDirection(foule1.getAgents(), 20, 10, 0.9);
                    agent.avancer();
                    if(!agents.contains(agent) && agent.estArrive()){
                        agents.add(agent);
                    }
                    agent.dessinAgent(contexte);
                }
                fenetre.actualiser(0.005);    
            } 
        }   
    Thread.sleep(3000);
    fenetre.fermer();
    }
}