package modelo.game;

import exceptions.InvalidJsonRecognizerClassException;
import exceptions.InvalidSizeException;
import modelo.questions.Question;
import utils.QuestionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constantes.Constantes.MAX_PLAYERS;
import static constantes.Constantes.QUESTIONS_FILE_PATH;

public class Game {
    private Player[] players = new Player[MAX_PLAYERS];
    private Integer currentPlayer = 0;
    private List<Round> rounds = new ArrayList<>();
    private Integer currentRound = 0;

    public void init() throws InvalidSizeException, IOException, InvalidJsonRecognizerClassException {
        createPlayers();
        createRounds();
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public Player getNextPlayer() {
        if(currentPlayer == players.length){
            currentPlayer = 0;
            return null;
        }
        Player player = players[currentPlayer];
        currentPlayer++;
        return player;
    }

    public Round getNextRound() {
        setWinner();
        if(currentRound >= rounds.size()){
            currentRound = 0;
            return null;
        }
        Round round = rounds.get(currentRound);
        return round;
    }

    public void setNextRound() {
        currentRound++;
    }

    private void createPlayers() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            this.players[i] = new Player();
        }
        setWinner();
    }

    private void setWinner() {
        if(players[0].getPoints() == players[1].getPoints()){
            players[0].changeStateToDraw();
            players[1].changeStateToDraw();
        }
        else if(players[0].getPoints() > players[1].getPoints()){
            players[0].changeStateToWinner();
            players[1].changeStateToLoser();
        }

        else{
            players[0].changeStateToLoser();
            players[1].changeStateToWinner();
        }

    }

    public Player getWinner() {
        Player winner = null;
        for(Player aPlayer : players){
            if(aPlayer.isTied()){
                return null;
            }
            if(aPlayer.isWinner()){
                winner = aPlayer;
            }
        }
        return winner;
    }

    public Player getLoser() {
        Player loser = null;
        for(Player aPlayer : players){
            if(aPlayer.isTied()){
                return null;
            }
            if(!aPlayer.isWinner()){
                loser = aPlayer;
            }
        }

        return loser;
    }

    public boolean getDraw() {

        return players[0].isTied();
    }

    private void createRounds() throws InvalidSizeException, IOException, InvalidJsonRecognizerClassException {

        List<Question> questions = getQuestionFromFile(QUESTIONS_FILE_PATH);

        for (Question question : questions) {
            this.rounds.add(new Round(players, question));
        }

    }

    private List<Question> getQuestionFromFile(String fileName) throws IOException, InvalidSizeException, InvalidJsonRecognizerClassException {
        QuestionFactory questionFactory = new QuestionFactory();
        String questionsString = getStringFromFile(fileName);

        return questionFactory.unmarshalArrayOfQuestions(questionsString);
    }

    private String getStringFromFile(String fileName) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(reader.readLine());
        reader.close();
        return stringBuilder.toString();

    }

    public Player getFirstPlayer() {
        return players[0];
    }
}
