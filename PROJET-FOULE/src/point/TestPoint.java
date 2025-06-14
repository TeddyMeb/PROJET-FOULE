/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

/**
 *
 * @author guillaume.laurent
 */
public class TestPoint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Point a = new Point(345, 278);
        Point b = new Point(122, 401);

        System.out.println("Coordonnees de a = " + a);
        
        System.out.println("Coordonnees de b = " + b);

        System.out.println("Angle de a = " + a.getAngle());
        
        System.out.println("Symetrique de a = " + a.symetrique());

        System.out.println("Distance ab = " + a.distance(b));

        System.out.println("Egalite de a et b = " + a.equals(b));

        System.out.println("Comparaison a<b = " + a.compareTo(b));

        a.additionnerCoord(b);
        System.out.println("Coordonnees de a apres addition de b = " + a);
        
        a.soustraireCoord(b);
        System.out.println("Coordonnees de a apres soustraction de b = " + a);

    }

}
