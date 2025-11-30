package gameplay;

import map.Field;

import java.util.HashMap;
import java.util.Scanner;

public class GameLoop {

    static {
        System.out.println(Message.PREFACE);
    }

    Scanner scanner = new Scanner(System.in);

    Simulation simulation = new Simulation(new Field(createdLine(), createdColumns()));

    private int createdColumns() {
        System.out.println(Message.COLUMN_QUERY);
        return enter(scanner);
    }

    private int createdLine() {
        System.out.println(Message.LINE_QUERY);
        return enter(scanner);
    }

    public void startGame() {

        simulation.initActions.putEntitiesrDefoultPositions(simulation.field);
        System.out.println(Message.MESSAGE_CREATION_WORLD);
        simulation.renderer.showMap(simulation.field);


        INNER:
        while (true) {
            System.out.println("""
                    Нажмите 1 что бы продолжить.
                    Нажмите 2 что бы изменить расположение объектов по умолчанию.
                    """);
            String input = makeChoice(scanner);
            switch (input) {
                case "2":
                    simulation.field.entities = new HashMap<>();
                    simulation.initActions.putEntitiesrDefoultPositions(simulation.field);
                    System.out.println("""
                            Мир создан!
                            """);
                    simulation.renderer.showMap(simulation.field);
                    break;
                case "1":
                    break INNER;
            }
        }

        System.out.println("""
                Нажмите 1 что бы сделать ход всеми существами.
                Нажмите 2 что бы запустить режим постоянного взаимодействия.
                """);
        try {
            while (true) {

                String input = makeChoice(scanner);

                switch (input) {
                    case "1":
                        simulation.nextTurn();
                        break;
                    case "2":
                        simulation.startSimulation();
                }
            }
        } catch (RuntimeException runtimeException) {


            if (simulation.IsPredator(simulation.field)) {
                System.out.println("""
                        -------------Конец---------------
                        Волки победили!!!
                        """);
                simulation.renderer.showMap(simulation.field);
            } else {
                System.out.println("""
                        -------------Конец---------------
                        Зайцы победили!!!
                        """);
                simulation.renderer.showMap(simulation.field);
            }
            simulation.SCANNER.close();
            scanner.close();
        }

        scanner.close();
    }

    private int enter(Scanner scanner) {
        String input = сheckInput(scanner.next());
        return Integer.parseInt(input);
    }

    private String сheckInput(String input) {
        while (!isNumber(input) || !isValidStartInput(input)) {
            if (!isNumber(input)) {
                System.out.println("Неверный формат, нужно ввести натуральное число.");
            }
            if (!isValidStartInput(input)) {
                System.out.println("Это число, но оно не входит в диапозн от 5 до 50.");
            }
            input = scanner.next();
        }
        return input;
    }

    private boolean isValidStartInput(String input) {
        return isNumber(input) && Integer.parseInt(input) <= 50 && Integer.parseInt(input) >= 5;
    }

    private boolean isNumber(String input) {
        for (char a : input.toCharArray()) {
            if (!Character.isDigit(a)) {
                return false;
            }
        }
        return true;
    }

    private String makeChoice(Scanner scanner) {
        String input = scanner.next();
        while (!("12".contains(input) && input.length() == 1)) {
            System.out.println("""
                    Неверный ввод
                    Нужно ввести цифру от 1 до 2.
                    """);
            input = scanner.next();
        }
        return input;
    }

}
