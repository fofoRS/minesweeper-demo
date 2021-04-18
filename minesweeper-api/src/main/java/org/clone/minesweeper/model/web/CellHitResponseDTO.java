package org.clone.minesweeper.model.web;

import java.util.List;

public class CellHitResponseDTO {
    private CellDTO cellHit;
    private List<CellDTO> cellsVisited;
    private boolean isHitCellABom;

    public CellHitResponseDTO(CellDTO cellHit, List<CellDTO> cellsVisited, boolean isHitCellABom) {
        this.cellHit = cellHit;
        this.cellsVisited = cellsVisited;
        this.isHitCellABom = isHitCellABom;
    }

    public CellHitResponseDTO() {
    }

    public CellDTO getCellHit() {
        return cellHit;
    }

    public void setCellHit(CellDTO cellHit) {
        this.cellHit = cellHit;
    }

    public List<CellDTO> getCellsVisited() {
        return cellsVisited;
    }

    public void setCellsVisited(List<CellDTO> cellsVisited) {
        this.cellsVisited = cellsVisited;
    }

    public void setHitCellABom(boolean hitCellABom) {
        isHitCellABom = hitCellABom;
    }

    public boolean isHitCellABom() {
        return isHitCellABom;
    }
}
