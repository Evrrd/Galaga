package game.actors;

import engine.StdDraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;


public class Ennemy {
    protected double x; // postion du joueur sur l'axe des abscisses
    protected double y; // position du joueur sur l'axe des ordonnées
    protected double length; // largeur du joueur
    protected double speedX; // vitesse horizontale
    protected double speedY; // vitesse verticale
    protected boolean alive = true; // état de vie de l'ennemi
    private String spriteFile; // chemin du fichier sprite

    
    public Ennemy(double x, double y, double length, double speedX, double speedY, String spriteFile) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.speedX = speedX;
        this.speedY= speedY;
        this.spriteFile = spriteFile;
    }

    /**
     * Dessine les ennemis selon leur sprite
     */
    public void draw() {
        if (!alive) {
            return;
        }

        Path cheminT1 = Paths.get("Galaga/ressources/sprites/" + spriteFile);
        try (BufferedReader reader = Files.newBufferedReader(cheminT1)) {
            String line;
            double i = 0.0;
            double j = 0.0;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case 'R' -> { StdDraw.setPenColor(StdDraw.RED); break; }
                        case 'B' -> { StdDraw.setPenColor(StdDraw.BLUE); break; }
                        case 'G' -> { StdDraw.setPenColor(StdDraw.GREEN); break; }
                        case 'Y' -> { StdDraw.setPenColor(StdDraw.YELLOW); break; }
                        case 'W' -> { StdDraw.setPenColor(StdDraw.WHITE); break; }
                        case 'N' -> { StdDraw.setPenColor(StdDraw.BLACK); break; }
                    }
                    StdDraw.filledSquare(x + i, y - j, 0.006);
                    i += 0.006;
                }
                i = 0.0;
                j += 0.006;
            }
        } catch (IOException ieo) {
            System.out.println(ieo);
        }
    }
    
    public void shooting(double speed) {
        Shoot s = new Shoot(x, y, 0.006);
        s.draw();
    }

    private void move() {}

    public void destroy() {
        alive = false;
    }

    public void update() {
        if (!alive) return;
        move();
    }

    public boolean isAlive() { return alive; }

    public double getX() { return x; }

    public double getY() { return y; }

}


