package game.actors;

public class Moth extends Ennemy {

    private static int direction = -1;
    private boolean hasShip = false;

    public Moth(String n, double xx, double yy, double proportioEnn, int valSc, double speedEnn, int indice) {
        super(n, xx, yy, proportioEnn, valSc, speedEnn, indice);
    }

    public void update() {
        if (alive) {
            x += speed * direction;
        }
    }

    public void updateMoving() {
        y -= speed * 5;
    }

    public void changeDirection() {
        direction *= -1;
    }

    public void setHasShip(boolean b) {
        hasShip = b;
    }

    public boolean getHasHip() {
        return hasShip;
    }

}
