package entity;

import map.Field;
import map.Node;
import java.util.List;

public class Predator extends Creature {

    private final int HEAL = 3;

    private int attsckPower;

    public Predator(Coordinates coordinates, int speed, int health, int attackPower) {
        super(coordinates, speed, health);
        this.attsckPower = attackPower;
    }

    @Override
    public void makeMove(Field field) {

        Herbivore herbivore = findHerbifore(field);

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
            field.entities.remove(herbivore.getCoordinate());
            field.entities.remove(getCoordinate());
            setCoordinate(herbivore.getCoordinate());
            field.entities.put(getCoordinate(), this);
        }
    }

    public int getAttsckPower() {
        return attsckPower;
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
