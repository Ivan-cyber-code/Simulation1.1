package Map;


import Entity.*;

public class Renderer {

    private String toKnowSprites(Coordinates coordinates, Field field){
        if(field.field.get(coordinates) instanceof Rock){
            return Sprites.rock;
        }
        if(field.field.get(coordinates) instanceof Tree){
            return Sprites.tree;
        }
        if(field.field.get(coordinates) instanceof Grass){
            return Sprites.grass;
        }
        if(field.field.get(coordinates) instanceof Herbivore){
            return Sprites.herbivore;
        }

        if(field.field.get(coordinates) instanceof Predator){
            return Sprites.predator;
        }
        return null;
    }

    public void showMap(Field field){
        for (int lines = 0; lines < field.lines; lines++) {
            for (int columns = 0; columns < field.columns; columns++) {
                if(field.field.containsKey(new Coordinates(lines,columns))){
                    System.out.print(toKnowSprites(new Coordinates(lines,columns), field));
                } else{
                    System.out.print(" * ");
                }
            }
            System.out.println();
        }
        System.out.println();

    }
}
