package Gameplay;

import Map.Field;

import java.util.Scanner;

public class GameLoop {
    static {
        System.out.println("""
                Приветсвую тебя ты в игре Симуляция!
                Мир сгенерирован
                Нажми 1 чтобы сделать ход все существами.
                Нажми 2 чтобы запустить бесконечный цикл симуляции.
                Нажми 3 чтобы поставить симуляцию на паузу.
                """);
    }

    Simulation simulation = new Simulation(new Field(10, 10));

    public void startGame() {
        simulation.initActions.putEntitiesInTheirDefoultPositions(simulation.field);

        Scanner scanner = new Scanner(System.in);

        String input = checkInput(scanner);


        scanner.close();
    }

    private String checkInput(Scanner scanner) {
        String input = scanner.next();
        while (!("123".indexOf(input) >= 0 && input.length() == 1)){
            System.out.println("""
                    Неверный ввод
                    Попробуйте ввести повторно
                    """);
            input = scanner.next();
        };

        return input;
    }

}
