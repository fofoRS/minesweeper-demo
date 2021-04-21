package client

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
)

type WebError struct {
	Code         uint8
	ErrorMessage string
}

func (err WebError) Error() string {
	return err.ErrorMessage
}

type webClient struct {
	baseURL    string
	parameters GameParameters
}

type ResponseErrorBody struct {
	message string
}

const (
	defaultURL string = "https://minesweeper-rod-demo.herokuapp.com"
)

func (web webClient) StartGame() (*Game, *WebError) {
	dataBytes, err := json.Marshal(web.parameters)
	if err != nil {
		return nil, &WebError{ErrorMessage: "Error parsing request into Json format."}
	}
	enpointURL := fmt.Sprintf("%s/api/v1/game", defaultURL)
	response, callErr := http.Post(enpointURL, "application/json", bytes.NewReader(dataBytes))
	if callErr != nil {
		return nil, &WebError{ErrorMessage: err.Error()}
	}
	defer response.Body.Close()

	responseBody := response.Body
	if response.StatusCode != http.StatusCreated {
		errorResponseDecoder := json.NewDecoder(responseBody)
		responseErrorBody := &ResponseErrorBody{}
		decodeError := errorResponseDecoder.Decode(responseErrorBody)
		if decodeError != nil {
			return nil, &WebError{ErrorMessage: decodeError.Error()}
		}
		return nil, &WebError{uint8(response.StatusCode), responseErrorBody.message}
	}

	successResponseDecoder := json.NewDecoder(responseBody)
	game := &Game{}
	decodeError := successResponseDecoder.Decode(game)
	if decodeError != nil {
		return nil, &WebError{ErrorMessage: decodeError.Error()}
	}
	return game, nil
}

func (web webClient) resetGame(id uint64) (*Grid, *WebError) {
	enpointURL := fmt.Sprintf("%s/api/v1/game/reset/%d", defaultURL, id)
	client := &http.Client{}
	req, _ := http.NewRequest("PATCH", enpointURL, nil)
	response, callErr := client.Do(req)
	if callErr != nil {
		return nil, &WebError{ErrorMessage: callErr.Error()}
	}
	defer response.Body.Close()

	responseBody := response.Body
	if response.StatusCode != http.StatusOK {
		errorResponseDecoder := json.NewDecoder(responseBody)
		responseErrorBody := &ResponseErrorBody{}
		decodeError := errorResponseDecoder.Decode(responseErrorBody)
		if decodeError != nil {
			return nil, &WebError{ErrorMessage: decodeError.Error()}
		}
		return nil, &WebError{uint8(response.StatusCode), responseErrorBody.message}
	}

	successResponseDecoder := json.NewDecoder(responseBody)
	grid := &Grid{}
	decodeError := successResponseDecoder.Decode(grid)
	if decodeError != nil {
		return nil, &WebError{ErrorMessage: decodeError.Error()}
	}
	return grid, nil
}
