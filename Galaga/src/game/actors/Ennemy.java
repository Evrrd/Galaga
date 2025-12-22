package game.actors;

import engine.StdDraw;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public class Ennemy {
    private String name;
    protected double x; // postion du joueur sur l'axe des abscisses
    protected double y; // position du joueur sur l'axe des ordonnées
    protected double proportion;
    protected double speed;
    protected int valeurScore;
    protected boolean alive = true; // état de vie de l'ennemi
    protected boolean canAction;
    protected int indiceFormation;

    public Ennemy(double xx, double yy) {
        x = xx;
        y = yy;
    }

    public Ennemy(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn, int iF) {
        name = n;
        x = xx;
        y = yy;
        proportion = (proportioEnn / 10) - 0.002;
        valeurScore = valSc;
        speed = speedEnn;
        canAction = false;
        indiceFormation = iF;
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

    public void setAlive(boolean b) {
        alive = b;
    }

    public double getProportion() {
        return proportion;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean getCanAction() {
        return canAction;
    }

    public void setCanAction(boolean b) {
        canAction = b;
    }

    public int getIndice() {
        return indiceFormation;
    }

    /**
     * Dessine les ennemis selon leur sprite
     */
    public void draw() {
        if (!alive) {
            return;
        }
        Path cheminT1;

        switch (name) {
            case "Bee":
                cheminT1 = Paths.get("ressources/sprites/bee.spr");
                break;

            case "Moth":
                cheminT1 = Paths.get("ressources/sprites/catcher.spr");
                break;

            case "Butterfly":
                cheminT1 = Paths.get("ressources/sprites/butterfly.spr");
                break;
            default:
                cheminT1 = Paths.get("ressources/sprites/ship.spr");
                break;
        }

        try (BufferedReader reader = Files.newBufferedReader(cheminT1)) {
            String line;
            double i = 0.0;
            double j = 0.0;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case 'R' -> {
                            StdDraw.setPenColor(StdDraw.RED);
                            break;
                        }
                        case 'B' -> {
                            StdDraw.setPenColor(StdDraw.BLUE);
                            break;
                        }
                        case 'G' -> {
                            StdDraw.setPenColor(StdDraw.GREEN);
                            break;
                        }
                        case 'Y' -> {
                            StdDraw.setPenColor(StdDraw.YELLOW);
                            break;
                        }
                        case 'W' -> {
                            StdDraw.setPenColor(StdDraw.WHITE);
                            break;
                        }
                        case 'N' -> {
                            StdDraw.setPenColor(StdDraw.BLACK);
                            break;
                        }
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
        canAction = false;
        alive = false;
    }

}
