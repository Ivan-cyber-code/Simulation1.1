package entity;

public abstract class Entity {
    Entity(Coordinates coordinates){
        this.coordinate=coordinates;
    }
    public Coordinates coordinate;
}
