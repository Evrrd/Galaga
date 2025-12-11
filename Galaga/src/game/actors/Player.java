package game.actors;

import engine.StdDraw;
import game.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

/**
 * Classe représentant le jouuer.
 * A ce stade cen'est qu'un point rouge qui se déplace avec les flèches du
 * clavier.
 */
public class Player {
    private double x; // postion du joueur sur l'axe des abscisses
    private double y; // position du joueur sur l'axe des ordonnées
    // private static Parameter param = new Parameter();

    private double proportionPlayer = 0.004;
    private int lengthPlayer = 15;

    private Shoot[] listShootPlayer;

    /**
     * Créé un joueur.
     * 
     * @param x      postion du joueur sur l'axe des abscisses
     * @param y      position du joueur sur l'axe des ordonnées
     * @param length largeur du joueur
     */
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        listShootPlayer = new Shoot[3];

    }

    /**
     * Dessine le joueur, ici c'est un rond rouge
     */
    public void draw() {

        Path cheminT1 = Paths.get("ressources/sprites/ship.spr");
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

                    StdDraw.filledSquare(x + i, y - j, proportionPlayer);
                    i += proportionPlayer;
                }
                i = 0.0;
                j += proportionPlayer;
            }
        } catch (IOException ieo) {
            System.out.println(ieo);
        }

        if (listShootPlayer[0] != null) {
            listShootPlayer[0].draw();
        }
        if (listShootPlayer[1] != null) {
            listShootPlayer[1].draw();
        }
        if (listShootPlayer[2] != null) {
            listShootPlayer[2].draw();
        }

        // StdDraw.setPenColor(StdDraw.RED);

    }

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update() {
        double speed = 0.01; // vitesse de déplacement du joueur
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) {
            if (x - speed > proportionPlayer) {
                x -= speed;
            }
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            if (x + speed < 1 - proportionPlayer * lengthPlayer) {
                x += speed;
            }
        }

        if (StdDraw.isKeyPressed(32)) {

            if (listShootPlayer[0] == null) {
                listShootPlayer[0] = new Shoot(
                        x + (lengthPlayer / 2) * proportionPlayer
                                - (1 / 2) * proportionPlayer,
                        y + (lengthPlayer / 2) * proportionPlayer, 1);

            } else if (listShootPlayer[1] == null) {
                listShootPlayer[1] = new Shoot(
                        x + (lengthPlayer / 2) * proportionPlayer
                                - (1 / 2) * proportionPlayer,
                        y + (lengthPlayer / 2) * proportionPlayer, 1);

            } else if (listShootPlayer[2] == null) {
                listShootPlayer[2] = new Shoot(
                        x + (lengthPlayer / 2) * proportionPlayer
                                - (1 / 2) * proportionPlayer,
                        y + (lengthPlayer / 2) * proportionPlayer, 1);
            }

        }

        if (listShootPlayer[0] != null) {
            listShootPlayer[0].update();
            if (listShootPlayer[0].getY() + 0.002 > 0.9) {
                listShootPlayer[0] = null;
            }
        }
        if (listShootPlayer[1] != null) {
            listShootPlayer[1].update();
            if (listShootPlayer[1].getY() + 0.002 > 0.9) {
                listShootPlayer[1] = null;

            }
        }
        if (listShootPlayer[2] != null) {
            listShootPlayer[2].update();
            if (listShootPlayer[2].getY() + 0.002 > 0.9) {
                listShootPlayer[2] = null;
            }
        }

    }
}
