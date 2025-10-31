import Entity.*;
import Map.*;

public class Simulation {

    Field field;
    int moveCounter;
    Renderer renderer = new Renderer();
    InitActions initActions = new InitActions();
    TurnActions turnActions = new TurnActions();
    int count;


    public Simulation(Field field) {
        this.field = field;
    }

    public void nextTurn() {
        System.out.println("Все существа начинаю делать ход:");
        turnActions.makeAMoveWithAllCreatures(field);
        System.out.println("Транслируем карту на экран");
        renderer.showMap(field);
    }

    public void startSimulation() {
        System.out.println("Расставляем сущности по местам");
        initActions.putEntitiesInTheirDefoultPositions(field);
        System.out.println("Транслируем карту на экран");
        renderer.showMap(field);
        boolean flag = true;

        System.out.println("Запускаю бесконечный цикл");
        while (flag) {

            count++;
            System.out.printf("-----------Период: %s----------------",count);
            System.out.println();
            System.out.println("Запускаем метод по запуску хода для всех сущностей");
            nextTurn();

            System.out.println("Выполняем задержку в 1..2..");
            try {
                Thread.sleep(1000); // Пауза 2 секунды
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Проверяем осталась ли трава?");
            if (!thereIsGrass(field)) {
                System.out.println("Травы нет. Посадить траву.");
                initActions.plantGrass(field);
                renderer.showMap(field);
            }

            System.out.println("Заец остался?");
            if (!thereIsHerbivore(field)) {
                System.out.println("Все зайцы уничтожены");
                flag = false;
                System.out.println("Транслируем карту на экран");
                renderer.showMap(field);
            }
        }
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
        for (Entity entity : field.field.values()) {
            if (entity instanceof Grass) {
                return true;
            }
        }
        return false;
    }


    Actions[] gameLoop;

}
