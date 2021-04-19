package org.clone.minesweeper.controller;

import org.clone.minesweeper.config.GameParameterProperties;
import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.GameParametersDTO;
import org.clone.minesweeper.model.web.GameParametersRequestDTO;
import org.springframework.http.HttpStatus;

import static org.clone.minesweeper.exception.ApiExceptionMessages.NO_CELLS_AVAILABLE_FOR_BOMBS;

public abstract class BaseGameController {

    protected void validateRequestArguments(GameParametersRequestDTO request){
        if(request.getNumberOfBombs() > (request.getRows() * request.getCells())) {
            throw new ApiException(NO_CELLS_AVAILABLE_FOR_BOMBS, HttpStatus.BAD_REQUEST);
        }
    }

    protected GameParametersDTO getGameParameters(
            GameParametersRequestDTO request, GameParameterProperties gameDefaultParameters) {
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
