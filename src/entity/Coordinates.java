package entity;

import java.util.Objects;

public class Coordinates {

    public final int line;
    public final int column;

    public Coordinates(int line, int column){
        this.line =line;
        this.column =column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return line == that.line && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }
}
