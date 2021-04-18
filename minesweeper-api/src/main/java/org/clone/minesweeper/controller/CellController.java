package org.clone.minesweeper.controller;

import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.web.CellHitResponseDTO;
import org.clone.minesweeper.service.CellService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cells")
public class CellController {

    private final CellService cellService;

    public CellController(CellService cellService) {
        this.cellService = cellService;
    }

    @PostMapping(value = "/hit", params = {"positionX", "positionY"})
    public CellHitResponseDTO handleHitCell(@RequestParam Integer positionX, @RequestParam Integer positionY) {
        Grid grid;
        try {
            grid = Grid.getGridInstance();
        } catch (IllegalStateException e) {
            throw new ApiException("Grid has not been created yet.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        validatePositions(positionX, positionY, grid);
        Cell cell = grid.getGrid()[positionX][positionY];
        return cellService.reveal(cell,grid);
    }

    @PostMapping(value = "/mark", params = {"positionX", "positionY"})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void markCell(@RequestParam Integer positionX, @RequestParam Integer positionY) {
        Grid grid;
        try {
            grid = Grid.getGridInstance();
        } catch (IllegalStateException e) {
            throw new ApiException("Grid has not been created yet.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        validatePositions(positionX, positionY, grid);
        Cell cell = grid.getGrid()[positionX][positionY];
        cellService.markAsPossibleBomb(cell);

    }

    private void validatePositions(Integer positionX, Integer positionY, Grid grid) {
        if(grid.getGrid().length <= positionX) {
            throw new ApiException("Row Position is out of boundary.", HttpStatus.BAD_REQUEST);
        } else if (grid.getGrid()[0].length <= positionY) {
            throw new ApiException("Column Position is out of boundary.", HttpStatus.BAD_REQUEST);
        }
    }
}
