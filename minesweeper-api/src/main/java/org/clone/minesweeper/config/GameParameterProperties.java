package org.clone.minesweeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "game.parameters")
public class GameParameterProperties {
    private Integer rows;
    private Integer cells;
    private Integer bombs;

    public Integer getRows() {
        return rows;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    public Integer getCells() {
        return cells;
    }
    public void setCells(Integer cells) {
        this.cells = cells;
    }
    public Integer getBombs() {
        return bombs;
    }
    public void setBombs(Integer bombs) {
        this.bombs = bombs;
    }
}
