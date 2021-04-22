package org.clone.minesweeper.service;

import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Game;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.web.CellDTO;
import org.clone.minesweeper.model.web.CellHitResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellService {

    private final GameService gameService;

    @Autowired
    public CellService(GameService gameService) {
        this.gameService = gameService;
    }

    public CellHitResponseDTO reveal(Cell.Position position, Game game) {
        Grid grid = game.getGrid();
        Cell cell = grid.getGrid()[position.getX()][position.getY()];
        cell.setVisited(true);
        CellDTO hitCellDTO;
        if (cell.isBomb()) {
            grid.updateCell(cell);
            game.setGrid(grid);
            game.setGameOver(true);
            gameService.updateGame(game);

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
        cell.setAdjacentsBombsCount(adjacentBombsCount);
        grid.updateCell(cell);
        game.setGrid(grid);
        gameService.updateGame(game);

        hitCellDTO = new CellDTO(cell.getPosition(),true,adjacentBombsCount);
        return new CellHitResponseDTO(hitCellDTO,adjacentsVisited,false);
    }

    public void markAsPossibleBomb(Cell.Position position, Game game) {
        Grid grid = game.getGrid();
        Cell cell = grid.getGrid()[position.getX()][position.getY()];
        cell.setMark(true);
        grid.updateCell(cell);
        game.setGrid(grid);
        gameService.updateGame(game);
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
