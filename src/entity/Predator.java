package entity;

//import java.util.Map;

import map.Field;
import map.Node;

import java.util.List;

public class Predator extends Creature {
    public Predator(Coordinates coordinates, int speed, int health, int attackPower) {
        super(coordinates, speed, health);
        this.attsckPower = attackPower;
    }

    private final int HEAL = 3;

    private int attsckPower;

    public int getAttsckPower() {
        return attsckPower;
    }

    @Override
    public void makeMove(Field field) {

        Herbivore herbivore = findHerbifore(field);

        List<Node> path = showPath(coordinate, herbivore.coordinate, field);

        setMoveConter();

        if (path.size() - 1 > getSpeed()) {
            move(field, path);
        } else {
            attack(field, herbivore);
        }
    }

//    private void putACreatureOnTheField(Field field) {
//        field.field.put(coordinate, this);
//    }

    private void attack(Field field, Herbivore herbivore) {
        herbivore.setHealth(-1 * getAttsckPower());
        this.setHealth(HEAL);
        if (herbivore.getHealth() <= 0) {
            field.entities.remove(herbivore.coordinate);
            field.entities.remove(coordinate);
            coordinate = herbivore.coordinate;
            field.entities.put(coordinate, this);
        }
    }

    private Herbivore findHerbifore(Field field) {
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Herbivore) {
                return ((Herbivore) entity);
            }
        }
        return null;
    }


}
