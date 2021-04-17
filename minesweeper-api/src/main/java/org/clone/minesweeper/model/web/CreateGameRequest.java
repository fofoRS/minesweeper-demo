package org.clone.minesweeper.model.web;

import javax.validation.constraints.PositiveOrZero;

public class CreateGameRequest {
    @PositiveOrZero
    private int rows;
    @PositiveOrZero
    private int cells;
    private int numberOfBombs;

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
