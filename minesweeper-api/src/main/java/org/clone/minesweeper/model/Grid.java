package org.clone.minesweeper.model;

import java.util.List;

public class Grid {
    private int rows;
    private int columns;
    private final Cell [][]grid;
    private List<Cell.Position> bombsPositions;

    public Grid(Cell[][] grid, List<Cell.Position> bombsPositions,int rows, int columns) {
        this.grid = grid;
        this.bombsPositions = bombsPositions;
        this.rows = rows;
        this.columns = columns;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void updateCell(Cell cell) {
        grid[cell.getPosition().getX()][cell.getPosition().getY()] = cell;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Cell.Position> getBombsPositions() {
        return bombsPositions;
    }
}
