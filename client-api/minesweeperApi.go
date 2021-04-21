package client

type MinesweeperApiErr struct {
	ErrorMessage string
}

func (err MinesweeperApiErr) Error() string {
	return err.ErrorMessage
}

type GameParameters struct {
	Rows          uint8 `json:"rows"`
	Columns       uint8 `json:"cells"`
	NumberOfBombs uint8 `json:"numberOfBombs"`
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

func (client *MinesWeeperClient) ResetGame(game Game) (*Game, *WebError) {
	grid, err := client.webClient.resetGame(game.Id)
	if err != nil {
		return nil, err
	}
	return &Game{game.Id, game.IsGameOver, *grid}, nil
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
