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
	CellPositions []Position `json:"positions"`
	BombPositions []Position `json:"bombPositions"`
}

type Position struct {
	X, Y uint8
}

type RevealCellResponse struct {
	HitCellABom  bool
	RevealedCell RevealedCell `json:"cellHit"`
	CellsVisited []RevealedCell
}

type RevealedCell struct {
	CellPosition   Position `json:"position"`
	NearBombsCount uint8
	Visited        bool
}

type MinesWeeperClient struct {
	webClient *webClient
}

// Start a new game with the parameter provided when MinesWeeperClient was created
// or with default parameters if any were provided.
func (client *MinesWeeperClient) StartGame() (*Game, *WebError) {
	return client.webClient.StartGame()
}

// Resets the grid of an existing game, and a new grid with the same parameters is create.
// This fuction return a new instance of the type Game that contains a new Grid.
// NOTE: only the create is reset, the game will kep the same id.
// The Game instance contains the positions of the bombs, so the caller can use the attribute
// to perform the checking with the needed to go to the api.
func (client *MinesWeeperClient) ResetGame(game Game) (*Game, *WebError) {
	grid, err := client.webClient.resetGame(game.Id)
	if err != nil {
		return nil, err
	}
	return &Game{game.Id, game.IsGameOver, *grid}, nil
}

// Reveals an specific cell, providing the position of the cell.
// It returns the cell position, if the cell is a bomb and the list of the neighboor cell revelead too.
// As well as the number of adjacents bombs.
func (client *MinesWeeperClient) RevealCell(id uint64, cellPosition Position) (*RevealCellResponse, *WebError) {
	revealedCell, err := client.webClient.revealCell(id, cellPosition.X, cellPosition.Y)
	if err != nil {
		return nil, err
	}
	return revealedCell, nil
}

// Marks a cell as a potential bomb providing the cell position.
func (client *MinesWeeperClient) MarkCell(id uint64, cellPosition Position) *WebError {
	return client.webClient.markCell(id, cellPosition.X, cellPosition.Y)
}

// This function creates a new MinesWeeperClient instance  which is the main entry point component
// to interate with the library and to perform the actions that call the backend api endpoints.
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
