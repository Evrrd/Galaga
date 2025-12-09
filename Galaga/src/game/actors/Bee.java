package game.actors;

public class Bee extends Ennemy{
    public Bee(double x, double y, double length, double speedX, double speedY, String spriteFile) {
        super(x, y, 0.006, 0.004, 0.003, "bee.spr");
    }

    public void move() {
        y -= speedY; //déplacement vers le bas
        x += speedX; //déplacement horizontal

        if (x <= 0.008 || x >= 0.920) {
            speedX = -speedX; //change de direction
        }

        if (y < 0.00) {
            alive = false; //l'ennemi sort de l'écran
        }
    }

}
