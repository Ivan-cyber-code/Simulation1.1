package gameplay;

import entity.*;
import map.*;

import java.util.*;

public class Simulation {

    private boolean Paused = false;
    Field field;
    int moveCounter;
    Renderer renderer = new Renderer();
    InitAction initActions = new InitAction();
    TurnAction turnActions = new TurnAction();

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
                """, countHarbivore(), countPredator());
        System.out.println();

        renderer.showMap(field);

        if (!isGrass(field)) {
            for (int i = countGrass() - 1; i <= countHarbivore(); i++) {
                initActions.plantGrass(field);
            }
            System.out.println();
        }
    }

    private int countPredator() {
        int count = 0;
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Predator) {
                count++;
            }
        }
        return count;
    }

    private int countHarbivore() {
        int count = 0;
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Herbivore) {
                count++;
            }
        }
        return count;
    }

    private synchronized void pauseGame() {
        Paused = true;
    }

    private synchronized void resumeGame() {
        Paused = false;
        synchronized (this) {
            notifyAll();
        }
    }

    private void controlSimulation() {
        try {
            while (true) {
                String input = checkInput(SCANNER.next());
                switch (input) {
                    case "3":
                        pauseGame();
                        break;
                    case "4":
                        resumeGame();
                }
            }
        } catch (RuntimeException e){

        }
    }

    private String checkInput(String input) {
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

        Thread thread = new Thread(this::controlSimulation);
        thread.start();


        while (true) {

            if (!Paused) {
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
                        System.out.println("Конец!!!");;
                    }
                }
            }
        }

    }

    public boolean IsPredator(Field field) {
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Predator) {
                return true;
            }
        }
        return false;
    }

    private int countGrass() {
        int count = 0;
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Grass) {
                count++;
            }
        }
        return count;
    }

    public boolean isHerbivore(Field field) {
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Herbivore) {
                return true;
            }
        }
        return false;
    }

    private boolean isGrass(Field field) {
        int countGrass = -1;
        int countHerbivore = countHarbivore();
        for (Entity entity : field.entities.values()) {
            if (entity instanceof Grass) {
                countGrass++;
            }
        }
        return countGrass >= countHerbivore;
    }

}
