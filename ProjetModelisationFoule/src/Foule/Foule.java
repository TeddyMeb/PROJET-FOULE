/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Foule;
import Agent.Agent;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import point.Point;

public class Foule {
    private ArrayList<Agent> agents;
    private Point sortie;

    public Foule(ArrayList<Agent> agents, Point sortie) {
        this.agents = agents;
        this.sortie = sortie;
        Collections.shuffle(agents);
    }
    
    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public Point getSortie() {
        return sortie;
    }

    public void setSortie(Point sortie) {
        this.sortie = sortie;
    }

    @Override
    public String toString() {
        String res = "Foule = \n";
        for(int i = 0; i<this.agents.size();i++){
            res = res + agents.get(i)+ "\n";
        }
        return res;
    }
    
    public void retirerAgents(Agent agent){
        if (agent.estDansZoneSortie()){
            agents.remove(agent);
        }
    }
     
     public void positionAgentAleatoire(int n){
         ArrayList<Agent> agentsAleatoires = new ArrayList<>();
         for (int k =0;k<n;k++){
            double coordonnees_x=50+Math.random()*700;
            double coordonnees_y=50+Math.random()*500;
            Agent agent = new Agent(new Point(coordonnees_x,coordonnees_y),sortie,5,1,Color.BLUE);
            agentsAleatoires.add(agent);
         }
         agents=agentsAleatoires;
    }
    public void positionAgentEnGrille(int lignes, int colonnes, double x_depart, double y_depart, double espace){
        agents.clear();
        for (int i=0; i<lignes; i++) {
            for (int j=0;j<colonnes; j++) {
                double x= x_depart + j*espace;
                double y= y_depart + i*espace;
                Agent agent = new Agent(new Point(x,y),this.sortie,5,1,Color.BLUE);
            agents.add(agent);
            }
        }
    }
    public void positionAgentEnGrille2(ArrayList<Agent> autreFoule, int lignes, int colonnes, double x_depart, double y_depart, double espace){
        agents.clear();
        int compteur=0;
        for (int i=0; i<lignes; i++) {
            for (int j=0;j<colonnes; j++) {
                double x= x_depart + j*espace;
                double y= y_depart + i*espace;
                Point objectif_autre_foule = autreFoule.get(compteur).getPosition();
                Agent agent = new Agent(new Point(x,y),objectif_autre_foule,5,1,Color.BLUE);
                compteur++;
            agents.add(agent);
            }
        }
    }
    public void positionAgentEnCercle(int nbAgents, Point centre, double rayon){
        agents.clear();
        ArrayList<Point> agentsTemporaire = new ArrayList<>();
        for (int i=0; i<nbAgents; i++) {
            double angle = 2*Math.PI*i/nbAgents;
            double x = centre.getX()+rayon*Math.cos(angle);
            double y = centre.getY()+rayon*Math.sin(angle);
            agentsTemporaire.add(new Point(x,y));
        }
        for (int i=0; i<nbAgents; i++){
            Point position = agentsTemporaire.get(i);
            Point objectif = agentsTemporaire.get((i+nbAgents/2)%nbAgents);
            Agent agent = new Agent(position,objectif,5,1,Color.RED);
            agents.add(agent);    
        }
    }

    public void positionner_dans_le_train(double x_depart, double y_depart, double espace,Color couleur){
        agents.clear();
        for (int i=0; i<2; i++) {
            for (int j=0;j<33; j++) {
                double x= x_depart + j*espace;
                double y= y_depart + i*espace;
                Agent agent = new Agent(new Point(x,y),this.sortie,5,3,couleur);
            agents.add(agent);
            }
        }
    }
}
