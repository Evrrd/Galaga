package game.actors;

public class Butterfly extends Ennemy {

    private static int direction = -1; // direction des Butterfly

    public Butterfly(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn, int indice) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn, indice);
    }

    /**
     * Udpate Butterfly
     */
    public void update() {
        if (alive) {
            x += speed * direction;
        }
    }

    /**
     * Change la direction des Butterfly
     */
    public void changeDirection() {
        direction *= -1;
    }

}
