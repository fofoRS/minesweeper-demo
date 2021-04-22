package client

import (
	"os"
	"testing"
)

var (
	minesWeeperClient *MinesWeeperClient
)

func TestMain(m *testing.M) {
	minesWeeperClient = NewApiClient(
		"https://minesweeper-rod-demo.herokuapp.com", WithRowsOptions(2), WithColumnsOptions(2), WithBombsOptions(1))
	os.Exit(m.Run())
}

func TestStartGame(t *testing.T) {
	game, err := minesWeeperClient.StartGame()
	if err != nil {
		t.Error("error calling api")
	}
	if (&Game{} == game) {
		t.Error("Game is empty")
	}

	if len(game.Grid.CellPositions) != 4 || len(game.Grid.BombPositions) != 1 {
		t.Errorf("Cell Wanted %d, got: %d and Bombs Wanted %d, got: %d\n",
			4, len(game.Grid.CellPositions), 1, len(game.Grid.BombPositions))
	}
}

func TestRevealedCell(t *testing.T) {
	game, err := minesWeeperClient.StartGame()
	if err != nil {
		t.Error("error calling api")
	}
	if (&Game{} == game) {
		t.Error("Game is empty")
	}

	position := game.Grid.BombPositions[0]
	response, err := minesWeeperClient.RevealCell(game.Id, position)
	if err != nil {
		t.Error("error calling api")
	}

	if position != response.RevealedCell.CellPosition {
		t.Errorf("position wanted: (%d,%d), got (%d,%d)\n",
			position.X, position.Y, response.RevealedCell.CellPosition.X, response.RevealedCell.CellPosition.Y)
	}
}
