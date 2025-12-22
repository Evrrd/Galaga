package game.actors;

public class Moth extends Ennemy {

    private static int direction = -1;

    public Moth(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn, int indice) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn, indice);
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
