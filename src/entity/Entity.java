package entity;

public abstract class Entity {



    private Coordinates coordinate;

    Entity(Coordinates coordinates) {
        this.coordinate = coordinates;
    }

    Coordinates getCoordinate() {
        return coordinate;
    }
    void setCoordinate(Coordinates coordinate) {
        this.coordinate = coordinate;
    }

}
