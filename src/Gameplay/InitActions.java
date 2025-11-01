package Gameplay;

import Entity.*;
import Map.*;

import java.util.Random;

public class InitActions extends Actions {

    public void putEntitiesInTheirDefoultPositions(Field field) {

        plantGrass(field);

        Coordinates coordinates = createNewUniqueCoordinates(field);
        Herbivore herbivore = new Herbivore(coordinates,1, 4);
        field.field.put(coordinates, herbivore);


        coordinates = createNewUniqueCoordinates(field);
        Predator predator = new Predator(coordinates,1, 3, 3);
        field.field.put(coordinates, predator);

        coordinates = createNewUniqueCoordinates(field);
        Rock rock = new Rock(coordinates);
        field.field.put(coordinates, rock);

        coordinates = createNewUniqueCoordinates(field);
        Tree tree = new Tree(coordinates);
        field.field.put(coordinates, tree);

    }

    public void plantGrass(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Grass grass = new Grass(coordinates);
        field.field.put(coordinates, grass);
    }

    private Coordinates createNewUniqueCoordinates(Field field) {
        Coordinates coordinates = creatRandomCoordinates(field);
        while (field.field.containsKey(coordinates)) {
            coordinates = creatRandomCoordinates(field);
        }
        return coordinates;
    }

    private Coordinates creatRandomCoordinates(Field field) {
        return new Coordinates(new Random().nextInt(field.lines), new Random().nextInt(field.columns));
    }
}
