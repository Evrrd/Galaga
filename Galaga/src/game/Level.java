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
            System.out.print(E.getX() + " | ");

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

        boolean changeDirBee = false;
        boolean changeDirMoth = false;
        boolean changeDirButterfly = false;

        for (Ennemy e : formation) {
            if (e instanceof Bee) {

                ((Bee) e).update();
                if ((e.getX() < 0) || (e.getX() > 1 - e.getProportion() * 16)) {
                    changeDirBee = true;
                }
            } else if (e instanceof Moth) {
                ((Moth) e).update();
                if ((e.getX() < 0) || (e.getX() > 1 - e.getProportion() * 16)) {
                    changeDirMoth = true;
                }
            } else if (e instanceof Butterfly) {
                ((Butterfly) e).update();
                if ((e.getX() < 0) || (e.getX() > 1 - e.getProportion() * 16)) {
                    changeDirButterfly = true;
                }
            }
        }

        for (Ennemy e : formation) {
            if (e instanceof Bee) {
                if (changeDirBee == true) {
                    ((Bee) e).changeDirection();
                    changeDirBee = false;
                }
            } else if (e instanceof Moth) {
                if (changeDirMoth == true) {
                    ((Moth) e).changeDirection();
                    changeDirMoth = false;
                }
            } else if (e instanceof Butterfly) {
                if (changeDirButterfly == true) {
                    ((Butterfly) e).changeDirection();
                    changeDirButterfly = false;
                }
            }
        }

        // System.out.println();
        // for (int i = 0; i < formation.size(); i++) {
        // if (formation.get(i) instanceof Bee) {
        // if (i == 31 || i == 43) {
        // System.out.println("Abeille : " + i + " x : " + formation.get(i).getX() + "
        // direction "
        // + ((Bee) formation.get(i)).getDirection() + "speed : " +
        // formation.get(i).getSpeed());
        // }

        // ((Bee) formation.get(i)).update();

        // if (i == 31 || i == 43) {
        // System.out.println("Abeille : " + i + " x : " + formation.get(i).getX() + "
        // direction "
        // + ((Bee) formation.get(i)).getDirection() + "speed : " +
        // formation.get(i).getSpeed());
        // }
        // } else if (formation.get(i) instanceof Moth) {
        // ((Moth) formation.get(i)).update();
        // } else if (formation.get(i) instanceof Butterfly) {
        // ((Butterfly) formation.get(i)).update();
        // }

        // }

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
