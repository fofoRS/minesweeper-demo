package org.clone.minesweeper.controller;

import org.clone.minesweeper.config.GameParameterProperties;
import org.clone.minesweeper.model.GameParametersDTO;
import org.clone.minesweeper.model.web.GameParametersRequestDTO;
import org.clone.minesweeper.model.web.GameResponseDTO;
import org.clone.minesweeper.model.web.GridResponseDTO;
import org.clone.minesweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponseDTO createGame(@Valid @RequestBody GameParametersRequestDTO request) {
        validateRequestArguments(request);
        GameParametersDTO gameParameters = getGameParameters(request,gameDefaultParameters);
        return gameService.createGame(gameParameters);
    }

    @PatchMapping("/reset/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public GridResponseDTO resetGame(@PathVariable Long id) {
        return gameService.resetGame(id);
    }
}
