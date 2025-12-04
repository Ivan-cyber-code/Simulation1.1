package map;

import entity.*;

public class Renderer {

    private String getSprite(Coordinates coordinates, Field field) {
        if (field.entities.get(coordinates) instanceof Rock) {
            return Sprites.ROCK;
        }
        if (field.entities.get(coordinates) instanceof Tree) {
            return Sprites.TREE;
        }
        if (field.entities.get(coordinates) instanceof Grass) {
            return Sprites.GRASS;
        }
        if (field.entities.get(coordinates) instanceof Herbivore) {
            return Sprites.HERBIVORE;
        }

        if (field.entities.get(coordinates) instanceof Predator) {
            return Sprites.PREDATOR;
        }
        return null;
    }

    private boolean isCellEven(int lines, int columns) {
        return (lines + columns) % 2 == 0;

    }

    public void showMap(Field field) {
        for (int lines = 0; lines < field.lines; lines++) {
            for (int columns = 0; columns < field.columns; columns++) {
                String cellColor = isCellEven(lines, columns) ? CellColor.BACKGROUND_WHITE : CellColor.BACKGROUND_BLUE;
                if (field.entities.containsKey(new Coordinates(lines, columns))) {
                    System.out.print(cellColor + getSprite(new Coordinates(lines, columns), field) + CellColor.BACKGROUND_RESET);
                } else {
                    System.out.print(cellColor + "   " + CellColor.BACKGROUND_RESET);
                }
            }
            System.out.println();
        }
        System.out.println();

    }
}
