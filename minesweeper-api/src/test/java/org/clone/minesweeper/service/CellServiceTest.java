package org.clone.minesweeper.service;

import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.web.CellDTO;
import org.clone.minesweeper.model.web.CellHitResponseDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
public class CellServiceTest {

    private CellService cellService;

    @Before
    public void init() {
        cellService = new CellService();
    }

    public void hitCell_returnsHitABoomTrue_WhenCellIsBomb () {
        Cell rowZeroCellZero = new Cell(new Cell.Position(0,0),false,List.of());
        Cell rowZeroCellOne = new Cell(new Cell.Position(0,1),false,List.of());
        Cell rowOneCellZero = new Cell(new Cell.Position(1,0),true,List.of());
        Cell rowOneCellOne = new Cell(new Cell.Position(1,1),false,List.of());
        Cell [][]grid =  {{rowZeroCellZero,rowZeroCellOne},{rowOneCellZero,rowOneCellOne}};
        Grid gridInstance = Grid.createInstance(grid, List.of(new Cell.Position(1,0)));

        CellHitResponseDTO response = cellService.reveal(rowOneCellZero,gridInstance);
        assertThat(response.getCellHit())
                .isEqualTo(new CellDTO(new Cell.Position(1,0),true,0));
        assertTrue(response.isHitCellABom());
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

        Grid gridInstance = Grid.createInstance(grid, List.of(new Cell.Position(1,1), new Cell.Position(1,2)));

        CellHitResponseDTO response = cellService.reveal(rowZeroCellTwo,gridInstance);
        assertThat(response.getCellHit())
                .isEqualTo(new CellDTO(new Cell.Position(0,2),true,2));
    }


}
