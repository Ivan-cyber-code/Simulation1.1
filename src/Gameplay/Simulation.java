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
        moveCounter++;

        System.out.printf("""
                -----------------ХОД: %s -------------------------------
                """, moveCounter);
        System.out.println();
        System.out.printf("""
                Всего зайцев: %s;
                Всехо волков: %s.
                """, numberOfHarbivore(), numberOfPredator());

        turnActions.makeAMoveWithAllCreatures(field);

        renderer.showMap(field);

        if (!thereIsGrass(field)) {
            int count=0;
            for (int i = numberOfGrass()-1; i <= numberOfHarbivore(); i++) {
                initActions.plantGrass(field);
                count++;
            }
            System.out.printf("Было посажено %s травы",count);
            System.out.println();
        }
    }

    private int numberOfPredator() {
        int count=0;
        for (Entity entity : field.field.values()){
            if (entity instanceof Predator){
                count++;
            }
        }
        return count;
    }

    private int numberOfHarbivore() {
        int count=0;
        for (Entity entity : field.field.values()){
            if (entity instanceof Herbivore){
                count++;
            }
        }
        return count;
    }


    public void startSimulation() {
        while (true) {

            nextTurn();
            try {
                Thread.sleep(1000); // Пауза 2 секунды
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (!thereIsHerbivore(field)) {
                throw new RuntimeException();
            }
        }
    }

    private int numberOfGrass() {
        int count=0;
        for (Entity entity : field.field.values()){
            if (entity instanceof Grass){
                count++;
            }
        }
        return count;
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
        int countGrass = -1;
        int countHerbivore = numberOfHarbivore();
        for (Entity entity : field.field.values()) {
            if (entity instanceof Grass) {
                countGrass++;
            }
        }
        if (countGrass>=countHerbivore){
            return true;
        }
        return false;
    }


    Actions[] gameLoop;

}
