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

        Grass grass = (Grass) field.findEntities(this);

        List<Node> path = showPath(getCoordinate(), grass.getCoordinate(), field);

        setMoveConter();

        if (path.size() - 1 > getSpeed()) {
            move(field, path);
        } else {
            eatGrass(field, grass);
        }
    }


    private void eatGrass(Field field, Entity grass) {
        field.deleteEntities(grass.getCoordinate());
        field.deleteEntities(getCoordinate());
        setCoordinate(grass.getCoordinate());
        setHealth(HEAL);
        field.installEntities(getCoordinate(),this);
    }
}

