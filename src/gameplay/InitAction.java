package gameplay;

import entity.*;
import map.*;

import java.util.Random;

public class InitAction extends Action {

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
        field.entities.put(coordinates, tree);
    }

    private void createRock(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Rock rock = new Rock(coordinates);
        field.entities.put(coordinates, rock);
    }

    public void createPredator(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Predator predator = new Predator(coordinates,1, 3, 4);
        field.entities.put(coordinates, predator);
    }

    public void createHerbivore(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Herbivore herbivore = new Herbivore(coordinates,2, 5);
        field.entities.put(coordinates, herbivore);
    }

    public void plantGrass(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Grass grass = new Grass(coordinates);
        field.entities.put(coordinates, grass);
    }

    private Coordinates createNewUniqueCoordinates(Field field) {
        Coordinates coordinates = creatRandomCoordinates(field);
        while (field.entities.containsKey(coordinates)) {
            coordinates = creatRandomCoordinates(field);
        }
        return coordinates;
    }

    private Coordinates creatRandomCoordinates(Field field) {
        return new Coordinates(new Random().nextInt(field.lines), new Random().nextInt(field.columns));
    }
}
