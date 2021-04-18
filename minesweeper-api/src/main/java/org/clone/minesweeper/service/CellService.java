package org.clone.minesweeper.service;

import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.web.CellDTO;
import org.clone.minesweeper.model.web.CellHitResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellService {

    public CellHitResponseDTO hitCell(Cell cell, Grid grid) {
        cell.setVisited(true);
        CellDTO hitCellDTO;
        if (cell.isBomb()) {
            hitCellDTO = new CellDTO(cell.getPosition(),true,0);
            return new CellHitResponseDTO(hitCellDTO, List.of(),true);
        }

        List<CellDTO> adjacentsVisited = new ArrayList<>();
        int adjacentBombsCount = 0;
        for (Cell.Position adjacent : cell.getAdjacents()) {
            Cell adjacentCell = grid.getGrid()[adjacent.getX()][adjacent.getY()];
            if(adjacentCell.isBomb()) {
                adjacentBombsCount++;
                continue;
            } else if(adjacentCell.isVisited()) {
                continue;
            }
            traverseCellAdjacent(adjacentCell.getPosition(),grid,adjacentsVisited);
        }
        hitCellDTO = new CellDTO(cell.getPosition(),true,adjacentBombsCount);
        return new CellHitResponseDTO(hitCellDTO,adjacentsVisited,false);
    }

    private void traverseCellAdjacent(
            Cell.Position adjacentPosition, Grid grid, List<CellDTO> adjacentsVisited) {
        Cell cell = grid.getGrid()[adjacentPosition.getX()][adjacentPosition.getY()];
        if(cell.isBomb() || cell.isVisited()) {
            return;
        }
        cell.setVisited(true);
        int adjacentBombs = 0;
        for (Cell.Position adjacent : cell.getAdjacents()) {
            Cell adjacentCell = grid.getGrid()[adjacent.getX()][adjacent.getY()];
            if(adjacentCell.isBomb()) {
                adjacentBombs ++;
                continue;
            } else if(adjacentCell.isVisited()) {
                continue;
            }
            traverseCellAdjacent(adjacentCell.getPosition(),grid,adjacentsVisited);
        }
        CellDTO currentCellDTO = new CellDTO(adjacentPosition,true,adjacentBombs);
        adjacentsVisited.add(currentCellDTO);
    }
}
