/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Agent;
import point.Point;
import java.awt.Color;
import FenetreGraphique.FenetreGraphique;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

public class Agent {
    private Point position;
    private Point cap;
    private Point objectif;
    private double rayon;
    private double vmax;
    private Color couleur;

    public Agent(Point position, Point objectif, double rayon, double vmax, Color couleur) {
        this.position = position;
        this.objectif = objectif;
        this.rayon = rayon;
        this.vmax = vmax;
        this.couleur = couleur;
        this.cap();
    }

    public Point getPosition() {
        return position;
    }

    public Point getCap() {
        return cap;
    }

    public Point getObjectif() {
        return objectif;
    }

    public double getRayon() {
        return rayon;
    }

    public double getVmax() {
        return vmax;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setCap(Point cap) {
        this.cap = cap;
    }

    public void setObjectif(Point objectif) {
        this.objectif = objectif;
    }

    @Override
    public String toString() {
        return "Agent{" + "position=" + position + ", cap=" + cap + ", objectif=" + objectif + ", rayon=" + rayon + ", vmax=" + vmax + ", couleur=" + couleur + '}';
    }
    
    public void cap(){
        double x = (this.objectif.getX()-this.position.getX())/(Math.sqrt((this.objectif.getX()-this.position.getX())*(this.objectif.getX()-this.position.getX()) + (this.objectif.getY()-this.position.getY())*(this.objectif.getY()-this.position.getY())));
        double y = (this.objectif.getY()-this.position.getY())/(Math.sqrt((this.objectif.getX()-this.position.getX())*(this.objectif.getX()-this.position.getX()) + (this.objectif.getY()-this.position.getY())*(this.objectif.getY()-this.position.getY())));
        this.cap = new Point(x,y);
    }
    
    public void avancer(){
        if (!this.estArrive())  
            this.position = new Point(this.position.getX()+vmax*this.cap.getX(),this.position.getY()+vmax*this.cap.getY());
    }
    public boolean estDansZoneSortie() {
        return Math.abs(position.getX()-objectif.getX())<=50-rayon && Math.abs(position.getY()-objectif.getY())<=25-rayon;
    }
    public boolean estArrive(){
        return Math.abs(position.getX()-objectif.getX())<=0.1 && Math.abs(position.getY()-objectif.getY())<=0.1;
    }
    public void dessinAgent(Graphics2D graph){
        graph.fillOval((int)(position.getX()-rayon),(int)(position.getY()-rayon),(int)(2*rayon),(int)(2*rayon));
        double x = position.getX();
        double y = position.getY();
        double x2= x+cap.getX()*20;
        double y2= y+cap.getY()*20;
        graph.drawLine((int)x,(int)y,(int)x2,(int)y2);
    }
}


