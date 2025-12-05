package gameplay;

import entity.*;
import map.*;

import java.util.*;

public class Simulation {

    private final static String PAUSE_GAME="3";
    private final static String CONTINUE_GAME="4";
    private final static String ACCEPTABLE_NUMBERS="34";
    private final static int VALID_SIZE_INPUT=1;
    private final static String INVALID_ACCEPTABLE_NUMBERS =  """
                    Неверный ввод
                    Нужно ввести цифру 3 или 4.
                    """;


    private boolean Paused = false;
    Field field;
    int moveCounter;
    Renderer renderer = new Renderer();
    InitAction initActions = new InitAction();
    TurnAction turnActions = new TurnAction();

    final Scanner SCANNER = new Scanner(System.in);

    Simulation(Field field) {
        this.field = field;
    }

    void nextTurn() {
        moveCounter++;

        System.out.printf("""
                -----------------ХОД: %s -----------------
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

    private void controlSimulation() {
        try {
            while (true) {
                String input = checkInput(SCANNER.next());
                switch (input) {
                    case PAUSE_GAME:
                        pauseGame();
                        break;
                    case CONTINUE_GAME:
                        resumeGame();
                }
            }
        } catch (RuntimeException e){

        }
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


    private String checkInput(String input) {
        while (!(ACCEPTABLE_NUMBERS.contains(input) && input.length() == VALID_SIZE_INPUT)) {
            System.out.println(INVALID_ACCEPTABLE_NUMBERS);
            input = SCANNER.next();
        }
        return input;
    }

    void startSimulation() {

        Thread thread = new Thread(this::controlSimulation);
        thread.start();


        while (true) {

            if (!Paused) {
                System.out.println(Message.SETTING_PAUSE);

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
                    System.out.println(Message.CONTINUATION_SIMULATION);
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.println("Конец!!!");;
                    }
                }
            }
        }

    }

    boolean IsPredator(Field field) {
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

    private boolean isHerbivore(Field field) {
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
