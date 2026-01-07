package game.actors;

import engine.StdDraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Shoot {

    private double x;

    private double y;
    private double proportion;

    private int direction;
    private boolean alive;

    public Shoot(double xx, double yy, int ddirection) {
        x = xx;
        y = yy;
        direction = ddirection;
        proportion = 0.004;
        alive = true;
    }

    /**
     * Return coordonné Y
     * 
     * @return double
     */
    public double getY() {
        return y;
    }

    /**
     * Return coordonné X
     * 
     * @return double
     */
    public double getX() {
        return x;
    }

    /**
     * Return proportion
     * 
     * @return double
     */
    public double getProportion() {
        return proportion;
    }

    /**
     * Permet de set le alive à false => tuer le shoot
     */
    public void destroy() {
        alive = false;
    }

    /**
     * Return si le shoot est en vie
     * 
     * @return boolean
     */
    public boolean getAlive() {
        return alive;
    }

    /**
     * draw le shoot
     */
    public void draw() {
        if (alive == true) {
            Path cheminT1 = Paths.get("ressources/sprites/shoot.spr");
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
                        i += proportion; // change propor
                    }
                    i = 0.0;
                    j += proportion; // change propor
                }
            } catch (IOException ieo) {
                System.out.println(ieo);
            }
        }
    }

    /**
     * Avance le shoot mais je l'utilise pas
     * 
     * @param k
     */
    public void moving(double k) {
        y += k;
    }

    /**
     * Update le shoot dans une certaine direction
     */
    public void update() {
        y += 0.01 * direction;
    }

}
