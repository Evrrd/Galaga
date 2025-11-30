package game.actors;

import engine.StdDraw;

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
    private double length; // largeur du joueur
    private static double percent = 0.005;

    /**
     * Créé un joueur.
     * 
     * @param x      postion du joueur sur l'axe des abscisses
     * @param y      position du joueur sur l'axe des ordonnées
     * @param length largeur du joueur
     */
    public Player(double x, double y, double length) {
        this.x = x;
        this.y = y;
        this.length = length;
    }

    /**
     * Dessine le joueur, ici c'est un rond rouge
     */
    public void draw() {

        Path cheminT1 = Paths.get("Galaga/ressources/sprites/ship.spr");
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
                    StdDraw.filledSquare(x + i, y - j, percent);
                    i += percent;
                }
                i = 0.0;
                j += percent;
            }
        } catch (IOException ieo) {
            System.out.println(ieo);
        }

        // StdDraw.setPenColor(StdDraw.RED);

    }
    
    public void shooting(double speed) {
        Shoot sa = new Shoot(x + 0.0235, y + 0.05, 0.006);
        sa.draw();
        
    }

    /**
     * Met à jour la position du joueur en fonction des touches préssé.
     */
    public void update() {
        double speed = 0.01; // vitesse de déplacement du joueur
        // Si la flèche gauche est préssé
        if (StdDraw.isKeyPressed(37)) {
            if (x - speed > 0.008) {
                x -= speed;
            }
        }
        // Si la flèche droite est préssé
        if (StdDraw.isKeyPressed(39)) {
            if (x + speed < 0.920) {
                x += speed;
            }
        }
        if (StdDraw.isKeyPressed(32)) {
            shooting(speed);
        }
    }
}
