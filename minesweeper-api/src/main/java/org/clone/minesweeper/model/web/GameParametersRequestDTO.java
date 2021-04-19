package org.clone.minesweeper.model.web;

import javax.validation.constraints.PositiveOrZero;

public class GameParametersRequestDTO {
    private int rows;
    private int cells;
    private int numberOfBombs;

    public GameParametersRequestDTO(@PositiveOrZero int rows, @PositiveOrZero int cells, int numberOfBombs) {
        this.rows = rows;
        this.cells = cells;
        this.numberOfBombs = numberOfBombs;
    }
    public GameParametersRequestDTO() { }

    public int getRows() {
        return rows;
    }

    public int getCells() {
        return cells;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

}
