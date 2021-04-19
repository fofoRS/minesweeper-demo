package org.clone.minesweeper.controller;

import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Game;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.web.CellHitResponseDTO;
import org.clone.minesweeper.service.CellService;
import org.clone.minesweeper.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/game/{id}/cells")
public class CellController {

    private final CellService cellService;
    private final GameService gameService;

    public CellController(CellService cellService, GameService gameService) {

        this.cellService = cellService;
        this.gameService = gameService;
    }

    @PostMapping(value = "/hit", params = {"positionX", "positionY"})
    public CellHitResponseDTO handleHitCell(
            @PathVariable Long id, @RequestParam Integer positionX, @RequestParam Integer positionY) {

        Game game = gameService.getGame(id)
                .orElseThrow(() -> new ApiException("Game not found for id: " + id,HttpStatus.NOT_FOUND));

        Grid grid = game.getGrid();
        if(grid == null) {
            throw new IllegalStateException("Game has not grid built yet");
        }
        validatePositions(positionX, positionY, game.getGrid());
        Cell.Position cellPosition = new Cell.Position(positionX,positionY);
        return cellService.reveal(cellPosition,game);
    }

    @PostMapping(value = "/mark", params = {"positionX", "positionY"})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void markCell(@PathVariable Long id, @RequestParam Integer positionX, @RequestParam Integer positionY) {
        Game game = gameService.getGame(id)
                .orElseThrow(() -> new ApiException("Game not found for id: " + id,HttpStatus.NOT_FOUND));

        Grid grid = game.getGrid();
        if(grid == null) {
            throw new IllegalStateException("Game has not grid built yet");
        }
        validatePositions(positionX, positionY, grid);
        Cell.Position cellPosition = new Cell.Position(positionX,positionY);
        cellService.markAsPossibleBomb(cellPosition,game);
    }

    private void validatePositions(Integer positionX, Integer positionY, Grid grid) {
        if(grid.getGrid().length <= positionX || positionX < 0) {
            throw new ApiException("Row Position is out of boundary.", HttpStatus.BAD_REQUEST);
        } else if (grid.getGrid()[0].length <= positionY || positionY < 0) {
            throw new ApiException("Column Position is out of boundary.", HttpStatus.BAD_REQUEST);
        }
    }
}
