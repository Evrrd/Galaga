package game.actors;

public class Butterfly extends Ennemy{
    public Butterfly (double x, double y, double length, double speedX, double speedY, String spriteFile) {
        super(x, y, 0.006, 0.0, 0.004, "butterfly.spr");
    }

    public void move() {
        y -= speedY;

        if (y < 0.000) {
            alive = false; //l'ennemi sort de l'écran
        }
    }
}
