package game.actors;

public class Moth extends Ennemy {

    private static int direction = -1; // direction de tout les Butterfly

    public Moth(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn, int indice) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn, indice);
    }

    /**
     * Update Moth
     */
    public void update() {
        if (alive) {
            x += speed * direction;
        }
    }

    /**
     * Change la direction de toute les Moth
     */
    public void changeDirection() {
        direction *= -1;
    }

}
