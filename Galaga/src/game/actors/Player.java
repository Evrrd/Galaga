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
    public Shoot sa;
    public Shoot sb;
    public Shoot sc;

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
        Parameter.shoot_one_go = false;
        Parameter.shoot_two_go = false;
        Parameter.shoot_three_go = false;

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

                    StdDraw.filledSquare(x + i, y - j, Parameter.proportion_player);
                    i += Parameter.proportion_player;
                }
                i = 0.0;
                j += Parameter.proportion_player;
            }
        } catch (IOException ieo) {
            System.out.println(ieo);
        }

        if (sa != null) {
            sa.draw();
        }
        if (sb != null) {
            sb.draw();
        }
        if (sc != null) {
            sc.draw();
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
            if (x - speed > Parameter.proportion_player) {
                x -= speed;
            }
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            if (x + speed < 1 - Parameter.proportion_player * Parameter.player_square_len) {
                x += speed;
            }
        }

        if (StdDraw.isKeyPressed(32)) {
            if (Parameter.shoot_one_go == false) {
                sa = new Shoot(
                        x + (Parameter.player_square_len / 2) * Parameter.proportion_player
                                - (Parameter.shoot_width / 2) * Parameter.proportion_player,
                        y + (Parameter.player_square_len / 2) * Parameter.proportion_player, 1);
                Parameter.shoot_one_go = true;

            } else if (Parameter.shoot_two_go == false) {
                sb = new Shoot(
                        x + (Parameter.player_square_len / 2) * Parameter.proportion_player
                                - (Parameter.shoot_width / 2) * Parameter.proportion_player,
                        y + (Parameter.player_square_len / 2) * Parameter.proportion_player, 1);
                Parameter.shoot_two_go = true;

            } else if (Parameter.shoot_three_go == false) {
                sc = new Shoot(
                        x + (Parameter.player_square_len / 2) * Parameter.proportion_player
                                - (Parameter.shoot_width / 2) * Parameter.proportion_player,
                        y + (Parameter.player_square_len / 2) * Parameter.proportion_player, 1);
                Parameter.shoot_three_go = true;
            }

        }
        if (sa != null) {
            sa.update();
            if (sa.getY() + 0.002 > 0.9) {
                sa = null;
                Parameter.shoot_one_go = false;
            }
        }
        if (sb != null) {
            sb.update();
            if (sb.getY() + 0.002 > 0.9) {
                sb = null;
                Parameter.shoot_two_go = false;

            }
        }
        if (sc != null) {
            sc.update();
            if (sc.getY() + 0.002 > 0.9) {
                sc = null;
                Parameter.shoot_three_go = false;
            }
        }

    }
}
