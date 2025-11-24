package gameplay;

import map.Field;

import java.util.HashMap;
import java.util.Scanner;

public class GameLoop {
    static {
        System.out.println("""
                Приветсвую тебя ты в игре Симуляция!
                
                Здесь волки и зайцы борются за выживание до тех пор пока в живых не останется только один  вид существ.
                
                В игре название сущности соответсвуют заглавной букве названия этой сущности на английском языке:
                Заяц = 'H';
                Волк = 'W';
                Камень = 'R';
                Трава = 'G'.

                Немного предисловия.
                
                Существа каждый ход теряют и/или восполняют здоровье:
                Заяц теряет 1HP если не ест траву и/или 3HP если поддвергся нападению со стороны волка;
                Зайц восполняет 3HP если ест траву;
                Волк теряет 1HP если не атакует зайца;
                Волк восполняет 3HP если атакует зайца.
                
                Существа имеют  разные базовые данные :
                Заяц = здоровье 5HP, скорость 2е клетки;
                Волк = здоровье 3HP, атака 3, скорость 1 клеткаю.
                
                В процессе игры трава для зайцев будет восполняться.
                
                Если существо продержалось 5 хода и не умерло то у него появляется потомство. 
                ВНИМАНИЕ стоит ограничение на рождаемость новых существ. 
                Заполненность карты не более чем на половину.
                
                
                ------------------------------------------------------
                ИТАК НАЧНЕМ!!!
                                
                Задай размеры поля для симуляции:
                P.S. Колличество строк и столбцов должно находиться в пределах от 5 до 50 включительно.
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
                Мир создан!
                """);
        simulation.renderer.showMap(simulation.field);




        INNER:
        while (true) {
            System.out.println("""
                    Нажмите 1 что бы продолжить.
                    Нажмите 2 что бы изменить расположение объектов по умолчанию.
                    """);
            String input = checkStartInput0(scanner);
            switch (input) {
                case "2":
                    simulation.field.field = new HashMap<>();
                    simulation.initActions.putEntitiesInTheirDefoultPositions(simulation.field);
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

               String input = checkStartInput(scanner);

                switch (input) {
                    case "1":
                        simulation.nextTurn();
                        break;
                    case "2":
                        simulation.startSimulation();
                    case "3":

                        simulation.initActions.putEntitiesInTheirDefoultPositions(simulation.field);
                }
            }
        } catch (RuntimeException runtimeException) {
            if (simulation.thereIsPredator(simulation.field)) {
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

        }

        scanner.close();
    }

    private String checkStartInput0(Scanner scanner) {
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
        return checkInputNumber(input) && Integer.parseInt(input) <= 50 && Integer.parseInt(input) >= 5;
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
        while (!("132".contains(input) && input.length() == 1)) {
            System.out.println("""
                    Неверный ввод
                    Нужно ввести цифру от 1 до 3.
                    """);
            input = scanner.next();
        }
        return input;
    }

}
