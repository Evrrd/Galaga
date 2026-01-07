package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import game.actors.Bee;
import game.actors.Butterfly;
import game.actors.Ennemy;
import game.actors.Moth;
import game.actors.Player;
import game.actors.Shoot;

public class Level {

    private String name;
    private Double speedFormation;
    private int cooldownAttaque;
    private int cooldownMissile;
    private int nbBee = 0;
    private int nbButterfly = 0;
    private int nbMoth = 0;
    private LinkedList<Shoot> lLEnnShoot;
    private ArrayList<Ennemy> formation;
    private ArrayList<Ennemy> ennCanShoot;
    private ArrayList<Integer> ennemyMoving;

    public Level(String n, String sF, String cS, String cM) {
        name = n;
        speedFormation = Double.valueOf(sF);
        cooldownAttaque = Integer.valueOf(cS);
        cooldownMissile = Integer.valueOf(cM);
        formation = new ArrayList<Ennemy>(); // Formation des ennemies
        lLEnnShoot = new LinkedList<Shoot>(); // Linked List des tirs Ennemi
        ennCanShoot = new ArrayList<Ennemy>(); // Liste des ennemies qui peuvent tirer
        ennemyMoving = new ArrayList<Integer>();
    }

    public String getName() {
        return name;
    }

    public Double getSpeedFormation() {
        return speedFormation;
    }

    public int getNombreRestantEnnemi() {
        return nbBee + nbButterfly + nbMoth;
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

    public void makeAddEnnemy(String[] sArray, int indice) {
        if (sArray[0].compareTo("Bee") == 0) {
            Bee E = new Bee(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]), indice);
            addEnnemy(E);
            nbBee += 1;

        } else if (sArray[0].compareTo("Butterfly") == 0) {
            Butterfly E = new Butterfly(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]), indice);
            addEnnemy(E);
            nbButterfly += 1;

        } else if (sArray[0].compareTo("Moth") == 0) {
            Moth E = new Moth(sArray[0], Double.valueOf(sArray[1]), Double.valueOf(sArray[2]),
                    Double.valueOf(sArray[3]), Integer.valueOf(sArray[4]), Double.valueOf(sArray[5]), indice);
            addEnnemy(E);
            nbMoth += 1;
        }
    }

    public void initShooter() { // and attack
        for (int i = 32; i <= 43; i++) {
            ennCanShoot.add(formation.get(i));
            formation.get(i).setCanAction(true);
            ennemyMoving.add(i);
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

        for (Shoot s : lLEnnShoot) {
            if (s != null) {
                s.draw();
            }
        }
    }

    public boolean updateEnnemyShoot(Player playerInst) {
        for (Shoot s : lLEnnShoot) {
            s.update();

            // Y player -> 0.2
            if (s != null) {
                if ((s.getX() >= playerInst.getX())
                        && s.getX() <= playerInst.getX() + playerInst.getProportion() * 15) {
                    if ((s.getY() - s.getProportion() * 6 <= 0.2)
                            && (s.getY() >= 0.2 - playerInst.getProportion() * 15)) {
                        playerInst.setAlive(false);
                        lLEnnShoot.clear();
                        return true;
                    }
                }
            }
            if (s.getY() - s.getProportion() * 6 < 0.1) {
                s.destroy();
                s = null;
            }

        }
        return false;
    }

    public boolean update(Shoot[] listShootPlayer, Player playerInst) {

        boolean changeDirBee = false;
        boolean changeDirMoth = false;
        boolean changeDirButterfly = false;

        // gestion déplacement ennemy
        for (Ennemy e : formation) {

            if (e.isAlive() == true) {
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

                if (e.getIsAttaque() == true) { // if il est en train d'attaquer
                    e.updateMoving();

                    // Gestion ennemi going hors champs
                    if (e.getY() < 0.1 + e.getProportion() * 13) {
                        e.destroy();
                        e.setIsAttaque(false);
                        if (ennCanShoot.contains(e)) {
                            ennCanShoot.remove(e);
                            ennemyMoving.remove(ennemyMoving.indexOf(e.getIndice()));
                            e.setCanAction(false);
                            if (e instanceof Bee) {
                                Ennemy et = formation.get(e.getIndice() - (24 / 2));
                                if (et instanceof Bee) {
                                    et.setCanAction(true);
                                    ennCanShoot.add(et);
                                    ennemyMoving.add(et.getIndice());
                                }
                                nbBee -= 1;

                            } else if (e instanceof Butterfly) {
                                if ((e.getIndice() - 16 / 2) > 0) {
                                    Ennemy et = formation.get(e.getIndice() - 16 / 2);
                                    if (et instanceof Butterfly) {
                                        et.setCanAction(true);
                                        ennCanShoot.add(et);
                                        ennemyMoving.add(et.getIndice());
                                    }
                                }
                                nbButterfly -= 1;
                            } else {

                                nbMoth -= 1;
                            }
                        }
                    }

                    // Gestion collision Ennemi Player
                    if (e.isAlive() == true) {
                        if ((e.getX() >= playerInst.getX() - e.getProportion() * 16)
                                && (e.getX() <= playerInst.getX() + playerInst.getProportion() * 16)) {

                            if ((e.getY() - e.getProportion() * 13 <= playerInst.getY())) {

                                playerInst.setAlive(false);
                                e.destroy();
                                e.setIsAttaque(false);
                                ennemyMoving.remove(ennemyMoving.indexOf(e.getIndice()));
                                if (ennCanShoot.contains(e)) {
                                    ennCanShoot.remove(e);
                                    e.setCanAction(false);
                                    if (e instanceof Bee) {
                                        Ennemy et = formation.get(e.getIndice() - 24 / 2);
                                        if (et instanceof Bee) {
                                            et.setCanAction(true);
                                            ennCanShoot.add(et);
                                            ennemyMoving.add(et.getIndice());
                                        }
                                        nbBee -= 1;
                                    } else if (e instanceof Butterfly) {
                                        Ennemy et = formation.get(e.getIndice() - 16 / 2);
                                        if (et instanceof Butterfly) {
                                            et.setCanAction(true);
                                            ennCanShoot.add(et);
                                            ennemyMoving.add(et.getIndice());
                                        }
                                        nbButterfly -= 1;
                                    } else {
                                        nbMoth -= 1;
                                    }
                                }
                                return true; // player has been touch
                            }
                        }
                    }
                }
            }
        }

        // gestion changement de direction
        for (int i = 0; i < formation.size(); i++) {
            if (formation.get(i) instanceof Bee) {
                if (changeDirBee == true) {
                    ((Bee) formation.get(i)).changeDirection();
                    changeDirBee = false;
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

        // gestion tir player
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
                            if ((posShootY >= e.getY()) && (posShootY <= e.getY() + e.getProportion() * 12)) {

                                playerInst.incScore(e.getScore());

                                if (e instanceof Bee) {
                                    if (ennCanShoot.contains(e)) {
                                        ennCanShoot.remove(e);
                                        ennemyMoving.remove(ennemyMoving.indexOf(e.getIndice()));
                                        e.setCanAction(false);
                                        Ennemy et = formation.get(e.getIndice() - 24 / 2); // nbBee = 24
                                        if (et instanceof Bee) {
                                            et.setCanAction(true);
                                            ennCanShoot.add(et);
                                            ennemyMoving.add(et.getIndice());
                                        }

                                    }
                                    nbBee -= 1;

                                } else if (e instanceof Butterfly) {
                                    if (ennCanShoot.contains(e)) {
                                        ennCanShoot.remove(e);
                                        ennemyMoving.remove(ennemyMoving.indexOf(e.getIndice()));
                                        e.setCanAction(false);
                                        if ((e.getIndice() - 16 / 2) > 0) {
                                            Ennemy et = formation.get(e.getIndice() - 16 / 2); // nbButterfly = 16
                                            if (et instanceof Butterfly) {
                                                et.setCanAction(true);
                                                ennCanShoot.add(et);
                                                ennemyMoving.add(et.getIndice());
                                            }
                                        }

                                    }
                                    nbButterfly -= 1;

                                } else if (e instanceof Moth) {
                                    if (ennCanShoot.contains(e)) {
                                        ennCanShoot.remove(e);
                                        ennemyMoving.remove(ennemyMoving.indexOf(e.getIndice()));
                                        e.setCanAction(false);
                                    }
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
        return false;

    }

    public void makeEnnemyShoot() {
        Random r = new Random();
        if (ennCanShoot.size() < 4) {
            if (nbBee < 4) { // ALL BEES ARE DEAD OMG WHAT
                // we put first line of butterfly
                for (int i = nbMoth + nbButterfly / 2; i <= nbMoth + nbButterfly; i++) {
                    if (formation.get(i).isAlive() == true) {
                        ennCanShoot.add(formation.get(i));
                        formation.get(i).setCanAction(true);
                        ennemyMoving.add(i);
                    }
                }
            }
            if (nbButterfly < 4) { // ALL THE BUTTERFLY ARE DEAD HELP US OMG
                for (int i = 0; i <= nbMoth; i++) {
                    if (formation.get(i).isAlive() == true) {
                        ennCanShoot.add(formation.get(i));
                        formation.get(i).setCanAction(true);
                        ennemyMoving.add(i);
                    }
                }
            }
        }
        if (ennCanShoot.size() != 0) {
            int randomEnnemy = r.nextInt(0, ennCanShoot.size());

            if (ennCanShoot.get(randomEnnemy).getCanAction() == true) {
                if (ennCanShoot.get(randomEnnemy).isAlive() == true) { // shouldnt be doing that but idk why some ennemi
                                                                       // arent dying
                    lLEnnShoot
                            .add(new Shoot(
                                    ennCanShoot.get(randomEnnemy).getX()
                                            + ennCanShoot.get(randomEnnemy).getProportion() * 8,
                                    ennCanShoot.get(randomEnnemy).getY()
                                            - ennCanShoot.get(randomEnnemy).getProportion() * 11,
                                    -1));
                }
            }
        }

    }

    public void makeEnnemyAttack() {

        if (ennemyMoving.size() != 0) {
            Random r = new Random();
            if (ennCanShoot.size() < 4) {
                if (nbBee < 4) { // ALL BEES ARE DEAD OMG WHAT
                    // we put first line of butterfly
                    for (int i = nbMoth + nbButterfly / 2; i <= nbMoth + nbButterfly; i++) {
                        if (formation.get(i).isAlive() == true) {
                            ennCanShoot.add(formation.get(i));
                            formation.get(i).setCanAction(true);
                            ennemyMoving.add(i);
                        }
                    }
                }
                if (nbButterfly < 4) { // ALL THE BUTTERFLY ARE DEAD HELP US OMG
                    for (int i = 0; i <= nbMoth; i++) {
                        if (formation.get(i).isAlive() == true) {
                            ennCanShoot.add(formation.get(i));
                            formation.get(i).setCanAction(true);
                            ennemyMoving.add(i);
                        }
                    }
                }
            }

            if (ennemyMoving.size() != 0) {
                int randomEnnemy = r.nextInt(0, ennemyMoving.size());
                if (formation.get(ennemyMoving.get(randomEnnemy)).getCanAction() == true) { // idk ?
                    formation.get(ennemyMoving.get(randomEnnemy)).setIsAttaque(true);
                }
            }

        }
    }

    public void setBackEnnemi() {
        for (int i : ennemyMoving) {
            if (formation.get(i) instanceof Bee) {
                if (i > 31) {
                    if (formation.get(i).getY() != 0.57) {
                        formation.get(i).setY(0.57);
                    }
                } else {
                    if (formation.get(i).getY() != 0.64) {
                        formation.get(i).setY(0.64);
                    }
                }

            } else if (formation.get(i) instanceof Butterfly) {
                if (i > 11) {
                    if (formation.get(i).getY() != 0.71) {
                        formation.get(i).setY(0.71);
                    }
                } else {
                    if (formation.get(i).getY() != 0.78) {
                        formation.get(i).setY(0.78);
                    }
                }
            } else {
                if (formation.get(i).getY() != 0.85) {
                    formation.get(i).setY(0.85);
                }
            }
            formation.get(i).draw();
        }

        lLEnnShoot.clear();
    }
}
