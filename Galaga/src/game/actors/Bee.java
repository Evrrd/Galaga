package game.actors;

public class Bee extends Ennemy {

    private static int direction = 1;

    private boolean canShoot;

    public Bee(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn);

        canShoot = false;
    }

    public void update() {
        if (alive) {

            x += speed * direction;
            // System.out.println("position x : " + x + "\n" + "direction : " + direction);
        }
    }

    public void changeDirection() {
        direction *= -1;
    }

    public boolean canBeeShoot() {
        return canShoot;
    }

    public int getDirection() {
        return direction;
    }

}
