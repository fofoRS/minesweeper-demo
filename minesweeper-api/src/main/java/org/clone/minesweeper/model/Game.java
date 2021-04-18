package org.clone.minesweeper.model;

import java.util.Objects;

public class Game {
    private Long id;
    private Grid grid;
    private boolean isGameOver;

    public Game(Grid grid) {
        this.grid = grid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return isGameOver == game.isGameOver &&
               Objects.equals(id, game.id) &&
               Objects.equals(grid, game.grid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grid, isGameOver);
    }
}
