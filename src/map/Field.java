package map;

import entity.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Field {
    private final int lines;
    private final int columns;

    private Map<Coordinates, Entity> entities = new HashMap<>();

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

    public void deleteEntities(Coordinates coordinates) {
        entities.remove(coordinates);
    }
    public void installEntities(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }
    public int[] countCreature() {
        int countHerbifore = 0;
        int countPredator = 0;
        int countGrass =  0;
        for (Entity entity : entities.values()) {
            if (entity instanceof Herbivore) {
                countHerbifore++;
            }
            if (entity instanceof Predator) {
                countPredator++;
            }
            if (entity instanceof Grass) {
                countGrass++;
            }
        }
        return new int[]{countHerbifore, countPredator, countGrass};
    }
    public Collection<Entity> getAllEntities(){
        return entities.values();
    }
    public int countSizeEntities(){
        return entities.size();
    }
    public int countSizeMap(){
        return lines*columns;
    }
    public Map<Coordinates, Entity> getEntities() {
        return entities;
    }
}
