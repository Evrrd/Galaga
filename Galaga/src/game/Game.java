package game;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import engine.StdDraw;
import game.actors.Ennemy;
import game.actors.Player;

/**
 * Classe du jeu principal.
 * Gère la création de l'espace de jeu et la boucle de jeu en temps réel.
 */
public class Game {
    public Player player; // Jouer, seul éléments actuellement dans notre jeu

    public Level levelInst; // Instance du level
    private int nbLevel; // Nombre du niveau actuel
    private int timeMissile; // timer prochain missile
    private int timeAttaque; // timer prochaine attaque
    private int bestScore; // meilleur score

    private double[] tabpluieX; // Coordonée x de la pluie arrière plan
    private double[] tabpluieY; // Coordonée y de la pluie arrière plan

    /**
     * Créé un jeu avec tous les éléments qui le composent
     */
    public Game() {
        player = new Player(0.472, 0.2);
        bestScore = takeBestScore();
        nbLevel = 1;
        timeMissile = 0;
        timeAttaque = 0;
        tabpluieX = new double[100];
        tabpluieY = new double[100];

        Random r = new Random();
        for (int a = 0; a < 100; a++) {
            tabpluieX[a] = r.nextInt(0, 100) * 0.01;
        }
        for (int b = 0; b < 100; b++) {
            tabpluieY[b] = r.nextInt(0, 100) * 0.01;
        }

    }

    /**
     * Parse String, sers pas à grand chose juste utiliser split aurait aussi autant
     * servi
     * mais pourquoi pas
     * 
     * @param s
     * @return String[]
     */
    public String[] parseStrings(String s) {
        if (s == null) {
            return null;
        }
        return s.split(" ");
    }

    /**
     * Prends le meilleur score dans le fichier Highscore et le renvoie
     * 
     * @return int
     */
    public int takeBestScore() {
        Path cheminBS = Paths.get("ressources/highscore/highscore.sc");
        try (BufferedReader reader = Files.newBufferedReader(cheminBS)) {
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Va chercher la formation du level dans le fichier level
     * et crée envoie les données à la classe Level
     * 
     * @param a
     */
    public void Level(int a) {

        String s = "level" + a + ".lvl";
        Path cheminT1 = Paths.get("ressources/levels/" + s);

        try (BufferedReader reader = Files.newBufferedReader(cheminT1)) {
            String line = null;

            String[] lineArray = parseStrings(reader.readLine()); // first line
            levelInst = new Level(lineArray[0], lineArray[1], lineArray[2], lineArray[3]);
            int indice = 0;
            while ((line = reader.readLine()) != null) {
                lineArray = parseStrings(line);
                levelInst.makeAddEnnemy(lineArray, indice);
                indice += 1;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        levelInst.initShooter();
    }

    /**
     * Initialise l'espace de jeu
     */
    private void init() {
        StdDraw.setCanvasSize(700, 750);
        StdDraw.enableDoubleBuffering();
        Level(1);
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

            if ((player.getALive() == false)) {
                lose();

                if (player.getNbLife() != 0) {
                    for (int i = 3; i > 0; i--) {
                        drawLoseOnce(i);
                        StdDraw.show();
                        StdDraw.pause(1000);
                    }
                }
            }

            if (levelInst.getNombreRestantEnnemi() == 0) {
                win();
            }
            StdDraw.show(); // on montre l'interface

            // }
            StdDraw.pause(30); // on attend 30 milisecondes avant de recommencer
            timeMissile += 30; // to change
            timeAttaque += 30;
            if (timeMissile > 600) {
                player.setCanShoot(true);
            }

            if (timeMissile > levelInst.getCooldownMissile()) {// levelInst.getCooldownMissile())
                levelInst.makeEnnemyShoot();
                timeMissile = 0;
            }
            if ((timeAttaque > levelInst.getCooldownAttaque()) && (levelInst.getCooldownAttaque() != -1)) {

                levelInst.makeEnnemyAttack();
                timeAttaque = 0;

            }

        }
    }

    /**
     * Toute les actions nécessaire quand le joueur se fait toucher, en gros
     * remettre tout à sa place et
     * enlever les tirs qui trainent sur le terrain
     * 
     * @return boolean
     */
    public boolean lose() {
        player.setToZListShootPlayer();
        levelInst.setBackEnnemi();
        player.setAlive(true);
        player.setX(0.472);
        player.decNbLife();
        return false;
    }

    /**
     * Écran lorsque le joueur à perdu (n'a plus de vie)
     */
    public void loseScreen() {

        while (!StdDraw.isKeyPressed(32)) {

            StdDraw.clear(StdDraw.BLACK);

            StdDraw.setPenColor(StdDraw.RED);

            for (int c = 0; c < 100; c++) {
                tabpluieY[c] = tabpluieY[c] - 0.001;
                if (tabpluieY[c] < 0) {
                    tabpluieY[c] = 1;
                }
                StdDraw.filledSquare(tabpluieX[c], tabpluieY[c], 0.004);
            }

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledRectangle(0.5, 0.5, 0.25, 0.25);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0.5, 0.5, 0.235, 0.235);

            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(0.46, 0.6, "Your score  : ");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.6, 0.6, player.getScore() + " !");

            if (player.getScore() > bestScore) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.text(0.5, 0.5, "WOW !! New HighScore :D");
                try {
                    FileWriter writer = new FileWriter("ressources/highscore/highscore.sc");
                    writer.write(player.getScore() + "");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (player.getScore() > bestScore / 2) {
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.text(0.5, 0.5, "Cool :)");
            } else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.text(0.5, 0.5, "Could be better but okay.. :l");
            }

            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(0.45, 0.4, "Push ");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.56, 0.4, " ESPACE ");
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(0.5, 0.37, " to start another game");

            StdDraw.show();
            StdDraw.pause(30);
        }

        player = new Player(0.472, 0.2);
        Level(1);
        nbLevel = 1;
        bestScore = takeBestScore();
        StdDraw.clear(StdDraw.BLACK);
        draw();
        StdDraw.show();
        StdDraw.pause(2000);

    }

    /**
     * Toutes les actions nécessaire lorsque le player a gagné
     */
    public void win() {
        drawWin(nbLevel);
        StdDraw.show();
        levelInst.setBackEnnemi();
        player.setToZListShootPlayer();
        StdDraw.pause(2000);
        nbLevel += 1;
        Level(nbLevel);
        StdDraw.clear(StdDraw.BLACK);
        draw();
        StdDraw.show();
        StdDraw.pause(1000);
    }

    /**
     * Écran de la victoire
     */
    public void BigWinScreen() {
        while (!StdDraw.isKeyPressed(32)) {

            StdDraw.clear(StdDraw.BLACK);

            StdDraw.setPenColor(StdDraw.BLUE);

            for (int c = 0; c < 100; c++) {
                tabpluieY[c] = tabpluieY[c] + 0.001;
                if (tabpluieY[c] > 1) {
                    tabpluieY[c] = 0;
                }
                StdDraw.filledSquare(tabpluieX[c], tabpluieY[c], 0.004);
            }

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledRectangle(0.5, 0.5, 0.25, 0.25);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0.5, 0.5, 0.235, 0.235);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.5, 0.65, "Bravo ! Incroyable !");
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(0.46, 0.6, "!! Your score  : ");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.6, 0.6, player.getScore() + " !");

            if (player.getScore() > bestScore) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.text(0.5, 0.5, "WOW !! New highscore :D");
                try {
                    FileWriter writer = new FileWriter("ressources/highscore/highscore.sc");
                    writer.write(player.getScore() + "");
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (player.getScore() > bestScore / 2) {
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.text(0.5, 0.5, "Cool ! :)");
            } else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.text(0.5, 0.5, "Could've done better. :l");
            }

            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(0.45, 0.4, "Push ");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.56, 0.4, " ESPACE ");
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.text(0.5, 0.37, " to start again");

            StdDraw.show();
            StdDraw.pause(30);
        }

        player = new Player(0.472, 0.2);
        Level(1);
        nbLevel = 1;
        bestScore = takeBestScore();
        StdDraw.clear(StdDraw.BLACK);
        draw();
        StdDraw.show();
        StdDraw.pause(1000);

    }

    /**
     * Condition d'arrêt du jeu
     * 
     * @return true car on n'as pas encore de conidtions d'arrêt
     */
    private boolean isGameRunning() {

        if (player.getNbLife() == 0) {
            loseScreen();
        } else if (nbLevel == 5) {
            BigWinScreen();
        }
        return true;

    }

    /**
     * Dessin tous les éléments du jeu
     */
    public void draw() {
        drawZoneScore();
        drawZoneLifeLevel();
        drawStars();
        player.draw();
        drawEnnemy();
        levelInst.drawEnnemyShoot();

    }

    /**
     * Dessin de la zone du score
     */
    public void drawZoneScore() {
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.line(0, 0.9, 1, 0.9);

        int score = player.getScore();
        int highScore;
        if (score > bestScore) {
            highScore = score;
        } else {
            highScore = bestScore;
        }

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.1, 0.97, "Score");
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.text(0.1, 0.93, score + "");

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5, 0.97, "HighScore");
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.text(0.5, 0.93, highScore + "");
    }

    /**
     * Dessin de la zone du bas, de la vie du joueur et des niveaux
     */
    public void drawZoneLifeLevel() {
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.line(0, 0.1, 1, 0.1);
        for (int i = 0; i < player.getNbLife(); i++) {
            Player p = new Player(0.05 + i * 0.1, 0.075);
            p.draw();
        }
        for (int k = 0; k < nbLevel; k++) {
            Path cheminT1 = Paths.get("ressources/sprites/level.spr");
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
                        StdDraw.filledSquare(0.95 + i - k * 0.05, 0.075 - j, 0.004);
                        i += 0.004;
                    }
                    i = 0.0;
                    j += 0.004;
                }
            } catch (IOException ieo) {
                System.out.println(ieo);
            }
        }
    }

    /**
     * Dessin des étoiles en arrière plan
     */
    public void drawStars() {
        StdDraw.setPenColor(StdDraw.YELLOW);

        for (int c = 0; c < 100; c++) {
            tabpluieY[c] = tabpluieY[c] - 0.001;
            if (tabpluieY[c] < 0) {
                tabpluieY[c] = 1;
            }
            StdDraw.filledSquare(tabpluieX[c], tabpluieY[c], tabpluieY[c] % 0.002); //
        }
    }

    /**
     * Dessin des ennemies
     */
    public void drawEnnemy() {
        for (Ennemy e : levelInst.getFormation()) {
            e.draw();
        }
    }

    /**
     * Dessin lorsque le joueur se fait toucher
     * 
     * @param i
     */
    public void drawLoseOnce(int i) {
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.filledRectangle(0.5, 0.5, 0.28, 0.18);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.5, 0.5, 0.275, 0.175);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0.5, 0.6, "I'm sorry you just died");
        StdDraw.text(0.5, 0.5, "But hey ! You have " + (player.getNbLife()) + " lives left ! ");
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.text(0.5, 0.45, "Good Luck !");
        StdDraw.text(0.5, 0.40, "Game starting in : ");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.615, 0.40, i + "");

    }

    /**
     * Dessin lorsque le joueur gagne un niveau
     * 
     * @param i
     */
    public void drawWin(int i) {
        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.filledRectangle(0.5, 0.5, 0.28, 0.18);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.5, 0.5, 0.275, 0.175);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0.5, 0.6, "WOW ?! You just beat a level omg ! ");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.72, 0.6, i + "");
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(0.5, 0.5, "With a score of : ");
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.65, 0.5, player.getScore() + " !");
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.text(0.5, 0.4, "Level " + (i + 1) + " will start ! ");

    }

    /**
     * Met a jour les attributs de tous les éléments du jeu
     */
    private void update() {
        player.update();
        if (levelInst.update(player.getListShootPlayer(), player)) {

        }
        if (levelInst.updateEnnemyShoot(player)) {
            // player has been killed

        }

    }
}
