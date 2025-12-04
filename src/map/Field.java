package map;

import entity.*;

import java.util.HashMap;
import java.util.Map;

public class Field {
    public Field(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
    }

    public final int lines;
    public final int columns;

    public Map<Coordinates, Entity> entities = new HashMap<>();
}
