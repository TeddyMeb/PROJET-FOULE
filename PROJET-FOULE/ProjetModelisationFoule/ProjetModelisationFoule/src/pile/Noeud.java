/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pile;

/**
 *
 * @author guillaume.laurent
 */
public class Noeud implements Comparable {

    public int x, y;
    public double distance;

    public Noeud(int x, int y, double distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public int compareTo(Object obj) {
        Noeud autre = (Noeud) obj;
        if (distance < autre.distance) {
            return -1;
        } else if (distance > autre.distance) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Noeud other = (Noeud) obj;
        return Double.doubleToLongBits(this.distance) == Double.doubleToLongBits(other.distance);
    }

    @Override
    public String toString() {
        return "Noeud{" + "x=" + x + ", y=" + y + ", distance=" + distance + '}';
    }

}
