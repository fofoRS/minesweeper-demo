package org.clone.minesweeper.model;

import java.util.List;

public class Grid {
    private final Cell [][]grid;
    private List<Cell.Position> bombsPositions;
    private static Grid INSTANCE;

    private Grid(Cell[][] grid, List<Cell.Position> bombsPositions) {
        this.grid = grid;
        this.bombsPositions = bombsPositions;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public static Grid createInstance(Cell [][]grid,List<Cell.Position> bombsPositions) {
        INSTANCE = new Grid(grid,bombsPositions);
        return INSTANCE;
    }

    public static Grid getGridInstance() {
        if(INSTANCE == null) {
            throw new IllegalStateException("No game instance found.");
        }
        return INSTANCE;
    }
}
