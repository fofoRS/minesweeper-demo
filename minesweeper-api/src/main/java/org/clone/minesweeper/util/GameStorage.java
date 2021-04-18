package org.clone.minesweeper.util;

import org.clone.minesweeper.model.Game;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Hashtable;
import java.util.Map;

@Component
public class GameStorage {
    private static Map<Long, Game> gamesTable;

    @PostConstruct
    public void init() {
        gamesTable = new Hashtable<>();
    }

    public Game save(Game game) {
        if(game == null) {
            throw new IllegalArgumentException("Game provided is null");
        }
        int size = gamesTable.size();
        Long newId = size + 1L;
        if(gamesTable.containsKey(newId)) {
            throw new IllegalStateException("A Game already existed for id: " + newId);
        }
        game.setId(newId);
        gamesTable.put(newId,game);
        return game;
    }

    public Game update(Game game) {
        Long id = game.getId();
        if(id == null || id == 0L || !gamesTable.containsKey(id)) {
            throw new IllegalArgumentException("Id provided not found: " + id);
        }
        gamesTable.replace(id,game);
        return game;
    }

}
