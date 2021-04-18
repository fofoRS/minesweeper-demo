package org.clone.minesweeper.model.web;

import org.clone.minesweeper.model.Cell;

import java.util.Objects;

public class CellDTO {
    private Cell.Position position;
    private Boolean isVisited;
    private Integer adjacentBombsCount;

    public CellDTO(Cell.Position position, Boolean isVisited, Integer adjacentBombsCount) {
        this.position = position;
        this.isVisited = isVisited;
        this.adjacentBombsCount = adjacentBombsCount;
    }

    public CellDTO() { }

    public Cell.Position getPosition() {
        return position;
    }

    public void setPosition(Cell.Position position) {
        this.position = position;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public Integer getNearBombsCount() {
        return adjacentBombsCount;
    }

    public void setNearBombsCount(Integer nearBombsCount) {
        this.adjacentBombsCount = nearBombsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellDTO cellDTO = (CellDTO) o;
        return Objects.equals(position, cellDTO.position) &&
               Objects.equals(isVisited, cellDTO.isVisited) &&
               Objects.equals(adjacentBombsCount, cellDTO.adjacentBombsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, isVisited, adjacentBombsCount);
    }
}
