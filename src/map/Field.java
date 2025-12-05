package map;

import entity.*;

import java.util.HashMap;
import java.util.Map;

public class Field {



    private final int lines;
    private final int columns;

    public Map<Coordinates, Entity> entities = new HashMap<>();

    public Field(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
    }

    public int getColumns() {
        return columns;
    }
    public int getLines() {
        return lines;
    }
}
