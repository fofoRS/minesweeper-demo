package org.clone.minesweeper.model.web;

import org.clone.minesweeper.model.Cell;

import java.util.List;

public class GridResponseDTO {
    private List<Cell.Position> positions;
    private List<Cell.Position> bombPositions;
    public GridResponseDTO(List<Cell.Position> positions, List<Cell.Position> bombPositions) {
        this.positions = positions;
        this.bombPositions = bombPositions;
    }
    public GridResponseDTO(){}

    public List<Cell.Position> getPositions() {
        return positions;
    }

    public List<Cell.Position> getBombPositions() {
        return bombPositions;
    }
}
