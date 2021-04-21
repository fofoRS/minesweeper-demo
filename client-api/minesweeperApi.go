package client

type GameParameters struct {
	Rows, Columns, NumberOfBombs uint8
}

type Game struct {
	Id         uint64
	IsGameOver bool `json:"gameOver"`
	Grid       Grid
}

type Grid struct {
	CellPositions []position `json:"positions"`
	BombPositions []position `json:"bombPositions"`
}

type position struct {
	X, Y uint8
}

type MinesWeeperClient struct {
	webClient *webClient
}

func (client *MinesWeeperClient) StartGame() (*Game, *WebError) {
	return client.webClient.StartGame()
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
