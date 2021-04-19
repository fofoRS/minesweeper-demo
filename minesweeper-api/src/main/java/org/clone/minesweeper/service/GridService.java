package org.clone.minesweeper.service;

import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.Game;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.model.GameParametersDTO;
import org.clone.minesweeper.model.web.GridResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class GridService {

    /**
     * Creates the grid for the game, adding the cells and distributes the bombs randomly.
     * @return
     */
    public GridResponseDTO createNewGrid(GameParametersDTO gameParameters) {
        int rows = gameParameters.getRows();
        int columns = gameParameters.getCells();
        int numberOfBombs = gameParameters.getNumberOfBombs();

        Random random = new Random(Instant.now().toEpochMilli());
        IntStream randomRowsPositionForBombs = new Random().ints(
                numberOfBombs,0,rows);

        Map<Cell.Position, List<Cell.Position>> mapBombPositions = randomRowsPositionForBombs.mapToObj(row -> {
            int randomCell = random.nextInt(columns);
            return new Cell.Position(row,randomCell);
        }).collect(Collectors.groupingBy(position -> position));

        Cell [][]grid = new Cell[rows][columns];
        List<Cell.Position> cellPositions = new ArrayList<>();
        List<Cell.Position> bombPositions = new ArrayList<>();

        for(int x = 0; x < rows; x++) {
            for (int j = 0; j < columns; j++) {
                Cell.Position cellPosition = new Cell.Position(x,j);
                boolean isBomb = false;
                if(mapBombPositions.containsKey(cellPosition)) {
                    isBomb = true;
                    bombPositions.add(cellPosition);
                }
                List<Cell.Position> adjacents = addAdjacents(columns, rows, x, j);
                Cell cellObject = new Cell(cellPosition,isBomb,adjacents);
                grid[x][j] = cellObject;
                cellPositions.add(cellPosition);
            }
        }
        Grid gridInstance = new Grid(grid,bombPositions, rows,columns);
        return new GridResponseDTO(cellPositions,bombPositions,gridInstance);
    }

    private List<Cell.Position> addAdjacents(int cells, int rows, int row, int cell) {
        var adjacentPositions = new ArrayList<Cell.Position>();
        if(row > 0) {
            adjacentPositions.add(new Cell.Position(row - 1, cell));
            if(cell > 0) {
                adjacentPositions.add(new Cell.Position(row -1, cell - 1));
            }
            if(cell < cells - 1) {
                adjacentPositions.add(new Cell.Position(row - 1, cell + 1));
            }
        }
        if(row < rows - 1) {
            adjacentPositions.add(new Cell.Position(row + 1, cell));
            if(cell > 0) {
                adjacentPositions.add(new Cell.Position(row + 1, cell -1));
            }
            if(cell < cells - 1) {
                adjacentPositions.add(new Cell.Position(row + 1, cell + 1));
            }
        }
        if(cell > 0) {
            adjacentPositions.add(new Cell.Position(row, cell - 1));
        }
        if(cell < cells - 1) {
            adjacentPositions.add(new Cell.Position(row, cell + 1));
        }
        return adjacentPositions;
    }
}
