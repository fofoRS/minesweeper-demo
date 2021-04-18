package org.clone.minesweeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.clone.minesweeper.model.web.GridParametersRequestDTO;
import org.clone.minesweeper.model.web.GridResponseDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GridControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    private ObjectMapper objectMapper;


    @Before
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void requestToStartGame_responseWithBadRequest_WhenMoreBombsThanCells() throws Exception {
        GridParametersRequestDTO badRequest = new GridParametersRequestDTO(4,4,17);
        mvc.perform(post("/api/v1/grid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().bytes("Number of bombs greater than total of cells".getBytes()));
    }

    @Test
    public void requestToStartGame_getGridWithRowsAndCellRequest() throws Exception {
        GridParametersRequestDTO badRequest = new GridParametersRequestDTO(4,4,0);
        MvcResult result = mvc.perform(post("/api/v1/grid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isOk()).andReturn();

        GridResponseDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), GridResponseDTO.class);
        assertFalse(response.getPositions().isEmpty());
        assertThat(response.getPositions().size()).isEqualTo(16);
    }

    @Test
    public void requestToStartGame_getGridCreatedWithDefaultParameters() throws Exception {
        MvcResult result = mvc.perform(post("/api/v1/grid")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk()).andReturn();

        GridResponseDTO response = objectMapper
                .readValue(result.getResponse().getContentAsString(), GridResponseDTO.class);
        assertFalse(response.getPositions().isEmpty());
        assertThat(response.getPositions().size()).isEqualTo(25);
    }
}
