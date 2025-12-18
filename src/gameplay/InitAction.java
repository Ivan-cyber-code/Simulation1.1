package gameplay;

import entity.*;
import map.*;

import java.util.Random;

public class InitAction extends Action {

    private final static int SPEED_PREDATOR=1;
    private final static int HEALTH_PREDATOR =3;
    private final static int ATTACK_POWER=4;

    private final static int SPEED_HERBIFORE=2;
    private final static int HEALTH_HERBIFORE=5;

    private int creatureOccupancyRate;

    void putEntitiesrDefoultPositions(Field field) {

        creatureOccupancyRate = getCreatureOccupancyRate(field);

        for (int i = 0; i < creatureOccupancyRate; i++) {
            plantGrass(field);
            createHerbivore(field);
            createPredator(field);
            createRock(field);
            createTree(field);
        }
    }

    private int getCreatureOccupancyRate(Field field) {
        return (field.getColumns() * field.getLines()) / 10;
    }

    void plantGrass(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Grass grass = new Grass(coordinates);
        field.installEntities(coordinates, grass);
    }
    private void createTree(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Tree tree = new Tree(coordinates);
        field.installEntities(coordinates,tree);
    }

    private void createRock(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Rock rock = new Rock(coordinates);
        field.installEntities(coordinates,rock);
    }

    void createPredator(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Predator predator = new Predator(coordinates,SPEED_PREDATOR, HEALTH_PREDATOR, ATTACK_POWER);
        field.installEntities(coordinates,predator);
    }
    void createHerbivore(Field field) {
        Coordinates coordinates = createNewUniqueCoordinates(field);
        Herbivore herbivore = new Herbivore(coordinates,SPEED_HERBIFORE, HEALTH_HERBIFORE);
        field.installEntities(coordinates,herbivore);
    }

    private Coordinates createNewUniqueCoordinates(Field field) {
        Coordinates coordinates = creatRandomCoordinates(field);
        while (field.getEntities().containsKey(coordinates)) {
            coordinates = creatRandomCoordinates(field);
        }
        return coordinates;
    }
    private Coordinates creatRandomCoordinates(Field field) {
        return new Coordinates(new Random().nextInt(field.getLines()), new Random().nextInt(field.getColumns()));
    }
}
