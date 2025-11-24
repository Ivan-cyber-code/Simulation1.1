package gameplay;

import entity.*;
import map.*;

import java.util.Scanner;

public class Simulation {

    private boolean isPaused = false;
    Field field;
    int moveCounter;
    Renderer renderer = new Renderer();
    InitActions initActions = new InitActions();
    TurnActions turnActions = new TurnActions();
    final Scanner SCANNER = new Scanner(System.in);


    public Simulation(Field field) {
        this.field = field;
    }

    public void nextTurn() {
        moveCounter++;

        System.out.printf("""
                -----------------ХОД: %s -------------------------------
                """, moveCounter);
        System.out.println();

        turnActions.makeAMoveWithAllCreatures(field);

        System.out.printf("""
                Всего зайцев: %s;
                Всехо волков: %s.
                """, numberOfHarbivore(), numberOfPredator());
        System.out.println();

        renderer.showMap(field);

        if (!isGrass(field)) {
            for (int i = numberOfGrass() - 1; i <= numberOfHarbivore(); i++) {
                initActions.plantGrass(field);
            }
            System.out.println();
        }
    }

    private int numberOfPredator() {
        int count = 0;
        for (Entity entity : field.field.values()) {
            if (entity instanceof Predator) {
                count++;
            }
        }
        return count;
    }

    private int numberOfHarbivore() {
        int count = 0;
        for (Entity entity : field.field.values()) {
            if (entity instanceof Herbivore) {
                count++;
            }
        }
        return count;
    }

    private synchronized void pauseGame() {

        isPaused = true;

    }

    private synchronized void resumeGame() {
        isPaused = false;
        synchronized (this) {
            notifyAll();
        }
    }

    private void handleUserInput() {

        while (true) {
            String input = checkInput();
            switch (input) {
                case "3":
                    pauseGame();
                    break;
                case "4":
                    resumeGame();
            }
        }

    }

    private String checkInput() {
        String input = SCANNER.next();
        while (!("34".contains(input) && input.length() == 1)) {
            System.out.println("""
                    Неверный ввод
                    Нужно ввести цифру 3 или 4.
                    """);
            input = SCANNER.next();
        }
        return input;
    }

    public void startSimulation() {

        new Thread(this::handleUserInput).start();


        while (true) {

            if (!isPaused) {
                System.out.println("Для постановки на паузу нажмите 3");

                nextTurn();
                try {
                    Thread.sleep(1000); // Пауза 2 секунды
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (!isHerbivore(field) || !IsPredator(field)) {
                    throw new RuntimeException();
                }
            } else {
                synchronized (this) {
                    System.out.println("Симуляция находится на паузе, нажмите 4 что бы продолжить");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public boolean IsPredator(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Predator) {
                return true;
            }
        }
        return false;
    }

    private int numberOfGrass() {
        int count = 0;
        for (Entity entity : field.field.values()) {
            if (entity instanceof Grass) {
                count++;
            }
        }
        return count;
    }

    public boolean isHerbivore(Field field) {
        for (Entity entity : field.field.values()) {
            if (entity instanceof Herbivore) {
                return true;
            }
        }
        return false;
    }

    private boolean isGrass(Field field) {
        int countGrass = -1;
        int countHerbivore = numberOfHarbivore();
        for (Entity entity : field.field.values()) {
            if (entity instanceof Grass) {
                countGrass++;
            }
        }
        return countGrass >= countHerbivore;
    }

}
