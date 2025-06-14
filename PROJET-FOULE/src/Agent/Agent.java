/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Agent;

import point.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import carte.Carte;

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

    public Point getPosition() {
        return position;
    }

    public double getDistance() {
        return direction;
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

    public Color getColor() {
        return this.couleur;
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

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public void setDistance(double direction) {
        this.direction = direction;
    }

    public void direction() {
        this.direction = Math.atan2(this.getCap().getY(), this.getCap().getX());
    }

    public void cap() {
        double x = (this.objectif.getX() - this.position.getX()) / (Math.sqrt((this.objectif.getX() - this.position.getX()) * (this.objectif.getX() - this.position.getX()) + (this.objectif.getY() - this.position.getY()) * (this.objectif.getY() - this.position.getY())));
        double y = (this.objectif.getY() - this.position.getY()) / (Math.sqrt((this.objectif.getX() - this.position.getX()) * (this.objectif.getX() - this.position.getX()) + (this.objectif.getY() - this.position.getY()) * (this.objectif.getY() - this.position.getY())));
        this.cap = new Point(x, y);
    }

    public void avancer() {
        Point nouvellePosition = new Point(this.position.getX() + vmax * this.cap.getX(), this.position.getY() + vmax * this.cap.getY());
        if (!this.estArrive()) {
            this.position = nouvellePosition;
        }
    }

    public void avancer_avec_Carte(Carte carte) {
        Point nouvellePosition = new Point(this.position.getX() + vmax * this.cap.getX(), this.position.getY() + vmax * this.cap.getY());
        if (!this.estArrive()) {
            if (carte.estAccessible(nouvellePosition)) {
                this.position = nouvellePosition;
            }
        } else {
            // Mouvement alternatif aléatoire
            while (!carte.estAccessible(nouvellePosition)) {
                double angleAleatoire = direction + (Math.random() - 0.5) * Math.PI / 2; // tourner jusqu’à 90° à gauche ou droite
                Point capAlternatif = new Point(Math.cos(angleAleatoire), Math.sin(angleAleatoire));
                nouvellePosition = new Point(position.getX() + vmax * capAlternatif.getX(), position.getY() + vmax * capAlternatif.getY());
                cap = capAlternatif;
                direction = angleAleatoire;
                position = nouvellePosition;
            }
        }

    }

    public boolean estDansZoneSortie() {
        return Math.abs(position.getX() - objectif.getX()) <= 50 - rayon && Math.abs(position.getY() - objectif.getY()) <= 25 - rayon;
    }

    public boolean estDansZoneSortieTrain() {
        return Math.abs(position.getX() - objectif.getX()) <= 12 - rayon && Math.abs(position.getY() - objectif.getY()) <= 14 - rayon;
    }

    public boolean estArrive() {
        return Math.abs(position.getX() - objectif.getX()) <= 1 && Math.abs(position.getY() - objectif.getY()) <= 1;
    }

    public void dessinAgent(Graphics2D graph, Color color) {
        double x = position.getX();
        double y = position.getY();
        double x2 = x + cap.getX() * 10;
        double y2 = y + cap.getY() * 10;
        graph.setColor(Color.LIGHT_GRAY);
        graph.drawLine((int) x, (int) y, (int) x2, (int) y2);
        graph.setColor(color);
        graph.fillOval((int) (position.getX() - rayon), (int) (position.getY() - rayon), (int) (2 * rayon), (int) (2 * rayon));
    }

    public boolean rayonLibre(double angle, double longueur, ArrayList<Agent> voisins) {
        double pas = 5.0;
        for (double d = pas; d <= longueur; d += pas) {
            double x = position.getX() + Math.cos(angle) * d;
            double y = position.getY() + Math.sin(angle) * d;
            Point point = new Point(x, y);
            for (Agent autre : voisins) {
                if (autre == this) {
                    continue;
                }
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
            if (rayonLibre(droite, longueurRayon * k, voisins)) {
                return droite;
            }
            if (rayonLibre(gauche, longueurRayon * k, voisins)) {
                return gauche;
            }
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

    public void mettreAJourDirectionAvecCollision(ArrayList<Agent> voisins, double longueurRayon, int k, double lambda) {
        double nouvelleDirection = choisirNouvelleDirection(voisins, longueurRayon, k);
        direction += lambda * (nouvelleDirection - direction);
        this.cap = new Point(Math.cos(direction), Math.sin(direction));
    }

    public void mettreAJourDirectionAvecCollisionEtMur(Carte carte, ArrayList<Agent> voisins, double longueurRayon, int k, double lambda) {
        int compteurBloque = 0;
        double meilleurAngle = choisirNouvelleDirection(voisins, longueurRayon, k);
        if (!peutAvancerDansDirection(meilleurAngle, carte, voisins)) {
            compteurBloque++;
            if (compteurBloque > 2) {
                // Forcer la rotation pour débloquer
                meilleurAngle += Math.PI / 2; // tourner de 45°
                this.avancer();
                this.avancer();
                this.avancer();
                this.avancer();
                compteurBloque = 0;
            }
        } else {
            compteurBloque = 0;
        }
        direction += lambda * (meilleurAngle - direction);
        cap = new Point(Math.cos(direction), Math.sin(direction));
    }

    public void mettreAJourCapDepuisCarte(Carte carte) {
        int x = (int) this.position.getX();
        int y = (int) this.position.getY();
        int dx = carte.getDistance(x + 1, y) - carte.getDistance(x - 1, y);
        int dy = carte.getDistance(x, y + 1) - carte.getDistance(x, y - 1);

        double angle = Math.atan2(-dy, -dx); // négatif car opposé au gradient
        this.direction = angle;
        this.cap = new Point(Math.cos(angle), Math.sin(angle));
    }

    public boolean peutAvancerDansDirection(double angle, Carte carte, ArrayList<Agent> voisins) {
        double nouvelleX = position.getX() + vmax * Math.cos(angle);
        double nouvelleY = position.getY() + vmax * Math.sin(angle);
        Point nouvellePos = new Point(nouvelleX, nouvelleY);

        // Vérifie si la position est libre dans la carte (pas un mur)
        int nbPointsTest = 60;
        double angleStep = 2 * Math.PI / nbPointsTest;

        for (int i = 0; i < nbPointsTest; i++) {
            double testAngle = i * angleStep;
            double testX = nouvelleX + rayon * Math.cos(testAngle);
            double testY = nouvelleY + rayon * Math.sin(testAngle);
            Point testPoint = new Point(testX, testY);
            if (!carte.estAccessible(testPoint)) {
                return false; // collision mur
            }
        }
        // Vérifie la collision avec les autres agents
        for (Agent autre : voisins) {
            if (autre == this) {
                continue;
            }
            if (nouvellePos.distance(autre.getPosition()) < (rayon + autre.getRayon())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agent{" + "position=" + position + ", cap=" + cap + ", objectif=" + objectif + ", rayon=" + rayon + ", vmax=" + vmax + ", couleur=" + couleur + '}';
    }

}
