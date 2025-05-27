/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carte;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author guillaume.laurent
 */
public class Carte {

    private BufferedImage image;

    public Carte(String nomDuFichier) {
        try {
            image = ImageIO.read(new File(nomDuFichier));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void dessiner(Graphics2D contexte) {
        contexte.drawImage(image, 0, 0, null);
    }

    public int getLargeur() {
        return image.getWidth();
    }

    public int getHauteur() {
        return image.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }
    
    

}
