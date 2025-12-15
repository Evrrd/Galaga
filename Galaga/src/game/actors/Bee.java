package game.actors;

public class Bee extends Ennemy {

    private static int direction = -1;

    private boolean canShoot;

    public Bee(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn);

        canShoot = false;
    }

    public void update() {
        if (alive) {
            double limit = x + speed * direction;
            if (((limit) <= (0)) || ((limit) >= (1 - proportion * 16))) {
                direction *= -1;
            } else {
                x += speed * direction;
            }

        }

    }

    public void changeDirection() {
        direction *= -1;
    }

    public boolean canBeeShoot() {
        return canShoot;
    }

}
