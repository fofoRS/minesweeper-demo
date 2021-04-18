package org.clone.minesweeper.controller;

import org.clone.minesweeper.config.GameParameterProperties;
import org.clone.minesweeper.exception.ApiException;
import org.clone.minesweeper.model.web.GridParametersRequestDTO;
import org.clone.minesweeper.service.GridService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class GridControllerUnitTest {

    @Mock
    private GameParameterProperties gameDefaultParameters;
    @Mock
    private GridService gridService;
    @InjectMocks
    private GridController controller;

    @Test(expected = ApiException.class)
    public void requestToStartGame_throwsException_whenMoreBombsThanCells() {
        GridParametersRequestDTO badRequest = new GridParametersRequestDTO(4,4,17);
        controller.buildGrid(badRequest);
    }
}
