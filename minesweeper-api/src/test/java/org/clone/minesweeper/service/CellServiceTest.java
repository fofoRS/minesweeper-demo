package org.clone.minesweeper.service;

import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Game;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.web.CellDTO;
import org.clone.minesweeper.model.web.CellHitResponseDTO;
import org.clone.minesweeper.util.GameStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CellServiceTest {

    private CellService cellService;
    private GameStorage gameStorage;
    @Mock
    private GameService gameService;

    @Before
    public void init() {
        cellService = new CellService(gameService);
        gameStorage = new GameStorage();
        gameStorage.init();
    }

    public void hitCell_returnsHitABoomTrue_WhenCellIsBomb () {
        Cell rowZeroCellZero = new Cell(new Cell.Position(0,0),false,List.of());
        Cell rowZeroCellOne = new Cell(new Cell.Position(0,1),false,List.of());
        Cell rowOneCellZero = new Cell(new Cell.Position(1,0),true,List.of());
        Cell rowOneCellOne = new Cell(new Cell.Position(1,1),false,List.of());
        Cell [][]grid =  {{rowZeroCellZero,rowZeroCellOne},{rowOneCellZero,rowOneCellOne}};

        Grid gridInstance = new Grid(grid,List.of(new Cell.Position(1,0)),2,2);
        Game game = new Game(gridInstance);
        gameStorage.save(game);

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);

        CellHitResponseDTO response = cellService.reveal(new Cell.Position(1,0),game);

        assertThat(response.getCellHit())
                .isEqualTo(new CellDTO(new Cell.Position(1,0),true,0));
        assertTrue(response.isHitCellABom());
        verify(gameService,times(1))
                .updateGame(gameArgumentCaptor.capture());
        Game gameArgCaptured = gameArgumentCaptor.getValue();
        assertTrue(gameArgCaptured.isGameOver());
    }

    @Test
    public void hitCell_returnsCorrectNumberOfAdjacentsBombs() {
        Cell rowZeroCellZero = new Cell(new Cell.Position(0,0),false,List.of());
        Cell rowZeroCellOne = new Cell(new Cell.Position(0,1),false,List.of());
        Cell rowZeroCellTwo = new Cell(new Cell.Position(0,2),false,List.of());
        Cell rowOneCellZero = new Cell(new Cell.Position(1,0),false,List.of());
        Cell rowOneCellOne = new Cell(new Cell.Position(1,1),true,List.of());
        Cell rowOneCellTwo = new Cell(new Cell.Position(1,2),true,List.of());
        Cell rowTwoCellZero = new Cell(new Cell.Position(2,0),false,List.of());
        Cell rowTwoCellOne = new Cell(new Cell.Position(2,1),false,List.of());
        Cell rowTwoCellTwo = new Cell(new Cell.Position(2,2),false,List.of());

        Cell [][]grid = {
                {rowZeroCellZero,rowZeroCellOne,rowZeroCellTwo},
                {rowOneCellZero,rowOneCellOne,rowOneCellTwo},
                {rowTwoCellZero,rowTwoCellOne,rowTwoCellTwo}};

        rowZeroCellTwo.addAdjacents(List.of(
                new Cell.Position(0,1),
                new Cell.Position(1,1),
                new Cell.Position(1,2)));

        Grid gridInstance = new Grid(
                grid,List.of(new Cell.Position(1,1), new Cell.Position(1,2)),2,2);
        Game game = new Game(gridInstance);

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        gameStorage.save(game);

        CellHitResponseDTO response = cellService.reveal(new Cell.Position(0,2),game);
        assertThat(response.getCellHit())
                .isEqualTo(new CellDTO(new Cell.Position(0,2),true,2));
        verify(gameService,times(1))
                .updateGame(gameArgumentCaptor.capture());
        Game gameArgCaptured = gameArgumentCaptor.getValue();
        assertFalse(gameArgCaptured.isGameOver());
    }


}
