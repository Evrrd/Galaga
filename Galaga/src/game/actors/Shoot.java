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
    private double proportion = 0.004;

    private int direction;
    private boolean alive = true;

    public Shoot(double xx, double yy, int ddirection) {
        x = xx;
        y = yy;
        direction = ddirection;

    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getProportion() {
        return proportion;
    }

    public void destroy() {
        alive = false;
    }

    public boolean getAlive() {
        return alive;
    }

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

    public void moving(double k) {
        y += k;
    }

    public void update() {
        y += 0.01 * direction;
    }

}
