package model;

public class Pair<T, U> {
    private T map;
    private U file;

    public Pair(T first, U second) {
        this.map = first;
        this.file = second;
    }

    public T getMap() {
        return map;
    }

    public void setMap(T map) {
        this.map = map;
    }

    public U getFile() {
        return file;
    }

    public void setFile(U file) {
        this.file = file;
    }

}
