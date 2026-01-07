package game.actors;

public class Bee extends Ennemy {

    private static int direction = 1; // Direction de toute les Bee

    private boolean canShoot; // Est-ce que cette abeille peut tirer (est ce que elle en bas de la formation)

    public Bee(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn, int indice) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn, indice);

        canShoot = false;
    }

    /**
     * Update bee
     */
    public void update() {
        if (alive) {

            x += speed * direction;
            // System.out.println("position x : " + x + "\n" + "direction : " + direction);
        }
    }

    /**
     * Changement de direction de toute les Bee
     */
    public void changeDirection() {
        direction *= -1;
    }

    /**
     * Return CanBeeShoot (si la bee peut tirer (si elle est en bas de la
     * formation))
     * 
     * 
     * @return boolean
     */
    public boolean canBeeShoot() {
        return canShoot;
    }

    /**
     * Return la direction (gauche ou droite (-1 ou 1))
     * 
     * @return int
     */
    public int getDirection() {
        return direction;
    }

}
