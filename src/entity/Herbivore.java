package entity;

import map.Field;
import map.Node;

import java.util.List;

public class Herbivore extends Creature {

    public Herbivore(Coordinates coordinates, int speed, int health) {
        super(coordinates, speed, health);
    }

    @Override
    public void makeMove(Field field) {

        Grass grass = getGrass(field);

        List<Node> path = showPath(getCoordinate(), grass.getCoordinate(), field);

        setMoveConter();

        if (path.size() - 1 > getSpeed()) {
            move(field, path);
        } else {
            eatGrass(field, grass);
        }
    }

    private Grass getGrass(Field field) {
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Grass) {
                return (Grass) entity;
            }
        }
        return null;
    }

    private void eatGrass(Field field, Entity grass) {
        field.entities.remove(grass.getCoordinate());
        field.entities.remove(getCoordinate());
        setCoordinate(grass.getCoordinate());
        System.out.println();
        setHealth(3);
        field.entities.put(getCoordinate(), this);
    }
}

