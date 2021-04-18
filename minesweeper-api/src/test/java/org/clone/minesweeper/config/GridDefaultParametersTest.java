package org.clone.minesweeper.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
public class GridDefaultParametersTest {

    @Autowired
    private GameParameterProperties properties;

    @Test
    public void validateDefaultGameParameters() {
        assertThat(properties.getRows());
        assertThat(properties.getCells());
        assertThat(properties.getBombs());
    }
}
