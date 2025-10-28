package Entity;

//import java.util.Map;

import Map.Field;
import Map.Node;

import java.util.List;

public class Herbivore extends Creature {


//    Herbivore herbivore = new Herbivore(new Coordinates(0,0), 2, 5);


    public Herbivore(Coordinates coordinates, int speed, int hp) {
        super(coordinates, speed, hp);
    }

    private Grass getGrass(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Grass) {
                return (Grass) entity;
            }
        }
        return null;
    }

    private void putACreatureOnTheField(Field field) {
        field.field.put(coordinate, new Herbivore(coordinate, getSpeed(), getHp()));
    }

    private void eatGrass(Field field, Entity grass) {
        field.field.remove(coordinate);
        coordinate = grass.coordinate;
        setHp(1);
        putACreatureOnTheField(field);
    }

    @Override
    public void makeMove(Field field) {
        Grass grass = getGrass(field);
        List<Node> path = findAWaytothegoal(coordinate, grass.coordinate, field);
        if (path.size() - 1 > getSpeed()) {
            moveTheTarget(field, path);
            putACreatureOnTheField(field);
        } else {
            eatGrass(field, grass);
        }
    }
}

