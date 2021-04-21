package client

type GameParameters struct {
	Rows, Columns, NumberOfBombs uint8
}

type Game struct {
	Id         uint64
	isGameOver bool
	grid       grid
}

type grid struct {
	cellPositions [][]position
	bombPositions [][]position
}

type position struct {
	x, y uint8
}

type MinesWeeperClient struct {
	webClient *webClient
}

func NewApiClient(baseURL string, options ...func(*GameParameters)) *MinesWeeperClient {
	gameParameters := &GameParameters{}
	for _, option := range options {
		option(gameParameters)
	}
	return &MinesWeeperClient{&webClient{baseURL, *gameParameters}}
}

func WithRowsOptions(rows uint8) func(*GameParameters) {
	return func(parameters *GameParameters) {
		parameters.Rows = rows
	}
}

func WithColumnsOptions(columns uint8) func(*GameParameters) {
	return func(parameters *GameParameters) {
		parameters.Columns = columns
	}
}

func WithBombsOptions(numberOfbombs uint8) func(*GameParameters) {
	return func(parameters *GameParameters) {
		parameters.NumberOfBombs = numberOfbombs
	}
}
