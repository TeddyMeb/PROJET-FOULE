/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Foule;
import Agent.Agent;
import point.Point;
import java.awt.Color;
import java.util.ArrayList;


public class TestFoule {

    
    public static void main(String[] args) {
        Agent agent1 = new Agent(new Point(12,20),new Point(3,6),1,0.9,Color.BLUE);
        Agent agent2 = new Agent(new Point(1,14),new Point(8,9),6,0.526,Color.RED);
        Agent agent3 = new Agent(new Point(8,10),new Point(25,13),0.8,0.01,Color.BLACK);
        Agent agent4 = new Agent(new Point(1,4),new Point(56,9),4,0.28,Color.GREEN);
        ArrayList<Agent> agents = new ArrayList<>();
        agents.add(agent1);
        agents.add(agent2);
        agents.add(agent3);
        agents.add(agent4);
        Foule foule1 = new Foule(agents, new Point(500,14));
        System.out.println(foule1);
        foule1.positionAgentAleatoire(10);
        System.out.println(foule1);
        Foule foule2 = new Foule(agents,new Point(500,14));
        foule2.positionAgentAleatoire(5);
    }  
}
