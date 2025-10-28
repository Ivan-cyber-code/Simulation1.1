package Entity;

//import java.util.Map;

import Map.Field;
import Map.Node;

import java.util.List;

public class Predator extends Creature {
    public Predator(Coordinates coordinates, int speed, int hp, int attsckPower) {
        super(coordinates, speed, hp);
        this.attsckPower = attsckPower;
    }

    private int attsckPower;

    public int getAttsckPower() {
        return attsckPower;
    }

    @Override
    public void makeMove(Field field) {
        Herbivore herbivore = getHerbifore(field);

        List<Node> path = findAWaytothegoal(coordinate, herbivore.coordinate, field);

        if (path.size() - 1 > getSpeed()) {
            moveTheTarget(field, path);
            putACreatureOnTheField(field);
        } else {
            attack(field, herbivore);
        }
    }

    private void putACreatureOnTheField(Field field) {
        field.field.put(coordinate, new Predator(coordinate, getSpeed(), getHp(), getAttsckPower()));
    }

    private void attack(Field field, Herbivore herbivore) {
        herbivore.setHp(-1 * getAttsckPower());
        if (herbivore.getHp() <= 0) {
            field.field.remove(coordinate);
            coordinate = herbivore.coordinate;
            putACreatureOnTheField(field);
        }
    }

    private Herbivore getHerbifore(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Herbivore) {
                return (Herbivore) entity;
            }
        }
        return null;
    }


}
