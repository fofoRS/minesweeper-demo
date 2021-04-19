package org.clone.minesweeper.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.clone.minesweeper.config.GameParameterProperties;
import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.Game;
import org.clone.minesweeper.model.GameParametersDTO;
import org.clone.minesweeper.model.web.GameParametersRequestDTO;
import org.clone.minesweeper.model.web.GameResponseDTO;
import org.clone.minesweeper.model.web.GridResponseDTO;
import org.clone.minesweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.Valid;

import static org.clone.minesweeper.exception.ApiExceptionMessages.GAME_IS_OVER;
import static org.clone.minesweeper.exception.ApiExceptionMessages.GAME_NOT_FOUND_MSG_TEMPLATE;

@RestController
@RequestMapping("/api/v1/game")
public class GameController extends BaseGameController {

    private final GameParameterProperties gameDefaultParameters;
    private final GameService gameService;

    @Autowired
    public GameController(GameParameterProperties gameParameterProperties,GameService gameService) {
        this.gameDefaultParameters = gameParameterProperties;
        this.gameService = gameService;
    }

    @Operation(
            method = "POST",
            description = "Handles the creation of a new game with the specific game parameters.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponseDTO createGame(
            @Valid @RequestBody(required = false) GameParametersRequestDTO request) {
        validateRequestArguments(request);
        GameParametersDTO gameParameters = getGameParameters(request,gameDefaultParameters);
        return gameService.createGame(gameParameters);
    }

    @Operation(method = "PATCH", description = "Handles the resetting of the grid for a given game.")
    @PatchMapping("/reset/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public GridResponseDTO resetGame(@PathVariable Long id) {
        Game game = gameService.getGame(id).orElseThrow(() ->
                new ApiException(
                        String.format(GAME_NOT_FOUND_MSG_TEMPLATE,id), HttpStatus.NOT_FOUND));
        checkIfGameIsOver(game);
        return gameService.resetGame(game);
    }

    private void checkIfGameIsOver(Game game) {
        if (game.isGameOver()) {
            throw  new ApiException(GAME_IS_OVER, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
