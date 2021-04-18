package org.clone.minesweeper.model;

import javax.validation.constraints.PositiveOrZero;

public class GameParametersDTO {

    private final int rows;
    private final int cells;
    private final int numberOfBombs;

    public GameParametersDTO(int rows, int cells, int numberOfBombs) {
        this.rows = rows;
        this.cells = cells;
        this.numberOfBombs = numberOfBombs;
    }
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
