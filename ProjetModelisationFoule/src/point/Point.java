package point;

import java.awt.Graphics2D;

public class Point implements Comparable {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0.0, 0.0);
    }

    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance(Point autre) {
        return Math.sqrt((this.x - autre.x) * (this.x - autre.x)
                + (this.y - autre.y) * (this.y - autre.y));
    }

    public void additionnerCoord(Point autre) {
        this.x = this.x + autre.x;
        this.y = this.y + autre.y;
    }

    public void soustraireCoord(Point autre) {
        this.x = this.x - autre.x;
        this.y = this.y - autre.y;
    }

    public void multiplierCoord(double k) {
        this.x = this.x * k;
        this.y = this.y * k;
    }

    public Point symetrique() {
        return new Point(-this.x, -this.y);
    }

    @Override
    public String toString() {
        return "(" + Math.round(x*100.0)/100.0  + ";" + Math.round(y*100.0)/100.0 + ")";
    }

    static private double EPSILON = 1e-14;

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
        Point autre = (Point) obj;
        if (Math.abs(this.x - autre.x) < EPSILON && Math.abs(this.y - autre.y) < EPSILON) {
            return true;
        } else {
            return false;
        }
    }

    public void dessiner(Graphics2D contexte) {
        contexte.fillOval((int) (x - 5), (int) (y - 5), 11, 11);
    }

    @Override
    public int compareTo(Object obj) {
        Point autre = (Point) obj;
        if (this.getAngle() > autre.getAngle()) {
            return 1;
        } else if (this.getAngle() < autre.getAngle()) {
            return -1;
        } else {
            return 0;
        }
    }
}
