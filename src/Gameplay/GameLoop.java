package Gameplay;

import Map.Field;

import java.util.Scanner;

public class GameLoop {
    static {
        System.out.println("""
                Приветсвую тебя ты в игре Симуляция!
                Задай размеры поля для симуляции.
                Колличество строк и столбцов должно находиться в пределах от 5 до 50 включительно. 
                """);
    }

    Scanner scanner = new Scanner(System.in);

    Simulation simulation = new Simulation(new Field(createdLine(), createdColumns()));

    private int createdColumns() {
        System.out.println("Колличество столбцов");
        return input(scanner);
    }

    private int createdLine() {
        System.out.println("Колличестов строк:");
        return input(scanner);
    }

    public void startGame() {

        simulation.initActions.putEntitiesInTheirDefoultPositions(simulation.field);
        System.out.println("""
                Мир создан с указанными размерами.
                Нажмите 1 что бы сделать ход всеми существами.
                Нажмите 2 что бы запустить режим постоянного взаимодействия.
                """);
        simulation.renderer.showMap(simulation.field);

        try {
            while (true) {

                String input = checkStartInput(scanner);

                switch (input) {
                    case "1":
                        simulation.nextTurn();
                        break;
                    case "2":
                        simulation.startSimulation();
                }
            }
        } catch (RuntimeException runtimeException) {
            simulation.renderer.showMap(simulation.field);
            System.out.println("Волки победили");
        }

        scanner.close();
    }

    private int input(Scanner scanner) {
        String input = scanner.next();
        while (!checkInputNumber(input) || !checkInputAcceptable(input)) {
            if (!checkInputNumber(input)) {
                System.out.println("Неверный формат, нужно ввести число целое число.");
            }
            if (!checkInputAcceptable(input)) {
                System.out.println("Это число, но оно не входит в диапозн от 0 до 50.");
            }
            input = scanner.next();
        }
        return Integer.parseInt(input);
    }

    private boolean checkInputAcceptable(String input) {
        if (checkInputNumber(input) && Integer.parseInt(input) <= 50 && Integer.parseInt(input) >= 5) {
            return true;
        }
        return false;
    }

    private boolean checkInputNumber(String input) {
        for (char a : input.toCharArray()) {
            if (!Character.isDigit(a)) {
                return false;
            }
        }
        return true;
    }

    private String checkStartInput(Scanner scanner) {
        String input = scanner.next();
        while (!("12".indexOf(input) >= 0 && input.length() == 1)) {
            System.out.println("""
                    Неверный ввод
                    Нужно ввести цифру 1 или 2.
                    """);
            input = scanner.next();
        }
        return input;
    }

}
