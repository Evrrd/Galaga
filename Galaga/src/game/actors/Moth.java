package game.actors;

public class Moth extends Ennemy {

    private static int direction = -1;

    public Moth(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn);
    }

    public void update() {
        if (alive) {
            double limit = x + speed * direction;
            if (((limit) < (0)) || ((limit) > (1 - proportion * 16))) {
                direction *= -1;
            }
            x += speed * direction;
        }
    }

    public void changeDirection() {
        direction *= -1;
    }

}
