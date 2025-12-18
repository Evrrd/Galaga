package game;

import java.io.BufferedReader;
import java.io.IOException;
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

    public Level levelInst;

    /**
     * Créé un jeu avec tous les éléments qui le composent
     */
    public Game() {
        player = new Player(0.472, 0.2);
        Level(1);

    }

    public String[] parseStrings(String s) {
        if (s == null) {
            return null;
        }
        return s.split(" ");
    }

    public void Level(int a) {
        String s = "level" + a + ".lvl";
        Path cheminT1 = Paths.get("ressources/levels/" + s);
        try (BufferedReader reader = Files.newBufferedReader(cheminT1)) {
            String line = null;

            String[] lineArray = parseStrings(reader.readLine()); // first line
            levelInst = new Level(lineArray[0], lineArray[1], lineArray[2], lineArray[3]);

            while ((line = reader.readLine()) != null) {
                lineArray = parseStrings(line);
                levelInst.makeAddEnnemy(lineArray);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Initialise l'espace de jeu
     */
    private void init() {
        StdDraw.setCanvasSize(Parameter.canvasWidth, Parameter.canvasHeigth);
        StdDraw.enableDoubleBuffering();
        Level(1);
    }

    /**
     * Initialise le jeu et lance la boucle de jeu en temps réel
     */
    public void launch() {
        init();

        while (isGameRunning()) {
            StdDraw.clear(StdDraw.WHITE); // On efface tous ce qu'il y a sur l'interface

            update(); // on met a jour les attributs de chaque éléments
            draw(); // on dessine chaques éléments

            StdDraw.show(); // on montre l'interface
            StdDraw.pause(40); // on attend 30 milisecondes avant de recommencer
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
        drawEnnemy();

    }

    public void drawZoneScore() {
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.line(0, Parameter.line_top_zone, 1, Parameter.line_top_zone);

    }

    public void drawZoneLifeLevel() {
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.line(0, Parameter.line_bottom_zone, 1, Parameter.line_bottom_zone);
    }

    public void drawEnnemy() {
        for (Ennemy e : levelInst.getFormation()) {
            e.draw();
        }
    }

    /**
     * Met a jour les attributs de tous les éléments du jeu
     */
    private void update() {
        player.update();
        levelInst.update(player.getListShootPlayer());
    }
}
