package client

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
)

type WebError struct {
	code         uint8
	errorMessage string
}

func (err WebError) Error() string {
	return err.errorMessage
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
		return nil, &WebError{errorMessage: "Error parsing request into Json format."}
	}
	enpointURL := fmt.Sprintf("%s/api/v1/game", defaultURL)
	response, callErr := http.Post(enpointURL, "application/json", bytes.NewReader(dataBytes))
	if callErr != nil {
		return nil, &WebError{errorMessage: err.Error()}
	}
	defer response.Body.Close()

	responseBody := response.Body
	if response.StatusCode != http.StatusCreated {
		errorResponseDecoder := json.NewDecoder(responseBody)
		responseErrorBody := &ResponseErrorBody{}
		decodeError := errorResponseDecoder.Decode(responseErrorBody)
		if decodeError != nil {
			return nil, &WebError{errorMessage: decodeError.Error()}
		}
		return nil, &WebError{uint8(response.StatusCode), responseErrorBody.message}
	}

	successResponseDecoder := json.NewDecoder(responseBody)
	game := &Game{}
	decodeError := successResponseDecoder.Decode(game)
	if decodeError != nil {
		return nil, &WebError{errorMessage: decodeError.Error()}
	}
	return game, nil
}
