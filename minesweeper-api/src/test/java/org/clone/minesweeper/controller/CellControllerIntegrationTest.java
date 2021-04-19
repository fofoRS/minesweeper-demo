package org.clone.minesweeper.controller;

import org.clone.minesweeper.model.Cell;
import org.clone.minesweeper.model.GameParametersDTO;
import org.clone.minesweeper.model.Grid;
import org.clone.minesweeper.service.CellService;
import org.clone.minesweeper.service.GridService;
import org.clone.minesweeper.util.GameStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CellControllerIntegrationTest {

    @Autowired
    private CellService cellService;

    @Autowired
    private GridService gridService;

    @Autowired
    private GameStorage gameStorage;

    @Autowired
    private MockMvc mvc;

    @Test
    public void hitCell_returnsInternalServerErrorStatus_whenGridInstanceNotFound() throws Exception {
        mvc.perform(post("/api/v1/cells/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .param("positionX", "1")
                .param("positionY", "2"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void hitCell_returnsBadRequest_whenPositionGraterThanGridBoundaries() throws Exception {
        gridService.createNewGrid(new GameParametersDTO(7,4,10));
        mvc.perform(post("/api/v1/cells/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .param("positionX", "1")
                .param("positionY", "5"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Column Position is out of boundary."));
    }

    @Test
    public void mark_returnsInternalServerErrorStatus_whenGridInstanceNotFound() throws Exception {
        mvc.perform(post("/api/v1/cells/mark")
                .contentType(MediaType.APPLICATION_JSON)
                .param("positionX", "1")
                .param("positionY", "2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void mark_returnsBadRequest_whenPositionGraterThanGridBoundaries() throws Exception {
        gridService.createNewGrid(new GameParametersDTO(7,4,10));
        mvc.perform(post("/api/v1/cells/mark")
                .contentType(MediaType.APPLICATION_JSON)
                .param("positionX", "8")
                .param("positionY", "3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Row Position is out of boundary."));
    }

    @Test
    public void mark_returnsNotContent() throws Exception {
        gridService.createNewGrid(new GameParametersDTO(7,7,10));
        mvc.perform(post("/api/v1/cells/mark")
                .contentType(MediaType.APPLICATION_JSON)
                .param("positionX", "6")
                .param("positionY", "3"))
                .andExpect(status().isNoContent());
    }
}
