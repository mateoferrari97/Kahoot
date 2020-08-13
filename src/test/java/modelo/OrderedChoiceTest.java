package modelo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import consumables.ScoreExclusivity;
import exceptions.InvalidJsonRecognizerClassException;
import exceptions.InvalidSizeException;
import modelo.options.CorrectOptionScorer;
import modelo.options.IncorrectOptionScorer;
import modelo.options.Option;
import modelo.questions.OrderedChoiceQuestion;
import modelo.questions.Question;
import modelo.scorers.OrderedScorer;
import modelo.scorers.QuestionScorer;
import exceptions.NoMoreConsumablesException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class OrderedChoiceTest {
    @Test
    public void testOrderedChoiceQuestionIncreasePlayerPointsWhenOptionsAreInTheCorrectOrder() throws InvalidSizeException {
        // Given
        List<Option> options = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        QuestionScorer scorer = new OrderedScorer();
        Question question = new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options, scorer, new ScoreExclusivity());

        Player player = new Player();
        List<Option> playerOptions = options;
        Integer expectedPlayerPoints = 1;

        // When
        question.selectOptions(playerOptions);
        question.score(player);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);
    }

    @Test
    public void testOrderedChoiceQuestionDoesNotIncreasePlayerPointsWhenOptionsAreInTheIncorrectOrder() throws InvalidSizeException {
        // Given
        List<Option> options = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        QuestionScorer scorer = new OrderedScorer();
        Question question = new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options, scorer, new ScoreExclusivity());

        Player player = new Player();
        List<Option> playerOptions = Arrays.asList(
                options.get(0),
                options.get(1),
                options.get(3),
                options.get(2));
        Integer expectedPlayerPoints = 0;

        // When
        question.selectOptions(playerOptions);
        question.score(player);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);
    }

    @Test
    public void testUnmarshalOrderedChoiceQuestionAndTestHowItWorks() throws InvalidJsonRecognizerClassException, InvalidSizeException {

        String jString = "{\"text\": \"ordene correctamente las siguientes opciones\",\"scorer\": \"OrderedScorer\",\"options\": [{\"text\": \"Primero\",\"optionScorer\": true},{\"text\": \"Segundo\",\"optionScorer\": false},{\"text\": \"Tercero\",\"optionScorer\": false},{\"text\": \"Cuarto\",\"optionScorer\": false}]}";
        JsonParser parser = new JsonParser();
        JsonObject jObj = parser.parse(jString).getAsJsonObject();

        Question question = OrderedChoiceQuestion.unmarshal(jObj);

        List<Option> options = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        Player player = new Player();
        List<Option> playerOptions = Arrays.asList(
                options.get(0),
                options.get(1),
                options.get(3),
                options.get(2));
        Integer expectedPlayerPoints = 0;

        // When
        question.selectOptions(playerOptions);
        question.score(player);

        // Then
        Assert.assertEquals(player.getPoints(), expectedPlayerPoints);

    }

    public void testGroupChoiceDoublePointsWhenScoreExclusivityActivatedAndOnePlayerAnswerIncorrectly() throws InvalidSizeException, NoMoreConsumablesException {
        // Given
        ScoreExclusivity scoreExclusivity = new ScoreExclusivity();
        List<Option> options = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        List<Option> options2 = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        QuestionScorer scorer = new OrderedScorer();
        Question question1= new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options, scorer, scoreExclusivity);
        Question question2 = new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options2, scorer, scoreExclusivity);

        List<Option> player1Options = Arrays.asList(
                options.get(0),
                options.get(1),
                options.get(3),
                options.get(2));

        List<Option> player2Options = Arrays.asList(
                options2.get(0),
                options2.get(1),
                options2.get(2),
                options2.get(3));

        Player player1 = new Player();
        Player player2 = new Player();
        Integer expectedPlayer1Points = 0;
        Integer expectedPlayer2Points = 2;

        // When
        player1.activateConsumable(scoreExclusivity);
        question1.selectOptions(player1Options);
        question2.selectOptions(player2Options);
        question1.score(player1);
        question2.score(player2);

        // Then
        Assert.assertEquals(player1.getPoints(), expectedPlayer1Points);
        Assert.assertEquals(player2.getPoints(), expectedPlayer2Points);
    }

    @Test
    public void testGroupChoiceQuadruplePointsWhenScoreExclusivityActivatedAndOnePlayerAnswerIncorrectly() throws InvalidSizeException, NoMoreConsumablesException {
        // Given
        ScoreExclusivity scoreExclusivity = new ScoreExclusivity();

        List<Option> options = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        List<Option> options2 = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        QuestionScorer scorer = new OrderedScorer();
        Question question1= new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options, scorer, scoreExclusivity);
        Question question2 = new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options2, scorer, scoreExclusivity);

        List<Option> player1Options = Arrays.asList(
                options.get(0),
                options.get(1),
                options.get(3),
                options.get(2));

        List<Option> player2Options = Arrays.asList(
                options2.get(0),
                options2.get(1),
                options2.get(2),
                options2.get(3));

        Player player1 = new Player();
        Player player2 = new Player();
        Integer expectedPlayer1Points = 0;
        Integer expectedPlayer2Points = 4;

        // When
        player1.activateConsumable(scoreExclusivity);
        player2.activateConsumable(scoreExclusivity);
        question1.selectOptions(player1Options);
        question2.selectOptions(player2Options);
        question1.score(player1);
        question2.score(player2);

        // Then
        Assert.assertEquals(player1.getPoints(), expectedPlayer1Points);
        Assert.assertEquals(player2.getPoints(), expectedPlayer2Points);
    }

    @Test
    public void testGroupChoiceDontModifyPointsWhenScoreExclusivityActivatedNoIncorrectAnswers() throws InvalidSizeException, NoMoreConsumablesException {
        // Given
        ScoreExclusivity scoreExclusivity = new ScoreExclusivity();
        List<Option> options = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        List<Option> options2 = Arrays.asList(
                new Option("Primero", new CorrectOptionScorer()),
                new Option("Segundo", new IncorrectOptionScorer()),
                new Option("Tercero", new IncorrectOptionScorer()),
                new Option("Cuarto", new IncorrectOptionScorer()));

        QuestionScorer scorer = new OrderedScorer();
        Question question1= new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options, scorer, scoreExclusivity);
        Question question2 = new OrderedChoiceQuestion("ordene correctamente las siguientes opciones", options2, scorer, scoreExclusivity);

        List<Option> player1Options = Arrays.asList(
                options.get(0),
                options.get(1),
                options.get(2),
                options.get(3));

        List<Option> player2Options = Arrays.asList(
                options2.get(0),
                options2.get(1),
                options2.get(2),
                options2.get(3));

        Player player1 = new Player();
        Player player2 = new Player();
        Integer expectedPlayer1Points = 0;
        Integer expectedPlayer2Points = 0;

        // When
        player1.activateConsumable(scoreExclusivity);
        question1.selectOptions(player1Options);
        question2.selectOptions(player2Options);
        question1.score(player1);
        question2.score(player2);

        // Then
        Assert.assertEquals(player1.getPoints(), expectedPlayer1Points);
        Assert.assertEquals(player2.getPoints(), expectedPlayer2Points);
    }
}
