package Gameplay;

import Entity.*;
import Map.*;

import java.util.Random;

public class InitActions extends Actions {

    int coefficientEntity;


    public void putEntitiesInTheirDefoultPositions(Field field) {

        coefficientEntity=(field.columns*field.lines)/10;

        for (int i = 0; i < coefficientEntity; i++) {
            plantGrass(field);
            createHerbivore(field);
            createPredator(field);
            createRock(field);
            createTree(field);
        }
    }

    private void createTree(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Tree tree = new Tree(coordinates);
        field.field.put(coordinates, tree);
    }

    private void createRock(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Rock rock = new Rock(coordinates);
        field.field.put(coordinates, rock);
    }

    public void createPredator(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Predator predator = new Predator(coordinates,1, 3, 3);
        field.field.put(coordinates, predator);
    }

    public void createHerbivore(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Herbivore herbivore = new Herbivore(coordinates,1, 5);
        field.field.put(coordinates, herbivore);
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
