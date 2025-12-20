package game.actors;

public class Butterfly extends Ennemy {

    private static int direction = -1;

    public Butterfly(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn);
    }

    public void update() {
        if (alive) {
            x += speed * direction;
        }
    }

    public void changeDirection() {
        direction *= -1;
    }

}
