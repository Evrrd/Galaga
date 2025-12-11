package game.actors;

import engine.StdDraw;
import game.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public class Ennemy {
    private double x; // postion du joueur sur l'axe des abscisses
    private double y; // position du joueur sur l'axe des ordonnées
    private double proportion;
    private double speed;
    private int valeurScore;
    private boolean alive = true; // état de vie de l'ennemi
    private String spriteFile; // chemin du fichier sprite

    public Ennemy(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public Ennemy(double xx, double yy, double proportioEnn, int valSc, double speedEnn) {
        x = xx;
        y = yy;
        proportion = proportioEnn;
        valeurScore = valSc;
        speed = speedEnn;
    }

    /**
     * Dessine les ennemis selon leur sprite
     */
    public void draw() {
        if (!alive) {
            return;
        }

        Path cheminT1 = Paths.get("ressources/sprites/bee.spr");
        try (BufferedReader reader = Files.newBufferedReader(cheminT1)) {
            String line = null;
            double i = 0.0;
            double j = 0.0;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case 'R':
                            StdDraw.setPenColor(StdDraw.RED);
                            break;
                        case 'B':
                            StdDraw.setPenColor(StdDraw.BLUE);
                            break;
                        case 'G':
                            StdDraw.setPenColor(StdDraw.GREEN);
                            break;
                        case 'Y':
                            StdDraw.setPenColor(StdDraw.YELLOW);
                            break;
                        case 'W':
                            StdDraw.setPenColor(StdDraw.WHITE);
                            break;
                        case 'N':
                            StdDraw.setPenColor(StdDraw.BLACK);
                            break;
                    }
                    StdDraw.filledSquare(x + i, y - j, proportion); // change prop
                    i += proportion; // here 2
                }
                i = 0.0;
                j += proportion; // here
            }
        } catch (IOException ieo) {
            System.out.println(ieo);
        }
    }

    // private abstract void move();

    public void destroy() {
        alive = false;
    }

    public void update() {
        if (!alive)
            return;
        // move();
    }

    public boolean isAlive() {
        return alive;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}

/*
 * class Bee extends Ennemy{
 * public Bee(double x, double y, double length, double speedX, double speedY,
 * String spriteFile) {
 * super(x, y, 0.006, 0.004, 0.003, "bee.spr");
 * }
 * 
 * @Override
 * protected void move() {
 * y -= speedY; //déplacement vers le bas
 * x += speedX; //déplacement horizontal
 * 
 * if (x <= 0.008 || x >= 0.920) {
 * speedX = -speedX; //change de direction
 * }
 * 
 * if (x < 0.00) {
 * alive = false; //l'ennemi sort de l'écran
 * }
 * }
 * 
 * }
 */