package org.clone.minesweeper.model;

import java.util.List;
import java.util.Objects;

public class Cell {

    private final Position position;
    private final boolean isBomb;
    private boolean isVisited;
    private boolean potentialBomb;
    private List<Position> adjacents;

    public Cell(Position position, boolean isBomb) {
        this.position = position;
        this.isBomb = isBomb;
    }

    public Cell(Position position, boolean isBomb,List<Position> adjacents) {
        this.position = position;
        this.isBomb = isBomb;
        this.adjacents = adjacents;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public List<Position> getAdjacents() {
        return adjacents;
    }

    public void addAdjacents(List<Position> adjacents) {
        this.adjacents = adjacents;
    }

    public boolean isPotentialBomb() {
        return potentialBomb;
    }

    public void setPotentialBomb(boolean potentialBomb) {
        this.potentialBomb = potentialBomb;
    }

    public void setAdjacents(List<Position> adjacents) {
        this.adjacents = adjacents;
    }

    public static class Position {
        private int x,y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(){}

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x &&
                   y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
