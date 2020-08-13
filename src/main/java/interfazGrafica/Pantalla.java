package interfazGrafica;

import interfazGrafica.Eventos.BooleanOptionsEventHandler;
import interfazGrafica.Eventos.MultipleChoiceOptionsEventHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import consumables.Multiplicator;
import modelo.Player;
import modelo.options.CorrectOptionScorer;
import modelo.options.IncorrectOptionScorer;
import modelo.options.Option;
import modelo.questions.BooleanQuestion;
import modelo.questions.MultipleChoiceQuestion;
import modelo.questions.Question;
import modelo.scorers.BooleanScorer;
import modelo.scorers.QuestionScorer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Pantalla extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Boolean Question");

        //----------------------------------------------------------------------
        //--------------------------Player and points labels--------------------
        //----------------------------------------------------------------------
        Player player = new Player();
        player.setText("Bautista:");

        Label playerText = new Label();
        playerText.setTextFill(Color.BLACK);
        playerText.setFont(new Font("Arial", 30));

        Label playerPoints = new Label();
        playerPoints.setTextFill(Color.BLACK);
        playerPoints.setFont(new Font("Arial", 30));

        playerText.setText(player.getText());
        Integer auxPlayerPoints = player.getPoints();
        playerPoints.setText(auxPlayerPoints.toString());

        HBox playerContenedorHorizontal = new HBox(playerText, playerPoints);
        playerContenedorHorizontal.setSpacing(10);

        //----------------------------------------------------------------------
        //--------------------------Question text label-------------------------
        //----------------------------------------------------------------------
        Label questionText = new Label();
        questionText.setTextFill(Color.BLUE);
        questionText.setFont(new Font("Arial", 30));
        questionText.setStyle("-fx-border-color: black;");

        BooleanQuestion question = getBooleanQuesiton();
        fillQuestionText(question, questionText);

        ArrayList<Question> questionArrayList = new ArrayList<Question>();



        //----------------------------------------------------------------------
        //--------------------------Buttons creation----------------------------
        //----------------------------------------------------------------------
        Button[] buttons = getQuestionOptions(question);

        fillWithEvents(buttons, question, playerPoints, player);

        HBox contenedorHorizontal = new HBox();
        fillContainer(contenedorHorizontal, buttons);
        contenedorHorizontal.setSpacing(400);

        //----------------------------------------------------------------------
        //--------------------------Principal Container-------------------------
        //----------------------------------------------------------------------
        VBox contenedorPrincipal = new VBox(playerContenedorHorizontal, questionText, contenedorHorizontal);
        contenedorPrincipal.setSpacing(100);
        contenedorPrincipal.setPadding(new Insets(300));

        Scene escena = new Scene(contenedorPrincipal);
        StackPane boludeo = new StackPane();
        Scene escenaDos = new Scene(boludeo);

        stage.setScene(escena);




        stage.show();
    }

    private void fillWithEvents(Button[] buttons, BooleanQuestion question, Label playerPoints, Player player) {
        int i = 0;
        for(Button aButton : buttons) {
            BooleanOptionsEventHandler booleanOptionsEventHandler = new BooleanOptionsEventHandler(question.getOptions().get(i), playerPoints, player, question);
            aButton.setOnAction(booleanOptionsEventHandler);
            i++;
        }
    }

    private void fillWithEvents(Button[] buttons, MultipleChoiceQuestion question, Label playerPoints, Player player) {
        int i = 0;
        for(Button aButton : buttons) {
            MultipleChoiceOptionsEventHandler multipleChoiceOptionsEventHandler = new MultipleChoiceOptionsEventHandler(question.getOptions().get(i), playerPoints, player, question);
            aButton.setOnAction(multipleChoiceOptionsEventHandler);
            i++;
        }
    }


    private Button[] getQuestionOptions(Question question) {
        String[] answerOptions = question.getAnswerOptions();
        Button[] buttons = new Button[answerOptions.length];
        int i = 0;
        for(String aString : answerOptions){
            buttons[i] = new Button(aString);
            i++;
        }
        return buttons;
    }

    private void fillQuestionText(Question question, Label questionText) {
        questionText.setText(question.getText());
    }

    private void fillContainer(HBox contenedorHorizontal, Button[] buttons) {
        for(Button aButton : buttons){
            contenedorHorizontal.getChildren().add(aButton);
        }
    }

    private BooleanQuestion getBooleanQuesiton() {
        List<Option> options = Arrays.asList(
                new Option("SI", new CorrectOptionScorer()),
                new Option("NO", new IncorrectOptionScorer()));

        QuestionScorer scorer = new BooleanScorer();
        BooleanQuestion question = new BooleanQuestion("Vamos a aprobar algoritmos 3?", options, scorer, new Multiplicator());

        return question;
    }

}
