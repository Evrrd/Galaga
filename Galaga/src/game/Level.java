package game;

import java.util.ArrayList;

import game.actors.Bee;
import game.actors.Butterfly;
import game.actors.Ennemy;
import game.actors.Moth;
import game.actors.Shoot;

public class Level {

    private String name;
    private Double speedFormation;
    private int cooldownShoot;
    private int cooldownMissile;

    private ArrayList<Ennemy> formation;

    public Level(String n, String sF, String cS, String cM) {
        name = n;
        speedFormation = Double.valueOf(sF);
        cooldownShoot = Integer.valueOf(cS);
        cooldownMissile = Integer.valueOf(cM);
        formation = new ArrayList<Ennemy>();
    }

    public String getName() {
        return name;
    }

    public Double getSpeedFormation() {
        return speedFormation;
    }

    public int getCooldownShoot() {
        return cooldownShoot;
    }

    public int getCooldownMissile() {
        return cooldownMissile;
    }

    public void addEnnemy(Ennemy e) {
        formation.add(e);
    }

    public void makeAddEnnemy(String[] sArray) {
        if (sArray[0].compareTo("Bee") == 0) {
            Bee E = new Bee(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]));
            addEnnemy(E);
        } else if (sArray[0].compareTo("Moth") == 0) {
            Moth E = new Moth(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]));
            addEnnemy(E);
        } else if (sArray[0].compareTo("Butterfly") == 0) {
            Butterfly E = new Butterfly(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]));
            addEnnemy(E);
        }
    }

    public ArrayList<Ennemy> getFormation() {
        return formation;
    }

    public void update(Shoot[] listShootPlayer) {

        for (Ennemy e : formation) {
            if (e instanceof Bee) {
                ((Bee) e).update();
            } else if (e instanceof Moth) {
                ((Moth) e).update();
            } else if (e instanceof Butterfly) {
                ((Butterfly) e).update();
            }

        }

        for (Shoot s : listShootPlayer) {
            if (s != null) {
                for (Ennemy e : formation) {
                    if (s == null) {
                        break;
                    }
                    Double posShootX = s.getX();
                    Double posShootY = s.getY() + s.getProportion() * 12;
                    if (e.isAlive() == true) {
                        if ((posShootX >= e.getX()) && (posShootX <= e.getX() + e.getProportion() * 17)) {
                            if (posShootY >= e.getY()) {
                                e.destroy();
                                s.destroy();
                            }
                        }
                    }
                }
            }
        }
    }
}
