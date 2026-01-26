package gameplay;

import action.*;
import map.Field;

import java.util.Scanner;

public class GameLoop {
    private final static int MAX_LIMIT_FIELD=50;
    private final static int MIN_LIMIT_FIELD=5;
    private final static String VALID_CHOICE="12";
    private final static int VALID_SIZE_INPUT=1;

    private final Scanner scanner = new Scanner(System.in);
    private Simulation simulation ;
    private final Action initAction = new SpawnEntityAction();

    public void startGame() {
        System.out.println(Message.PREFACE);
        simulation = new Simulation(new Field(createdLine(), createdColumns()));
        initAction.execute(simulation.getField());
        System.out.println(Message.MESSAGE_CREATION_WORLD);
        simulation.showCountCreature();
        simulation.renderer.showMap(simulation.getField());
        INNER:
        while (true) {
            System.out.println(Message.ASSERTION_POSITION_ENTITIES);
            String input = makeChoice();
            switch (input) {
                case "2":
                    simulation.getField().getEntities().clear();
                    initAction.execute(simulation.getField());
                    System.out.println(Message.MESSAGE_CREATION_WORLD);
                    simulation.renderer.showMap(simulation.getField());
                    break;
                case "1":
                    break INNER;
            }
        }
        System.out.println(Message.MAKE_MOVE);
        try {
            while (true) {
                String input = makeChoice();
                switch (input) {
                    case "1":
                        simulation.nextTurn();
                        System.out.println(Message.MAKE_MOVE);
                        break;
                    case "2":
                        simulation.startSimulation();
                }
            }
        } catch (RuntimeException runtimeException) {
            if (simulation.IsPredator(simulation.getField())) {
                System.out.println(Message.VICTORI_PREDATOR);
                simulation.renderer.showMap(simulation.getField());
            } else {
                System.out.println(Message.VICTORI_HERBIFORE);
                simulation.renderer.showMap(simulation.getField());
            }
            simulation.SCANNER.close();
            scanner.close();
        }
        scanner.close();
    }

    private int createdColumns() {
        System.out.println(Message.COLUMN_QUERY);
        return enter(scanner);
    }
    private int createdLine() {
        System.out.println(Message.LINE_QUERY);
        return enter(scanner);
    }
    private int enter(Scanner scanner) {
        String input = сheckInput(scanner.next());
        return Integer.parseInt(input);
    }
    private String сheckInput(String input) {
        while (!isNumber(input) || !isValidStartInput(input)) {
            if (!isNumber(input)) {
                System.out.println(Message.INVALID_FORMAT_LIMIT_FIELD);
            }
            if (!isValidStartInput(input)) {
                System.out.println(Message.INVALID_CONDITION);
            }
            input = scanner.next();
        }
        return input;
    }
    private boolean isValidStartInput(String input) {
        return isNumber(input) && Integer.parseInt(input) <= MAX_LIMIT_FIELD && Integer.parseInt(input) >= MIN_LIMIT_FIELD;
    }
    private boolean isNumber(String input) {
        for (char a : input.toCharArray()) {
            if (!Character.isDigit(a)) {
                return false;
            }
        }
        return true;
    }
    private String makeChoice() {
        String input = scanner.next();
        while (!(VALID_CHOICE.contains(input) && input.length() == VALID_SIZE_INPUT)) {
            System.out.println(Message.INVALID_FORMAT_INPUT);
            input = scanner.next();
        }
        return input;
    }
}
