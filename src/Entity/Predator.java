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

        setMoveConter();

        if (path.size() - 1 > getSpeed()) {
            moveTheTarget(field, path);
        } else {
            attack(field, herbivore);
        }
    }

//    private void putACreatureOnTheField(Field field) {
//        field.field.put(coordinate, this);
//    }

    private void attack(Field field, Herbivore herbivore) {
        herbivore.setHp(-1 * getAttsckPower());
        this.setHp(3);
        if (herbivore.getHp() <= 0) {
            field.field.remove(herbivore.coordinate);
            field.field.remove(coordinate);
            coordinate = herbivore.coordinate;
            field.field.put(coordinate, this);
        }
    }

    private Herbivore getHerbifore(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Herbivore) {
                return ((Herbivore) entity);
            }
        }
        return null;
    }


}
