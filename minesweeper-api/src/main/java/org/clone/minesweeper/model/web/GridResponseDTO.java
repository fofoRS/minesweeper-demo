package org.clone.minesweeper.model.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Grid;

import java.util.List;

public class GridResponseDTO {
    private List<Cell.Position> positions;
    private List<Cell.Position> bombPositions;
    @JsonIgnore
    private Grid grid;

    public GridResponseDTO(List<Cell.Position> positions, List<Cell.Position> bombPositions,Grid grid) {
        this.positions = positions;
        this.bombPositions = bombPositions;
        this.grid = grid;
    }
    public GridResponseDTO(){}

    public List<Cell.Position> getPositions() {
        return positions;
    }

    public List<Cell.Position> getBombPositions() {
        return bombPositions;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
