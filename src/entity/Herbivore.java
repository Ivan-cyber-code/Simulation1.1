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
        List<Node> path = showPath(getCoordinate(), Grass.class, field);
        setMoveConter();
        if (path.size() - 1 >= getSpeed()) {
            move(field, path);
        } else {
            eatGrass(field, path);
        }
    }

    private void eatGrass(Field field, List<Node> grass) {
        field.deleteEntities(getCoordinate());
        field.deleteEntities(grass.get(grass.size()-1).getCoordinates());
        setCoordinate(grass.get(grass.size() - 1).getCoordinates());
        setHealth(HEAL);
        field.installEntities(getCoordinate(), this);
    }
}

