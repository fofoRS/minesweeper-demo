package org.clone.minesweeper.model.web;

import javax.validation.constraints.PositiveOrZero;

public class GridParametersRequestDTO {
    private int rows;
    private int cells;
    private int numberOfBombs;

    public GridParametersRequestDTO(@PositiveOrZero int rows, @PositiveOrZero int cells, int numberOfBombs) {
        this.rows = rows;
        this.cells = cells;
        this.numberOfBombs = numberOfBombs;
    }
    public GridParametersRequestDTO() { }

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
