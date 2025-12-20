package game;

import java.util.ArrayList;
import java.util.Random;

import engine.StdDraw;
import game.actors.Bee;
import game.actors.Butterfly;
import game.actors.Ennemy;
import game.actors.Moth;
import game.actors.Shoot;

public class Level {

    private String name;
    private Double speedFormation;
    private int cooldownAttaque;
    private int cooldownMissile;
    private int nbBee = 0;
    private int nbButterfly = 0;
    private int nbMoth = 0;
    private Shoot[] listEShoots;

    private ArrayList<Ennemy> formation;

    public Level(String n, String sF, String cS, String cM) {
        name = n;
        speedFormation = Double.valueOf(sF);
        cooldownAttaque = Integer.valueOf(cS);
        cooldownMissile = Integer.valueOf(cM);
        formation = new ArrayList<Ennemy>();
        listEShoots = new Shoot[10];
    }

    public String getName() {
        return name;
    }

    public Double getSpeedFormation() {
        return speedFormation;
    }

    public int getCooldownAttaque() {
        return cooldownAttaque;
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
            nbBee += 1;

        } else if (sArray[0].compareTo("Butterfly") == 0) {
            Butterfly E = new Butterfly(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]));
            addEnnemy(E);
            nbButterfly += 1;

        } else if (sArray[0].compareTo("Moth") == 0) {
            Moth E = new Moth(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]));
            addEnnemy(E);
            nbMoth += 1;
        }
    }

    public ArrayList<Ennemy> getFormation() {
        return formation;
    }

    public void drawEnnemyShoot() {
        // for (Shoot s : listEShoots) {
        // if (s != null) {
        // s.draw();
        // }
        // }

        for (Shoot s : listEShoots) {
            if (s != null) {
                s.draw();
            }
        }
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

        for (int i = 0; i < formation.size(); i++) {
            if (formation.get(i) instanceof Bee) {
                if (changeDirBee == true) {
                    ((Bee) formation.get(i)).changeDirection();
                    changeDirBee = false;
                }
                if (i > 32) {
                    formation.get(i).setCanAction(true); // position of Bee en bas of the formation
                }
            } else if (formation.get(i) instanceof Moth) {
                if (changeDirMoth == true) {
                    ((Moth) formation.get(i)).changeDirection();
                    changeDirMoth = false;
                }
            } else if (formation.get(i) instanceof Butterfly) {
                if (changeDirButterfly == true) {
                    ((Butterfly) formation.get(i)).changeDirection();
                    changeDirButterfly = false;
                }
            }

        }

        // for (Ennemy e : formation) { // maybe change it not optimal rn
        // if (e instanceof Bee) {
        // if (changeDirBee == true) {
        // ((Bee) e).changeDirection();
        // changeDirBee = false;
        // }
        // } else if (e instanceof Moth) {
        // if (changeDirMoth == true) {
        // ((Moth) e).changeDirection();
        // changeDirMoth = false;
        // }
        // } else if (e instanceof Butterfly) {
        // if (changeDirButterfly == true) {
        // ((Butterfly) e).changeDirection();
        // changeDirButterfly = false;
        // }
        // }
        // }

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

        for (int i = 0; i < 10; i++) {
            if (listEShoots[i] != null) {
                listEShoots[i].update();
                if (listEShoots[i].getY() - listEShoots[i].getProportion() * 6 < 0.1) {
                    listEShoots[i] = null;
                }
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
                                if (e instanceof Bee) {
                                    nbBee -= 1;
                                } else if (e instanceof Butterfly) {
                                    nbButterfly -= 1;
                                } else if (e instanceof Moth) {
                                    nbMoth -= 1;
                                }
                                e.destroy();
                                s.destroy();

                            }
                        }
                    }
                }
            }
        }

    }

    public void makeEnnemyShoot() {
        Random r = new Random();
        int randomEnnemy = r.nextInt(0, 8);
        int posRE = 0;
        for (int i = nbButterfly + nbMoth - 1; i < nbButterfly + nbMoth + nbBee - 1; i++) { // 20 -> 43 for Bee
            if (formation.get(i).getCanAction() == true) {
                randomEnnemy -= 1;
            }
            if (randomEnnemy == 0) {
                posRE = i;
                break;

            }
        }

        // for (Shoot s : listEShoots) {
        // if (s == null) {
        // s = new Shoot(formation.get(posRE).getX() +
        // formation.get(posRE).getProportion() * 16,
        // formation.get(posRE).getY() - formation.get(posRE).getProportion() * 11, -1);
        // break;
        // }
        // }

        for (int i = 0; i < 10; i++) {
            if (listEShoots[i] == null) {
                listEShoots[i] = new Shoot(formation.get(posRE).getX() +
                        formation.get(posRE).getProportion() * 16,
                        formation.get(posRE).getY() - formation.get(posRE).getProportion() * 11, -1);

                break;
            } else {
                i += 1;
            }
        }

    }

}
