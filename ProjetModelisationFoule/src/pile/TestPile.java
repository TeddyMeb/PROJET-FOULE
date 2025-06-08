/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pile;

import java.util.PriorityQueue;

/**
 *
 * @author guillaume.laurent
 */
public class TestPile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PriorityQueue<Noeud> pile = new PriorityQueue<>();

        pile.add(new Noeud(1, 2, 0.0));
        pile.add(new Noeud(3, 4, 10.0));
        pile.add(new Noeud(5, 6, 5.0));
        pile.add(new Noeud(5, 6, 25.0));
    
        System.out.println(pile.poll());
        System.out.println(pile.poll());
        System.out.println(pile.poll());
        System.out.println(pile.poll());
    }

}
