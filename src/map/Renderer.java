package map;

import entity.*;

public class Renderer {

    public void showMap(Field field) {
        for (int lines = 0; lines < field.getLines(); lines++) {
            for (int columns = 0; columns < field.getColumns(); columns++) {
                String cellColor = showCellColor(lines,columns);
                if (isSprittes(lines, columns, field)) {
                    showBusyCell(cellColor, lines, columns, field);
                } else {
                    showFreeCell(cellColor);
                }
            }
            System.out.println();
        }
        System.out.println();

    }

    private void showFreeCell(String cellColor) {
        System.out.print(cellColor + "   " + CellColor.BACKGROUND_RESET);
    }

    private void showBusyCell(String cellColor, int lines, int columns, Field field) {
        System.out.print(cellColor + getSprite(new Coordinates(lines, columns), field) + CellColor.BACKGROUND_RESET);
    }

    private boolean isSprittes(int lines, int columns, Field field) {
        return field.getEntities().containsKey(new Coordinates(lines, columns));
    }

    private String showCellColor(int lines, int columns) {
        return isCellEven(lines, columns) ? CellColor.BACKGROUND_WHITE : CellColor.BACKGROUND_BLUE;
    }

    private String getSprite(Coordinates coordinates, Field field) {
        if (field.getEntities().get(coordinates) instanceof Rock) {
            return Sprites.ROCK;
        }
        if (field.getEntities().get(coordinates) instanceof Tree) {
            return Sprites.TREE;
        }
        if (field.getEntities().get(coordinates) instanceof Grass) {
            return Sprites.GRASS;
        }
        if (field.getEntities().get(coordinates) instanceof Herbivore) {
            return Sprites.HERBIVORE;
        }
        return Sprites.PREDATOR;
    }

    private boolean isCellEven(int lines, int columns) {
        return (lines + columns) % 2 == 0;

    }
}
