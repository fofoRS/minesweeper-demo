package org.clone.minesweeper.exception;

public class ApiExceptionMessages {
    public static final String GAME_IS_OVER = "Game is over, not more action allowed.";
    public static final String NO_CELLS_AVAILABLE_FOR_BOMBS =
            "Number of bombs greater than total of cells";
    public static final String GAME_NOT_FOUND_MSG_TEMPLATE = "Game not found for id: %s";
    public static final String ROW_OUT_OF_BOUNDARY = "Row Position is out of boundary";
    public static final String COLUMN_OUT_OF_BOUNDARY = "Column Position is out of boundary.";
}
