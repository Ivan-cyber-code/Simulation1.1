package entity;

import map.Field;
import map.Node;
import java.util.List;

public class Predator extends Creature {



    private int attsckPower;

    public Predator(Coordinates coordinates, int speed, int health, int attackPower) {
        super(coordinates, speed, health);
        this.attsckPower = attackPower;
    }

    @Override
    public void makeMove(Field field) {

        Herbivore herbivore = (Herbivore) field.findEntities(this);

        List<Node> path = showPath(getCoordinate(), herbivore.getCoordinate(), field);

        setMoveConter();

        if (path.size() - 1 > getSpeed()) {
            move(field, path);
        } else {
            attack(field, herbivore);
        }
    }

    private void attack(Field field, Herbivore herbivore) {
        herbivore.setHealth(-1 * getAttsckPower());
        this.setHealth(HEAL);
        if (herbivore.getHealth() <= 0) {
            killing(field,herbivore);
        }
    }

    private void killing(Field field, Herbivore herbivore) {
        field.deleteEntities(herbivore.getCoordinate());
        field.deleteEntities(getCoordinate());
        setCoordinate(herbivore.getCoordinate());
        field.installEntities(getCoordinate(),this);
    }

    public int getAttsckPower() {
        return attsckPower;
    }

}
