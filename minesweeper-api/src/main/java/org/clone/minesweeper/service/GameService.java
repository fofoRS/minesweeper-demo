package org.clone.minesweeper.service;

import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.Game;
import org.clone.minesweeper.model.GameParametersDTO;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.web.GameResponseDTO;
import org.clone.minesweeper.model.web.GridResponseDTO;
import org.clone.minesweeper.util.GameStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private final GameStorage gameRepository;
    private final GridService gridService;

    @Autowired
    public GameService(GameStorage gameRepository, GridService gridService) {
        this.gameRepository = gameRepository;
        this.gridService = gridService;
    }

    public GameResponseDTO createGame(GameParametersDTO gameParameters) {
        GridResponseDTO gridResponse = gridService.createNewGrid(gameParameters);
        Game game = new Game(gridResponse.getGrid());
        Game createdGame = gameRepository.save(game);
        return new GameResponseDTO(createdGame.getId(),gridResponse,false);
    }

    public GridResponseDTO resetGame(Long gameId) {
        Game game = getGame(gameId).orElseThrow(() ->
                new ApiException("Game not found for id: " + gameId, HttpStatus.NOT_FOUND));
        Grid grid = game.getGrid();
        GameParametersDTO gridParameters =
                new GameParametersDTO(grid.getRows(),grid.getColumns(), grid.getBombsPositions().size());
        GridResponseDTO gridResponse = gridService.createNewGrid(gridParameters);
        game.setGrid(gridResponse.getGrid());
        return gridResponse;
    }

    public void updateGame(Game game) {
        try {
            gameRepository.update(game);
        } catch (IllegalArgumentException e) {
            throw new ApiException(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Game> getGame(Long id) {
        Game game = gameRepository.getById(id);
        return Optional.ofNullable(game);
    }
}
