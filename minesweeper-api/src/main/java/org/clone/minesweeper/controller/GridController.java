package org.clone.minesweeper.controller;

import org.clone.minesweeper.config.GameParameterProperties;
import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.GameParametersDTO;
import org.clone.minesweeper.model.web.GridResponseDTO;
import org.clone.minesweeper.model.web.GridParametersRequestDTO;
import org.clone.minesweeper.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/grid")
public class GridController {

    private static final String NO_CELLS_AVAILABLE_FOR_BOMBS =
            "Number of bombs greater than total of cells";

    private final GameParameterProperties gameDefaultParameters;
    private final GridService gridService;

    @Autowired
    public GridController(GameParameterProperties gameDefaultParameters, GridService gridService) {
        this.gameDefaultParameters = gameDefaultParameters;
        this.gridService = gridService;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GridResponseDTO buildGrid(@Valid @RequestBody GridParametersRequestDTO request) {
        validateRequestArguments(request);
        return gridService.createNewGrid(getGameParameters(request));
    }

    private void validateRequestArguments(GridParametersRequestDTO request){
        if(request.getNumberOfBombs() > (request.getRows() * request.getCells())) {
            throw new ApiException(NO_CELLS_AVAILABLE_FOR_BOMBS,HttpStatus.BAD_REQUEST);
        }
    }

    private GameParametersDTO getGameParameters(GridParametersRequestDTO request) {
        int rows, cells, bombs;
        if(request.getRows() == 0) {
            rows = gameDefaultParameters.getRows();
        } else {
            rows = request.getRows();
        }
        if(request.getCells() == 0) {
            cells = gameDefaultParameters.getCells();
        } else {
            cells = request.getCells();
        }
        if(request.getNumberOfBombs() == 0) {
            bombs = gameDefaultParameters.getBombs();
        } else {
            bombs = request.getNumberOfBombs();
        }
        return new GameParametersDTO(rows,cells,bombs);
    }
}
