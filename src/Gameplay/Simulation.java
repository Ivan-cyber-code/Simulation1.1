package Gameplay;

import Entity.*;
import Map.*;

public class Simulation {

    Field field;
    int moveCounter;
    Renderer renderer = new Renderer();
    InitActions initActions = new InitActions();
    TurnActions turnActions = new TurnActions();


    public Simulation(Field field) {
        this.field = field;
    }

    public void nextTurn() {
        turnActions.makeAMoveWithAllCreatures(field);
        renderer.showMap(field);
    }

    public void startSimulation() {
        while (true) {

            moveCounter++;
            nextTurn();
            try {
                Thread.sleep(1000); // Пауза 2 секунды
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (!thereIsGrass(field)) {
                initActions.plantGrass(field);
                renderer.showMap(field);
            }

            if (!thereIsHerbivore(field)) {
                break;
            }
        }
    }

    private boolean thereIsHerbivore(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Herbivore) {
                return true;
            }
        }
        return false;
    }

    private boolean thereIsGrass(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Grass) {
                return true;
            }
        }
        return false;
    }


    Actions[] gameLoop;

}
