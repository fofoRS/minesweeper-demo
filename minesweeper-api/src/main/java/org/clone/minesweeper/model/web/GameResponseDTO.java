package org.clone.minesweeper.model.web;

import org.clone.minesweeper.model.Grid;

public class GameResponseDTO {
    private Long id;
    private GridResponseDTO grid;
    private boolean isGameOver;

    public GameResponseDTO(Long id, GridResponseDTO grid, boolean isGameOver) {
        this.id = id;
        this.grid = grid;
        this.isGameOver = isGameOver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GridResponseDTO getGrid() {
        return grid;
    }

    public void setGrid(GridResponseDTO grid) {
        this.grid = grid;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
