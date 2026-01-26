package gameplay;

import action.Action;
import action.MakeMoveCreaturesAction;
import action.SpawnCreatureAction;
import entity.*;
import map.*;

import java.util.*;

public class Simulation {
    private final static String PAUSE_GAME = "3";
    private final static String CONTINUE_GAME = "4";
    private final static String ACCEPTABLE_NUMBERS = "34";
    private final static int VALID_SIZE_INPUT = 1;
    private final static int PERIOD_SLEEP = 1000;
    private final static String INVALID_ACCEPTABLE_NUMBERS = """
            Неверный ввод
            Нужно ввести цифру 3 или 4.
            """;

    private final static Action[] turnAction = new Action[]{new MakeMoveCreaturesAction(), new SpawnCreatureAction()};
    final Scanner SCANNER = new Scanner(System.in);

    private boolean Paused = false;
    private Field field;
    int moveCounter;
    Renderer renderer = new Renderer();

    Simulation(Field field) {
        this.field = field;
    }

    void startSimulation() {
        Thread thread = new Thread(this::controlSimulation);
        thread.start();
        while (true) {
            if (!Paused) {
                System.out.println(Message.SETTING_PAUSE);
                nextTurn();
                try {
                    Thread.sleep(PERIOD_SLEEP);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                gameOver();
            } else {
                synchronized (this) {
                    System.out.println(Message.CONTINUATION_SIMULATION);
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

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
        } catch (RuntimeException e) {

        }
    }
    void nextTurn() {
        upCounter();
        for (Action action : turnAction) {
            action.execute(field);
        }
        showCountCounter();
        showCountCreature();
        renderer.showMap(field);
        System.out.println();
    }
    private void gameOver() {
        if (!isHerbivore(field) || !IsPredator(field)) {
            throw new RuntimeException();
        }
    }

    private void upCounter() {
        moveCounter++;
    }
    private void showCountCounter() {
        System.out.printf("""
                -----------------ХОД: %s -----------------
                                
                """, moveCounter);
    }
    void showCountCreature() {
        int[] countHerbifore0Predator1Grass2 = field.countCreature();
        System.out.printf("""
                Всего зайцев: %s;
                Всехо волков: %s.
                                
                """, countHerbifore0Predator1Grass2[0], countHerbifore0Predator1Grass2[1]);
    }

    public Field getField() {
        return field;
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
    boolean IsPredator(Field field) {
        for (Entity entity : field.getAllEntities()) {
            if (entity instanceof Predator) {
                return true;
            }
        }
        return false;
    }
    private boolean isHerbivore(Field field) {
        for (Entity entity : field.getAllEntities()) {
            if (entity instanceof Herbivore) {
                return true;
            }
        }
        return false;
    }


}
