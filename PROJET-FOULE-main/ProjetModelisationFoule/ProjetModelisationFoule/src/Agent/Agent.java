/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Agent;
import point.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Agent {
    private Point position;
    private Point cap;
    private Point objectif;
    private double rayon;
    private double vmax;
    private Color couleur;
    private double direction;

    public Agent(Point position, Point objectif, double rayon, double vmax, Color couleur) {
        this.position = position;
        this.objectif = objectif;
        this.rayon = rayon;
        this.vmax = vmax;
        this.couleur = couleur;
        this.cap();
        this.direction();
    }

    public void direction(){
        this.direction=Math.atan2(this.getCap().getY(),this.getCap().getX());
    }

    public Point getPosition() {
        return position;
    }

    public double getDistance(){
        return direction;
    }

    public void setDistance(double direction){
        this.direction=direction;
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
        return Math.abs(position.getX()-objectif.getX())<=1 && Math.abs(position.getY()-objectif.getY())<=1;
    }
    public void dessinAgent(Graphics2D graph){
        graph.fillOval((int)(position.getX()-rayon),(int)(position.getY()-rayon),(int)(2*rayon),(int)(2*rayon));
        double x = position.getX();
        double y = position.getY();
        double x2= x+cap.getX()*20;
        double y2= y+cap.getY()*20;
        graph.drawLine((int)x,(int)y,(int)x2,(int)y2);
    }
    public boolean rayonLibre(double angle, double longueur, ArrayList<Agent> voisins) {
        double pas = 5.0;
        for (double d = pas; d <= longueur; d += pas) {
            double x = position.getX() + Math.cos(angle) * d;
            double y = position.getY() + Math.sin(angle) * d;
            Point point = new Point(x, y);

            for (Agent autre : voisins) {
                if (autre == this) continue;
                if (point.distance(autre.getPosition()) < this.rayon * 2) {
                    return false; 
                }
            }
        }
    return true; 
    }

    public double choisirNouvelleDirection(ArrayList<Agent> voisins, double longueurRayon, int k) {
        double angleObjectif = this.getAngleDirectionVersObjectif();
        int nombreRayons = 30;
        double angleStep = Math.PI / 30;

        // Critère 1
        if (rayonLibre(angleObjectif, longueurRayon * k, voisins)) {
            return angleObjectif;
        }

        // Critère 2
        for (int i = 1; i <= nombreRayons; i++) {
            double droite = angleObjectif + i * angleStep;
            double gauche = angleObjectif - i * angleStep;
            if (rayonLibre(droite, longueurRayon * k, voisins)) return droite;
            if (rayonLibre(gauche, longueurRayon * k, voisins)) return gauche;
        }

        // Critère 3
        double meilleurAngle = angleObjectif;
        int maxRayonsLibres = 0;
        for (int i = -nombreRayons; i <= nombreRayons; i++) {
            double angle = angleObjectif + i * angleStep;
            int libres = 0;
            while (rayonLibre(angle, longueurRayon * (libres + 1), voisins)) {
                libres++;
            }
            if (libres > maxRayonsLibres) {
                maxRayonsLibres = libres;
                meilleurAngle = angle;
            }
        }
        return meilleurAngle;
    }
    
    public double getAngleDirectionVersObjectif() {
    double dx = objectif.getX() - position.getX();
    double dy = objectif.getY() - position.getY();
    return Math.atan2(dy, dx);
    }
    
    public void mettreAJourDirection(ArrayList<Agent> voisins, double longueurRayon, int k, double lambda) {
        double nouvelleDirection = choisirNouvelleDirection(voisins, longueurRayon, k);
        direction += lambda * (nouvelleDirection - direction);
        this.cap= new Point(Math.cos(direction),Math.sin(direction));
    }
    
}


