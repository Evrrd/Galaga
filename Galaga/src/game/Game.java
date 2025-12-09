package game;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import engine.StdDraw;
import game.actors.Ennemy;
import game.actors.Player;

/**
 * Classe du jeu principal.
 * Gère la création de l'espace de jeu et la boucle de jeu en temps réel.
 */
public class Game {
    public Player player; // Jouer, seul éléments actuellement dans notre jeu

    public Ennemy[] tabEnnemy;

    /**
     * Créé un jeu avec tous les éléments qui le composent
     */
    public Game() {
        player = new Player(0.452, 0.2);
        // Level(a);

    }

    /*
     * public void Level(int a) {
     * String s = "level" + a + ".spr";
     * Path cheminT1 = Paths.get("ressources/sprites/" + s);
     * try (BufferedReader reader = Files.newBufferedReader(cheminT1)) {
     * String line = null;
     * while ((line = reader.readLine()) != null) {
     * 
     * }
     * }
     * }
     */

    /**
     * Initialise l'espace de jeu
     */
    private void init() {
        StdDraw.setCanvasSize(Parameter.canvasWidth, Parameter.canvasHeigth);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Initialise le jeu et lance la boucle de jeu en temps réel
     */
    public void launch() {
        init();

        while (isGameRunning()) {
            StdDraw.clear(StdDraw.BLACK); // On efface tous ce qu'il y a sur l'interface

            update(); // on met a jour les attributs de chaque éléments
            draw(); // on dessine chaques éléments

            StdDraw.show(); // on montre l'interface
            StdDraw.pause(50); // on attend 30 milisecondes avant de recommencer
        }
    }

    /**
     * Condition d'arrêt du jeu
     * 
     * @return true car on n'as pas encore de conidtions d'arrêt
     */
    private boolean isGameRunning() {
        return true;
    }

    /**
     * Dessin tous les éléments du jeu
     */
    public void draw() {
        drawZoneScore();
        drawZoneLifeLevel();
        player.draw();

    }

    public void drawZoneScore() {
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.line(0, Parameter.line_top_zone, 1, Parameter.line_top_zone);

    }

    public void drawZoneLifeLevel() {
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.line(0, Parameter.line_bottom_zone, 1, Parameter.line_bottom_zone);
    }

    /**
     * Met a jour les attributs de tous les éléments du jeu
     */
    private void update() {
        player.update();
    }
}
