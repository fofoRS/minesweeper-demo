package client

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
	"net/url"
	"strconv"
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
	var enpointURL string
	if len(web.baseURL) == 0 {
		enpointURL = fmt.Sprintf("%s/api/v1/game", defaultURL)
	} else {
		enpointURL = fmt.Sprintf("%s/api/v1/game", web.baseURL)
	}
	response, callErr := http.Post(enpointURL, "application/json", bytes.NewReader(dataBytes))
	if callErr != nil {
		return nil, &WebError{ErrorMessage: err.Error()}
	}
	defer response.Body.Close()

	responseBody := response.Body
	statusCodeEvaluator := func(statusCode int) bool {
		return statusCode == http.StatusCreated
	}
	checkError := checkResponseStatus(*response, statusCodeEvaluator)
	if checkError != nil {
		return nil, checkError
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
	var enpointURL string
	if len(web.baseURL) == 0 {
		enpointURL = fmt.Sprintf("%s/api/v1/game/reset/%d", defaultURL, id)
	} else {
		enpointURL = fmt.Sprintf("%s/api/v1/game/reset/%d", web.baseURL, id)
	}
	client := &http.Client{}
	req, _ := http.NewRequest("PATCH", enpointURL, nil)
	response, callErr := client.Do(req)
	if callErr != nil {
		return nil, &WebError{ErrorMessage: callErr.Error()}
	}
	defer response.Body.Close()

	responseBody := response.Body
	statusCodeEvaluator := func(statusCode int) bool {
		return statusCode == http.StatusOK
	}
	checkError := checkResponseStatus(*response, statusCodeEvaluator)
	if checkError != nil {
		return nil, checkError
	}
	successResponseDecoder := json.NewDecoder(responseBody)
	grid := &Grid{}
	decodeError := successResponseDecoder.Decode(grid)
	if decodeError != nil {
		return nil, &WebError{ErrorMessage: decodeError.Error()}
	}
	return grid, nil
}

func (web webClient) revealCell(id uint64, positionX, positionY uint8) (*RevealCellResponse, *WebError) {
	var baseURL string
	if len(web.baseURL) == 0 {
		baseURL = defaultURL
	} else {
		baseURL = web.baseURL
	}
	enpointURL, err := url.Parse(fmt.Sprintf("%s/api/v1/game/%d/cells/hit", baseURL, id))
	if err != nil {
		return nil, &WebError{ErrorMessage: err.Error()}
	}
	params := url.Values{}
	params.Add("positionX", strconv.FormatUint(uint64(positionX), 10))
	params.Add("positionY", strconv.FormatUint(uint64(positionY), 10))
	enpointURL.RawQuery = params.Encode()
	response, callErr := http.Post(enpointURL.String(), "application/json", nil)
	if callErr != nil {
		return nil, &WebError{ErrorMessage: callErr.Error()}
	}
	defer response.Body.Close()
	statusCodeEvaluator := func(statusCode int) bool {
		return statusCode == http.StatusOK
	}
	responseBody := response.Body
	checkError := checkResponseStatus(*response, statusCodeEvaluator)
	if checkError != nil {
		return nil, checkError
	}

	successResponseDecoder := json.NewDecoder(responseBody)
	revealedResponse := &RevealCellResponse{}
	decodeError := successResponseDecoder.Decode(revealedResponse)
	if decodeError != nil {
		return nil, &WebError{ErrorMessage: decodeError.Error()}
	}
	return revealedResponse, nil
}

func (web webClient) markCell(id uint64, positionX, positionY uint8) *WebError {
	var baseURL string
	if len(web.baseURL) == 0 {
		baseURL = defaultURL
	} else {
		baseURL = web.baseURL
	}
	enpointURL, err := url.Parse(fmt.Sprintf("%s/api/v1/game/%d/cells/mark", baseURL, id))
	if err != nil {
		return &WebError{ErrorMessage: err.Error()}
	}
	params := url.Values{}
	params.Add("positionX", strconv.FormatUint(uint64(positionX), 10))
	params.Add("positionY", strconv.FormatUint(uint64(positionY), 10))
	enpointURL.RawQuery = params.Encode()
	response, callErr := http.Post(enpointURL.String(), "application/json", nil)
	if callErr != nil {
		return &WebError{ErrorMessage: callErr.Error()}
	}
	defer response.Body.Close()
	statusCodeEvaluator := func(statusCode int) bool {
		return statusCode == http.StatusNoContent
	}
	checkError := checkResponseStatus(*response, statusCodeEvaluator)
	if checkError != nil {
		return checkError
	}
	return nil
}

func checkResponseStatus(response http.Response, evaluator func(int) bool) *WebError {
	if evaluator(response.StatusCode) {
		return nil
	}
	responseBody := response.Body
	errorResponseDecoder := json.NewDecoder(responseBody)
	responseErrorBody := &ResponseErrorBody{}
	decodeError := errorResponseDecoder.Decode(responseErrorBody)
	if decodeError != nil {
		return &WebError{ErrorMessage: decodeError.Error()}
	}
	return &WebError{uint8(response.StatusCode), responseErrorBody.message}

}
