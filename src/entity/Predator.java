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
        List<Node> path = showPath(getCoordinate(), Herbivore.class, field);
        setMoveConter();
        if (path.size() - 1 > getSpeed()) {
            move(field, path);
        } else {
            attack(field, path);
        }
    }

    private void attack(Field field, List<Node> herbivore) {
        Herbivore herbivore1 = (Herbivore) field.getEntities().get(herbivore.get(herbivore.size()-1).getCoordinates());
        herbivore1.setHealth(-1*getAttsckPower());
        this.setHealth(HEAL);
        if (herbivore1.getHealth() <= 0) {
            killing(field, herbivore1);
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
